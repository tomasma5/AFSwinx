package cz.cvut.fel.matyapav.afandroid.components;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fel.matyapav.afandroid.builders.InputFieldBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.BasicBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.FieldBuilderFactory;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutDefinitions;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;
import cz.cvut.fel.matyapav.afandroid.rest.AFSwinxConnection;
import cz.cvut.fel.matyapav.afandroid.rest.holder.AFDataHolder;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * Created by Pavel on 26.12.2015.
 */
public class AFForm extends AFComponent {

    private List<AFField> fields;

    public AFForm(Activity activity, AFSwinxConnection modelConnection, AFSwinxConnection dataConnection, AFSwinxConnection sendConnection) {
        super(activity, modelConnection, dataConnection, sendConnection);
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

    public boolean validateData(){
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

    @Override
    public AFDataHolder reserialize() {
        AFDataHolder dataHolder = new AFDataHolder();
        for (AFField field : getFields()) {

            BasicBuilder fieldBuilder =
                    FieldBuilderFactory.getInstance().getFieldBuilder(field.getFieldInfo());
            Object data = fieldBuilder.getData(field);
            String propertyName = field.getId();
            // Based on dot notation determine road. Road is used to add object to its right place
            String[] roadTrace = propertyName.split("\\.");
            if (roadTrace.length > 1) {
                AFDataHolder startPoint = dataHolder;
                for (int i = 0; i < roadTrace.length; i++) {
                    String roadPoint = roadTrace[i];
                    // If road end then add this property as inner propety
                    if (i + 1 == roadTrace.length) {
                        startPoint.addPropertyAndValue(roadPoint, (String) data);
                    } else {
                        // Otherwise it will be inner class so add if doesn't exist continue.
                        AFDataHolder roadHolder = startPoint.getInnerClassByKey(roadPoint);
                        if (roadHolder == null) {
                            roadHolder = new AFDataHolder();
                            roadHolder.setClassName(roadPoint);
                            startPoint.addInnerClass(roadHolder);
                        }
                        // Set start point on current possition in tree
                        startPoint = roadHolder;
                    }
                }
            } else {
                dataHolder.addPropertyAndValue(propertyName, (String) data);
            }
        }
        return dataHolder;
    }


}
