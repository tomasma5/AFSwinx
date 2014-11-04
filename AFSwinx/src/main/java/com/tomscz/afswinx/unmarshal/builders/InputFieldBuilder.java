package com.tomscz.afswinx.unmarshal.builders;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.data.AFData;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.unmarshal.builders.abstraction.component.BaseComponentsBuilder;
import com.tomscz.afswinx.unmarshal.builders.abstraction.layout.BaseLayoutBuilder;

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
        //First check if build is avaiable
        if (!isBuildAvailable(fieldInfo)) {
            throw new IllegalArgumentException("Input field couldn't be build for this field");
        }
        //Create layout builder
        BaseLayoutBuilder layoutBuilder = new BaseLayoutBuilder(fieldInfo.getLayout());
        //Build label
        JLabel fieldLabel = buildSimpleLabel(fieldInfo.getLabel());
        //And input text field
        JTextField textField = new JTextField();
        textField.setColumns(DEFAULT_NUMBER_OF_COLUMS);
        coreComponent = textField;
        //Add components to layout builder
        layoutBuilder.addComponent(textField);
        layoutBuilder.addLabel(fieldLabel);
        JTextArea message = buildSimpleMessage();
        layoutBuilder.addMessage(message);
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

    @Override
    public Component getCoreComponent() {
        return coreComponent;
    }

}
