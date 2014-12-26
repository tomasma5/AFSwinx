package com.tomscz.afswinx.component.widget.builder.abstraction;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ResourceBundle;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.tomscz.afrest.commons.SupportedValidations;
import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.AFOptions;
import com.tomscz.afrest.rest.dto.AFValidationRule;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.component.skin.BaseSkin;
import com.tomscz.afswinx.component.skin.Skin;
import com.tomscz.afswinx.component.widget.builder.DropDownMenuBuilder;
import com.tomscz.afswinx.component.widget.builder.InputBuilder;
import com.tomscz.afswinx.component.widget.builder.TextAreaBuilder;
import com.tomscz.afswinx.component.widget.builder.WidgetBuilder;
import com.tomscz.afswinx.localization.LocalizationUtils;
import com.tomscz.afswinx.swing.component.SwinxAFDatePicker;
import com.tomscz.afswinx.validation.AFValidations;
import com.tomscz.afswinx.validation.factory.AFValidatorFactory;

/**
 * This class is abstract field builder which provide some logic to their children.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public abstract class BaseWidgetBuilder implements WidgetBuilder {

    protected Component coreComponent;
    protected BaseLayoutBuilder layoutBuilder;
    protected JTextArea message;
    protected JTextArea fieldLabel;
    protected ResourceBundle localization;
    protected Skin skin;
    protected SupportedWidgets widgetType;

    private static final int LABEL_WIDHT = 50;
    private static final int LABEL_HEIGHT = 20;

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
    protected JTextArea buildSimpleLabel(String text) {
        if (text != null && !text.isEmpty()) {
            text = LocalizationUtils.getTextFromExtendBundle(text, localization, null);
            JTextArea labelArea = new JTextArea();
            labelArea.setText(text);
            labelArea.setVisible(true);
            labelArea.setWrapStyleWord(true);
            labelArea.setLineWrap(true);
            labelArea.setEditable(false);
            labelArea.setOpaque(false);
            int height = LABEL_HEIGHT;
            int width = LABEL_WIDHT;
            if (skin != null) {
                if (skin.getLabelFont() != null) {
                    labelArea.setFont(skin.getLabelFont());
                }
                if (skin.getLabelColor() != null) {
                    labelArea.setForeground(skin.getLabelColor());
                }
                if (skin.getLabelWidht() > 0) {
                    width = skin.getLabelWidht();
                }
                if (skin.getLabelHeight() > 0) {
                    height = skin.getLabelHeight();
                }
            }
            Dimension dimension = new Dimension(width, height);
            labelArea.setPreferredSize(dimension);
            labelArea.setMaximumSize(dimension);
            return labelArea;
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

    protected void crateValidators(AFSwinxPanel panel, AFFieldInfo fieldInfo)
            throws AFSwinxBuildException {
        if (fieldInfo.getRules() != null) {
            for (AFValidationRule rules : fieldInfo.getRules()) {
                // If there is no specify validation type, or type is retype then do nothing.
                // Retype validator is specific one and must be set outside builders
                if (rules.getValidationType().equals(SupportedValidations.RETYPE)) {
                    panel.setRetype(true);
                    continue;
                }
                SupportedWidgets widget = fieldInfo.getWidgetType();
                try {
                    AFValidations validator =
                            AFValidatorFactory.getInstance().createValidator(
                                    rules.getValidationType(), rules.getValue(), widget);
                    validator.setLocalization(localization);
                    panel.addValidator(validator);
                } catch (NumberFormatException e) {
                    throw new AFSwinxBuildException(buildValidationBuilderFailedMessage(fieldInfo,
                            rules, "This value cannot be converted to number."));
                } catch (NullPointerException e) {
                    throw new AFSwinxBuildException(buildValidationBuilderFailedMessage(fieldInfo,
                            rules, "There was null pointer exception."));
                }
            }
        }
    }

    private String buildValidationBuilderFailedMessage(AFFieldInfo fieldInfo,
            AFValidationRule rule, String cause) {
        return "Validation builder failed on field" + fieldInfo.getId()
                + " during building validation" + rule.getValidationType().name() + " with value "
                + rule.getValue() + ". " + cause;
    }

    @Override
    public void setLocalization(ResourceBundle localization) {
        this.localization = localization;
    }

    @Override
    public void setSkin(Skin skin) {
        if (skin != null) {
            this.skin = skin;
        } else {
            this.skin = new BaseSkin();
        }
    }

    protected void buildBase(AFFieldInfo fieldInfo) {
        // First check if build is available
        if (!isBuildAvailable(fieldInfo)) {
            throw new IllegalArgumentException("Component couldn't be build for this field");
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
    public void customizeComponent(JComponent component, AFFieldInfo fieldInfo) {
        if (fieldInfo.getReadOnly()) {
            component.setEnabled(!fieldInfo.getReadOnly());
        }
        skinComponent(component);
    }

    @SuppressWarnings("unchecked")
    private void skinComponent(JComponent componentToSkin) {
        if (skin.getFieldColor() != null) {
            componentToSkin.setForeground(skin.getFieldColor());
        }
        if (skin.getFieldFont() != null) {
            componentToSkin.setFont(skin.getFieldFont());
        }
        SupportedWidgets widgetType = getWidgetType();
        if (widgetType == null) {
            return;
        }
        if (widgetType.equals(SupportedWidgets.TEXTAREA)) {
            int rows = TextAreaBuilder.rows;
            int colums = TextAreaBuilder.columns;
            JTextArea texArea = (JTextArea) componentToSkin;
            if (skin.getTextAreaColums() > 0) {
                colums = skin.getTextAreaColums();
            }
            if (skin.getTextAreaRows() > 0) {
                rows = skin.getTextAreaRows();
            }
            texArea.setRows(rows);
            texArea.setColumns(colums);
        }
        if (widgetType.equals(SupportedWidgets.NUMBERFIELD)
                || widgetType.equals(SupportedWidgets.NUMBERDOUBLEFIELD)
                || widgetType.equals(SupportedWidgets.NUMBERLONGFIELD)
                || widgetType.equals(SupportedWidgets.TEXTFIELD)
                || widgetType.equals(SupportedWidgets.PASSWORD)) {
            JTextField textField = (JTextField) componentToSkin;
            int width = InputBuilder.DEFAULT_WIDTH;
            int height = textField.getPreferredSize().height;
            if (skin.getInputWidth() > 0) {
                width = skin.getInputWidth();
            }
            Dimension dimension = new Dimension(width, height);
            textField.setPreferredSize(dimension);
            textField.setMinimumSize(dimension);
            textField.setMaximumSize(dimension);
        }
        if (widgetType.equals(SupportedWidgets.CALENDAR)) {
            SwinxAFDatePicker dataPicker = (SwinxAFDatePicker) componentToSkin;
            JComponent componentToskin = dataPicker.getVisibleComponent();
            int colums = skin.getInputWidth();
            if (colums > 0) {
                Dimension currentSize = componentToskin.getPreferredSize();
                componentToskin.setPreferredSize(new Dimension(colums, currentSize.height));
            }
        }
        if(widgetType.equals(SupportedWidgets.DROPDOWNMENU)){
            JComboBox<AFOptions> comboBox = (JComboBox<AFOptions>) componentToSkin;
            int height = comboBox.getPreferredSize().height;
            int width = DropDownMenuBuilder.DEFAULT_WIDTH;
            if (skin.getInputWidth() > 0) {
                width = skin.getInputWidth();
            }
            comboBox.setPreferredSize(new Dimension(width,height));
            comboBox.setMinimumSize(new Dimension(width, height));
            comboBox.setMaximumSize(new Dimension(width, height));
        }
    }
    
    protected void addDummyFieldOption(AFFieldInfo fieldInfo){
        String valueLocalized =
                LocalizationUtils.getTextValueFromLocalOrExtendBundle("options.unselected", localization);
        fieldInfo.addOption(new AFOptions("", valueLocalized));
    }

}
