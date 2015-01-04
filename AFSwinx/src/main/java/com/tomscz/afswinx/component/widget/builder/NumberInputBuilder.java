package com.tomscz.afswinx.component.widget.builder;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.validation.NumberValidator;

/**
 * This builder build component in which can be only number. Other character can be inserted, but
 * validations will prevent from submit form.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class NumberInputBuilder extends InputBuilder {

    public NumberInputBuilder() {
        widgetType = SupportedWidgets.NUMBERFIELD;
    }

    @Override
    public AFSwinxPanel buildComponent(AFFieldInfo fieldInfo) throws IllegalArgumentException,
            AFSwinxBuildException {
        AFSwinxPanel panel = super.buildComponent(fieldInfo);
        // Add number validator which will provide number validation
        NumberValidator validator = new NumberValidator(fieldInfo.getWidgetType());
        // Use localization based on parent localization
        validator.setLocalization(localization);
        panel.addValidator(validator);
        return panel;
    }

}
