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
        //set form layout orientation
        if(form.getLayoutOrientation().equals(LayoutOrientation.AXISX)){ //AXIS X
            formView.setOrientation(LinearLayout.VERTICAL);
        }else { //AXIS Y
            formView.setOrientation(LinearLayout.HORIZONTAL);
        }

        //set fields layout orientation
        int setOfFieldsOrientation;
        if(form.getLayoutOrientation().equals(LayoutOrientation.AXISX)){ //AXIS X
            setOfFieldsOrientation = LinearLayout.HORIZONTAL;
        }else{ //AXIS Y
            setOfFieldsOrientation = LinearLayout.VERTICAL;
        }

        //determine layout
        int numberOfColumns = form.getLayoutDefinitions().getNumberOfColumns();

        int i = 0;
        LinearLayout setOfFields = null;;
        for (AFField field : form.getFields()) {
            if(!field.getFieldInfo().isVisible()){
                continue;
            }
            if (i % numberOfColumns == 0) {
                if(setOfFields != null) {
                    formView.addView(setOfFields);
                }
                setOfFields = new LinearLayout(getActivity());
                setOfFields.setOrientation(setOfFieldsOrientation);
                setOfFields.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }

            View fieldView = field.getCompleteView();
            fieldView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f / numberOfColumns));
            setOfFields.addView(fieldView);

            if(i == form.getVisibleFieldsCount() - 1){
                formView.addView(setOfFields);
            }

            i++;
        }

        return formView;
    }
}