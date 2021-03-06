package com.tomscz.afswinx.component.widget.builder;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.AFOptions;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.component.widget.builder.abstraction.BaseWidgetBuilder;
import com.tomscz.afswinx.rest.rebuild.holder.AFData;
import com.tomscz.afswinx.swing.component.AFComponentDataHolder;
import com.tomscz.afswinx.swing.component.SwinxAFRadioButton;
import com.tomscz.afswinx.swing.component.converter.AFOptionToAFSwinxOption;

/**
 * This is builder which can build {@link SwinxAFRadioButton}.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class OptionBuilder extends BaseWidgetBuilder {

    AFOptionToAFSwinxOption converter = new AFOptionToAFSwinxOption();

    public OptionBuilder() {
        widgetType = SupportedWidgets.OPTION;
    }

    @Override
    public AFSwinxPanel buildComponent(AFFieldInfo field) throws IllegalArgumentException,
            AFSwinxBuildException {
        super.buildBase(field);
        // Create panel which holds all necessary informations
        AFSwinxPanel afPanel =
                new AFSwinxPanel(field.getId(), field.getWidgetType(), fieldLabel, message);
        ButtonGroup buttonGroup = new ButtonGroup();

        if (field.getOptions() == null || field.getOptions().isEmpty()) {
            // If there is no option, then create true and false
            AFOptions trueOption = new AFOptions("true", "true");
            AFOptions falseOption = new AFOptions("false", "false");
            field.addOption(falseOption);
            field.addOption(trueOption);
        }
        if (!field.required()) {
            super.addDummyFieldOption(field);
        }
        for (AFOptions option : field.getOptions()) {
            setOptions(field, option, buttonGroup, afPanel);
        }
        // Build layout on that panel
        layoutBuilder.buildLayout(afPanel);
        // Add validations
        super.crateValidators(afPanel, field);
        return afPanel;
    }

    private void setOptions(AFFieldInfo field, AFOptions option, ButtonGroup buttonGroup,
            AFSwinxPanel afPanel) {
        AFComponentDataHolder optionToAdd = converter.convert(option, localization);
        if (optionToAdd != null) {
            SwinxAFRadioButton<AFComponentDataHolder> radioButton =
                    new SwinxAFRadioButton<AFComponentDataHolder>(optionToAdd);
            radioButton.setText(optionToAdd.getValueToDisplay());
            buttonGroup.add(radioButton);
            layoutBuilder.addComponent(radioButton);
            afPanel.addDataHolderComponent(radioButton);
            customizeComponent(radioButton, field);
        }
    }

    @Override
    public void setData(AFSwinxPanel panel, AFData data) {
        if (panel != null && panel.getDataHolder() != null && data != null) {
            for (JComponent component : panel.getDataHolder()) {
                @SuppressWarnings("unchecked")
                SwinxAFRadioButton<AFComponentDataHolder> radioButton =
                        (SwinxAFRadioButton<AFComponentDataHolder>) component;
                AFComponentDataHolder concereteDataHolder = radioButton.getDataHolder();
                if (concereteDataHolder.getValue().equals(data.getValue())) {
                    radioButton.setSelected(true);
                    return;
                }
            }
        }
    }

    @Override
    public Object getData(AFSwinxPanel panel) {
        AFComponentDataHolder selectedObject = getSelectedObject(panel);
        if (selectedObject != null) {
            return selectedObject.getKey();
        }
        return null;
    }

    @Override
    public Object getPlainData(AFSwinxPanel panel) {
        AFComponentDataHolder selectedObject = getSelectedObject(panel);
        if (selectedObject != null) {
            return selectedObject.getValueToDisplay();
        }
        return null;
    }

    private AFComponentDataHolder getSelectedObject(AFSwinxPanel panel) {
        if (panel != null && panel.getDataHolder() != null) {
            for (JComponent component : panel.getDataHolder()) {
                @SuppressWarnings("unchecked")
                SwinxAFRadioButton<AFComponentDataHolder> radioButton =
                        (SwinxAFRadioButton<AFComponentDataHolder>) component;
                AFComponentDataHolder concereteDataHolder = radioButton.getDataHolder();
                if (radioButton.isSelected()) {
                    return concereteDataHolder;
                }
            }
        }
        return null;
    }

}
