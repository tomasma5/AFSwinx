package cz.cvut.fel.matyapav.afandroid.components.parts.validators;

import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.ValidationRule;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedWidgets;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * Created by Pavel on 14.02.2016.
 */
public class RequiredValidator implements AFValidator {

    @Override
    public boolean validate(AFField field, StringBuilder errorMsgs, ValidationRule rule) {
        boolean validationIsFine = true;
        if(Utils.isFieldWritable(field.getFieldInfo().getWidgetType()) || field.getFieldInfo().getWidgetType().equals(SupportedWidgets.CALENDAR)){
            EditText textfield = (EditText) field.getFieldView();
            if (textfield.getText() == null || textfield.getText().toString().isEmpty()) {
                validationIsFine = false;
            }
        }
        if(field.getFieldInfo().getWidgetType().equals(SupportedWidgets.OPTION)){
            RadioGroup radioGroup = (RadioGroup) field.getFieldView();
            if(radioGroup.getCheckedRadioButtonId() == -1){
                validationIsFine = false;
            }
        }
        if(field.getFieldInfo().getWidgetType().equals(SupportedWidgets.DROPDOWNMENU)){
            Spinner spinner = (Spinner) field.getFieldView();
            if(spinner.getSelectedItem() == null){
                validationIsFine = false;
            }
        }
        if(!validationIsFine){
            errorMsgs.append(Localization.translate("validation.required", field.getFieldView().getContext()));
        }
        return validationIsFine;
    }
}
