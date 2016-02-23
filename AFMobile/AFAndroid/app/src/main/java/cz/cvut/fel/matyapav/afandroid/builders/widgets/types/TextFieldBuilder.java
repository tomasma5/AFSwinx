package cz.cvut.fel.matyapav.afandroid.builders.widgets.types;

import android.app.Activity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;

import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
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

    @Override
    public void setData(AFField field, Object value) {
        if(value != null) {
            ((EditText) field.getFieldView()).setText(value.toString());
        }else{
            ((EditText) field.getFieldView()).setText("");
        }
    }

    @Override
    public Object getData(AFField field) {
        return ((EditText)field.getFieldView()).getText().toString();
    }

    private void addInputType(EditText field, SupportedWidgets widgetType){
        //textfield or password
        if (widgetType.equals(SupportedWidgets.TEXTFIELD)) {
            field.setInputType(InputType.TYPE_CLASS_TEXT);
        } else if (widgetType.equals(SupportedWidgets.PASSWORD)) {
            field.setTransformationMethod(PasswordTransformationMethod.getInstance());
            field.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else if (widgetType.equals(SupportedWidgets.NUMBERFIELD)) {
            field.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        } else if (widgetType.equals(SupportedWidgets.NUMBERDOUBLEFIELD)){
            field.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }
        //TODO another input types
    }
}
