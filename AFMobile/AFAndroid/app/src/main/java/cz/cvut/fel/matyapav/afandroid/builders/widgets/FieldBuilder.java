package cz.cvut.fel.matyapav.afandroid.builders.widgets;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import cz.cvut.fel.matyapav.afandroid.builders.widgets.types.AbstractBuilder;
import cz.cvut.fel.matyapav.afandroid.components.skins.Skin;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.FieldInfo;
import cz.cvut.fel.matyapav.afandroid.enums.LabelPosition;

/**
 * Builds input field
 * Created by Pavel on 25.12.2015.
 */
public class FieldBuilder {

    public AFField prepareField(FieldInfo properties, StringBuilder road, Activity activity, Skin skin) {


        AFField field = new AFField(properties);
        field.setId(road.toString()+properties.getId());
        //LAYOUT PROPERTIES OF FIELD
        if (properties.getLayout().getLayoutDefinition() != null) {
            field.setLayoutDefinitions(properties.getLayout().getLayoutDefinition() );
        }
        if (properties.getLayout().getLayoutOrientation() != null) {
            field.setLayoutOrientation(properties.getLayout().getLayoutOrientation());
        }

        //LABEL
        TextView label = new TextView(activity);
        if (properties.getLabel() != null && !properties.getLabel().isEmpty()) {
            String labelText = Localization.translate(properties.getLabel(), activity);
            //set label position
            LabelPosition pos = properties.getLayout().getLabelPosition();
            if (pos != null) {
                field.setLabelPosition(pos);
            }
            label.setTextColor(skin.getLabelColor());
            label.setTypeface(skin.getLabelFont());
            label.setText(labelText);
            field.setLabel(label);
        }

        //ERROR TEXT
        TextView errorView = new TextView(activity);
        errorView.setVisibility(View.GONE);
        errorView.setTextColor(skin.getValidationColor());
        errorView.setTypeface(skin.getValidationFont());
        field.setErrorView(errorView);

        //VALIDATIONS
        field.setValidations(properties.getRules());

        //Input view
        View inputField = null;
        AbstractBuilder fieldBuilder = FieldBuilderFactory.getInstance().getFieldBuilder(properties, skin);
        if(fieldBuilder != null && (inputField = fieldBuilder.buildFieldView(activity))!= null){
            inputField.setEnabled(!properties.isReadOnly());
            field.setFieldView(inputField);
        }

        //put it all together
        //when field is not visible don't even add it to form;

        View completeView = buildCompleteView(field, skin);
        if(!properties.isVisible()){
           completeView.setVisibility(View.GONE);
        }
        field.setCompleteView(completeView);
        return field;
    }

    private View buildCompleteView(AFField field, Skin skin){
        LinearLayout fullLayout = new LinearLayout(field.getLabel().getContext());
        fullLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //set orientation of label and field itself
        if(field.getLayoutOrientation().equals(LayoutOrientation.AXISY)){
            fullLayout.setOrientation(LinearLayout.HORIZONTAL);
            field.getLabel().setLayoutParams(new LinearLayout.LayoutParams(skin.getLabelWidth(), skin.getLabelHeight()));
            field.getFieldView().setLayoutParams(new LinearLayout.LayoutParams(skin.getInputWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));
        }else if(field.getLayoutOrientation().equals(LayoutOrientation.AXISX)){
            fullLayout.setOrientation(LinearLayout.VERTICAL);
            field.getLabel().setLayoutParams(new LinearLayout.LayoutParams(skin.getLabelWidth(), skin.getLabelHeight()));
            field.getFieldView().setLayoutParams(new LinearLayout.LayoutParams(skin.getInputWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));
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
