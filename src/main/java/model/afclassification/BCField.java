package model.afclassification;

import model.ComponentResource;
import model.DtoEntity;
import model.Screen;

import javax.persistence.*;

/**
 * This structure is for representing component fields in business cases
 */
@Entity
@Table(name = BCField.TABLE_NAME)
public class BCField extends DtoEntity {

    public static final String TABLE_NAME = "BCField";
    public static final String BC_FIELD_ID = "bc_field_id";

    @Id
    @Column(name = BC_FIELD_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = Field.FIELD_ID)
    private Field field;
    @ManyToOne
    @JoinColumn(name = BCPhase.PHASE_ID)
    private BCPhase phase;
    @ManyToOne
    @JoinColumn(name = Screen.SCREEN_ID)
    private Screen screen;
    @ManyToOne
    @JoinColumn(name = ComponentResource.COMPONENT_ID)
    private ComponentResource component;

    @OneToOne
    private BCFieldSeverity fieldSpecification;

    public BCField() {
    }

    public BCField(Field field, BCPhase phase, Screen screen, ComponentResource component) {
        this.field = field;
        this.phase = phase;
        this.screen = screen;
        this.component = component;
    }

    public BCPhase getPhase() {
        return phase;
    }

    public void setPhase(BCPhase phase) {
        this.phase = phase;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public BCFieldSeverity getFieldSpecification() {
        return fieldSpecification;
    }

    public void setFieldSpecification(BCFieldSeverity fieldSpecification) {
        this.fieldSpecification = fieldSpecification;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public ComponentResource getComponent() {
        return component;
    }

    public void setComponent(ComponentResource component) {
        this.component = component;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
