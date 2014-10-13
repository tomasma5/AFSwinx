package com.tomscz.afswinx.validation;

import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.validation.exception.ValidationException;


public interface AFValidations {

    /**
     * Every validator must implement this method. If exception is thrown, that indicates, that
     * validation was failed.
     * 
     * @param swinxInstance global instance of AFSwinx
     * @param parentPanel this is parent which holds data fields and all necessary informations 
     * @param value concrete value 
     * @throws ValidationException if validation is failed then this exception will be thrown
     */
    public void validate(AFSwinx swinxInstance, AFSwinxPanel parentPanel, Object value)
            throws ValidationException;
}
