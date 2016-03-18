﻿using AFWinPhone.components.parts;
using AFWinPhone.builders.skins;
using AFWinPhone.builders.widgets;
using AFWinPhone.enums;
using AFWinPhone.utils;
using System;
using System.Text;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Media;

namespace AFWinPhone.builders
{
    class FieldBuilder
    {

        public AFField prepareField(FieldInfo properties, StringBuilder road, Skin skin)
        {

            AFField field = new AFField(properties);
            field.setId(road.ToString() + properties.getId());

            //LABEL
            TextBlock label = new TextBlock();
            if (!String.IsNullOrEmpty(properties.getLabelText()))
            {
                String labelText = Localization.translate(properties.getLabelText());
                //set label position
                LabelPosition pos = properties.getLayout().getLabelPosition();
                label.Foreground = new SolidColorBrush(skin.getLabelColor());
                label.FontFamily = skin.getLabelFont();
                label.FontSize = skin.getLabelFontSize();
                label.Text = labelText;
                label.TextWrapping = TextWrapping.Wrap;
                field.setLabel(label);
            }

            //ERROR TEXT
            TextBlock errorView = new TextBlock();
            //errorView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            errorView.Visibility = Visibility.Collapsed;
            errorView.Foreground = new SolidColorBrush(skin.getValidationColor());
            errorView.FontFamily = skin.getValidationFont();
            errorView.FontSize = skin.getValidationFontSize();
            field.setErrorView(errorView);

            //Input view
            FrameworkElement widget = null;
            AbstractWidgetBuilder widgetBuilder = WidgetBuilderFactory.getInstance().getFieldBuilder(properties, skin);
            if (widgetBuilder != null && (widget = widgetBuilder.buildFieldView()) != null)
            {
                field.setFieldView(widget);
            }

            //put it all together
            //when field is not visible don't even add it to form;
            FrameworkElement completeView = buildCompleteView(field, skin);
            if (!properties.isVisible())
            {
                completeView.Visibility = Visibility.Collapsed;
            }
            field.setCompleteView(completeView);
            return field;
        }

        private FrameworkElement buildCompleteView(AFField field, Skin skin)
        {
            StackPanel fullLayout = new StackPanel();
            //fullLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            LayoutProperties layout = field.getFieldInfo().getLayout();
            //set orientation of label and field itself
            if (layout.getLayoutOrientation() != null)
            {
                if (layout.getLayoutOrientation().Equals(LayoutOrientation.AXISY))
                {
                    fullLayout.Orientation = Orientation.Horizontal;
                }
                else if (layout.getLayoutOrientation().Equals(LayoutOrientation.AXISX))
                {
                    fullLayout.Orientation = Orientation.Vertical;
                }
            }
            else {
                fullLayout.Orientation = Orientation.Vertical; //default
            }

            //set label and field view layout params
            if (field.getLabel() != null)
            {
                if (skin.getLabelWidth() >= 0)
                {
                    field.getLabel().Width = skin.getLabelWidth();
                }
                else
                {
                    field.getLabel().HorizontalAlignment = skin.getLabelHorizontalAlignment();
                }
                if(skin.getLabelHeight() >= 0) { 
                    field.getLabel().Height = skin.getLabelHeight();
                }
                else
                {
                    field.getLabel().VerticalAlignment = skin.getLabelVerticalAlignment();
                }
            }
            field.getFieldView().Width = skin.getInputWidth();
            field.getFieldView().VerticalAlignment = VerticalAlignment.Top;

            //LABEL BEFORE
            if (field.getLabel() != null && layout.getLabelPosition() != null && !layout.getLabelPosition().Equals(LabelPosition.NONE))
            {
                if (layout.getLabelPosition().Equals(LabelPosition.BEFORE))
                {
                    fullLayout.Children.Add(field.getLabel());
                }
            }
            else if (field.getLabel() != null && layout.getLabelPosition() == null)
            {
                fullLayout.Children.Add(field.getLabel()); //default is before
            }

            if (field.getFieldView() != null)
            {
                fullLayout.Children.Add(field.getFieldView());
            }
            //LABEL AFTER
            if (field.getLabel() != null && layout.getLabelPosition() != null && !layout.getLabelPosition().Equals(LabelPosition.NONE))
            {
                if (layout.getLabelPosition().Equals(LabelPosition.AFTER))
                {
                    fullLayout.Children.Add(field.getLabel());
                }
            }

            //add errorview on the top of field
            StackPanel fullLayoutWithErrors = new StackPanel();
            fullLayoutWithErrors.HorizontalAlignment = HorizontalAlignment.Stretch;
            fullLayoutWithErrors.VerticalAlignment = VerticalAlignment.Top;
            //fullLayoutWithErrors.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            fullLayoutWithErrors.Margin = new Thickness(0, 0, 10, 10);
            fullLayoutWithErrors.Orientation = Orientation.Vertical;
            fullLayoutWithErrors.Children.Add(field.getErrorView());
            fullLayoutWithErrors.Children.Add(fullLayout);
            return fullLayoutWithErrors;
        }
    }
}
