package com.tomscz.afswinx.validation;

import java.util.ResourceBundle;

import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.localization.LocalizationUtils;
import com.tomscz.afswinx.validation.exception.ValidationException;


/**
 * This interface specify behavior which must implement each validator which will be used to
 * validation.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public interface AFValidations {

    /**
     * Every validator must implement this method. If exception is thrown, that indicates, that
     * validation was failed. You can use resource bundle. Just simply use {@link LocalizationUtils}
     * to made localization on texts.
     * 
     * @param swinxInstance global instance of AFSwinx
     * @param parentPanel this is parent which holds data fields and all necessary informations
     * @param value concrete value
     * @throws ValidationException if validation is failed then this exception will be thrown
     */
    public void validate(AFSwinx swinxInstance, AFSwinxPanel parentPanel, Object value)
            throws ValidationException;

    public void setLocalization(ResourceBundle localization);
}
