package cz.cvut.fel.matyapav.afandroid.components.parts;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cz.cvut.fel.matyapav.afandroid.components.validators.MaxCharsValidator;
import cz.cvut.fel.matyapav.afandroid.components.validators.RequiredValidator;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.afandroid.enums.LabelPosition;
import cz.cvut.fel.matyapav.afandroid.utils.Constants;

/**
 * This class defines GUI of the field + operations with it like validation
 */
public class AFField {

    private String id;
    private TextView label;
    private String labelPosition = LabelPosition.BEFORE.getPosition(); //DEFAULT
    private View field;
    private TextView errorView;
    private List<ValidationRule> validations;

    public boolean validate() {
        boolean allValidationsFine = true;
        StringBuilder errorMsgs = new StringBuilder();
        errorView.setVisibility(View.INVISIBLE);
        if(validations != null) {
            for (ValidationRule rule : validations) {
                if (rule.getValidationType().equals(Constants.REQUIRED) && Boolean.valueOf(rule.getValue())) {
                    allValidationsFine = new RequiredValidator().validate(field, errorMsgs, rule);
                }
                if (rule.getValidationType().equals(Constants.MAXLENGHT)) {
                    allValidationsFine = new MaxCharsValidator().validate(field, errorMsgs, rule);
                }
            }
        }
        if(!allValidationsFine){
            errorView.setText(errorMsgs.toString());
            errorView.setVisibility(View.VISIBLE);
        }
        return allValidationsFine;
    }

    public TextView getLabel() {
        return label;
    }

    public void setLabel(TextView label) {
        this.label = label;
    }

    public View getField() {
        return field;
    }

    public void setField(View field) {
        this.field = field;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ValidationRule> getValidations() {
        return validations;
    }

    public void setValidations(List<ValidationRule> validations) {
        this.validations = validations;
    }

    public void setLabelPosition(String labelPosition) {
        this.labelPosition = labelPosition;
    }

    public String getLabelPosition() {
        return labelPosition;
    }

    public TextView getErrorView() {
        return errorView;
    }

    public void setErrorView(TextView errorView) {
        this.errorView = errorView;
    }

    public View getView(){
        LinearLayout fieldWithLabel = new LinearLayout(label.getContext());
        fieldWithLabel.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        //LABEL BEFORE

        if(label != null && !labelPosition.equals(LabelPosition.NONE.getPosition())) {
            if (labelPosition.equals(LabelPosition.BEFORE.getPosition())) {
                fieldWithLabel.addView(label);
            }
        }
        if(field != null) {
            fieldWithLabel.addView(field);
        }
        //LABEL AFTER
        if(label != null && !labelPosition.equals(LabelPosition.NONE.getPosition())) {
            if (labelPosition.equals(LabelPosition.AFTER.getPosition())) {
                fieldWithLabel.addView(label);
            }
        }
        //add errorview under fields
        LinearLayout fullLayoutWithErrors = new LinearLayout(label.getContext());
        fullLayoutWithErrors.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        fullLayoutWithErrors.setOrientation(LinearLayout.VERTICAL);
        fullLayoutWithErrors.addView(fieldWithLabel);
        fullLayoutWithErrors.addView(getErrorView());
        return fullLayoutWithErrors;

    }


}
