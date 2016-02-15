package cz.cvut.fel.matyapav.afandroid.components.parts;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import cz.cvut.fel.matyapav.afandroid.components.validators.AFValidator;
import cz.cvut.fel.matyapav.afandroid.components.validators.MaxCharsValidator;
import cz.cvut.fel.matyapav.afandroid.components.validators.RequiredValidator;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedWidgets;
import cz.cvut.fel.matyapav.afandroid.enums.LabelPosition;
import cz.cvut.fel.matyapav.afandroid.utils.Constants;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * This class defines GUI of the field + operations with it like validation
 */
public class AFField {

    private SupportedWidgets widgetType;
    private String id;
    private TextView label;
    private String labelPosition = LabelPosition.BEFORE.getPosition(); //DEFAULT
    private View fieldView;
    private TextView errorView;
    private List<ValidationRule> validations;

    public AFField(SupportedWidgets widgetType) {
        this.widgetType = widgetType;
    }

    public boolean validate() {
        boolean allValidationsFine = true;
        StringBuilder errorMsgs = new StringBuilder();
        errorView.setVisibility(View.GONE);
        if(validations != null) {
            for (ValidationRule rule : validations) {
                AFValidator validator = Utils.getFieldValidator(rule);
                System.err.println("VALIDATION RULE "+rule.toString());
                System.err.println("VALIDATOR "+validator.toString());
                boolean validationResult = validator.validate(this,errorMsgs,rule);
                if(allValidationsFine){ //if once false stays false
                    allValidationsFine = validationResult;
                }
                System.err.println("RESULT "+allValidationsFine);
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

    public View getFieldView() {
        return fieldView;
    }

    public void setFieldView(View fieldView) {
        this.fieldView = fieldView;
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
        TableRow fullLayoutWithErrors = new TableRow(label.getContext());
        //add errorview on the before fields
        fieldView.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.6f));

        LinearLayout labelPlusError = new LinearLayout(label.getContext());
        labelPlusError.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,0.4f));
        labelPlusError.setOrientation(LinearLayout.VERTICAL);
        labelPlusError.addView(errorView);
        labelPlusError.addView(label);

        //LABEL BEFORE
        if(label != null && !labelPosition.equals(LabelPosition.NONE.getPosition())) {
            if (labelPosition.equals(LabelPosition.BEFORE.getPosition())) {
                fullLayoutWithErrors.addView(labelPlusError);
            }
        }
        if(fieldView != null) {
            fullLayoutWithErrors.addView(fieldView);
        }
        //LABEL AFTER
        if(label != null && !labelPosition.equals(LabelPosition.NONE.getPosition())) {
            if (labelPosition.equals(LabelPosition.AFTER.getPosition())) {
                fullLayoutWithErrors.addView(labelPlusError);
            }
        }
        return fullLayoutWithErrors;
    }

    public SupportedWidgets getWidgetType() {
        return widgetType;
    }

    @Override
    public String toString() {
        return "AFField{" +
                id + '\'' +
                ", label=" + label +
                ", labelPosition='" + labelPosition + '\'' +
                ", fieldView=" + fieldView +
                ", validations=" + validations +
                '}';
    }
}
