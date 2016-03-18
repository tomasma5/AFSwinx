﻿using AFWinPhone.enums;
using AFWinPhone.utils;
using System;
using System.Text;
using Windows.UI.Xaml.Controls;

namespace AFWinPhone.components.parts.validators
{
    class MaxCharsValidator : AFValidator
    {
        public bool validate(AFField field, StringBuilder errorMsgs, ValidationRule rule)
        {
            bool validationIsFine = true;
            if (Utils.IsFieldWritable(field.getFieldInfo().getWidgetType()))
            {
                TextBox textfield = (TextBox)field.getFieldView();
                if (textfield.Text != null && textfield.Text.Length > Convert.ToInt32(rule.getValue()))
                {
                    validationIsFine = false;
                }
            }
            if (field.getFieldInfo().getWidgetType().Equals(SupportedWidgets.PASSWORD))
            {
                PasswordBox textfield = (PasswordBox)field.getFieldView();
                if (textfield.Password != null && textfield.Password.Length > Convert.ToInt32(rule.getValue()))
                {
                    validationIsFine = false;
                }
            }
            if (!validationIsFine)
            {
                errorMsgs.Append(Localization.translate("validation.maxchars"));
            }
            return validationIsFine;
        }
    }
}
