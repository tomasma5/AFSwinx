using AFWindowsPhone.enums;
using AFWindowsPhone.utils;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI.Xaml.Controls;

namespace AFWindowsPhone.builders.components.parts.validators
{
    class RequiredValidator : AFValidator
    {
        public bool validate(AFField field, StringBuilder errorMsgs, ValidationRule rule)
        {
            bool validationIsFine = true;
            if (Utils.isFieldWritable(field.getFieldInfo().getWidgetType()) || field.getFieldInfo().getWidgetType().Equals(SupportedWidgets.CALENDAR))
            {
                TextBox textfield = (TextBox) field.getFieldView();
                if (String.IsNullOrWhiteSpace(textfield.Text))
                {
                    validationIsFine = false;
                }
            }
            if (field.getFieldInfo().getWidgetType().Equals(SupportedWidgets.OPTION))
            {
                RadioGroup radioGroup = (RadioGroup)field.getFieldView();
                if (radioGroup.getCheckedRadioButtonId() == -1)
                {
                    validationIsFine = false;
                }
            }
            if (field.getFieldInfo().getWidgetType().Equals(SupportedWidgets.DROPDOWNMENU))
            {
                Spinner spinner = (Spinner)field.getFieldView();
                if (spinner.getSelectedItem() == null)
                {
                    validationIsFine = false;
                }
            }
            if (!validationIsFine)
            {
                errorMsgs.Append(Localization.translate("validation.required"));
            }
            return validationIsFine;
        }
    }
}
