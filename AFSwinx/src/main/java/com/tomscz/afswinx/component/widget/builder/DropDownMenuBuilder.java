package com.tomscz.afswinx.component.widget.builder;

import javax.swing.JComboBox;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.AFOptions;
import com.tomscz.afrest.rest.dto.data.AFData;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.component.widget.builder.abstraction.BaseComponentsBuilder;
import com.tomscz.afswinx.localization.LocalizationUtils;

public class DropDownMenuBuilder extends BaseComponentsBuilder {

    public DropDownMenuBuilder(){
        widgetType = SupportedWidgets.DROPDOWNMENU;
    }
    
    @Override
    public AFSwinxPanel buildComponent(AFFieldInfo fieldInfo) throws IllegalArgumentException, AFSwinxBuildException {
        super.buildBase(fieldInfo);
        AFOptions[] dataToCombo;
        if (fieldInfo.getOptions() != null) {
            dataToCombo =
                    fieldInfo.getOptions().toArray(new AFOptions[fieldInfo.getOptions().size()]);
            for (AFOptions option : dataToCombo) {
                String valueLocalized =
                        LocalizationUtils.getTextFromExtendBundle(option.getValue(), localization,
                                null);
                option.setValue(valueLocalized);
            }
        } else {
            dataToCombo = new AFOptions[1];
        }
        JComboBox<AFOptions> comboBox = new JComboBox<AFOptions>(dataToCombo);
        layoutBuilder.addComponent(comboBox);
        coreComponent = comboBox;
        customizeComponent(comboBox, fieldInfo);
        // Create panel which holds all necessary informations
        AFSwinxPanel afPanel =
                new AFSwinxPanel(fieldInfo.getId(), fieldInfo.getWidgetType(), comboBox,
                        fieldLabel, message);
        // Build layout on that panel
        layoutBuilder.buildLayout(afPanel);
        // Add validations
        super.crateValidators(afPanel, fieldInfo);
        return afPanel;
    }

    @Override
    public void setData(AFSwinxPanel panel, AFData data) {
        if (panel.getDataHolder() != null && !panel.getDataHolder().isEmpty()) {
            @SuppressWarnings("unchecked")
            JComboBox<AFOptions> comboBox = (JComboBox<AFOptions>) panel.getDataHolder().get(0);
            AFOptions option = new AFOptions(data.getKey(), data.getValue());
            comboBox.setSelectedItem(option);
        }
    }

    @Override
    public Object getData(AFSwinxPanel panel) {
        AFOptions selectedItem = getSelectedOption(panel);
        if (panel.getDataHolder() != null && !panel.getDataHolder().isEmpty()) {
            return selectedItem.getKey();
        }
        return null;
    }

    @Override
    public Object getPlainData(AFSwinxPanel panel) {
        AFOptions selectedItem = getSelectedOption(panel);
        if (panel.getDataHolder() != null && !panel.getDataHolder().isEmpty()) {
            return selectedItem.getValue();
        }
        return null;
    }
    
    private AFOptions getSelectedOption(AFSwinxPanel panel){
        if (panel.getDataHolder() != null && !panel.getDataHolder().isEmpty()) {
            @SuppressWarnings("unchecked")
            JComboBox<AFOptions> comboBox = (JComboBox<AFOptions>) panel.getDataHolder().get(0);
            AFOptions selectedItem = (AFOptions) comboBox.getSelectedItem();
            return selectedItem;
        }
        return null;
    }

}
