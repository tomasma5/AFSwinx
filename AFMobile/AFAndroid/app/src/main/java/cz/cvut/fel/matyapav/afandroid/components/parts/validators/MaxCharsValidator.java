package cz.cvut.fel.matyapav.afandroid.components.parts.validators;

import android.widget.EditText;

import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.ValidationRule;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * Created by Pavel on 14.02.2016.
 */
public class MaxCharsValidator implements AFValidator {

    @Override
    public boolean validate(AFField field, StringBuilder errorMsgs, ValidationRule rule) {
        boolean validationIsFine = true;
        if(Utils.isFieldWritable(field.getFieldInfo().getWidgetType())){
            EditText textfield = (EditText) field.getFieldView();
            if (textfield.getText() != null && textfield.getText().toString().length() > Integer.parseInt(rule.getValue())) {
                validationIsFine = false;
            }
        }
        if(!validationIsFine){
            errorMsgs.append(Localization.translate("validation.maxchars", field.getFieldView().getContext()));
        }
        return true;
    }
}
