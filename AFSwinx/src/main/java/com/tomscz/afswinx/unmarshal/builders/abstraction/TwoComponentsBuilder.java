package com.tomscz.afswinx.unmarshal.builders.abstraction;

import javax.swing.JLabel;

import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.rest.dto.AFFieldInfo;
import com.tomscz.afswinx.rest.dto.AFValidationRule;
import com.tomscz.afswinx.unmarshal.builders.FieldBuilder;
import com.tomscz.afswinx.validation.AFValidations;
import com.tomscz.afswinx.validation.factory.AFValidatorFactory;

public abstract class TwoComponentsBuilder implements FieldBuilder {

    @Override
    public boolean isBuildAvailable(AFFieldInfo fieldWithLabel) {
        if (fieldWithLabel != null) return true;
        return false;
    }

    protected JLabel buildSimpleLabel(String text) {
        if (text != null && !text.isEmpty()) {
            return new JLabel(text);
        }
        return null;
    }

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
