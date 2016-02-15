package cz.cvut.fel.matyapav.afandroid.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cz.cvut.fel.matyapav.afandroid.builders.widgets.BasicBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.CheckboxFieldBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.DateFieldBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.DropDownFieldBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.OptionFieldBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.TextFieldBuilder;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.FieldInfo;
import cz.cvut.fel.matyapav.afandroid.components.parts.ValidationRule;
import cz.cvut.fel.matyapav.afandroid.components.validators.AFValidator;
import cz.cvut.fel.matyapav.afandroid.components.validators.MaxCharsValidator;
import cz.cvut.fel.matyapav.afandroid.components.validators.MaxValueValidator;
import cz.cvut.fel.matyapav.afandroid.components.validators.MinValueValidator;
import cz.cvut.fel.matyapav.afandroid.components.validators.RequiredValidator;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedValidations;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedWidgets;

/**
 * Created by Pavel on 25.12.2015.
 */
public class Utils {

    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    public static BasicBuilder getFieldBuilder(FieldInfo properties){
        if(isFieldWritable(properties.getWidgetType())){
            return new TextFieldBuilder(properties);
        }
        if(properties.getWidgetType().equals(SupportedWidgets.CALENDAR.getWidgetName())) {
            return new DateFieldBuilder();
        }
        if(properties.getWidgetType().equals(SupportedWidgets.OPTION.getWidgetName())){
            return new OptionFieldBuilder(properties);
        }
        if(properties.getWidgetType().equals(SupportedWidgets.DROPDOWNMENU.getWidgetName())){
            return new DropDownFieldBuilder(properties);
        }
        if(properties.getWidgetType().equals(SupportedWidgets.CHECKBOX.getWidgetName())){
            return new CheckboxFieldBuilder();
        }
        System.err.println("BUILDER FOR "+properties.getWidgetType()+" NOT FOUND");
        return null;
    }

    public static AFValidator getFieldValidator(ValidationRule rule){
        if (rule.getValidationType().equals(SupportedValidations.REQUIRED.getValidationType()) && Boolean.valueOf(rule.getValue())) {
            return new RequiredValidator();
        }
        if (rule.getValidationType().equals(SupportedValidations.MAXLENGTH.getValidationType())) {
            return new MaxCharsValidator();
        }
        if (rule.getValidationType().equals(SupportedValidations.MAX.getValidationType())){
            return new MaxValueValidator();
        }
        if (rule.getValidationType().equals(SupportedValidations.MIN.getValidationType())){
            return new MinValueValidator();
        }

        System.err.println("VALIDATOR FOR "+rule.getValidationType()+" NOT FOUND");
        return null;
    }

    public static boolean isFieldWritable(String fieldType){
        return fieldType.equals(SupportedWidgets.TEXTFIELD.getWidgetName()) || fieldType.equals(SupportedWidgets.NUMBERFIELD.getWidgetName())
                || fieldType.equals(SupportedWidgets.NUMBERDOUBLEFIELD.getWidgetName()) || fieldType.equals(SupportedWidgets.PASSWORD.getWidgetName());
    }

    public static boolean isFieldNumberField(AFField field){
        return field.getWidgetType() == SupportedWidgets.NUMBERFIELD || field.getWidgetType() == SupportedWidgets.NUMBERDOUBLEFIELD;
    }
}
