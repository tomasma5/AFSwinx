package cz.cvut.fel.matyapav.afandroid.builders;

import android.app.Activity;
import android.graphics.Color;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.utils.Constants;
import cz.cvut.fel.matyapav.afandroid.components.parts.FieldInfo;
import cz.cvut.fel.matyapav.afandroid.enums.LabelPosition;

/**
 * Builds input field
 * Created by Pavel on 25.12.2015.
 */
public class InputFieldBuilder{

    public static final float LABEL_PERCENTAGE_WIDTH = 0.4f;

    public AFField buildField(FieldInfo properties, Activity activity) {
        AFField field = new AFField();
        field.setId(properties.getId());

        EditText inputField = new EditText(activity);

        //LABEL
        if(properties.getLabel() != null && !properties.getLabel().isEmpty()) {
            String labelText = Localization.translate(properties.getLabel(),activity);
            LabelPosition pos = properties.getLayout().getLabelPosition();
            if(pos != null) {
                String labelPosition = properties.getLayout().getLabelPosition().getPosition();
                field.setLabelPosition(labelPosition);
            }
            TextView label = new TextView(activity);
            label.setText(labelText);
            label.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, LABEL_PERCENTAGE_WIDTH));
            field.setLabel(label);

        }
        //ERROR TEXT
        TextView errorView = new TextView(activity);
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
        inputField.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f-LABEL_PERCENTAGE_WIDTH));

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
