package com.tomscz.afswinx.component.widget.builder;

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.component.widget.builder.abstraction.BaseWidgetBuilder;
import com.tomscz.afswinx.rest.rebuild.holder.AFData;

/**
 * This builder build input field editable component without any restrictions. Restriction could be
 * specify as validation rules.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class InputBuilder extends BaseWidgetBuilder {

   public static final int DEFAULT_WIDTH = 20;

    public InputBuilder() {
        widgetType = SupportedWidgets.TEXTFIELD;
    }

    @Override
    public AFSwinxPanel buildComponent(AFFieldInfo field) throws IllegalArgumentException, AFSwinxBuildException {
        super.buildBase(field);
        // And input text field
        JTextField textField = new JTextField();
        customizeComponent(textField,field);
        layoutBuilder.addComponent(textField);
        coreComponent = textField;
        // Create panel which holds all necessary informations
        AFSwinxPanel afPanel =
                new AFSwinxPanel(field.getId(), field.getWidgetType(), textField, fieldLabel,
                        message);
        // Build layout on that panel
        layoutBuilder.buildLayout(afPanel);
        // Add validations
        super.crateValidators(afPanel, field);
        return afPanel;
    }

    @Override
    public void setData(AFSwinxPanel panel, AFData data) {
        if (panel.getDataHolder() != null && !panel.getDataHolder().isEmpty()) {
            JTextComponent textField = (JTextComponent) panel.getDataHolder().get(0);
            textField.setText(data.getValue());
        }
    }

    @Override
    public Object getData(AFSwinxPanel panel) {
        if (panel.getDataHolder() != null && !panel.getDataHolder().isEmpty()) {
            JTextComponent textField = (JTextComponent) panel.getDataHolder().get(0);
            return textField.getText();
        }
        return null;
    }

    @Override
    public Object getPlainData(AFSwinxPanel panel) {
        return getData(panel);
    }

}
