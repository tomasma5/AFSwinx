
using AFWindowsPhone.builders.components.types;
using AFWindowsPhone.enums;
using AFWindowsPhone.utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AFWindowsPhone.builders.components.parts.validators
{
    class LessThanValidator : AFValidator
    {
        public bool validate(AFField field, StringBuilder errorMsgs, ValidationRule rule)
        {
            bool validationIsFine = true;
            Object otherData = ((AFForm)field.getParent()).getDataFromFieldWithId(rule.getValue());
            if (otherData != null)
            {
                if (Utils.isFieldNumberField(field))
                {
                    //TODO pro cisla
                }
                if (field.getFieldInfo().getWidgetType().Equals(SupportedWidgets.CALENDAR))
                {
                    Object fieldData = ((AFForm)field.getParent()).getDataFromFieldWithId(field.getId());
                    if (fieldData != null)
                    {
                        DateTime? date = Utils.parseDate(fieldData.ToString());
                        DateTime? otherDate = Utils.parseDate(otherData.ToString());
                        if (date > otherDate)
                        {
                            validationIsFine = false;
                        }
                    }
                }
                if (!validationIsFine)
                {
                    String otherFieldLabelText = (field.getParent()).getFieldById(rule.getValue()).getFieldInfo().getLabelText();
                    errorMsgs.Append(Localization.translate("validation.lessthan") + " "
                            + Localization.translate(otherFieldLabelText));
                }
            }
            return validationIsFine;

        }
    }
}
