﻿using AFWinPhone.components.parts;
using AFWinPhone.builders.skins;
using System;
using Windows.UI.Xaml;
using System.Linq;
using Windows.UI.Xaml.Controls;
using AFWinPhone.utils;

namespace AFWinPhone.builders.widgets
{
    class OptionWidgetBuilder : BasicBuilder
    {
        public OptionWidgetBuilder(Skin skin, FieldInfo properties) : base(skin, properties)
        {
        }

        public override FrameworkElement buildFieldView()
        {
            StackPanel radioGroup = new StackPanel();
            radioGroup.HorizontalAlignment = HorizontalAlignment.Stretch;
            radioGroup.VerticalAlignment = VerticalAlignment.Top;
            radioGroup.Orientation = Orientation.Vertical;
            int numberOfOptions;
            if (getProperties().getOptions() != null && getProperties().getOptions().Any())
            {
                numberOfOptions = getProperties().getOptions().Count;
                RadioButton[] options = new RadioButton[numberOfOptions];
                String groupName = Guid.NewGuid().ToString();
                for (int i = 0; i < numberOfOptions; i++)
                {
                    options[i] = new RadioButton();
                    options[i].FontFamily = getSkin().getFieldFont();
                    options[i].FontSize = getSkin().getFieldFontSize();
                    options[i].Content = getProperties().getOptions()[i].getValue();
                    options[i].GroupName = groupName;
                    if (getProperties().isReadOnly())
                    {
                        options[i].IsEnabled = false;
                    }
                    radioGroup.Children.Add(options[i]);
                }
            }
            else {
                //YES NO OPTIONS
                numberOfOptions = 2;
                RadioButton[] options = new RadioButton[numberOfOptions];
                options[0] = new RadioButton();
                options[0].FontFamily = getSkin().getFieldFont();
                options[0].FontSize = getSkin().getFieldFontSize();
                options[0].Content = Localization.translate("option.yes");
                options[1] = new RadioButton();
                options[1].FontFamily = getSkin().getFieldFont();
                options[1].FontSize = getSkin().getFieldFontSize();
                options[1].Content = Localization.translate("option.no");
                if (getProperties().isReadOnly())
                {
                    options[0].IsEnabled = false;
                    options[1].IsEnabled = false;
                }
                radioGroup.Children.Add(options[0]);
                radioGroup.Children.Add(options[1]);
            }
          
            return radioGroup;
        }

        public override void setData(AFField field, Object value)
        {

            StackPanel group = (StackPanel) field.getFieldView();
            if (value == null)
            {
                foreach (RadioButton btn in group.Children)
                {
                    btn.IsChecked = false;
                }
                field.setActualData(value);
                return;
            }
            for (int i = 0; i < group.Children.Count; i++)
            {
                RadioButton btn = (RadioButton) group.Children[i];
                if (btn.Content.Equals(value)
                        || (Utils.TryToConvertIntoBoolean(value.ToString()) == true && i == 0)
                        || (Utils.TryToConvertIntoBoolean(value.ToString()) == false && i == 1))
                {
                    btn.IsChecked = true;
                    field.setActualData(value);
                    break;
                }
            };

        }

        public override Object getData(AFField field)
        {
            StackPanel group = (StackPanel) field.getFieldView();
      
            for (int i = 0; i < group.Children.Count; i++)
            {
                RadioButton btn = (RadioButton) group.Children[i];
                if (btn.IsChecked.Value)
                {
                    if (btn.Content.Equals(Localization.translate("option.yes")))
                    {
                        return "true";
                    }
                    else if (btn.Content.Equals(Localization.translate("option.no")))
                    {
                        return "false";
                    }
                    else {
                        return btn.Content;
                    }
                }
            }
            return null; //nothing is checked
        }
    }
}
