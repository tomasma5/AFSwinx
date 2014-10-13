package com.tomscz.afswinx.unmarshal.builders;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.rest.dto.AFFieldInfo;
import com.tomscz.afswinx.rest.dto.data.AFData;
import com.tomscz.afswinx.unmarshal.builders.abstraction.TwoComponentsBuilder;

public class InputFieldBuilder extends TwoComponentsBuilder {
    
    private static final int DEFAULT_NUMBER_OF_COLUMS = 10;

    @Override
    public AFSwinxPanel buildComponent(AFFieldInfo field) throws IllegalArgumentException {
        if (!isBuildAvailable(field)) {
            throw new IllegalArgumentException("Input field couldn't be build for this field");
        }

        JPanel panel = new JPanel();
        JLabel fieldLabel = buildSimpleLabel(field.getLabel());
        JTextField textField = new JTextField();
        textField.setColumns(DEFAULT_NUMBER_OF_COLUMS);
        // TODO use swing layout builder to build right layouts
        if (fieldLabel != null) {
            fieldLabel.setLabelFor(textField);
            panel.add(fieldLabel);
        }

        panel.add(textField);
        AFSwinxPanel afPanel = new AFSwinxPanel(field.getId(),field.getWidgetType(),textField, fieldLabel, panel);
        return afPanel;
    }

    @Override
    public void setData(AFSwinxPanel panel, AFData data) {
        JTextField textField = (JTextField) panel.getDataHolder();
        if(textField != null){
            textField.setText(data.getValue());
        }
    }
}
