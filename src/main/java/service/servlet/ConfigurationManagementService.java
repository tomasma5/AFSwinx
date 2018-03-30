package service.servlet;

import model.afclassification.ConfigurationPack;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * This service is used for {@link ConfigurationPack} management
 */
public interface ConfigurationManagementService {

    /**
     * Create configuration.
     *
     * @param configuration the configuration
     */
    public void createConfiguration(ConfigurationPack configuration);

    /**
     * Remove configuration by id.
     *
     * @param id the id
     */
    public void removeConfigurationById(ObjectId id);

    /**
     * Update configuration.
     *
     * @param updatedConfiguration the updated configuration
     */
    public void updateConfiguration(ConfigurationPack updatedConfiguration);

    /**
     * Gets all configurations by application.
     *
     * @param applicationId the application id
     * @return the all configurations by application
     */
    public List<ConfigurationPack> getAllConfigurationsByApplication(ObjectId applicationId);

    /**
     * Find configuration by id.
     *
     * @param id the id
     * @return the configuration pack
     */
    public ConfigurationPack findConfigurationById(ObjectId id);

    /**
     * Find or create new configuration pack.
     *
     * @param configurationId the configuration id
     * @return the configuration pack
     */
    public ConfigurationPack findOrCreateNewConfiguration(String configurationId);


}
