package com.tomscz.afswinx.component;

import java.util.List;

import com.tomscz.afrest.commons.SupportedComponents;
import com.tomscz.afswinx.common.Utils;
import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.component.builders.ComponentDataPacker;
import com.tomscz.afswinx.component.factory.WidgetBuilderFactory;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.component.widget.builder.WidgetBuilder;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.rebuild.holder.AFData;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataHolder;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataPack;
import com.tomscz.afswinx.validation.exception.ValidationException;

/**
 * This class represents form. This components hold widget and is able to get model, set data to
 * model and send it back to server.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AFSwinxForm extends AFSwinxTopLevelComponent {

    private static final long serialVersionUID = 1L;

    public AFSwinxForm(AFSwinxConnection modelConnection, AFSwinxConnection dataConnection,
            AFSwinxConnection postConnection) {
        this.modelConnection = modelConnection;
        this.dataConnection = dataConnection;
        this.setSendConnection(postConnection);
    }

    @Override
    public SupportedComponents getComponentType() {
        return SupportedComponents.FORM;
    }

    @Override
    public void fillData(List<AFDataPack> dataPack) {
        if (dataPack == null || dataPack.isEmpty()) {
            return;
        }
        AFDataPack dataToSet = dataPack.get(0);
        for (AFData field : dataToSet.getData()) {
            String fieldName = field.getKey();
            ComponentDataPacker dataPacker = getPanels().get(fieldName);
            if (dataPacker == null) {
                continue;
            }
            setDataToField(dataPacker, field, fieldName);
        }
    }

    /**
     * This method find panel to which must be set data. And then call method to set it. It alsow is
     * able to set data to retype field.
     * 
     * @param componentDataPacker data packer which hold all information about concrete widget.
     * @param field which was received from server. This field contains data to set.
     * @param fieldName actual field key.
     */
    private void setDataToField(ComponentDataPacker componentDataPacker, AFData field,
            String fieldName) {
        AFSwinxPanel panelToSetData = componentDataPacker.getComponent();
        setDataToField(panelToSetData, field);
        // If this component should has been retyped, then set value to their clone
        if (panelToSetData.isRetype()) {
            componentDataPacker = getPanels().get(Utils.generateCloneKey(fieldName));
            if (componentDataPacker != null) {
                panelToSetData = componentDataPacker.getComponent();
                setDataToField(panelToSetData, field);
            }
        }
    }

    /**
     * This method fill data to concrete widget. WidgetBuilder is created based on panel and their
     * widget type.
     * 
     * @param panelToSetData to which will be set data.
     * @param field data which will be set.
     */
    private void setDataToField(AFSwinxPanel panelToSetData, AFData field) {
        WidgetBuilder builder =
                WidgetBuilderFactory.getInstance().createWidgetBuilder(
                        panelToSetData.getWidgetType());
        builder.setLocalization(localization);
        builder.setData(panelToSetData, field);
    }

    @Override
    public void clearData() {
        for (String key : getPanels().keySet()) {
            ComponentDataPacker dataPacker = getPanels().get(key);
            setDataToField(dataPacker, new AFData("", ""), key);
        }
    }

    @Override
    public boolean validateData() {
        boolean isValid = true;
        for (String key : getPanels().keySet()) {
            // Validate all records and show all error message
            ComponentDataPacker dataPacker = getPanels().get(key);
            AFSwinxPanel panel = dataPacker.getComponent();
            // If panel is not visible, then don't validate it
            if (!panel.isVisible()) {
                continue;
            }
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

    /**
     * This method display validation text, if validation failed.
     * 
     * @param panel in which will be validation text displayed.
     * @param validationException exception which holds validation message.
     */
    private void displayValidationText(AFSwinxPanel panel, ValidationException validationException) {
        if (panel.getMessage() != null) {
            panel.getMessage().setVisible(true);
            panel.getMessage().setText(validationException.getValidationTextToDisplay());
        }
    }

    /**
     * This method hide validation message. It can hide message even if it were not displayed.
     * 
     * @param panel in which will be validation message hidden.
     */
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
            // Retype panels are clones. Do not send them
            if (panel.isRetype()) {
                continue;
            }
            WidgetBuilder fieldBuilder =
                    WidgetBuilderFactory.getInstance().createWidgetBuilder(panel.getWidgetType());
            Object data = fieldBuilder.getData(panel);
            String propertyName = panel.getPanelId();
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
