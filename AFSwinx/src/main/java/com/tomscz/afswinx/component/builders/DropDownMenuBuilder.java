package com.tomscz.afswinx.component.builders;

import javax.swing.JComboBox;

import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.AFOptions;
import com.tomscz.afrest.rest.dto.data.AFData;
import com.tomscz.afswinx.component.builders.abstraction.BaseComponentsBuilder;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.localization.LocalizationUtils;

public class DropDownMenuBuilder extends BaseComponentsBuilder {

    @Override
    public AFSwinxPanel buildComponent(AFFieldInfo fieldInfo) throws IllegalArgumentException {
        super.buildBase(fieldInfo); 
        AFOptions[] dataToCombo;
        if(fieldInfo.getOptions() != null){
            dataToCombo = fieldInfo.getOptions().toArray(new AFOptions[fieldInfo.getOptions().size()]);
            for(AFOptions option :dataToCombo){
                String valueLocalized = LocalizationUtils.getTextFromExtendBundle(option.getValue(), localization, null);
                option.setValue(valueLocalized);
            }
        }
        else{
            dataToCombo = new AFOptions[1];
        }
        JComboBox<AFOptions> comboBox = new JComboBox<AFOptions>(dataToCombo);
        layoutBuilder.addComponent(comboBox);
        coreComponent = comboBox;
        //Create panel which holds all necessary informations
        AFSwinxPanel afPanel = new AFSwinxPanel(fieldInfo.getId(),fieldInfo.getWidgetType(),comboBox, fieldLabel,message);
        //Build layout on that panel
        layoutBuilder.buildLayout(afPanel);
        //Add validations
        super.crateValidators(afPanel, fieldInfo);
        return afPanel;
    }

    @Override
    public void setData(AFSwinxPanel panel, AFData data) {
        if (panel.getDataHolder() != null) {
            @SuppressWarnings("unchecked")
            JComboBox<AFOptions> comboBox = (JComboBox<AFOptions>) panel.getDataHolder();
            AFOptions option = new AFOptions(data.getKey(), data.getValue());
            comboBox.setSelectedItem(option);
        }

    }

    @Override
    public Object getData(AFSwinxPanel panel) {
        if (panel.getDataHolder() != null) {
            @SuppressWarnings("unchecked")
            JComboBox<AFOptions> comboBox = (JComboBox<AFOptions>) panel.getDataHolder();
            AFOptions selectedItem = (AFOptions) comboBox.getSelectedItem();
            return selectedItem.getKey();
        }
        return null;
    }

}
