using AFWindowsPhone.utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml.Controls;

namespace AFWindowsPhone.builders.components.parts.validators
{
    class MinValueValidator : AFValidator
    {
        public bool validate(AFField field, StringBuilder errorMsgs, ValidationRule rule)
        {
            bool validationIsFine = true;
            if (Utils.isFieldNumberField(field))
            {
                TextBox numberField = (TextBox) field.getFieldView();
                if (String.IsNullOrEmpty(numberField.Text) &&
                        Convert.ToDouble(numberField.Text) < Convert.ToDouble(rule.getValue()))
                {
                    validationIsFine = false;
                }
            }
            if (!validationIsFine)
            {
                errorMsgs.Append(Localization.translate("validation.minval") + " " + rule.getValue());
            }
            return validationIsFine;
        }
    }
}
