package cz.cvut.fel.matyapav.afandroid.builders;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.FieldBuilderFactory;
import cz.cvut.fel.matyapav.afandroid.components.AFComponent;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.AFForm;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutDefinitions;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;

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
            form.insertData(data);
        }
        AFAndroid.getInstance().addCreatedComponent(getComponentKeyName(), form);
        return form;
    }

    @Override
    protected View buildComponentView(AFComponent form) {
        //TODO zobecnit
        LinearLayout formView = new LinearLayout(getActivity());
        formView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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
                if(!field.getFieldInfo().isVisible()){
                    continue;
                }
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
                if (form.getVisibleFieldsCount() % 2 != 0 && i == form.getVisibleFieldsCount() - 1) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    fieldView.setLayoutParams(params);
                    formView.addView(fieldView);
                } else {
                    System.err.println("ADDING TO COUPLE "+field.getId());
                    fieldView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f)); //occupies half space
                    couple.addView(fieldView);
                }
                i++;
            }
        }
        return formView;
    }
}