package cz.cvut.fel.matyapav.afandroid;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cz.cvut.fel.matyapav.afandroid.enums.LabelPosition;

/**
 * This class defines GUI of the field + operations with it like validation
 */
public class AFField {

    private String id;
    private TextView label;
    private String labelPosition = LabelPosition.BEFORE.getPosition(); //DEFAULT
    private EditText field;
    private TextView errorView;
    private List<ValidationRule> validations;

    public boolean validate() {
        boolean allValidationsFine = true;
        StringBuilder errorMsgs = new StringBuilder();
        errorView.setVisibility(View.INVISIBLE);
        if(validations != null) {
            for (ValidationRule rule : validations) {
                if (rule.getValidationType().equals(Constants.REQUIRED) && Boolean.valueOf(rule.getValue())) {
                    if (field.getText() == null || field.getText().toString().isEmpty()) {
                        errorMsgs.append("This field is required");
                        allValidationsFine = false;
                    }
                }
                if (rule.getValidationType().equals(Constants.MAXLENGHT)) {
                    if (field.getText() != null && field.getText().toString().length() > Integer.parseInt(rule.getValue())) {
                        errorMsgs.append("Maximum chars :" + rule.getValue());
                        allValidationsFine = false;
                    }
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

    public EditText getField() {
        return field;
    }

    public void setField(EditText field) {
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

        fieldWithLabel.addView(field);
        fieldWithLabel.addView(errorView);

        //LABEL AFTER
        if(label != null && !labelPosition.equals(LabelPosition.NONE.getPosition())) {
            if (labelPosition.equals(LabelPosition.AFTER.getPosition())) {
                fieldWithLabel.addView(label);
            }
        }

        return fieldWithLabel;

    }


}
