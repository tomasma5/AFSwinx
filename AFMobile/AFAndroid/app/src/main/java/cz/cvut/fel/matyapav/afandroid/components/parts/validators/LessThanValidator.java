package cz.cvut.fel.matyapav.afandroid.components.parts.validators;

import android.content.Context;

import java.util.Date;

import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.ValidationRule;
import cz.cvut.fel.matyapav.afandroid.components.types.AFForm;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedWidgets;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class LessThanValidator implements AFValidator {

    @Override
    public boolean validate(Context context, AFField field, StringBuilder errorMsgs, ValidationRule rule) {
        boolean validationIsFine = true;
        AFField otherField = field.getParent().getFieldById(rule.getValue());
        Object otherData = ((AFForm) field.getParent()).getDataFromFieldWithId(rule.getValue());
        Object fieldData = ((AFForm) field.getParent()).getDataFromFieldWithId(field.getId());

        if (fieldData != null && otherData != null) {
            if (Utils.isFieldNumberField(field) && Utils.isFieldNumberField(otherField)) {
                if (Utils.isFieldIntegerField(field) && Utils.isFieldDoubleField(otherField)) {
                    int number = Integer.parseInt(fieldData.toString());
                    double otherNumber = Double.parseDouble(otherData.toString());
                    if (number >= otherNumber) {
                        validationIsFine = false;
                    }
                }
                if (Utils.isFieldDoubleField(field) && Utils.isFieldIntegerField(otherField)) {
                    double number = Double.parseDouble(fieldData.toString());
                    int otherNumber = Integer.parseInt(otherData.toString());
                    if (number >= otherNumber) {
                        validationIsFine = false;
                    }
                }
                if (Utils.isFieldIntegerField(field) && Utils.isFieldIntegerField(otherField)) {
                    int number = Integer.parseInt(fieldData.toString());
                    int otherNumber = Integer.parseInt(otherData.toString());
                    if (number >= otherNumber) {
                        validationIsFine = false;
                    }
                }
                if (Utils.isFieldDoubleField(field) && Utils.isFieldDoubleField(otherField)) {
                    double number = Double.parseDouble(fieldData.toString());
                    double otherNumber = Double.parseDouble(otherData.toString());
                    if (number >= otherNumber) {
                        validationIsFine = false;
                    }
                }
            }
            if (field.getFieldInfo().getWidgetType().equals(SupportedWidgets.CALENDAR) &&
                    otherField.getFieldInfo().getWidgetType().equals(SupportedWidgets.CALENDAR)) {
                Date date = Utils.parseDate(fieldData.toString());
                Date otherDate = Utils.parseDate(otherData.toString());
                if (date.after(otherDate)) {
                    validationIsFine = false;
                }
            }
        }

        if (!validationIsFine) {
            String otherFieldLabelText = (field.getParent()).getFieldById(rule.getValue()).getFieldInfo().getLabelText();
            errorMsgs.append(Localization.translate(context, "validation.lessthan")).append(" ").append(Localization.translate(context, otherFieldLabelText));
        }

        return validationIsFine;
    }
}
