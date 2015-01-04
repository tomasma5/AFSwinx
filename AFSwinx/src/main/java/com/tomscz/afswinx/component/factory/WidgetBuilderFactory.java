package com.tomscz.afswinx.component.factory;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afswinx.component.widget.builder.CheckBoxBuilder;
import com.tomscz.afswinx.component.widget.builder.DateBuilder;
import com.tomscz.afswinx.component.widget.builder.DropDownMenuBuilder;
import com.tomscz.afswinx.component.widget.builder.PasswordBuilder;
import com.tomscz.afswinx.component.widget.builder.WidgetBuilder;
import com.tomscz.afswinx.component.widget.builder.InputBuilder;
import com.tomscz.afswinx.component.widget.builder.LabelBuider;
import com.tomscz.afswinx.component.widget.builder.NumberInputBuilder;
import com.tomscz.afswinx.component.widget.builder.OptionBuilder;
import com.tomscz.afswinx.component.widget.builder.TextAreaBuilder;

/**
 * This class is singleton. Get it by using {@link WidgetBuilderFactory#getInstance()}. It is used
 * to create concrete builder widget builder based on field type.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class WidgetBuilderFactory {

    private static WidgetBuilderFactory instance;

    private WidgetBuilderFactory() {}

    public static synchronized WidgetBuilderFactory getInstance() {
        if (instance == null) {
            instance = new WidgetBuilderFactory();
        }
        return instance;
    }

    /**
     * This method create widget builder based on type which were received from definition.
     * 
     * @param fieldInfo definition which hold widget type.
     * @return WidgetBuilder which is able to build component of type which were received in
     *         fieldInfo in parameter.
     */
    public WidgetBuilder createWidgetBuilder(AFFieldInfo fieldInfo) {
        SupportedWidgets widgetType = fieldInfo.getWidgetType();
        WidgetBuilder fieldBuilder;
        fieldBuilder = createWidgetBuilder(widgetType);
        if (fieldBuilder == null) {
            fieldBuilder = new InputBuilder();
        }
        return fieldBuilder;
    }

    /**
     * This method create widget builder which match to widget in parameter.
     * 
     * @param widget which must be shown.
     * @return Widget builder which is able to build component based on widget received in
     *         parameter.
     */
    public WidgetBuilder createWidgetBuilder(SupportedWidgets widget) {
        if (widget == null) {
            return new InputBuilder();
        }
        if (widget.equals(SupportedWidgets.TEXTFIELD)) {
            return new InputBuilder();
        }
        if (widget.equals(SupportedWidgets.LABEL)) {
            return new LabelBuider();
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
        if (widget.equals(SupportedWidgets.PASSWORD)) {
            return new PasswordBuilder();
        }
        //Return input builder if no type was specify
        return new InputBuilder();
    }

}
