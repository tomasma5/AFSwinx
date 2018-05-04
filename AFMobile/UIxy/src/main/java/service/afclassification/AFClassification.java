package service.afclassification;

import com.tomscz.afrest.commons.SupportedValidations;
import com.tomscz.afrest.rest.dto.AFClassInfo;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afrest.rest.dto.AFValidationRule;
import model.Application;
import model.afclassification.*;
import service.afclassification.computational.ccm.units.Classification;
import service.afclassification.computational.scm.units.Scoring;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classification module which can be used for scoring and classifying fields.
 */
public class AFClassification {

    private Scoring scoringModule;
    private Classification classificationModule;

    protected AFClassification() {
    }

    /**
     * Sets scoring module.
     *
     * @param scoringModule the scoring module
     */
    public void setScoringModule(Scoring scoringModule) {
        this.scoringModule = scoringModule;
    }

    /**
     * Sets classification module.
     *
     * @param classificationModule the classification module
     */
    public void setClassificationModule(Classification classificationModule) {
        this.classificationModule = classificationModule;
    }

    /**
     * Classifies given meta model.
     *
     * @param metaModelPack     the meta model pack
     * @param client            the client
     * @param configurationPack the phase config pack
     */
    public void classifyMetaModel(AFMetaModelPack metaModelPack, Client client, ConfigurationPack configurationPack, List<BCField> fieldList, Application application) {
        for (BCField field : fieldList) {
            System.out.println("[Classification][AFClassification] Classifing field: " + field.getField().getFieldName());
            long start = System.currentTimeMillis();
            GeneratedField result = classifyField(field, client, configurationPack, application);
            System.out.println("[Classification][AFClassification] Classification of field took " + (System.currentTimeMillis() - start) + " ms");
            if (result != null) {
                System.out.println("[Classification][AFClassification] The field " + field.getField().getFieldName() + " has behavior: " + result.getBehavior());
                AFFieldInfo fieldInfo = getFieldInfoFromMetaModel(metaModelPack.getClassInfo(), field.getField().getFieldName());
                editFieldProperties(metaModelPack, field, result, fieldInfo);
            }
        }
    }

    private GeneratedField classifyField(BCField field, Client client, ConfigurationPack configuration, Application application) {
        Double score = scoringModule.scoreField(field.getFieldSpecification()
                        .getPurpose(), field.getFieldSpecification().getSeverity(),
                client, application);
        if (score == null) {
            return null;
        }
        Behavior behavior = classificationModule.classify(score, configuration);
        GeneratedField generatedField = new GeneratedField();
        generatedField.setBcField(field);
        generatedField.setBehavior(behavior);
        return generatedField;
    }

    private void editFieldProperties(AFMetaModelPack metaModelPack, BCField field, GeneratedField result, AFFieldInfo fieldInfo) {
        if (fieldInfo != null) {
            switch (result.getBehavior()) {
                case REQUIRED:
                    enableRequiredOnField(fieldInfo);
                    break;
                case VALIDATION:
                    disableRequiredOnField(fieldInfo);
                    fieldInfo.setVisible(true);
                    break;
                case ONLY_DISPLAY:
                    doNotValidateField(fieldInfo);
                    fieldInfo.setVisible(true);
                    break;
                case HIDDEN:
                    doNotValidateField(fieldInfo);
                    fieldInfo.setVisible(false);
                    break;
                case NOT_PRESENT:
                    removeFieldInfoFromMetaModel(metaModelPack.getClassInfo(), field.getField().getFieldName());
                    break;
                default:
                    break;
            }
        }
        System.out.println(fieldInfo);
    }

    private AFFieldInfo getFieldInfoFromMetaModel(AFClassInfo classInfo, String fieldId) {

        AFClassInfo properClassInfo = getProperAfClassInfo(classInfo, fieldId);
        fieldId = fieldId.contains(".") ? fieldId.substring(fieldId.lastIndexOf('.') + 1) : fieldId;
        for (AFFieldInfo fieldInfo : properClassInfo.getFieldInfo()) {
            if (fieldInfo.getId().equals(fieldId)) {
                return fieldInfo;
            }
        }
        return null;
    }


    private AFClassInfo getProperAfClassInfo(AFClassInfo classInfo, String fieldId) {
        if (!fieldId.contains(".")) {
            return classInfo;
        }
        for (AFClassInfo innerClassInfo : classInfo.getInnerClasses()) {
            int nextDotIndex = fieldId.indexOf('.');
            if (!classInfo.getName().equals(fieldId.substring(0, nextDotIndex))) {
                return getProperAfClassInfo(innerClassInfo, fieldId.substring(nextDotIndex + 1));
            }
        }
        return null;
    }

    private void removeFieldInfoFromMetaModel(AFClassInfo classInfo, String fieldId) {
        AFClassInfo properClassInfo = getProperAfClassInfo(classInfo, fieldId);
        fieldId = fieldId.contains(".") ? fieldId.substring(fieldId.lastIndexOf('.') + 1) : fieldId;
        int indexToRemove = -1;
        int i = 0;
        for (AFFieldInfo fieldInfo : properClassInfo.getFieldInfo()) {
            if (fieldInfo.getId().equals(fieldId)) {
                indexToRemove = i;
                break;
            }
            i++;
        }
        if (indexToRemove != -1) {
            properClassInfo.getFieldInfo().remove(indexToRemove);
            System.out.println(fieldId + " successfully removed");
        }
    }


    private void doNotValidateField(AFFieldInfo fieldInfo) {
        System.out.println("[Classification][AFClassification] Clearing validations of " + fieldInfo.getId());
        if (fieldInfo.getRules() != null) {
            fieldInfo.getRules().clear();
        }
    }

    private void disableRequiredOnField(AFFieldInfo fieldInfo) {
        if (fieldInfo.getRules() != null) {
            if (removeRequiredRuleFromField(fieldInfo, true)) {
                System.out.println("[Classification][AFClassification] Disabling REQUIRED on field " + fieldInfo.getId());
                fieldInfo.getRules().add(new AFValidationRule(SupportedValidations.REQUIRED, "false"));
            }
        }
    }

    private void enableRequiredOnField(AFFieldInfo fieldInfo) {
        System.out.println("[Classification][AFClassification] Enabling REQUIRED on field " + fieldInfo.getId());
        removeRequiredRuleFromField(fieldInfo, false);
        removeRequiredRuleFromField(fieldInfo, true);
        fieldInfo.addRule(new AFValidationRule(SupportedValidations.REQUIRED, "true"));
        fieldInfo.setVisible(true);
    }


    private boolean removeRequiredRuleFromField(AFFieldInfo fieldInfo, boolean removeWithTrueValue) {
        if (fieldInfo.getRules() != null) {
            int indexOfRequiredRule = -1;
            int i = 0;
            for (AFValidationRule validationRule : fieldInfo.getRules()) {
                if (validationRule.getValidationType().equals(SupportedValidations.REQUIRED) &&
                        validationRule.getValue().equals(removeWithTrueValue ? "true" : "false")) {
                    indexOfRequiredRule = i;
                    break;
                }
                i++;
            }
            if (indexOfRequiredRule != -1) {
                fieldInfo.getRules().remove(indexOfRequiredRule);
                return true;
            }
        }
        return false;
    }

}
