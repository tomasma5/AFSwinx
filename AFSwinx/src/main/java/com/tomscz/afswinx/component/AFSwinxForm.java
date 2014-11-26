package com.tomscz.afswinx.component;

import java.util.List;

import com.tomscz.afrest.commons.SupportedComponents;
import com.tomscz.afrest.rest.dto.data.AFData;
import com.tomscz.afrest.rest.dto.data.AFDataPack;
import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.component.builders.ComponentDataPacker;
import com.tomscz.afswinx.component.factory.WidgetBuilderFactory;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.component.widget.builder.FieldBuilder;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataHolder;
import com.tomscz.afswinx.validation.exception.ValidationException;

public class AFSwinxForm extends AFSwinxTopLevelComponent {

    private static final long serialVersionUID = 1L;

    public AFSwinxForm(AFSwinxConnection modelConnection, AFSwinxConnection dataConnection,
            AFSwinxConnection postConnection) {
        this.modelConnection = modelConnection;
        this.dataConnection = dataConnection;
        this.postConnection = postConnection;
    }

    @Override
    public SupportedComponents getComponentType() {
        return SupportedComponents.FORM;
    }

    @Override
    public void fillData(List<AFDataPack> dataPack) {
        AFDataPack dataToSet = dataPack.get(0);
        if (dataToSet.getClassName().isEmpty()) {
            return;
        }
        for (AFData field : dataToSet.getData()) {
            String fieldName = field.getKey();
            ComponentDataPacker dataPacker = getPanels().get(fieldName);
            if(dataPacker == null){
                continue;
            }
            AFSwinxPanel panelToSetData = dataPacker.getComponent();
            FieldBuilder builder =
                    WidgetBuilderFactory.getInstance().createWidgetBuilder(
                            panelToSetData.getWidgetType());
            builder.setData(panelToSetData, field);
        }
    }

    @Override
    public boolean validateData() {
        boolean isValid = true;
        for (String key : getPanels().keySet()) {
            // Validate all records and show all error message
            ComponentDataPacker dataPacker = getPanels().get(key);
            AFSwinxPanel panel = dataPacker.getComponent();
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

    @Override
    public AFDataHolder resealize() {
        AFDataHolder dataHolder = new AFDataHolder();
        for (String key : getPanels().keySet()) {
            ComponentDataPacker dataPacker = getPanels().get(key);
            AFSwinxPanel panel = dataPacker.getComponent();
            FieldBuilder fieldBuilder =
                    WidgetBuilderFactory.getInstance().createWidgetBuilder(panel.getWidgetType());
            Object data = fieldBuilder.getData(panel);
            String propertyName = panel.getPanelId();
            String[] roadTrace = propertyName.split("\\.");
            if (roadTrace.length > 1) {
                AFDataHolder startPoint = dataHolder;
                for (int i = 0; i < roadTrace.length; i++) {
                    String roadPoint = roadTrace[i];
                    if (i + 1 == roadTrace.length) {
                        startPoint.addPropertyAndValue(roadPoint, (String) data);
                    } else {
                        AFDataHolder roadHolder = startPoint.getInnerClassByKey(roadPoint);
                        if (roadHolder == null) {
                            roadHolder = new AFDataHolder();
                            roadHolder.setClassName(roadPoint);
                            startPoint.addInnerClass(roadHolder);
                        }
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
