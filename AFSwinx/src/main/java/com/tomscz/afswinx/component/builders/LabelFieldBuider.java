package com.tomscz.afswinx.component.builders;

import javax.swing.JLabel;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.data.AFData;
import com.tomscz.afswinx.component.builders.abstraction.BaseComponentsBuilder;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;

public class LabelFieldBuider extends BaseComponentsBuilder {
    
    public LabelFieldBuider(){
        widgetType = SupportedWidgets.LABEL;
    }
    
    @Override
    public AFSwinxPanel buildComponent(AFFieldInfo field) throws IllegalArgumentException {
        if (!isBuildAvailable(field)) {
            throw new IllegalArgumentException("Input field couldn't be build for this field");
        }
        super.buildBase(field);
        JLabel dataLabel = new JLabel();
        // TODO add layout based on label position, but its for top level class
        layoutBuilder.addComponent(dataLabel);
        AFSwinxPanel afPanel =
                new AFSwinxPanel(field.getId(), field.getWidgetType(), dataLabel, fieldLabel);
        layoutBuilder.buildLayout(afPanel);
        return afPanel;
    }

    @Override
    public void setData(AFSwinxPanel panel, AFData data) {
        if (panel.getDataHolder() != null) {
            JLabel dataLabel = (JLabel) panel.getDataHolder();
            dataLabel.setText(data.getValue());
        }

    }

    @Override
    public Object getData(AFSwinxPanel panel) {
        if (panel.getDataHolder() != null) {
            JLabel dataLabel = (JLabel) panel.getDataHolder();
            return dataLabel.getText();
        }
        return null;
    }

}
