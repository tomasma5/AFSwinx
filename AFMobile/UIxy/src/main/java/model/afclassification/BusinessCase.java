package model.afclassification;

import model.Application;
import model.DtoEntity;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Business case holds information about some application use case
 */
@Entity
@Table(name = BusinessCase.TABLE_NAME)
public class BusinessCase extends DtoEntity {

    public static final String TABLE_NAME = "business_case";
    public static final String BUSINESS_CASE_ID = "business_case_id";
    public static final String BUSINESS_CASE_NAME = "name";
    public static final String BUSINESS_CASE_DESCRIPTION = "description";

    @Id
    @Column(name = BUSINESS_CASE_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = BUSINESS_CASE_NAME)
    private String name;

    @Column(name = BUSINESS_CASE_DESCRIPTION)
    private String description;

    @OneToMany(mappedBy = "businessCase")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<BCPhase> phases;

    @ManyToOne
    @JoinColumn(name = Application.APPLICATION_ID)
    private Application application;

    public BusinessCase() {
    }

    public BusinessCase(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addPhase(BCPhase phase) {
        if (phases == null) {
            phases = new ArrayList<>();
        }
        this.phases.add(phase);
    }

    public List<BCPhase> getPhases() {
        return phases;
    }

    public void setPhases(List<BCPhase> phases) {
        this.phases = phases;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    @Override
    public Integer
    getId() {
        return id;
    }
}
