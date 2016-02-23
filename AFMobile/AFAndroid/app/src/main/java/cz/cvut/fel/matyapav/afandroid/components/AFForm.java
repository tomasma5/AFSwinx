package cz.cvut.fel.matyapav.afandroid.components;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fel.matyapav.afandroid.builders.widgets.FieldBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.types.BasicBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.FieldBuilderFactory;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutDefinitions;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;
import cz.cvut.fel.matyapav.afandroid.rest.AFSwinxConnection;
import cz.cvut.fel.matyapav.afandroid.rest.BaseRestBuilder;
import cz.cvut.fel.matyapav.afandroid.rest.RequestTask;
import cz.cvut.fel.matyapav.afandroid.rest.RestBuilderFactory;
import cz.cvut.fel.matyapav.afandroid.rest.holder.AFDataHolder;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * Created by Pavel on 26.12.2015.
 */
public class AFForm extends AFComponent {

    public AFForm(Activity activity, AFSwinxConnection modelConnection, AFSwinxConnection dataConnection, AFSwinxConnection sendConnection) {
        super(activity, modelConnection, dataConnection, sendConnection);
    }

    public AFForm(String name, ViewGroup view, LayoutDefinitions layoutDefinitions, LayoutOrientation layoutOrientation) {
        super(name, view, layoutDefinitions, layoutOrientation);
    }

    @Override
    protected AFDataHolder reserialize() {
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

    @Override
    SupportedComponents getComponentType() {
        return SupportedComponents.FORM;
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

    public boolean validateData(){
        boolean allValidationsFine = true;
        for(AFField field : getFields()){
            if(!field.validate()){
                allValidationsFine = false;
            }
        }
        return allValidationsFine;
    }

    /*TROCHU POUPRAVENA MARTINOVA METODA*/
    public void sendData() throws Exception{
        if (getSendConnection() == null) {
            throw new IllegalStateException(
                    "The post connection was not specify. Check your XML configuration or Connection which was used to build this form");
        }
        Object data = generateSendData();
        if (data == null) {
            return;
        }
        System.err.println("SEND CONNECTION "+ Utils.getConnectionEndPoint(getSendConnection()));
        RequestTask sendTask = new RequestTask(getActivity(),getSendConnection().getHttpMethod(), getSendConnection().getContentType(),
                getSendConnection().getSecurity(), data, Utils.getConnectionEndPoint(getSendConnection()));
        Object response = sendTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        if(response instanceof Exception){
            throw (Exception) response;
        }
    }

    /*MARTINOVA METODA*/
    private Object generateSendData() {
        // before building data and sending, validate actual data
        boolean isValid = validateData();
        if (!isValid) {
            return null;
        }
        AFSwinxConnection sendConnection = getSendConnection();
        // Generate send connection based on which will be retrieve data. The send connection is
        // used to generate data in this case it will be generated JSON
        if (sendConnection == null) {
            sendConnection = new AFSwinxConnection("", 0, "");
        }
        BaseRestBuilder dataBuilder = RestBuilderFactory.getInstance().getBuilder(sendConnection);
        Object data = dataBuilder.reselialize(reserialize());
        return data;
    }

    public void resetData() {
        for (AFField field: getFields()) {
            BasicBuilder builder = FieldBuilderFactory.getInstance().getFieldBuilder(field.getFieldInfo());
            builder.setData(field, field.getActualData());
        }
    }
}
