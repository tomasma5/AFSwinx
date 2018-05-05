package cz.cvut.fel.matyapav.afandroid.builders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import cz.cvut.fel.matyapav.afandroid.builders.skins.Skin;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.AbstractWidgetBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.WidgetBuilderFactory;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.FieldInfo;
import cz.cvut.fel.matyapav.afandroid.components.parts.LayoutProperties;
import cz.cvut.fel.matyapav.afandroid.enums.LabelPosition;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;

/**
 * Builds input field
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class FieldBuilder {

    public AFField prepareField(FieldInfo properties, StringBuilder road, Context context, Skin skin) {

        AFField field = new AFField(context, properties);
        field.setId(road.toString()+properties.getId());

        //LABEL
        TextView label = new TextView(context);
        if (properties.getLabelText() != null && !properties.getLabelText().isEmpty()) {
            String labelText = Localization.translate(context, properties.getLabelText());
            //set label position
            LabelPosition pos = properties.getLayout().getLabelPosition();
            label.setTextColor(skin.getLabelColor());
            label.setTypeface(skin.getLabelFont());
            label.setText(labelText);
            field.setLabel(label);
        }

        //ERROR TEXT
        TextView errorView = new TextView(context);
        errorView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        errorView.setVisibility(View.GONE);
        errorView.setTextColor(skin.getValidationColor());
        errorView.setTypeface(skin.getValidationFont());
        field.setErrorView(errorView);

        //Input view
        View widget = null;
        AbstractWidgetBuilder widgetBuilder = WidgetBuilderFactory.getInstance().getFieldBuilder(context, properties, skin);
        if(widgetBuilder != null && (widget = widgetBuilder.buildFieldView(context))!= null){
            field.setFieldView(widget);
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
        LinearLayout fullLayout = new LinearLayout(field.getFieldView().getContext());
        fullLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        LayoutProperties layout = field.getFieldInfo().getLayout();
        //set orientation of label and field itself
        if(layout.getLayoutOrientation() != null){
            if(layout.getLayoutOrientation().equals(LayoutOrientation.AXISY)){
                fullLayout.setOrientation(LinearLayout.HORIZONTAL);
            }else if(layout.getLayoutOrientation().equals(LayoutOrientation.AXISX)){
                fullLayout.setOrientation(LinearLayout.VERTICAL);
            }
        }else{
            fullLayout.setOrientation(LinearLayout.VERTICAL); //default
        }

        //set label and field view layout params
        if(field.getLabel() != null) {
            field.getLabel().setLayoutParams(new LinearLayout.LayoutParams(skin.getLabelWidth(), skin.getLabelHeight()));
        }
        field.getFieldView().setLayoutParams(new LinearLayout.LayoutParams(skin.getInputWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));

        //LABEL BEFORE
        if (field.getLabel() != null && layout.getLabelPosition() != null && !layout.getLabelPosition().equals(LabelPosition.NONE)) {
            if (layout.getLabelPosition().equals(LabelPosition.BEFORE)) {
                fullLayout.addView(field.getLabel());
            }
        }else if(field.getLabel() != null && layout.getLabelPosition() == null){
            fullLayout.addView(field.getLabel()); //default is before
        }

        if(field.getFieldView() != null) {
            fullLayout.addView(field.getFieldView());
        }
        //LABEL AFTER
        if(field.getLabel() != null && layout.getLabelPosition() != null && !layout.getLabelPosition().equals(LabelPosition.NONE)) {
            if (layout.getLabelPosition().equals(LabelPosition.AFTER)) {
                fullLayout.addView(field.getLabel());
            }
        }

        //add errorview on the top of field
        LinearLayout fullLayoutWithErrors = new LinearLayout(field.getFieldView().getContext());
        fullLayoutWithErrors.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        fullLayoutWithErrors.setPadding(0, 0, 10, 10);
        fullLayoutWithErrors.setOrientation(LinearLayout.VERTICAL);
        fullLayoutWithErrors.addView(field.getErrorView());
        fullLayoutWithErrors.addView(fullLayout);
        return fullLayoutWithErrors;
    }

}
