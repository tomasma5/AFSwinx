package cz.cvut.fel.matyapav.afandroid.builders;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.builders.tasks.GetFormDefinitionTask;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.AFForm;
import cz.cvut.fel.matyapav.afandroid.components.parts.ClassDefinition;
import cz.cvut.fel.matyapav.afandroid.components.parts.FieldInfo;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutDefinitions;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;
import cz.cvut.fel.matyapav.afandroid.parsers.JSONDefinitionParser;
import cz.cvut.fel.matyapav.afandroid.parsers.JSONParser;
import cz.cvut.fel.matyapav.afandroid.utils.Constants;

/**
 * Builds for from class definition
 * Created by Pavel on 26.12.2015.
 */
public class FormBuilder {

    private Activity activity;

    public FormBuilder(Activity activity) {
        this.activity = activity;
    }

    public AFForm createForm(final String url){
        GetFormDefinitionTask task = new GetFormDefinitionTask(activity);
        try {
            String response = task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url).get(); //make it synchronous to return form
            if(response != null) {
                AFForm form = buildForm(response);
                AFAndroid.getInstance().addCreatedComponent(form.getName(), form);
                return form;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AFForm buildForm(String response){
        AFForm form = new AFForm();
        LinearLayout formView = new LinearLayout(activity);
        try {
            JSONParser parser = new JSONDefinitionParser();
            JSONObject jsonObj = new JSONObject(response).getJSONObject(Constants.CLASS_INFO);
            ClassDefinition classDef = parser.parse(jsonObj);
            prepareForm(classDef, form, 0, false);
            formView.addView(buildFormView(form));
        } catch (JSONException e) {
            e.printStackTrace();
            formView = (LinearLayout) buildError("Cannot build form "+e.getMessage());
        }
        form.setView(formView);
        return form;
    }

    private View buildFormView(AFForm form) {
        //TODO zobecnit
        LinearLayout formView = new LinearLayout(activity);
        formView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if(form.getLayoutOrientation().equals(LayoutOrientation.AXISX)){ //AXIS X
            formView.setOrientation(LinearLayout.VERTICAL);
        }else { //AXIS Y
            formView.setOrientation(LinearLayout.HORIZONTAL);
        }

        //ONE COLUMN LAYOUT
        if(form.getLayoutDefinitions().equals(LayoutDefinitions.ONECOLUMNLAYOUT)){
            for (AFField field : form.getFields()) {
                formView.addView(field.getView());
            }
        //TWO COLUMNS LAYOUT
        }else if(form.getLayoutDefinitions().equals(LayoutDefinitions.TWOCOLUMNSLAYOUT)){
            int coupleOrientation;
            if(form.getLayoutOrientation().equals(LayoutOrientation.AXISX)){ //AXIS X
                coupleOrientation = LinearLayout.HORIZONTAL;
            }else{ //AXIS Y
                coupleOrientation = LinearLayout.VERTICAL;
            }

            int i = 0;
            LinearLayout couple = null;
            for (AFField field : form.getFields()) {
                if (i % 2 == 0) {
                    if (couple != null) {
                        //add couple
                        formView.addView(couple);
                    }
                    couple = new LinearLayout(activity);
                    couple.setOrientation(coupleOrientation);
                    couple.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                }

                View fieldView = field.getView();
                if(form.getFields().size() % 2 != 0 && i == form.getFields().size()-1){
                    //if number of elements are odd last element will occupy space alone
                    fieldView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    formView.addView(fieldView);
                }else{
                    fieldView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.5f)); //occupies half space
                    couple.addView(fieldView);
                }
                i++;
            }
        }

        return formView;
    }

    private void prepareForm(ClassDefinition classDef, AFForm form, int numberOfInnerClasses, boolean parsingInnerClass){
        if(parsingInnerClass){
            numberOfInnerClasses = 0;
        }
        if(classDef != null) {
            if(!parsingInnerClass) { //set following properties only once at the beginning
                form.setName(classDef.getClassName());
                form.setLayoutDefinitions(classDef.getLayout().getLayoutDefinition());
                form.setLayoutOrientation(classDef.getLayout().getLayoutOrientation());
            }
            //fieldsView = (TableLayout) buildLayout(classDef, activity);
            InputFieldBuilder builder = new InputFieldBuilder();
            for (FieldInfo field : classDef.getFieldInfos()) {
                if(field.isInnerClass()){
                   prepareForm(classDef.getInnerClasses().get(numberOfInnerClasses), form, numberOfInnerClasses++, true);
                }else {
                    AFField affield = builder.prepareField(field, activity);
                    if (affield != null) {
                        form.addField(affield);
                    }
                }
            }
        }
        System.err.println("NUMBER OF ELEMENTS IN FORM "+form.getFields().size());
    }

    private View buildError(String errorMsg) {
        LinearLayout err = new LinearLayout(activity);
        err.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        TextView msg = new TextView(activity);
        msg.setText(errorMsg);
        err.addView(msg);

        return err;
    }

    private View buildLayout(ClassDefinition classDefinition, Context context){
        TableLayout form = new TableLayout(context);
        form.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //TODO onecolumn twocolumn properties
        //TODO axisX axisY properties

        return form;

    }

    public Button buildSubmitButton(String text, AFForm form){
        Button btn = new Button(activity);
        btn.setText(text);
        btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return btn;
    }


}