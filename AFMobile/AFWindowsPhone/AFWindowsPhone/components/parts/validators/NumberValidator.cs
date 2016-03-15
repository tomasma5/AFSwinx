using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml.Controls;
using AFWindowsPhone.builders.components.parts;
using AFWindowsPhone.builders.components.parts.validators;
using AFWindowsPhone.enums;
using AFWindowsPhone.utils;

namespace AFWindowsPhone.components.parts.validators
{
    class NumberValidator : AFValidator
    {
        public bool validate(AFField field, StringBuilder errorMsgs, ValidationRule rule)
        {
            bool validationIsFine = true;
            TextBox numberField = (TextBox)field.getFieldView();
            if (field.getFieldInfo().getWidgetType().Equals(SupportedWidgets.NUMBERFIELD))
            {
                try
                {
                    if (!String.IsNullOrWhiteSpace(numberField.Text))
                    {
                        Convert.ToInt32(numberField.Text);
                    }

                }
                catch (Exception ex)
                {
                    validationIsFine = false;
                }
            }
            if (!validationIsFine)
            {
                errorMsgs.Append(Localization.translate("validation.number"));
            }
            return validationIsFine;
        }
    }
}
