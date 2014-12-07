package com.tomscz.afswinx.component.widget.builder;

import javax.swing.JPasswordField;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;

public class PasswordBuilder extends InputBuilder{

    public PasswordBuilder(){
        super.widgetType = SupportedWidgets.PASSWORD;
    }
    
    @Override
    public AFSwinxPanel buildComponent(AFFieldInfo field) throws IllegalArgumentException,
            AFSwinxBuildException {
        super.buildBase(field);
        // And input text field
        JPasswordField passwordField = new JPasswordField();
        customizeComponent(passwordField,field);
        layoutBuilder.addComponent(passwordField);
        coreComponent = passwordField;
        // Create panel which holds all necessary informations
        AFSwinxPanel afPanel =
                new AFSwinxPanel(field.getId(), field.getWidgetType(), passwordField, fieldLabel,
                        message);
        // Build layout on that panel
        layoutBuilder.buildLayout(afPanel);
        // Add validations
        super.crateValidators(afPanel, field);
        return afPanel;
    }

}
