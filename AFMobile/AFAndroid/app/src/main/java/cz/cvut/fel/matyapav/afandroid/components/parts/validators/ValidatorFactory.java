package cz.cvut.fel.matyapav.afandroid.components.parts.validators;

import cz.cvut.fel.matyapav.afandroid.components.parts.ValidationRule;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedValidations;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
 */
public class ValidatorFactory {

    private static ValidatorFactory instance = null;


    public static synchronized ValidatorFactory getInstance() {
        if(instance == null){
            instance = new ValidatorFactory();
        }
        return instance;
    }

    public AFValidator getValidator(ValidationRule rule){
        if (rule.getValidationType().equals(SupportedValidations.REQUIRED.getValidationType())) {
            return new RequiredValidator();
        }
        if (rule.getValidationType().equals(SupportedValidations.MAXLENGTH.getValidationType())) {
            return new MaxCharsValidator();
        }
        if (rule.getValidationType().equals(SupportedValidations.MAXVALUE.getValidationType())){
            return new MaxValueValidator();
        }
        if (rule.getValidationType().equals(SupportedValidations.MINVALUE.getValidationType())){
            return new MinValueValidator();
        }
        if(rule.getValidationType().equals(SupportedValidations.LESSTHAN.getValidationType())){
            return new LessThanValidator();
        }
        System.err.println("VALIDATOR FOR "+rule.getValidationType()+" NOT FOUND");
        return null;
    }
}
