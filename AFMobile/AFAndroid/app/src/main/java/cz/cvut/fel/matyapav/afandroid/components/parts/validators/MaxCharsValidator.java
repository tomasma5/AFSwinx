package cz.cvut.fel.matyapav.afandroid.components.parts.validators;

import android.content.Context;
import android.widget.EditText;

import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.ValidationRule;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * Validator which checks maximum length of input value
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class MaxCharsValidator implements AFValidator {

    @Override
    public boolean validate(Context context, AFField field, StringBuilder errorMsgs, ValidationRule rule) {
        boolean validationIsFine = true;
        if(Utils.isFieldWritable(field.getFieldInfo().getWidgetType())){
            EditText textfield = (EditText) field.getFieldView();
            if (textfield.getText() != null && textfield.getText().toString().length() > Integer.parseInt(rule.getValue())) {
                validationIsFine = false;
            }
        }
        if(!validationIsFine){
            errorMsgs.append(Localization.translate(context, "validation.maxchars"));
        }
        return validationIsFine;
    }
}
