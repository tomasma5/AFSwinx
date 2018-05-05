package cz.cvut.fel.matyapav.afandroid.components.parts.validators;

import android.content.Context;

import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.ValidationRule;

/**
 * Validator interface
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public interface AFValidator {

    boolean validate(Context context, AFField field, StringBuilder errorMsgs, ValidationRule rule);
}
