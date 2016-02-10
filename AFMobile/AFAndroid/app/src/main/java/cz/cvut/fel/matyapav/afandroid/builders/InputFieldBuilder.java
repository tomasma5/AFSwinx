package cz.cvut.fel.matyapav.afandroid.builders;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import cz.cvut.fel.matyapav.afandroid.AFField;
import cz.cvut.fel.matyapav.afandroid.Constants;
import cz.cvut.fel.matyapav.afandroid.FieldInfo;
import cz.cvut.fel.matyapav.afandroid.Utils;
import cz.cvut.fel.matyapav.afandroid.enums.LabelPosition;

/**
 * Builds input field
 * Created by Pavel on 25.12.2015.
 */
public class InputFieldBuilder{

    public static final int DEFAULT_WIDTH = 350; //IN PIXELS

    public AFField buildField(FieldInfo properties, Context context, String lang) {
        AFField field = new AFField();
        field.setId(properties.getId());

        EditText inputField = new EditText(context);

        //LABEL
        if(properties.getLabel() != null && !properties.getLabel().isEmpty()) {
            String labelText = Utils.translate(properties.getLabel(), lang);
            LabelPosition pos = properties.getLayout().getLabelPosition();
            if(pos != null) {
                String labelPosition = properties.getLayout().getLabelPosition().getPosition();
                field.setLabelPosition(labelPosition);
            }
            TextView label = new TextView(context);
            label.setText(labelText);
            field.setLabel(label);

        }
        //ERROR TEXT
        TextView errorView = new TextView(context);
        errorView.setVisibility(View.INVISIBLE);
        errorView.setTextColor(Color.RED);
        field.setErrorView(errorView);

        //LAYOUT PROPERTIES OF FIELD
        if(properties.getLayout().getLayoutDefinition() != null){
            //set layout definition
        }
        if(properties.getLayout().getLayoutOrientation() != null){
            //set layout orientation
        }
        if(properties.getLayout().getLabelPosition() != null){
            //set label position
        }
        inputField.setWidth(DEFAULT_WIDTH);

        //USER INTERACTION PROPERTIES OF FIELD SUCH AS VISIBILITY ETC.
        if(properties.isReadOnly()){
            inputField.setEnabled(false);
        }
        if(!properties.isVisible()){
            inputField.setVisibility(View.INVISIBLE);
        }
        addInputType(inputField, properties.getWidgetType());

        field.setValidations(properties.getRules());
        field.setField(inputField);

        return field;
    }

    private void addInputType(EditText textField, String widgetType){
        //textfield or password
        if(widgetType.equals(Constants.TEXTFIELD)) {
            textField.setInputType(InputType.TYPE_CLASS_TEXT);
        }else if(widgetType.equals(Constants.PASSWORD)){
            textField.setTransformationMethod(PasswordTransformationMethod.getInstance());
            textField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }else if(widgetType.equals(Constants.NUMBERFIELD)){
            textField.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        //TODO another input types
    }

}
