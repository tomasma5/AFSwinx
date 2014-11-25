package com.tomscz.afswinx.component.factory;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afswinx.component.widget.builder.CheckBoxBuilder;
import com.tomscz.afswinx.component.widget.builder.DateBuilder;
import com.tomscz.afswinx.component.widget.builder.DropDownMenuBuilder;
import com.tomscz.afswinx.component.widget.builder.FieldBuilder;
import com.tomscz.afswinx.component.widget.builder.InputFieldBuilder;
import com.tomscz.afswinx.component.widget.builder.LabelFieldBuider;
import com.tomscz.afswinx.component.widget.builder.NumberInputBuilder;
import com.tomscz.afswinx.component.widget.builder.OptionBuilder;
import com.tomscz.afswinx.component.widget.builder.TextAreaBuilder;

public class WidgetBuilderFactory {

    private static WidgetBuilderFactory instance;

    private WidgetBuilderFactory() {

    }

    public static synchronized WidgetBuilderFactory getInstance() {
        if (instance == null) {
            instance = new WidgetBuilderFactory();
        }
        return instance;
    }

    public FieldBuilder createWidgetBuilder(AFFieldInfo fieldInfo) {
        SupportedWidgets widgetType = fieldInfo.getWidgetType();
        FieldBuilder fieldBuilder;
        if (widgetType != null) {
            fieldBuilder = createWidgetBuilder(fieldInfo.getWidgetType());
        } else {
            fieldBuilder = createWidgetBuilderBasedOnRules(fieldInfo);
            //If widgetType is not set in fieldInfo then set it locally
            fieldInfo.setWidgetType(fieldBuilder.getWidgetType());
        }

        if (fieldBuilder == null) {
            fieldBuilder = new InputFieldBuilder();
        }
        return fieldBuilder;
    }

    public FieldBuilder createWidgetBuilder(SupportedWidgets widget) {
        if (widget.equals(SupportedWidgets.TEXTFIELD)) {
            return new InputFieldBuilder();
        }
        if (widget.equals(SupportedWidgets.LABEL)) {
            return new LabelFieldBuider();
        }
        if (widget.equals(SupportedWidgets.NUMBERFIELD)) {
            return new NumberInputBuilder();
        }
        if (widget.equals(SupportedWidgets.DROPDOWNMENU)) {
            return new DropDownMenuBuilder();
        }
        if (widget.equals(SupportedWidgets.CHECKBOX)) {
            return new CheckBoxBuilder();
        }
        if(widget.equals(SupportedWidgets.TEXTAREA)){
            return new TextAreaBuilder();
        }
        if(widget.equals(SupportedWidgets.OPTION)){
            return new OptionBuilder();
        }
        if(widget.equals(SupportedWidgets.CALENDAR)){
            return new DateBuilder();
        }
        return new InputFieldBuilder();
    }

    private FieldBuilder createWidgetBuilderBasedOnRules(AFFieldInfo fieldInfo) {
        SupportedWidgets widget = SupportedWidgets.LABEL;
        // First do candidate based on options, if there are options.
        if (fieldInfo.getOptions() != null && !fieldInfo.getOptions().isEmpty()) {

        } else {
            // TODO
        }
        return createWidgetBuilder(widget);
    }

}
