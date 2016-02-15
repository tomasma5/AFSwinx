package cz.cvut.fel.matyapav.afandroid.builders.widgets;

import android.app.Activity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;

import cz.cvut.fel.matyapav.afandroid.components.parts.FieldInfo;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedWidgets;

/**
 * Created by Pavel on 14.02.2016.
 */
public class TextFieldBuilder implements BasicBuilder {

    private FieldInfo properties;

    public TextFieldBuilder(FieldInfo properties) {
        this.properties = properties;
    }

    @Override
    public View buildFieldView(Activity activity) {
        EditText text = new EditText(activity);
        addInputType(text, properties.getWidgetType());
        return text;
    }

    private void addInputType(EditText field, String widgetType){
        //textfield or password
        if (widgetType.equals(SupportedWidgets.TEXTFIELD.getWidgetName())) {
            field.setInputType(InputType.TYPE_CLASS_TEXT);
        } else if (widgetType.equals(SupportedWidgets.PASSWORD.getWidgetName())) {
            field.setTransformationMethod(PasswordTransformationMethod.getInstance());
            field.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else if (widgetType.equals(SupportedWidgets.NUMBERFIELD.getWidgetName())) {
            field.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        } else if (widgetType.equals(SupportedWidgets.NUMBERDOUBLEFIELD.getWidgetName())){
            field.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }
        //TODO another input types
    }
}
