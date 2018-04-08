package service.servlet;

import model.afclassification.ConfigurationPack;

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
    public void createOrUpdate(ConfigurationPack configuration);

    /**
     * Remove configuration by id.
     *
     * @param pack the id
     */
    public void removeConfigurationById(ConfigurationPack pack);

    /**
     * Gets all configurations by application.
     *
     * @param applicationId the application id
     * @return the all configurations by application
     */
    public List<ConfigurationPack> getAllConfigurationsByApplication(int applicationId);

    /**
     * Find configuration by id.
     *
     * @param id the id
     * @return the configuration pack
     */
    public ConfigurationPack findConfigurationById(int id);

    /**
     * Find or create new configuration pack.
     *
     * @param configurationId the configuration id
     * @return the configuration pack
     */
    public ConfigurationPack findOrCreateNewConfiguration(String configurationId);


}
