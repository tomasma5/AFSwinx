package cz.cvut.fel.matyapav.afandroid.components.validators;

import android.view.View;
import android.widget.EditText;

import cz.cvut.fel.matyapav.afandroid.components.parts.ValidationRule;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;

/**
 * Created by Pavel on 14.02.2016.
 */
public class MaxCharsValidator implements AFValidator {

    @Override
    public boolean validate(View field, StringBuilder errorMsgs, ValidationRule rule) {
        if(field instanceof EditText){
            EditText textfield = (EditText) field;
            if (textfield.getText() != null && textfield.getText().toString().length() > Integer.parseInt(rule.getValue())) {
                errorMsgs.append(Localization.translate("field.required", field.getContext()));
                return false;
            }
        }
        return true;
    }
}
