package cz.cvut.fel.matyapav.afandroid.builders;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import cz.cvut.fel.matyapav.afandroid.builders.widgets.BasicBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.FieldBuilderFactory;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedWidgets;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.FieldInfo;
import cz.cvut.fel.matyapav.afandroid.enums.LabelPosition;

/**
 * Builds input field
 * Created by Pavel on 25.12.2015.
 */
public class InputFieldBuilder{

    public AFField prepareField(FieldInfo properties, StringBuilder road, Activity activity) {
        //check if widget type is supported
        SupportedWidgets widgetType;
        try {
            widgetType =properties.getWidgetType();
        }catch (IllegalArgumentException e){
            System.err.println(e.getLocalizedMessage());
            //e.printStackTrace();
            return null; //if not return null
        }


        AFField field = new AFField(properties);
        field.setId(road.toString()+properties.getId());

        //LABEL
        TextView label = new TextView(activity);
        if (properties.getLabel() != null && !properties.getLabel().isEmpty()) {
            String labelText = Localization.translate(properties.getLabel(), activity);
            //set label position
            LabelPosition pos = properties.getLayout().getLabelPosition();
            if (pos != null) {
                field.setLabelPosition(pos);
            }

            label.setText(labelText);
            label.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            field.setLabel(label);
        }
        //ERROR TEXT
        TextView errorView = new TextView(activity);
        errorView.setVisibility(View.GONE);
        errorView.setTextColor(Color.RED);
        field.setErrorView(errorView);

        //LAYOUT PROPERTIES OF FIELD
        if (properties.getLayout().getLayoutDefinition() != null) {
            field.setLayoutDefinitions(properties.getLayout().getLayoutDefinition() );
        }
        if (properties.getLayout().getLayoutOrientation() != null) {
            field.setLayoutOrientation(properties.getLayout().getLayoutOrientation());
        }

        View inputField = null;
        BasicBuilder fieldBuilder = FieldBuilderFactory.getInstance().getFieldBuilder(properties);
        if(fieldBuilder != null && (inputField = fieldBuilder.buildFieldView(activity))!= null){
            //USER INTERACTION PROPERTIES OF FIELD SUCH AS VISIBILITY ETC.
            if (properties.isReadOnly()) {
                inputField.setEnabled(false);
            }
            if (!properties.isVisible()) {
                inputField.setVisibility(View.INVISIBLE);
            }
            //if field should not be visible


            field.setValidations(properties.getRules());
            field.setFieldView(inputField);
        }
        View fieldView = buildFieldView(field);
        if(!properties.isVisible()){
            fieldView.setVisibility(View.GONE);
        }
        field.setCompleteView(fieldView);
        return field;
    }

    private View buildFieldView(AFField field){
        //TODO zeptat se Martina jestli mohou mít fieldy i twocolumnlayout
        LinearLayout fullLayout = new LinearLayout(field.getLabel().getContext());
        fullLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //set orientation of label and field itself
        if(field.getLayoutOrientation().equals(LayoutOrientation.AXISY)){
            fullLayout.setOrientation(LinearLayout.HORIZONTAL);
            field.getLabel().setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.35f)); //TODO let user define weight
            field.getFieldView().setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.65f));
        }else if(field.getLayoutOrientation().equals(LayoutOrientation.AXISX)){
            fullLayout.setOrientation(LinearLayout.VERTICAL);
            field.getLabel().setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            field.getFieldView().setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        //LABEL BEFORE
        if(field.getLabel() != null && !field.getLabelPosition().equals(LabelPosition.NONE)) {
            if (field.getLabelPosition().equals(LabelPosition.BEFORE)) {
                fullLayout.addView(field.getLabel());
            }
        }
        if(field.getFieldView() != null) {
            fullLayout.addView(field.getFieldView());
        }
        //LABEL AFTER
        if(field.getLabel() != null && !field.getLabelPosition().equals(LabelPosition.NONE)) {
            if (field.getLabelPosition().equals(LabelPosition.AFTER)) {
                fullLayout.addView(field.getLabel());
            }
        }

        //add errorview on the top of field
        LinearLayout fullLayoutWithErrors = new LinearLayout(field.getLabel().getContext());
        fullLayoutWithErrors.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        fullLayoutWithErrors.setPadding(0, 0, 10, 10);
        fullLayoutWithErrors.setOrientation(LinearLayout.VERTICAL);
        fullLayoutWithErrors.addView(field.getErrorView());
        fullLayoutWithErrors.addView(fullLayout);
        return fullLayoutWithErrors;
    }

}
