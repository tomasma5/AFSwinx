package service.servlet;

import model.Screen;
import model.afclassification.Configuration;
import model.afclassification.ConfigurationPack;
import org.bson.types.ObjectId;

import java.util.List;

public interface ConfigurationManagementService {

    public void createConfiguration(ConfigurationPack configuration);

    public void removeConfigurationById(ObjectId id);

    public void updateConfiguration(ConfigurationPack updatedConfiguration);

    public List<ConfigurationPack> getAllConfigurationsByApplication(ObjectId applicationId);

    public ConfigurationPack findConfigurationById(ObjectId id);

    public ConfigurationPack findOrCreateNewConfiguration(String configurationId);
}
