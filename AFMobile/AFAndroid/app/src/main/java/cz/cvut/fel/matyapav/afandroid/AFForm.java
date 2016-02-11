package cz.cvut.fel.matyapav.afandroid;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 26.12.2015.
 */
public class AFForm {

    private View view;
    private Button submitBtn;
    private String name;
    private List<AFField> fields;

    public AFForm() {
    }

    public AFForm(View formView, String name) {
        this.view = formView;
        this.name = name;
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

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public List<AFField> getFields() {
        return fields;
    }

    public void setFields(List<AFField> fields) {
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Button getSubmitBtn() {
        return submitBtn;
    }

    public void setSubmitBtn(Button submitBtn) {
        this.submitBtn = submitBtn;
    }
}
