package com.tomscz.afswinx.unmarshal.builders;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.rest.dto.AFFieldInfo;
import com.tomscz.afswinx.unmarshal.builders.abstraction.TwoComponentsBuilder;

public class InputFieldBuilder extends TwoComponentsBuilder {
    
    private static final int DEFAULT_NUMBER_OF_COLUMS = 10;

    @Override
    public AFSwinxPanel buildComponent(AFFieldInfo field) throws IllegalArgumentException {
        if (!isBuildAvaiable(field)) {
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
        AFSwinxPanel afPanel = new AFSwinxPanel(fieldLabel, textField, panel);
        return afPanel;
    }
}
