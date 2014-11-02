package com.tomscz.afswinx.unmarshal.builders.abstraction.component;

import java.awt.Component;

import javax.swing.JLabel;

import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.AFValidationRule;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.unmarshal.builders.FieldBuilder;
import com.tomscz.afswinx.validation.AFValidations;
import com.tomscz.afswinx.validation.factory.AFValidatorFactory;

/**
 * This class is abstract field builder which provide some logic to their children.
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public abstract class BaseComponentsBuilder implements FieldBuilder {

    protected Component coreComponent;
    
    @Override
    public boolean isBuildAvailable(AFFieldInfo fieldWithLabel) {
        if (fieldWithLabel != null) return true;
        return false;
    }

    /**
     * This method create simple {@link JLabel}, if there is no text then null is returned.
     * @param text to {@link JLabel}
     * @return {@link JLabel} with text. If there is no text then null is returned.
     */
    protected JLabel buildSimpleLabel(String text) {
        if (text != null && !text.isEmpty()) {
            return new JLabel(text);
        }
        return null;
    }
    
    public abstract Component getCoreComponent();

    protected void crateValidators(AFSwinxPanel panel, AFFieldInfo fieldInfo) {
        if (fieldInfo.getRules() != null) {
            for (AFValidationRule rules : fieldInfo.getRules()) {
                // TODO add parameters
                AFValidations validator =
                        AFValidatorFactory.getInstance().createValidator(rules.getValidationType(),
                                null);
                panel.addValidator(validator);
            }
        }
    }

}
