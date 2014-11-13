package com.tomscz.afswinx.component.builders;

import javax.swing.JTextField;

import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.data.AFData;
import com.tomscz.afswinx.component.builders.abstraction.BaseComponentsBuilder;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;

/**
 * This builder build input field editable component without any restrictions
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public class InputFieldBuilder extends BaseComponentsBuilder {
    
    private static final int DEFAULT_NUMBER_OF_COLUMS = 10;

    @Override
    public AFSwinxPanel buildComponent(AFFieldInfo fieldInfo) throws IllegalArgumentException {
         super.buildBase(fieldInfo);
        //And input text field
        JTextField textField = new JTextField();   
        layoutBuilder.addComponent(textField);
        textField.setColumns(DEFAULT_NUMBER_OF_COLUMS);
        coreComponent = textField;
        //Create panel which holds all necessary informations
        AFSwinxPanel afPanel = new AFSwinxPanel(fieldInfo.getId(),fieldInfo.getWidgetType(),textField, fieldLabel,message);
       
        //Build layout on that panel
        layoutBuilder.buildLayout(afPanel);
        //Add validations
        super.crateValidators(afPanel, fieldInfo);
        return afPanel;
    }

    @Override
    public void setData(AFSwinxPanel panel, AFData data) {     
        if(panel.getDataHolder() != null){
            JTextField textField = (JTextField) panel.getDataHolder();
            textField.setText(data.getValue());
        }
    }

    @Override
    public Object getData(AFSwinxPanel panel) {
        if(panel.getDataHolder() != null){
            JTextField textField = (JTextField) panel.getDataHolder();
            return textField.getText();
        }
        return null;
    }

}
