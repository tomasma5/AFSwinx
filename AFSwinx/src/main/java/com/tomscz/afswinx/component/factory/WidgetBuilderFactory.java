package com.tomscz.afswinx.component.factory;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afswinx.component.builders.CheckBoxBuilder;
import com.tomscz.afswinx.component.builders.DropDownMenuBuilder;
import com.tomscz.afswinx.component.builders.FieldBuilder;
import com.tomscz.afswinx.component.builders.InputFieldBuilder;
import com.tomscz.afswinx.component.builders.LabelFieldBuider;
import com.tomscz.afswinx.component.builders.NumberInputBuilder;

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
        }

        if (fieldBuilder == null) {
            fieldBuilder = new InputFieldBuilder();
        }
        return fieldBuilder;
    }

    public FieldBuilder createWidgetBuilder(SupportedWidgets widget) {
        if (widget.equals(SupportedWidgets.INPUTFIELD)) {
            return new InputFieldBuilder();
        }
        if (widget.equals(SupportedWidgets.LABEL)) {
            return new LabelFieldBuider();
        }
        if (widget.equals(SupportedWidgets.NUMBERINPUT)) {
            return new NumberInputBuilder();
        }
        if (widget.equals(SupportedWidgets.DROPDOWNMENU)) {
            return new DropDownMenuBuilder();
        }
        if (widget.equals(SupportedWidgets.CHECKBOX)) {
            return new CheckBoxBuilder();
        }
        return new InputFieldBuilder();
    }

    private FieldBuilder createWidgetBuilderBasedOnRules(AFFieldInfo fieldInfo) {
        SupportedWidgets widget = null;
        // First do candidate based on options, if there are options.
        if (fieldInfo.getOptions() != null && !fieldInfo.getOptions().isEmpty()) {

        } else {
            // TODO
        }
        return createWidgetBuilder(widget);
    }

}
