package cz.cvut.fel.matyapav.afandroid.builders;

import android.app.Activity;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;

import cz.cvut.fel.matyapav.afandroid.utils.Constants;

/**
 * Created by Pavel on 14.02.2016.
 */
public class TextFieldBuilder implements BasicBuilder {

    String widgetType;

    public TextFieldBuilder(String widgetType) {
        this.widgetType = widgetType;
    }

    @Override
    public View buildFieldView(Activity activity) {
        EditText text = new EditText(activity);
        addInputType(text, widgetType);
        return text;
    }

    private void addInputType(EditText field, String widgetType){
        //textfield or password
        if (widgetType.equals(Constants.TEXTFIELD)) {
            field.setInputType(InputType.TYPE_CLASS_TEXT);
        } else if (widgetType.equals(Constants.PASSWORD)) {
            field.setTransformationMethod(PasswordTransformationMethod.getInstance());
            field.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else if (widgetType.equals(Constants.NUMBERFIELD)) {
            field.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        //TODO another input types
    }
}
