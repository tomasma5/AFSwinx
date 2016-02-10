package cz.cvut.fel.matyapav.afandroid;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 26.12.2015.
 */
public class AFForm {

    private View formView;
    private List<AFField> fields;

    public AFForm() {
    }

    public AFForm(View formView) {
        this.formView = formView;
    }

    public void addField(AFField field){
        if(fields == null){
            fields = new ArrayList<AFField>();
        }
        fields.add(field);
    }

    public boolean validateForm(){
        boolean allValidationsFine = true;
        for(AFField field : fields){
            if(!field.validate()){
                allValidationsFine = false;
            }
        }
        return allValidationsFine;
    }

    public View getFormView() {
        return formView;
    }

    public void setFormView(View formView) {
        this.formView = formView;
    }

    public List<AFField> getFields() {
        return fields;
    }

    public void setFields(List<AFField> fields) {
        this.fields = fields;
    }
}
