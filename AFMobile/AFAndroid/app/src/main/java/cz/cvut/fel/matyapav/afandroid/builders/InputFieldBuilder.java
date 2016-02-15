package cz.cvut.fel.matyapav.afandroid.builders;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import cz.cvut.fel.matyapav.afandroid.builders.widgets.BasicBuilder;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedWidgets;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.FieldInfo;
import cz.cvut.fel.matyapav.afandroid.enums.LabelPosition;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * Builds input field
 * Created by Pavel on 25.12.2015.
 */
public class InputFieldBuilder{

    public AFField buildField(FieldInfo properties, Activity activity) {
        //check if widget type is supported
        SupportedWidgets widgetType;
        try {
            widgetType = SupportedWidgets.valueOf(properties.getWidgetType());
        }catch (IllegalArgumentException e){
            System.err.println(e.getLocalizedMessage());
            //e.printStackTrace();
            return null; //if not return null
        }
        //if field should not be visible
        if(!properties.isVisible()){
            return null;
        }

        AFField field = new AFField(widgetType);
        field.setId(properties.getId());

        //LABEL
        if (properties.getLabel() != null && !properties.getLabel().isEmpty()) {
            String labelText = Localization.translate(properties.getLabel(), activity);
            //set label position
            LabelPosition pos = properties.getLayout().getLabelPosition();
            if (pos != null) {
                String labelPosition = properties.getLayout().getLabelPosition().getPosition();
                field.setLabelPosition(labelPosition);
            }
            TextView label = new TextView(activity);
            label.setText(labelText);
            label.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            label.setSingleLine(false);
            label.setLines(2); //TODO always 2 lines which we dont want to have - but solves problem with visibility when label text is long enough to break into two lines
            field.setLabel(label);


        }
        //ERROR TEXT
        TextView errorView = new TextView(activity);
        errorView.setVisibility(View.GONE);
        errorView.setTextColor(Color.RED);
        field.setErrorView(errorView);

        //LAYOUT PROPERTIES OF FIELD
        if (properties.getLayout().getLayoutDefinition() != null) {
            //set layout definition
        }
        if (properties.getLayout().getLayoutOrientation() != null) {
            //set layout orientation
        }

        View inputField = null;
        BasicBuilder fieldBuilder = Utils.getFieldBuilder(properties);
        if(fieldBuilder != null && (inputField = fieldBuilder.buildFieldView(activity))!= null){
            //USER INTERACTION PROPERTIES OF FIELD SUCH AS VISIBILITY ETC.
            if (properties.isReadOnly()) {
                inputField.setEnabled(false);
            }
            if (!properties.isVisible()) {
                inputField.setVisibility(View.INVISIBLE);
            }

            field.setValidations(properties.getRules());
            field.setFieldView(inputField);
        }
        return field;
    }

}
