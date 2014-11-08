package com.tomscz.afswinx.unmarshal.builders.abstraction.component;

import java.awt.Component;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.AFValidationRule;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.localization.LocalizationUtils;
import com.tomscz.afswinx.unmarshal.builders.FieldBuilder;
import com.tomscz.afswinx.validation.AFValidations;
import com.tomscz.afswinx.validation.factory.AFValidatorFactory;

/**
 * This class is abstract field builder which provide some logic to their children.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public abstract class BaseComponentsBuilder implements FieldBuilder {

    protected Component coreComponent;
    protected ResourceBundle localization;

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
            return new JLabel(text);
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

}
