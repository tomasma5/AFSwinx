package cz.cvut.fel.matyapav.afandroid.builders;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.LinkAddress;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import cz.cvut.fel.matyapav.afandroid.components.parts.LayoutProperties;
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
            formView.addView(buildFields(classDef, form, new LinearLayout(activity), 0, false));
        } catch (JSONException e) {
            e.printStackTrace();
            formView = (LinearLayout) buildError("Cannot build form "+e.getMessage());
        }
        form.setView(formView);
        return form;
    }

    private View buildFields(ClassDefinition classDef, AFForm form,  LinearLayout formView, int numberOfInnerClasses, boolean parsingInnerClass ){
        if(parsingInnerClass){
            numberOfInnerClasses = 0;
        }
        TableLayout fieldsView = null;
        if(classDef != null) {
            form.setName(classDef.getClassName());
            fieldsView = (TableLayout) buildLayout(classDef, activity);
            InputFieldBuilder builder = new InputFieldBuilder();
            for (FieldInfo field : classDef.getFields()) {
                if(field.isInnerClass()){
                    fieldsView.addView(buildFields(classDef.getInnerClasses().get(numberOfInnerClasses), form, formView, numberOfInnerClasses++, true));
                }else {
                    AFField affield = builder.buildField(field, activity);
                    if (affield != null) {
                        form.addField(affield);
                        System.err.println(affield.toString());
                        fieldsView.addView(affield.getView());
                    }
                }
            }
        }
        System.err.println("NUMBER OF ELEMENTS IN FORM "+form.getFields().size());
        return fieldsView;
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