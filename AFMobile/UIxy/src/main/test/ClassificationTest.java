import com.tomscz.afrest.commons.SupportedValidations;
import com.tomscz.afrest.rest.dto.AFClassInfo;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afrest.rest.dto.AFValidationRule;
import model.Application;
import model.afclassification.*;
import org.junit.Before;
import org.junit.Test;
import service.afclassification.AFClassification;
import service.afclassification.computational.ccm.units.BaseClassificationUnit;
import service.afclassification.computational.scm.units.Scoring;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class tests metamodel modifications which is based on classification result
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
public class ClassificationTest {

    private AFMetaModelPack metaModelPack;
    private AFClassification afClassification;
    private ConfigurationPack configurationPack;
    List<BCField> bcFields;

    @Before
    public void setUp() throws Exception {
        metaModelPack = new AFMetaModelPack();
        AFClassInfo classInfo = new AFClassInfo();

        AFFieldInfo toBeDeletedFieldInfo = new AFFieldInfo();
        toBeDeletedFieldInfo.setId("toBeDeleted");

        AFFieldInfo toBeRequiredFieldInfo = new AFFieldInfo();
        toBeDeletedFieldInfo.addRule(new AFValidationRule(SupportedValidations.REQUIRED, "false")); //should be replaced with true value
        toBeRequiredFieldInfo.setId("toBeRequired");

        AFFieldInfo toBeOnlyValidatedFieldInfo = new AFFieldInfo();
        toBeOnlyValidatedFieldInfo.setId("toBeOnlyValidated");
        toBeOnlyValidatedFieldInfo.addRule(new AFValidationRule(SupportedValidations.REQUIRED, "true")); //this one should be removed
        toBeOnlyValidatedFieldInfo.addRule(new AFValidationRule(SupportedValidations.MAXLENGTH, "255")); //this one should stay

        AFFieldInfo toBeOnlyDisplayed = new AFFieldInfo();
        toBeOnlyDisplayed.setId("toBeDisplayed");
        toBeOnlyDisplayed.addRule(new AFValidationRule(SupportedValidations.REQUIRED, "true")); //this one should be removed
        toBeOnlyDisplayed.addRule(new AFValidationRule(SupportedValidations.MAXLENGTH, "255")); //this one should be removed

        AFFieldInfo toBeHidden = new AFFieldInfo();
        toBeHidden.setId("toBeHidden");
        toBeHidden.addRule(new AFValidationRule(SupportedValidations.REQUIRED, "true")); //this one should be removed
        toBeHidden.addRule(new AFValidationRule(SupportedValidations.MAXLENGTH, "255")); //this one should be removed
        toBeHidden.setVisible(true); //should be replaced by false

        classInfo.addFieldInfo(toBeDeletedFieldInfo);
        classInfo.addFieldInfo(toBeRequiredFieldInfo);
        classInfo.addFieldInfo(toBeOnlyValidatedFieldInfo);
        classInfo.addFieldInfo(toBeOnlyDisplayed);
        classInfo.addFieldInfo(toBeOnlyDisplayed);
        metaModelPack.setClassInfo(classInfo);

        bcFields = new ArrayList<>();
        bcFields.add(createBCFieldForFieldInfo(toBeDeletedFieldInfo, Purpose.INFORMATION_MINING, Severity.NICE_TO_HAVE));
        bcFields.add(createBCFieldForFieldInfo(toBeRequiredFieldInfo, Purpose.SYSTEM_IDENTIFICATION, Severity.CRITICAL));
        bcFields.add(createBCFieldForFieldInfo(toBeOnlyValidatedFieldInfo, Purpose.SYSTEM_INFORMATION, Severity.REQUIRED));
        bcFields.add(createBCFieldForFieldInfo(toBeHidden, Purpose.INFORMATION_MINING, Severity.NEEDED));
        bcFields.add(createBCFieldForFieldInfo(toBeOnlyDisplayed, Purpose.SYSTEM_INFORMATION, Severity.NEEDED));

        afClassification = new AFClassificationTest();
        afClassification.setScoringModule(new TestScoringUnit());
        afClassification.setClassificationModule(new BaseClassificationUnit());

        configurationPack = new ConfigurationPack(true);
    }

    private BCField createBCFieldForFieldInfo(AFFieldInfo fieldInfo, Purpose purpose, Severity severity) {
        Field field = new Field();
        field.setFieldName(fieldInfo.getId());
        BCField bcField = new BCField();
        bcField.setField(field);
        BCFieldSeverity fieldSeverity = new BCFieldSeverity();
        fieldSeverity.setPurpose(purpose);
        fieldSeverity.setSeverity(severity);
        bcField.setFieldSpecification(fieldSeverity);
        return bcField;
    }

    /**
     * If field has not present behavior it should be deleted from metamodel
     *
     * @throws Exception exception
     */
    @Test
    public void shouldDeleteNotPresentField() throws Exception {
        assertTrue(metaModelPack.getClassInfo().getFieldInfo().stream()
                .anyMatch(fieldInfo -> fieldInfo.getId().equals("toBeDeleted")));
        afClassification.classifyMetaModel(metaModelPack, null, configurationPack, bcFields, null);
        assertFalse(metaModelPack.getClassInfo().getFieldInfo().stream()
                .anyMatch(fieldInfo -> fieldInfo.getId().equals("toBeDeleted")));
    }

    /**
     * If filed has required behavior it should have required validation rule set to true
     *
     * @throws Exception exception
     */
    @Test
    public void shouldBeRequired() throws Exception {
        afClassification.classifyMetaModel(metaModelPack, null, configurationPack, bcFields, null);
        for (AFFieldInfo f : metaModelPack.getClassInfo().getFieldInfo()) {
            if (f.getId().equals("toBeRequired")) {
                AFValidationRule expectedRule = new AFValidationRule(SupportedValidations.REQUIRED, "true");
                assertTrue(f.getRules().stream()
                        .anyMatch(rule -> rule.getValidationType().equals(expectedRule.getValidationType()) && rule.getValue().equals("true"))
                );
                break;
            }
        }
    }

    /**
     * If field has validation behavior it should be validated but not required
     *
     * @throws Exception exception
     */
    @Test
    public void shouldBeOnlyValidated() throws Exception {
        afClassification.classifyMetaModel(metaModelPack, null, configurationPack, bcFields, null);
        for (AFFieldInfo f : metaModelPack.getClassInfo().getFieldInfo()) {
            if (f.getId().equals("toBeOnlyValidated")) {
                AFValidationRule expectedRule = new AFValidationRule(SupportedValidations.REQUIRED, "true");
                assertTrue(f.getRules().stream()
                        .anyMatch(rule -> rule.getValidationType().equals(expectedRule.getValidationType()) && rule.getValue().equals("false"))
                );
                //check that other rule is still here
                assertTrue(f.getRules().stream()
                        .anyMatch(rule -> rule.getValidationType().equals(SupportedValidations.MAXLENGTH) && rule.getValue().equals("255")));
                break;
            }
        }
    }

    /**
     * If filed has only display behavior it should have no validation rules and be visible
     *
     * @throws Exception exception
     */
    @Test
    public void shouldBeOnlyDisplayed() throws Exception {
        for (AFFieldInfo f : metaModelPack.getClassInfo().getFieldInfo()) {
            if (f.getId().equals("toBeDisplayed")) {
                assertTrue(f.getRules().size() > 0);
                break;
            }
        }
        afClassification.classifyMetaModel(metaModelPack, null, configurationPack, bcFields, null);
        for (AFFieldInfo f : metaModelPack.getClassInfo().getFieldInfo()) {
            if (f.getId().equals("toBeDisplayed")) {
                assertTrue(f.getRules().size() == 0);
                break;
            }
        }
    }

    /**
     * If field has hidden behavior it should be hidden and have no validations
     *
     * @throws Exception exception
     */
    @Test
    public void shouldBeHidden() throws Exception {
        for (AFFieldInfo f : metaModelPack.getClassInfo().getFieldInfo()) {
            if (f.getId().equals("toBeHidden")) {
                assertTrue(f.getRules().size() > 0);
                assertTrue(f.getVisible());
                break;
            }
        }
        afClassification.classifyMetaModel(metaModelPack, null, configurationPack, bcFields, null);
        for (AFFieldInfo f : metaModelPack.getClassInfo().getFieldInfo()) {
            if (f.getId().equals("toBeHidden")) {
                assertTrue(f.getRules().size() == 0);
                assertFalse(f.getVisible());
                break;
            }
        }
    }


    /**
     * Scoring unit which gives specific values for test
     */
    private static class TestScoringUnit implements Scoring {

        @Override
        public Double scoreField(List<String> possibleValues, String actualValue, Purpose purpose, Severity severity, Client client) {
            return null;
        }

        @Override
        public Double scoreField(Purpose purpose, Severity severity, Client client, Application application) {
            if (purpose == Purpose.INFORMATION_MINING && severity == Severity.NICE_TO_HAVE) {
                return 5D; //not present
            }
            if (purpose == Purpose.INFORMATION_MINING && severity == Severity.NEEDED) {
                return 30D; //Hidden
            }
            if (purpose == Purpose.SYSTEM_INFORMATION && severity == Severity.NEEDED) {
                return 50D; //Only display
            }
            if (purpose == Purpose.SYSTEM_INFORMATION && severity == Severity.REQUIRED) {
                return 80D; //validation
            }
            if (purpose == Purpose.SYSTEM_IDENTIFICATION && severity == Severity.CRITICAL) {
                return 100D; //required
            }
            return 80D;
        }
    }

    private static class AFClassificationTest extends AFClassification {
        //to make contructor not protected for test . So we do not have to use AFClassification factory since it is dependent on a lot of things.
    }

}
