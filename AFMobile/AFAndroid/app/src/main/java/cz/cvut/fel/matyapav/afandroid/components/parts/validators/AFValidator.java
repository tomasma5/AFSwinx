package cz.cvut.fel.matyapav.afandroid.components.parts.validators;

import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.ValidationRule;

/**
 * Created by Pavel on 14.02.2016.
 */
public interface AFValidator {

    boolean validate(AFField field, StringBuilder errorMsgs, ValidationRule rule);
}
