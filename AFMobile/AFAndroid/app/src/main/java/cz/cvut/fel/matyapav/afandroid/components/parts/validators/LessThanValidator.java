package cz.cvut.fel.matyapav.afandroid.components.parts.validators;

import android.widget.EditText;

import java.util.Date;

import cz.cvut.fel.matyapav.afandroid.builders.widgets.WidgetBuilderFactory;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.ValidationRule;
import cz.cvut.fel.matyapav.afandroid.components.types.AFForm;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedWidgets;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * Created by Pavel on 02.03.2016.
 */
public class LessThanValidator implements AFValidator {

    @Override
    public boolean validate(AFField field, StringBuilder errorMsgs, ValidationRule rule) {
        boolean validationIsFine = true;
        Object otherData = ((AFForm) field.getParent()).getDataFromFieldWithId(rule.getValue());
        if(otherData != null) {
            if (Utils.isFieldNumberField(field)) {
                //TODO pro cisla
            }
            if (field.getFieldInfo().getWidgetType().equals(SupportedWidgets.CALENDAR)) {
                Object fieldData = ((AFForm) field.getParent()).getDataFromFieldWithId(field.getId());
                if(fieldData!= null) {
                    Date date = Utils.parseDate(fieldData.toString());
                    Date otherDate = Utils.parseDate(otherData.toString());
                    if(date.after(otherDate)){
                        validationIsFine = false;
                    }
                }
            }
            if (!validationIsFine) {
                String otherFieldLabelText = (field.getParent()).getFieldById(rule.getValue()).getFieldInfo().getLabelText();
                errorMsgs.append(Localization.translate("validation.lessthan") + " "
                        + Localization.translate(otherFieldLabelText));
            }
        }
        return validationIsFine;
    }
}
