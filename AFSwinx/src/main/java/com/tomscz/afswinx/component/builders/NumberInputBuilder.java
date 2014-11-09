package com.tomscz.afswinx.component.builders;

import com.tomscz.afrest.rest.dto.AFFieldInfo;
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
public class NumberInputBuilder extends InputFieldBuilder {

    @Override
    public AFSwinxPanel buildComponent(AFFieldInfo fieldInfo) throws IllegalArgumentException {
        AFSwinxPanel panel = super.buildComponent(fieldInfo);
        //Add number validator which will provide number validation
        NumberValidator validador = new NumberValidator();
        //Use localization based on parent localization
        validador.setLocalization(localization);
        panel.addValidator(validador);
        return panel;
    }

}
