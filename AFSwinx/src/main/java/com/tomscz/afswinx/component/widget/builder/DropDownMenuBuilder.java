package com.tomscz.afswinx.component.widget.builder;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.AFOptions;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.component.widget.builder.abstraction.BaseWidgetBuilder;
import com.tomscz.afswinx.localization.LocalizationUtils;
import com.tomscz.afswinx.rest.rebuild.holder.AFData;

/**
 * This is builder which can build drop down menu component.
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public class DropDownMenuBuilder extends BaseWidgetBuilder {

    public static final int DEFAULT_WIDTH = 20;

    public DropDownMenuBuilder() {
        widgetType = SupportedWidgets.DROPDOWNMENU;
    }

    @Override
    public AFSwinxPanel buildComponent(AFFieldInfo fieldInfo) throws IllegalArgumentException,
            AFSwinxBuildException {
        super.buildBase(fieldInfo);
        AFOptions[] dataToCombo;
        // If this field is not required then add dummy field
        if (!fieldInfo.required()) {
            super.addDummyFieldOption(fieldInfo);
        }
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
            dataToCombo = new AFOptions[0];
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
            AFOptions option = new AFOptions(data.getValue(), data.getValue());
            String valueLocalized =
                    LocalizationUtils
                            .getTextFromExtendBundle(option.getValue(), localization, null);
            option.setValue(valueLocalized);
            for (int i = 0; i < comboBox.getModel().getSize(); i++) {
                AFOptions optionInComboBox = comboBox.getModel().getElementAt(i);
                if (optionInComboBox.equals(option)) {
                    comboBox.setSelectedItem(optionInComboBox);
                }
            }
            // Verify if data were selected
            AFOptions selectedOption = getSelectedOption(panel);
            if (selectedOption == null || !selectedOption.equals(option)) {
                AFOptions[] dataToCombo = new AFOptions[comboBox.getModel().getSize() + 1];
                for (int i = 0; i < comboBox.getModel().getSize(); i++) {
                    AFOptions optionInComboBox = comboBox.getModel().getElementAt(i);
                    dataToCombo[i] = optionInComboBox;
                }
                dataToCombo[dataToCombo.length - 1] = option;
                comboBox.setModel(new DefaultComboBoxModel<>(dataToCombo));
                comboBox.setSelectedItem(option);
            }
        }
    }

    @Override
    public Object getData(AFSwinxPanel panel) {
        if (panel.getDataHolder() != null && !panel.getDataHolder().isEmpty()) {
            AFOptions selectedItem = getSelectedOption(panel);
            if (selectedItem != null) {
                return selectedItem.getKey();
            }
        }
        return null;
    }

    @Override
    public Object getPlainData(AFSwinxPanel panel) {
        AFOptions selectedItem = getSelectedOption(panel);
        if (panel.getDataHolder() != null && !panel.getDataHolder().isEmpty()
                && selectedItem != null) {
            return selectedItem.getValue();
        }
        return null;
    }

    private AFOptions getSelectedOption(AFSwinxPanel panel) {
        if (panel.getDataHolder() != null && !panel.getDataHolder().isEmpty()) {
            @SuppressWarnings("unchecked")
            JComboBox<AFOptions> comboBox = (JComboBox<AFOptions>) panel.getDataHolder().get(0);
            AFOptions selectedItem = (AFOptions) comboBox.getSelectedItem();
            return selectedItem;
        }
        return null;
    }

}
