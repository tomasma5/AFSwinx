package com.tomscz.afswinx.component.widget.builder;

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.data.AFData;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.component.widget.builder.abstraction.BaseComponentsBuilder;

/**
 * This builder build input field editable component without any restrictions. Restriction could be
 * specify as validation rules.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class InputFieldBuilder extends BaseComponentsBuilder {

   public static final int DEFAULT_NUMBER_OF_COLUMS = 10;

    public InputFieldBuilder() {
        widgetType = SupportedWidgets.TEXTFIELD;
    }

    @Override
    public AFSwinxPanel buildComponent(AFFieldInfo field) throws IllegalArgumentException {
        super.buildBase(field);
        // And input text field
        JTextField textField = new JTextField();
        skinComponent(textField);
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
