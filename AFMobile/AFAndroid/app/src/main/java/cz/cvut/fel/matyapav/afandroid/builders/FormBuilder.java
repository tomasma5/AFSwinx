package cz.cvut.fel.matyapav.afandroid.builders;

import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.FieldBuilderFactory;
import cz.cvut.fel.matyapav.afandroid.components.AFComponent;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;
import cz.cvut.fel.matyapav.afandroid.rest.RequestTask;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.AFForm;
import cz.cvut.fel.matyapav.afandroid.components.parts.ClassDefinition;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutDefinitions;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;
import cz.cvut.fel.matyapav.afandroid.parsers.JSONDefinitionParser;
import cz.cvut.fel.matyapav.afandroid.parsers.JSONParser;
import cz.cvut.fel.matyapav.afandroid.utils.Constants;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * Builds for from class definition
 * Created by Pavel on 26.12.2015.
 */
public class FormBuilder extends AFComponentBuilder<FormBuilder>{

    @Override
    public AFForm createComponent() throws Exception {
        initializeConnections();
        String modelResponse = getModelResponse();
        //create form from response
        AFForm form = (AFForm) buildComponent(modelResponse, SupportedComponents.FORM);
        //fill it with data (if there are some)
        String data = getDataResponse();
        if(data != null) {
            insertData(data, form, new StringBuilder());
        }
        AFAndroid.getInstance().addCreatedComponent(getComponentKeyName(), form);
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
                    //System.err.println("ROAD+KEY" + (road + key));
                    AFField field = ((AFForm) form).getFieldById(road + key);
                    //System.err.println("FIELD" + field);
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
        field.setActualData(val);
        FieldBuilderFactory.getInstance().getFieldBuilder(field.getFieldInfo()).setData(field, val);
    }

    @Override
    protected View buildComponentView(AFComponent form) {
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


}