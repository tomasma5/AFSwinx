package model.afclassification;

import model.MongoDocumentEntity;
import org.bson.types.ObjectId;

import java.util.*;

/**
 * Holds multiple configurations, for each {@link Behavior} one. It is also binded to application and has name for
 * better identification in system
 */
public class ConfigurationPack extends MongoDocumentEntity {

    private String configurationName;

    private List<Configuration> configurationMap;

    private ObjectId applicationId;

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

    public ObjectId getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(ObjectId applicationId) {
        this.applicationId = applicationId;
    }

    public void setConfigurationName(String configurationName) {
        this.configurationName = configurationName;
    }
}
