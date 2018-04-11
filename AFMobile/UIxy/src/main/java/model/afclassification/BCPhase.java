package model.afclassification;

import model.DtoEntity;
import model.Screen;
import model.converter.SupportedClassificationUnitConverter;
import model.converter.SupportedScoringUnitConverter;
import org.hibernate.annotations.Cascade;
import service.afclassification.computational.ccm.SupportedClassificationUnit;
import service.afclassification.computational.scm.SupportedScoringUnit;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Business case phase - contains information about fields and screens
 */
@Entity
@Table(name = BCPhase.TABLE_NAME)
public class BCPhase extends DtoEntity {

    public static final String TABLE_NAME = "business_phase";
    public static final String PHASE_ID = "phase_id";
    public static final String PHASE_NAME = "phase_name";
    public static final String CLASSIFICATION_UNIT = "classification_unit";
    public static final String SCORIGN_UNIT = "scoringUnit";

    @Id
    @Column(name = PHASE_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "phase")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<BCField> fields;

    @OneToMany(mappedBy = "phase")
    private List<Screen> linkedScreens;

    @OneToOne
    private ConfigurationPack configuration;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = BusinessCase.BUSINESS_CASE_ID)
    private BusinessCase businessCase;

    @Column(name = CLASSIFICATION_UNIT)
    @Convert(converter = SupportedClassificationUnitConverter.class)
    private SupportedClassificationUnit classificationUnit;

    @Column(name = SCORIGN_UNIT)
    @Convert(converter = SupportedScoringUnitConverter.class)
    private SupportedScoringUnit scoringUnit;

    @Column(name = PHASE_NAME)
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
        if(!this.fields.contains(field)) {
            this.fields.add(field);
        }
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
        if(!this.linkedScreens.contains(screen)) {
            this.linkedScreens.add(screen);
        }
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

    public BusinessCase getBusinessCase() {
        return businessCase;
    }

    public void setBusinessCase(BusinessCase businessCase) {
        this.businessCase = businessCase;
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

    @Override
    public Integer getId() {
        return id;
    }
}
