package model.afclassification;

import model.Application;
import model.DtoEntity;

import javax.persistence.*;
import java.util.*;

/**
 * Holds multiple configurations, for each {@link Behavior} one. It is also binded to application and has name for
 * better identification in system
 */
@Entity
@Table(name = ConfigurationPack.TABLE_NAME)
public class ConfigurationPack extends DtoEntity {

    public static final String TABLE_NAME = "configuration_pack";
    public static final String CONFIG_PACK_ID = "configuration_pack_id";
    public static final String CONFIG_PACK_NAME = "pack_name";

    @Id
    @Column(name = CONFIG_PACK_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = CONFIG_PACK_NAME)
    private String configurationName;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "configurationPack")
    private List<Configuration> configurationMap;

    @ManyToOne
    @JoinColumn(name = Application.APPLICATION_ID)
    private Application application;

    public ConfigurationPack() {
    }

    public ConfigurationPack(boolean insertDefaultValues) {
        if (insertDefaultValues) {
            fillWithDefaultValues();
        }
    }

    private void fillWithDefaultValues() {
        addConfiguration(Behavior.NOT_PRESENT, 0, 10);
        addConfiguration(Behavior.HIDDEN, 10, 40);
        addConfiguration(Behavior.ONLY_DISPLAY, 40, 60);
        addConfiguration(Behavior.VALIDATION, 60, 90);
        addConfiguration(Behavior.REQUIRED, 90, 100);
    }

    public List<Configuration> getConfigurations() {
        return configurationMap;
    }

    public void setConfigurations(List<Configuration> configurationMap) {
        this.configurationMap = configurationMap;
    }

    public void addConfiguration(Configuration configuration) {
        if (configurationMap == null) {
            configurationMap = new ArrayList<>();
        }
        configurationMap.add(configuration);
    }

    public void addConfiguration(Behavior behavior, double thresholdStart, double thresholdEnd) {
        if (thresholdEnd < thresholdStart) {
            //start should be lower than end - if not swap it
            double tmp = thresholdStart;
            thresholdStart = thresholdEnd;
            thresholdEnd = tmp;
        }
        Configuration configuration = new Configuration(behavior, thresholdStart, thresholdEnd);
        addConfiguration(configuration);
    }

    public String getConfigurationName() {
        return configurationName;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public void setConfigurationName(String configurationName) {
        this.configurationName = configurationName;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
