package com.tomscz.afswinx.validation;

import java.util.ResourceBundle;

/**
 * This is base validator class. Use it to implements your own validation. Each validator in AFSwinx
 * is child of this class.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public abstract class AFBaseValidator implements AFValidations {

    protected ResourceBundle localization;

    @Override
    public void setLocalization(ResourceBundle localization) {
        this.localization = localization;
    }

}
