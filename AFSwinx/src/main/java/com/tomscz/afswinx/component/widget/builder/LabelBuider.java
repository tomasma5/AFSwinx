package com.tomscz.afswinx.component.widget.builder;

import javax.swing.JLabel;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.component.widget.builder.abstraction.BaseWidgetBuilder;
import com.tomscz.afswinx.rest.rebuild.holder.AFData;

/**
 * This component can build labels. Label is not the same as component in readonly mode.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class LabelBuider extends BaseWidgetBuilder {

    public LabelBuider() {
        widgetType = SupportedWidgets.LABEL;
    }

    @Override
    public AFSwinxPanel buildComponent(AFFieldInfo field) throws IllegalArgumentException {
        super.buildBase(field);
        JLabel dataLabel = new JLabel();
        // Add label to layout
        layoutBuilder.addComponent(dataLabel);
        AFSwinxPanel afPanel =
                new AFSwinxPanel(field.getId(), field.getWidgetType(), dataLabel, fieldLabel);
        layoutBuilder.buildLayout(afPanel);
        return afPanel;
    }

    @Override
    public void setData(AFSwinxPanel panel, AFData data) {
        if (panel.getDataHolder() != null && !panel.getDataHolder().isEmpty()) {
            JLabel dataLabel = (JLabel) panel.getDataHolder().get(0);
            dataLabel.setText(data.getValue());
        }

    }

    @Override
    public Object getData(AFSwinxPanel panel) {
        if (panel.getDataHolder() != null && !panel.getDataHolder().isEmpty()) {
            JLabel dataLabel = (JLabel) panel.getDataHolder().get(0);
            return dataLabel.getText();
        }
        return null;
    }

    @Override
    public Object getPlainData(AFSwinxPanel panel) {
        return getData(panel);
    }

}
