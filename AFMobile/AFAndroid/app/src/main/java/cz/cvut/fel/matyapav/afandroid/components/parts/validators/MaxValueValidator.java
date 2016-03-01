package cz.cvut.fel.matyapav.afandroid.components.parts.validators;

import android.widget.EditText;

import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.ValidationRule;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * Created by Pavel on 15.02.2016.
 */
public class MaxValueValidator implements AFValidator {

    @Override
    public boolean validate(AFField field, StringBuilder errorMsgs, ValidationRule rule) {
        boolean validationIsFine = true;
        if(Utils.isFieldNumberField(field)){
            EditText numberField = (EditText) field.getFieldView();
            if(numberField.getText() != null && !numberField.getText().toString().isEmpty() &&
                    Double.valueOf(numberField.getText().toString()) > Double.valueOf(rule.getValue())){
                validationIsFine = false;
            }
        }
        if(!validationIsFine){
            errorMsgs.append(Localization.translate("validation.maxval", field.getFieldView().getContext())+" "+rule.getValue());
        }
        return validationIsFine;
    }
}
