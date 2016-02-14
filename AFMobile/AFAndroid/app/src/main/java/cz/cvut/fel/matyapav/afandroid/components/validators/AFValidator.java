package cz.cvut.fel.matyapav.afandroid.components.validators;

import android.view.View;

import cz.cvut.fel.matyapav.afandroid.components.parts.ValidationRule;

/**
 * Created by Pavel on 14.02.2016.
 */
public interface AFValidator {

    public boolean validate(View field, StringBuilder errorMsgs, ValidationRule rule);
}
