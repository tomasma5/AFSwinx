package service.servlet;

import model.afclassification.ConfigurationPack;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
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
     * @param packId the id
     */
    public void removeConfigurationById(Integer packId) throws ServiceException;

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

    /**
     * Creates or finds configuration pack and fills it with data from request
     * @param req the request
     * @param configId string representation of configuration id
     * @param appIdString string representation of application id
     * @param configMapRecordsCount how many configuration records should be in config pack
     */
    public void saveConfigurationPackFromRequest(HttpServletRequest req, String configId, String appIdString, String configMapRecordsCount);
}
