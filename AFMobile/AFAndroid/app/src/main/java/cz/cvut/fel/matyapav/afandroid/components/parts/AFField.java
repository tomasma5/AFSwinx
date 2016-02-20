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
import cz.cvut.fel.matyapav.afandroid.enums.LayoutDefinitions;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedWidgets;
import cz.cvut.fel.matyapav.afandroid.enums.LabelPosition;
import cz.cvut.fel.matyapav.afandroid.utils.Constants;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * This class defines GUI of the field + operations with it like validation
 */
public class AFField {

    private FieldInfo fieldInfo;
    private String id;
    private TextView label;
    private LabelPosition labelPosition = LabelPosition.BEFORE; //DEFAULT
    private LayoutDefinitions layoutDefinitions = LayoutDefinitions.ONECOLUMNLAYOUT; //DEFAULT
    private LayoutOrientation layoutOrientation = LayoutOrientation.AXISX; //DEFAULT
    private View fieldView;
    private TextView errorView;
    private View completeView;
    private List<ValidationRule> validations;


    public AFField(FieldInfo fieldInfo) {
        this.fieldInfo = fieldInfo;
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

    public void setLabelPosition(LabelPosition labelPosition) {
        this.labelPosition = labelPosition;
    }

    public LabelPosition getLabelPosition() {
        return labelPosition;
    }

    public TextView getErrorView() {
        return errorView;
    }

    public void setErrorView(TextView errorView) {
        this.errorView = errorView;
    }

    public void setCompleteView(View view){
        this.completeView = view;
    }

    public View getCompleteView() {
        return completeView;
    }

    public LayoutDefinitions getLayoutDefinitions() {
        return layoutDefinitions;
    }

    public void setLayoutDefinitions(LayoutDefinitions layoutDefinitions) {
        this.layoutDefinitions = layoutDefinitions;
    }

    public LayoutOrientation getLayoutOrientation() {
        return layoutOrientation;
    }

    public void setLayoutOrientation(LayoutOrientation layoutOrientation) {
        this.layoutOrientation = layoutOrientation;
    }



    public FieldInfo getFieldInfo() {
        return fieldInfo;
    }

    @Override
    public String toString() {
        return "AFField{" +
                "completeView=" + completeView +
                ", fieldInfo=" + fieldInfo +
                ", id='" + id + '\'' +
                ", label=" + label +
                ", labelPosition=" + labelPosition +
                ", layoutDefinitions=" + layoutDefinitions +
                ", layoutOrientation=" + layoutOrientation +
                ", fieldView=" + fieldView +
                ", errorView=" + errorView +
                ", validations=" + validations +
                '}';
    }
}
