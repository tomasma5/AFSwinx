package com.tomscz.afswinx.component.builders;

import javax.swing.JCheckBox;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.data.AFData;
import com.tomscz.afswinx.component.builders.abstraction.BaseComponentsBuilder;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;

public class CheckBoxBuilder extends BaseComponentsBuilder {

    public CheckBoxBuilder(){
        widgetType = SupportedWidgets.CHECKBOX;
    }
    
    @Override
    public AFSwinxPanel buildComponent(AFFieldInfo field) throws IllegalArgumentException {
        super.buildBase(field);
        // And input text field
        JCheckBox checkBox = new JCheckBox();
        layoutBuilder.addComponent(checkBox);
        coreComponent = checkBox;
        // Create panel which holds all necessary informations
        AFSwinxPanel afPanel =
                new AFSwinxPanel(field.getId(), field.getWidgetType(), checkBox, fieldLabel,
                        message);
        // Build layout on that panel
        layoutBuilder.buildLayout(afPanel);
        // Add validations
        super.crateValidators(afPanel, field);
        return afPanel;
    }

    @Override
    public void setData(AFSwinxPanel panel, AFData data) {
        if (panel.getDataHolder() != null && data.getValue() != null) {
            JCheckBox checkBox = (JCheckBox) panel.getDataHolder();
            // Try convert data value to boolean
            boolean value = Boolean.getBoolean(data.getValue().toLowerCase());
            checkBox.setSelected(value);
        }

    }

    @Override
    public Object getData(AFSwinxPanel panel) {
        if (panel.getDataHolder() != null) {
            JCheckBox checkBox = (JCheckBox) panel.getDataHolder();
            return String.valueOf(checkBox.isSelected());
        }
        return null;
    }

}
