using AFWindowsPhone.utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml.Controls;
using AFWindowsPhone.components.parts.validators;

namespace AFWindowsPhone.builders.components.parts.validators
{
    class MaxValueValidator : AFValidator
    {
        public bool validate(AFField field, StringBuilder errorMsgs, ValidationRule rule)
        {
            bool validationIsFine = true;
            if (Utils.IsFieldNumberField(field))
            {
                TextBox numberField = (TextBox)field.getFieldView();
                try
                {
                    if (!String.IsNullOrEmpty(numberField.Text) &&
                        Convert.ToDouble(numberField.Text) > Convert.ToDouble(rule.getValue()))
                    {
                        validationIsFine = false;
                    }
                }
                catch (Exception ex) //catch convert exception
                {
                    validationIsFine = true; //if there is no number in field we cant validate its value
                }
            }
            if (!validationIsFine)
            {
                errorMsgs.Append(Localization.translate("validation.maxval") + " " + rule.getValue());
            }
            return validationIsFine;

        }
    }
}
