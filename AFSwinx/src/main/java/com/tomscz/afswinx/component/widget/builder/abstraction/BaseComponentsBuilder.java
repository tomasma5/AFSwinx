package com.tomscz.afswinx.component.widget.builder.abstraction;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ResourceBundle;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sourceforge.jdatepicker.impl.JDatePickerImpl;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.AFValidationRule;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.component.skin.Skin;
import com.tomscz.afswinx.component.widget.builder.InputFieldBuilder;
import com.tomscz.afswinx.component.widget.builder.WidgetBuilder;
import com.tomscz.afswinx.localization.LocalizationUtils;
import com.tomscz.afswinx.validation.AFValidations;
import com.tomscz.afswinx.validation.factory.AFValidatorFactory;

/**
 * This class is abstract field builder which provide some logic to their children.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public abstract class BaseComponentsBuilder implements WidgetBuilder {

    protected Component coreComponent;
    protected BaseLayoutBuilder layoutBuilder;
    protected JTextArea message;
    protected JLabel fieldLabel;
    protected ResourceBundle localization;
    protected Skin skin;
    protected SupportedWidgets widgetType;

    @Override
    public boolean isBuildAvailable(AFFieldInfo fieldWithLabel) {
        if (fieldWithLabel != null) return true;
        return false;
    }

    /**
     * This method create simple {@link JLabel}, if there is no text then null is returned. It also
     * do localization. If there is no localization then default is used
     * 
     * @param text to {@link JLabel}
     * @return {@link JLabel} with text. If there is no text then null is returned.
     */
    protected JLabel buildSimpleLabel(String text) {
        if (text != null && !text.isEmpty()) {
            text = LocalizationUtils.getTextFromExtendBundle(text, localization, null);
            JLabel label = new JLabel(text);
            if (skin != null) {
                if (skin.getLabelFont() != null) {
                    label.setFont(skin.getLabelFont());
                }
                if (skin.getLabelColor() != null) {
                    label.setForeground(skin.getLabelColor());
                }
            }
            return label;
        }
        return null;
    }

    /**
     * This method build simple message widget which will be used to display validations message
     * 
     * @return message widget which is used to display data
     */
    protected JTextArea buildSimpleMessage() {
        // Use textarea because of data wrap
        JTextArea textValidationComponent = new JTextArea();
        textValidationComponent.setVisible(false);
        textValidationComponent.setWrapStyleWord(true);
        textValidationComponent.setLineWrap(true);
        textValidationComponent.setEditable(false);
        textValidationComponent.setOpaque(false);
        if (skin != null) {
            if (skin.getValidationColor() != null) {
                textValidationComponent.setForeground(skin.getValidationColor());
            }
            if (skin.getValidationFont() != null) {
                textValidationComponent.setFont(skin.getValidationFont());
            }
        }
        return textValidationComponent;
    }

    protected void crateValidators(AFSwinxPanel panel, AFFieldInfo fieldInfo) {
        if (fieldInfo.getRules() != null) {
            for (AFValidationRule rules : fieldInfo.getRules()) {
                // TODO add parameters
                AFValidations validator =
                        AFValidatorFactory.getInstance().createValidator(rules.getValidationType(),
                                null);
                validator.setLocalization(localization);
                panel.addValidator(validator);
            }
        }
    }

    @Override
    public void setLocalization(ResourceBundle localization) {
        this.localization = localization;
    }

    @Override
    public void setSkin(Skin skin) {
        this.skin = skin;

    }

    protected void buildBase(AFFieldInfo fieldInfo) {
        // First check if build is available
        if (!isBuildAvailable(fieldInfo)) {
            throw new IllegalArgumentException("Input field couldn't be build for this field");
        }
        // Create layout builder
        this.layoutBuilder = new BaseLayoutBuilder(fieldInfo.getLayout());
        // Build label
        this.fieldLabel = buildSimpleLabel(fieldInfo.getLabel());
        // Add components to layout builder
        layoutBuilder.addLabel(fieldLabel);
        this.message = buildSimpleMessage();
        layoutBuilder.addMessage(message);
    }

    public SupportedWidgets getWidgetType() {
        return widgetType;
    }

    @Override
    public void skinComponent(JComponent componentToSkin) {
        if (skin != null) {
            if (skin.getFieldColor() != null) {
                componentToSkin.setForeground(skin.getFieldColor());
            }
            if (skin.getFieldFont() != null) {
                componentToSkin.setFont(skin.getFieldFont());
            }
            if (getWidgetType() == null) {
                return;
            }
            if (getWidgetType().equals(SupportedWidgets.TEXTAREA)) {
                int rows = skin.getTextAreaRows();
                int colums = skin.getTextAreaColums();
                JTextArea texArea = (JTextArea) componentToSkin;
                if (rows > 0) {
                    texArea.setRows(rows);
                }
                if (colums > 0) {
                    texArea.setColumns(colums);
                }
            }
            if (getWidgetType().equals(SupportedWidgets.NUMBERFIELD)
                    || getWidgetType().equals(SupportedWidgets.TEXTFIELD)) {
                JTextField textField = (JTextField) componentToSkin;
                int colums = skin.getInputColum();
                if (colums > 0) {
                    textField.setColumns(colums);
                } else {
                    textField.setColumns(InputFieldBuilder.DEFAULT_NUMBER_OF_COLUMS);
                }
            }
            if(getWidgetType().equals(SupportedWidgets.CALENDAR)){
                JDatePickerImpl dataPicker = (JDatePickerImpl) componentToSkin;
                int colums = skin.getInputColum();
                if(colums > 0){
                    Dimension currentSize = dataPicker.getPreferredSize();
                    dataPicker.setPreferredSize(new Dimension(colums, currentSize.height));
                }     
            }
        }
    }


}
