package com.tomscz.afswinx.component.factory;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afswinx.component.widget.builder.CheckBoxBuilder;
import com.tomscz.afswinx.component.widget.builder.DateBuilder;
import com.tomscz.afswinx.component.widget.builder.DropDownMenuBuilder;
import com.tomscz.afswinx.component.widget.builder.PasswordBuilder;
import com.tomscz.afswinx.component.widget.builder.WidgetBuilder;
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

    public WidgetBuilder createWidgetBuilder(AFFieldInfo fieldInfo) {
        SupportedWidgets widgetType = fieldInfo.getWidgetType();
        WidgetBuilder fieldBuilder;
        fieldBuilder = createWidgetBuilder(widgetType);
        if (fieldBuilder == null) {
            fieldBuilder = new InputFieldBuilder();
        }
        return fieldBuilder;
    }

    public WidgetBuilder createWidgetBuilder(SupportedWidgets widget) {
        if (widget == null) {
            return new InputFieldBuilder();
        }
        if (widget.equals(SupportedWidgets.TEXTFIELD)) {
            return new InputFieldBuilder();
        }
        if (widget.equals(SupportedWidgets.LABEL)) {
            return new LabelFieldBuider();
        }
        if (widget.equals(SupportedWidgets.NUMBERFIELD)
                || widget.equals(SupportedWidgets.NUMBERDOUBLEFIELD)
                || widget.equals(SupportedWidgets.NUMBERLONGFIELD)) {
            return new NumberInputBuilder();
        }
        if (widget.equals(SupportedWidgets.DROPDOWNMENU)) {
            return new DropDownMenuBuilder();
        }
        if (widget.equals(SupportedWidgets.CHECKBOX)) {
            return new CheckBoxBuilder();
        }
        if (widget.equals(SupportedWidgets.TEXTAREA)) {
            return new TextAreaBuilder();
        }
        if (widget.equals(SupportedWidgets.OPTION)) {
            return new OptionBuilder();
        }
        if (widget.equals(SupportedWidgets.CALENDAR)) {
            return new DateBuilder();
        }
        if(widget.equals(SupportedWidgets.PASSWORD)){
            return new PasswordBuilder();
        }
        return new InputFieldBuilder();
    }

}
