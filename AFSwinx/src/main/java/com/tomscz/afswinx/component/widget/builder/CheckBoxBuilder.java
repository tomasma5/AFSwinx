package com.tomscz.afswinx.component.widget.builder;

import javax.swing.JComponent;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.AFOptions;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.component.widget.builder.abstraction.BaseWidgetBuilder;
import com.tomscz.afswinx.rest.rebuild.holder.AFData;
import com.tomscz.afswinx.swing.component.AFComponentDataHolder;
import com.tomscz.afswinx.swing.component.SwinxAFCheckBox;
import com.tomscz.afswinx.swing.component.converter.AFOptionToAFSwinxOption;

/**
 * This is builder which can build {@link SwinxAFCheckBox} component.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class CheckBoxBuilder extends BaseWidgetBuilder {

    public CheckBoxBuilder() {
        widgetType = SupportedWidgets.CHECKBOX;
    }

    @Override
    public AFSwinxPanel buildComponent(AFFieldInfo field) throws IllegalArgumentException,
            AFSwinxBuildException {
        super.buildBase(field);
        // Create panel which holds all necessary informations
        AFSwinxPanel afPanel =
                new AFSwinxPanel(field.getId(), field.getWidgetType(), fieldLabel, message);
        // If this field is not required then add dummy field
        if (field.getOptions() == null || field.getOptions().isEmpty()) {
            AFComponentDataHolder option = new AFComponentDataHolder("true", "true", "true");
            SwinxAFCheckBox<AFComponentDataHolder> checkBox =
                    new SwinxAFCheckBox<AFComponentDataHolder>(option);
            layoutBuilder.addComponent(checkBox);
            afPanel.addDataHolderComponent(checkBox);
            customizeComponent(checkBox, field);
        } else {
            if (!field.required()) {
                super.addDummyFieldOption(field);
            }
        }
        AFOptionToAFSwinxOption converter = new AFOptionToAFSwinxOption();
        if (field.getOptions() != null) {
            for (AFOptions option : field.getOptions()) {
                AFComponentDataHolder optionToAdd = converter.convert(option, localization);
                if (optionToAdd != null) {
                    SwinxAFCheckBox<AFComponentDataHolder> checkBox =
                            new SwinxAFCheckBox<AFComponentDataHolder>(optionToAdd);
                    checkBox.setText(optionToAdd.getValueToDisplay());
                    layoutBuilder.addComponent(checkBox);
                    afPanel.addDataHolderComponent(checkBox);
                    customizeComponent(checkBox, field);
                }
            }
        }

        // Build layout on that panel
        layoutBuilder.buildLayout(afPanel);
        // Add validations
        super.crateValidators(afPanel, field);
        return afPanel;
    }

    @Override
    public void setData(AFSwinxPanel panel, AFData data) {
        if (panel.getDataHolder() != null && data.getValue() != null
                && !panel.getDataHolder().isEmpty()) {
            for (JComponent component : panel.getDataHolder()) {
                @SuppressWarnings("unchecked")
                SwinxAFCheckBox<AFComponentDataHolder> checkBox =
                        (SwinxAFCheckBox<AFComponentDataHolder>) component;
                AFComponentDataHolder dataHolder = checkBox.getDataHolder();
                if (dataHolder.getKey().equals(data.getValue())) {
                    checkBox.setSelected(true);
                } else {
                    checkBox.setSelected(false);
                }
            }
        }
    }

    @Override
    public Object getData(AFSwinxPanel panel) {
        AFComponentDataHolder dataHolder = getSelectedDataObject(panel);
        if (dataHolder != null) {
            return String.valueOf(dataHolder.getKey());
        }
        return null;
    }

    @Override
    public Object getPlainData(AFSwinxPanel panel) {
        AFComponentDataHolder dataHolder = getSelectedDataObject(panel);
        if (dataHolder != null) {
            return String.valueOf(dataHolder.getValueToDisplay());
        } else if (panel.getDataHolder() != null && panel.getDataHolder().size() > 0) {
            @SuppressWarnings("unchecked")
            SwinxAFCheckBox<AFComponentDataHolder> checkBox =
                    (SwinxAFCheckBox<AFComponentDataHolder>) panel.getDataHolder().get(0);
            if (checkBox.getDataHolder().getValueToDisplay().equals("true")) {
                return "false";
            }
        }
        return null;
    }

    private AFComponentDataHolder getSelectedDataObject(AFSwinxPanel panel) {
        if (panel.getDataHolder() != null && !panel.getDataHolder().isEmpty()) {
            for (JComponent component : panel.getDataHolder()) {
                @SuppressWarnings("unchecked")
                SwinxAFCheckBox<AFComponentDataHolder> checkBox =
                        (SwinxAFCheckBox<AFComponentDataHolder>) component;
                AFComponentDataHolder dataHolder = checkBox.getDataHolder();
                if (checkBox.isSelected()) {
                    return dataHolder;
                }
            }
        }
        return null;
    }

}
