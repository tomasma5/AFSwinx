package model.afclassification;

import model.DtoEntity;
import model.converter.BehaviorConverter;

import javax.persistence.*;

/**
 * Holds information about score bounds configuration of specific {@link Behavior}.
 * For example REQUIRED will be selected if score is from 90 to 100
 */
@Entity
@Table(name = Configuration.TABLE_NAME)
public class Configuration extends DtoEntity {

    public static final String TABLE_NAME = "Configuration";
    public static final String CONFIG_ID = "configuration_id";
    public static final String BEHAVIOR = "behavior";
    public static final String THRESHOLD_START = "threshold_start";
    public static final String THRESHOLD_END = "threshold_end";

    @Id
    @Column(name = CONFIG_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = BEHAVIOR)
    @Convert(converter = BehaviorConverter.class)
    private Behavior behavior;

    @Column(name = THRESHOLD_START)
    private Double thresholdStart;

    @Column(name = THRESHOLD_END)
    private Double thresholdEnd;

    @ManyToOne(cascade = CascadeType.ALL)
    private ConfigurationPack configurationPack;

    public Configuration() {

    }

    public Configuration(Behavior behavior, Double thresholdStart, Double thresholdEnd) {
        this.behavior = behavior;
        this.thresholdStart = thresholdStart;
        this.thresholdEnd = thresholdEnd;
    }

    public Behavior getBehavior() {
        return behavior;
    }

    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
    }

    public Double getThresholdStart() {
        return thresholdStart;
    }

    public void setThresholdStart(Double thresholdStart) {
        this.thresholdStart = thresholdStart;
    }

    public Double getThresholdEnd() {
        return thresholdEnd;
    }

    public void setThresholdEnd(Double thresholdEnd) {
        this.thresholdEnd = thresholdEnd;
    }

    public ConfigurationPack getConfigurationPack() {
        return configurationPack;
    }

    public void setConfigurationPack(ConfigurationPack configurationPack) {
        this.configurationPack = configurationPack;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
