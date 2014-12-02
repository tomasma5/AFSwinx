package com.tomscz.afswinx.component.widget.builder;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.AFOptions;
import com.tomscz.afrest.rest.dto.data.AFData;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.component.widget.builder.abstraction.BaseComponentsBuilder;
import com.tomscz.afswinx.swing.component.AFOptionToAFSwinxOption;
import com.tomscz.afswinx.swing.component.AFComponentDataHolder;
import com.tomscz.afswinx.swing.component.JRadioButton;

/**
 * This is builder which can build {@link JRadioButton}.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class OptionBuilder extends BaseComponentsBuilder {

    public OptionBuilder() {
        widgetType = SupportedWidgets.OPTION;
    }


    @Override
    public AFSwinxPanel buildComponent(AFFieldInfo field) throws IllegalArgumentException {
        super.buildBase(field);
        // Create panel which holds all necessary informations
        AFSwinxPanel afPanel =
                new AFSwinxPanel(field.getId(), field.getWidgetType(), fieldLabel, message);
        ButtonGroup buttonGroup = new ButtonGroup();
        if (field.getOptions() != null) {
            AFOptionToAFSwinxOption converter = new AFOptionToAFSwinxOption();
            for (AFOptions option : field.getOptions()) {
                AFComponentDataHolder optionToAdd = converter.convert(option, localization);
                if (optionToAdd != null) {
                    JRadioButton<AFComponentDataHolder> radioButton =
                            new JRadioButton<AFComponentDataHolder>(optionToAdd);
                    radioButton.setText(optionToAdd.getValueToDisplay());
                    buttonGroup.add(radioButton);
                    layoutBuilder.addComponent(radioButton);
                    afPanel.addDataHolderComponent(radioButton);
                    customizeComponent(radioButton, field);
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
        if (panel != null && panel.getDataHolder() != null && data != null) {
            for (JComponent component : panel.getDataHolder()) {
                @SuppressWarnings("unchecked")
                JRadioButton<AFComponentDataHolder> radioButton =
                        (JRadioButton<AFComponentDataHolder>) component;
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
                JRadioButton<AFComponentDataHolder> radioButton =
                        (JRadioButton<AFComponentDataHolder>) component;
                AFComponentDataHolder concereteDataHolder = radioButton.getDataHolder();
                if (radioButton.isSelected()) {
                    return concereteDataHolder;
                }
            }
        }
        return null;
    }


}
