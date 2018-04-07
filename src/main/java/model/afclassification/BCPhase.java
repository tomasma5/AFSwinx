package model.afclassification;

import model.MongoDocumentEntity;
import model.Screen;
import org.bson.types.ObjectId;
import service.afclassification.computational.ccm.SupportedClassificationUnit;
import service.afclassification.computational.scm.SupportedScoringUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * Business case phase - contains information about fields and screens
 */
public class BCPhase extends MongoDocumentEntity {

    private List<BCField> fields;

    private List<Screen> linkedScreens;

    private ConfigurationPack configuration;

    private ObjectId businessCaseId;

    private SupportedClassificationUnit classificationUnit;

    private SupportedScoringUnit scoringUnit;

    private String name;

    /**
     * Adds bc field.
     *
     * @param field the field
     */
    public synchronized void addBCField(BCField field) {
        if (this.fields == null) {
            this.fields = new ArrayList<>();
        }
        this.fields.add(field);
    }

    /**
     * Adds linked screen.
     *
     * @param screen the screen
     */
    public synchronized void addLinkedScreen(Screen screen) {
        if (this.linkedScreens == null) {
            this.linkedScreens = new ArrayList<>();
        }
        this.linkedScreens.add(screen);
    }

    public List<BCField> getFields() {
        return fields;
    }

    public void setFields(List<BCField> fields) {
        this.fields = fields;
    }

    public ConfigurationPack getConfiguration() {
        return configuration;
    }

    public void setConfiguration(ConfigurationPack configuration) {
        this.configuration = configuration;
    }

    public List<Screen> getLinkedScreens() {
        return linkedScreens;
    }

    public void setLinkedScreens(List<Screen> linkedScreens) {
        this.linkedScreens = linkedScreens;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectId getBusinessCaseId() {
        return businessCaseId;
    }

    public void setBusinessCaseId(ObjectId businessCaseId) {
        this.businessCaseId = businessCaseId;
    }

    public SupportedClassificationUnit getClassificationUnit() {
        return classificationUnit;
    }

    public void setClassificationUnit(SupportedClassificationUnit classificationUnit) {
        this.classificationUnit = classificationUnit;
    }

    public SupportedScoringUnit getScoringUnit() {
        return scoringUnit;
    }

    public void setScoringUnit(SupportedScoringUnit scoringUnit) {
        this.scoringUnit = scoringUnit;
    }
}
