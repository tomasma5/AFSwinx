package cz.cvut.fel.matyapav.afandroid.components.validators;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
        if(Utils.isFieldWritable(field.getWidgetType().getWidgetName())){
            EditText textfield = (EditText) field.getFieldView();
            if (textfield.getText() == null || textfield.getText().toString().isEmpty()) {
                validationIsFine = false;
            }
        }
        if(field.getWidgetType() == SupportedWidgets.OPTION){
            RadioGroup radioGroup = (RadioGroup) field.getFieldView();
            if(radioGroup.getCheckedRadioButtonId() == -1){
                validationIsFine = false;
            }
        }
        if(field.getWidgetType() == SupportedWidgets.CALENDAR){
            LinearLayout dateLayout = (LinearLayout) field.getFieldView();
            EditText dateText = (EditText) dateLayout.getChildAt(0);
            if (dateText.getText() == null || dateText.getText().toString().isEmpty()) {
                validationIsFine = false;
            }
        }
        if(!validationIsFine){
            errorMsgs.append(Localization.translate("validation.required", field.getFieldView().getContext()));
        }
        return validationIsFine;
    }
}
