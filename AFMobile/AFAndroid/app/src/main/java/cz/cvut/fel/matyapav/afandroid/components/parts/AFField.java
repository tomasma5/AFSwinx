package cz.cvut.fel.matyapav.afandroid.components.parts;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import cz.cvut.fel.matyapav.afandroid.components.parts.validators.AFValidator;
import cz.cvut.fel.matyapav.afandroid.components.parts.validators.ValidatorFactory;
import cz.cvut.fel.matyapav.afandroid.components.types.AFComponent;


/**
 * This class defines GUI of the field + operations with it like validation
 */
public class AFField {

    private FieldInfo fieldInfo;
    private String id;
    private TextView label;

    private View fieldView;
    private TextView errorView;
    private View completeView;
    private Object actualData;

    private AFComponent parent;
    private Context context;

    public AFField(Context context, FieldInfo fieldInfo) {
        this.context = context;
        this.fieldInfo = fieldInfo;
    }

    public boolean validate() {
        boolean allValidationsFine = true;
        StringBuilder errorMsgs = new StringBuilder();
        errorView.setVisibility(View.GONE);
        if (fieldInfo.getRules() != null) {
            for (ValidationRule rule : fieldInfo.getRules()) {
                AFValidator validator = ValidatorFactory.getInstance().getValidator(rule);
                System.out.println("VALIDATION RULE " + rule.toString());
                System.out.println("VALIDATOR " + validator.toString());
                if(validator != null) {
                    boolean validationResult = validator.validate(context, this, errorMsgs, rule);
                    if (allValidationsFine) { //if once false stays false
                        allValidationsFine = validationResult;
                    }
                }
                System.out.println("RESULT " + allValidationsFine);
            }
        }
        if (!allValidationsFine) {
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

    public TextView getErrorView() {
        return errorView;
    }

    public void setErrorView(TextView errorView) {
        this.errorView = errorView;
    }

    public void setCompleteView(View view) {
        this.completeView = view;
    }

    public View getCompleteView() {
        return completeView;
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
                ", fieldView=" + fieldView +
                ", errorView=" + errorView +
                '}';
    }

    public void setActualData(Object actualData) {
        this.actualData = actualData;
    }

    public Object getActualData() {
        return actualData;
    }

    public void setParent(AFComponent parent) {
        this.parent = parent;
    }

    public AFComponent getParent() {
        return parent;
    }
}
