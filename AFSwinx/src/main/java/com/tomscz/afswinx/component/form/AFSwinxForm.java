package com.tomscz.afswinx.component.form;

import java.util.HashMap;

import com.tomscz.afrest.commons.SupportedComponents;
import com.tomscz.afrest.rest.dto.data.AFData;
import com.tomscz.afrest.rest.dto.data.AFDataPack;
import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.component.builders.FieldBuilder;
import com.tomscz.afswinx.component.factory.WidgetBuilderFactory;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataHolder;
import com.tomscz.afswinx.validation.exception.ValidationException;

public class AFSwinxForm extends AFSwinxTopLevelComponent {

    private static final long serialVersionUID = 1L;

    private AFSwinxConnection modelConnection;
    private AFSwinxConnection postConnection;
    private AFSwinxConnection dataConnection;
    private HashMap<String, AFSwinxPanel> panels = new HashMap<String, AFSwinxPanel>();

    private SupportedComponents componentType;

    public AFSwinxForm(AFSwinxConnection modelConnection, AFSwinxConnection dataConnection,
            AFSwinxConnection postConnection) {
        this.modelConnection = modelConnection;
        this.dataConnection = dataConnection;
        this.postConnection = postConnection;
    }

    @Override
    protected SupportedComponents getComponentType() {
        return componentType;
    }

    @Override
    protected AFSwinxConnection getModelConnection() {
        return modelConnection;
    }

    @Override
    protected AFSwinxConnection getPostConnection() {
        return postConnection;
    }

    @Override
    protected AFSwinxConnection getDataConnection() {
        return dataConnection;
    }

    @Override
    public void fillData(AFDataPack dataPack) {
        if(dataPack.getClassName().isEmpty()){
            return;
        }
        for (AFData field : dataPack.getData()) {
            String fieldName = field.getKey();
            AFSwinxPanel panelToSetData = panels.get(fieldName);
            FieldBuilder builder =
                    WidgetBuilderFactory.getInstance().createWidgetBuilder(
                            panelToSetData.getWidgetType());
            builder.setData(panelToSetData, field);
        }
    }
    
    @Override
    public boolean validateData() {
        boolean isValid = true;
        for (String key : panels.keySet()) {
            // Validate all records and show all error message
            AFSwinxPanel panel = panels.get(key);
            try {
                panel.validateModel();
                // data are valid, hide error message
                hideValidationText(panel);
            } catch (ValidationException e) {
                // date are invalid display error message
                this.displayValidationText(panel, e);
                isValid = false;
            }
        }
        return isValid;
    }

    private void displayValidationText(AFSwinxPanel panel, ValidationException e) {
        if (panel.getMessage() != null) {
            panel.getMessage().setVisible(true);
            panel.getMessage().setText(e.getValidationTextToDisplay());
        }
    }

    private void hideValidationText(AFSwinxPanel panel) {
        if (panel.getMessage() != null) {
            panel.getMessage().setVisible(false);
            panel.getMessage().setText("");
        }
    }

    public HashMap<String, AFSwinxPanel> getPanels() {
        return panels;
    }

    @Override
    public AFDataHolder resealize() {
        AFDataHolder dataHolder = new AFDataHolder();
//        dataHolder.setClassName(getName());
        for (String key : panels.keySet()) {
            AFSwinxPanel panel = panels.get(key); 
            FieldBuilder fieldBuilder = WidgetBuilderFactory.getInstance()
            .createWidgetBuilder(panel.getWidgetType());
            Object data =fieldBuilder.getData(panel);
            String propertyName = panel.getPanelId();
            dataHolder.addPropertyAndValue(propertyName, (String)data);
        }
        // TODO Auto-generated method stub
        return dataHolder ;
    }
    
}
