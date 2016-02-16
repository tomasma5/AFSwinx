package cz.cvut.fel.matyapav.afandroid.components;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutDefinitions;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;

/**
 * Created by Pavel on 26.12.2015.
 */
public class AFForm extends AFComponent {

    private List<AFField> fields;

    public AFForm() {
    }

    public AFForm(String name, View view, LayoutDefinitions layoutDefinitions, LayoutOrientation layoutOrientation) {
        super(name, view, layoutDefinitions, layoutOrientation);
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

    @Override
    SupportedComponents getComponentType() {
        return SupportedComponents.FORM;
    }

    public List<AFField> getFields() {
        return fields;
    }

    public void setFields(List<AFField> fields) {
        this.fields = fields;
    }

    public AFField getFieldById(String id){
        for (AFField field: getFields()) {
            if(field.getId().equals(id)){
                return field;
            }
        }
        //not found
        return null;
    }

}
