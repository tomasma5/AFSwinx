package cz.cvut.fel.matyapav.afandroid.builders;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.FieldBuilderFactory;
import cz.cvut.fel.matyapav.afandroid.components.AFComponent;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedWidgets;
import cz.cvut.fel.matyapav.afandroid.rest.RequestTask;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.AFForm;
import cz.cvut.fel.matyapav.afandroid.components.parts.ClassDefinition;
import cz.cvut.fel.matyapav.afandroid.components.parts.FieldInfo;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutDefinitions;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;
import cz.cvut.fel.matyapav.afandroid.parsers.JSONDefinitionParser;
import cz.cvut.fel.matyapav.afandroid.parsers.JSONParser;
import cz.cvut.fel.matyapav.afandroid.utils.Constants;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * Builds for from class definition
 * Created by Pavel on 26.12.2015.
 */
public class FormBuilder extends AFComponentBuilder<FormBuilder>{

    @Override
    public AFForm createComponent() throws Exception {
        initializeConnections();
        RequestTask task = new RequestTask(getActivity(), modelConnection.getHttpMethod(), modelConnection.getContentType(),
                modelConnection.getSecurity(), null, Utils.getConnectionEndPoint(modelConnection));

        Object modelResponse = task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get(); //make it synchronous to return form
        if(modelResponse instanceof Exception){
            throw (Exception) modelResponse;
        }

        //create form from response
        AFForm form = buildForm((String) modelResponse);
        //fill it with data (if there are some)
        if(dataConnection != null){
            RequestTask getData = new RequestTask(getActivity(), dataConnection.getHttpMethod(), dataConnection.getContentType(),
                    dataConnection.getSecurity(), null, Utils.getConnectionEndPoint(dataConnection));
            Object data = getData.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
            if(data instanceof Exception){
                throw (Exception) data;
            }
            insertData((String) data, form, new StringBuilder());
        }

        AFAndroid.getInstance().addCreatedComponent(componentKeyName, form);
        return form;
    }

    @Override
    protected void insertData(String dataResponse, AFComponent form, StringBuilder road){
        try {
            JSONObject jsonObject = new JSONObject(dataResponse);
            Iterator<String> keys = jsonObject.keys();
            while(keys.hasNext()){
                String key = keys.next();
                if(jsonObject.get(key) instanceof JSONObject){
                    String roadBackup = road.toString();
                    road.append(key);
                    road.append(".");
                    insertData(jsonObject.get(key).toString(), form, road); //parse class types
                    road = new StringBuilder(roadBackup.toString());
                }else {
                    System.err.println("ROAD+KEY" + (road + key));
                    AFField field = ((AFForm) form).getFieldById(road + key);
                    System.err.println("FIELD" + field);
                    if (field != null) {
                        setFieldValue(field, jsonObject.get(key));
                    }
                }

            }
        } catch (JSONException e) {
            System.err.println("CANNOT PARSE DATA");
            e.printStackTrace();
        }
    }

    private void setFieldValue(AFField field, Object val){
        FieldBuilderFactory.getInstance().getFieldBuilder(field.getFieldInfo()).setData(field, val);
    }

    private AFForm buildForm(String response){
        AFForm form = new AFForm(getActivity(), modelConnection, dataConnection, sendConnection);
        LinearLayout formView = new LinearLayout(getActivity());
        try {
            JSONParser parser = new JSONDefinitionParser();
            JSONObject jsonObj = new JSONObject(response).getJSONObject(Constants.CLASS_INFO);
            ClassDefinition classDef = parser.parse(jsonObj);
            prepareForm(classDef, form, 0, false, new StringBuilder());
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
        LinearLayout formView = new LinearLayout(getActivity());
        formView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if(form.getLayoutOrientation().equals(LayoutOrientation.AXISX)){ //AXIS X
            formView.setOrientation(LinearLayout.VERTICAL);
        }else { //AXIS Y
            formView.setOrientation(LinearLayout.HORIZONTAL);
        }

        //ONE COLUMN LAYOUT
        if(form.getLayoutDefinitions().equals(LayoutDefinitions.ONECOLUMNLAYOUT)){
            for (AFField field : form.getFields()) {
                formView.addView(field.getCompleteView());
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
                    couple = new LinearLayout(getActivity());
                    couple.setOrientation(coupleOrientation);
                    couple.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                }

                View fieldView = field.getCompleteView();
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

    private void prepareForm(ClassDefinition classDef, AFForm form, int numberOfInnerClasses, boolean parsingInnerClass, StringBuilder road){
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
                    String roadBackup = road.toString();
                    road.append(classDef.getInnerClasses().get(numberOfInnerClasses).getClassName());
                    road.append(".");
                    prepareForm(classDef.getInnerClasses().get(numberOfInnerClasses), form, numberOfInnerClasses++, true, road);
                    road = new StringBuilder(roadBackup);
                }else {
                    AFField affield = builder.prepareField(field, road, getActivity());
                    if (affield != null) {
                        form.addField(affield);
                    }
                }
            }
        }
        System.err.println("NUMBER OF ELEMENTS IN FORM " + form.getFields().size());
    }

    private View buildError(String errorMsg) {
        LinearLayout err = new LinearLayout(getActivity());
        err.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        TextView msg = new TextView(getActivity());
        msg.setText(errorMsg);
        err.addView(msg);

        return err;
    }

    public Button buildSubmitButton(String text, AFForm form){
        Button btn = new Button(getActivity());
        btn.setText(text);
        btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return btn;
    }


}