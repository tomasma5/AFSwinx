package cz.cvut.fel.matyapav.afandroid.components.parts.validators;

import android.content.Context;
import android.widget.EditText;

import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.ValidationRule;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * Validator which checks if input value is bigger than given minimum value
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class MinValueValidator implements AFValidator {

    @Override
    public boolean validate(Context context, AFField field, StringBuilder errorMsgs, ValidationRule rule) {
        boolean validationIsFine = true;
        if(Utils.isFieldNumberField(field)){
            EditText numberField = (EditText) field.getFieldView();
            if(numberField.getText() != null && !numberField.getText().toString().isEmpty() &&
                    Double.valueOf(numberField.getText().toString()) < Double.valueOf(rule.getValue())){
                validationIsFine = false;
            }
        }
        if(!validationIsFine){
            errorMsgs.append(Localization.translate(context, "validation.minval")).append(" ").append(rule.getValue());
        }
        return validationIsFine;
    }
}
