package com.tomscz.afswinx.unmarshal.builders;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.rest.dto.AFFieldInfo;
import com.tomscz.afswinx.unmarshal.builders.abstraction.TwoComponentsBuilder;

public class LabelFieldBuider extends TwoComponentsBuilder {

    @Override
    public AFSwinxPanel buildComponent(AFFieldInfo field) throws IllegalArgumentException {
        if (!isBuildAvaiable(field)) {
            throw new IllegalArgumentException("Input field couldn't be build for this field");
        }
        
        JPanel panel = new JPanel();
        JLabel fieldLabel = null;
        JLabel dataLabel = null;

        fieldLabel = super.buildSimpleLabel(field.getLabel());
        dataLabel = new JLabel();
        // TODO add layout based on label position, but its for top level class
        if(fieldLabel != null){
            panel.add(fieldLabel);
        }
 
        panel.add(dataLabel);
        AFSwinxPanel afPanel = new AFSwinxPanel(dataLabel, fieldLabel);
        afPanel.add(panel);
        return afPanel;
    }
}
