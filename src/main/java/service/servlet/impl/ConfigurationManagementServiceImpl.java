package service.servlet.impl;

import dao.ConfigurationDao;
import model.afclassification.ConfigurationPack;
import org.bson.types.ObjectId;
import service.servlet.ConfigurationManagementService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of configuration management service
 */
@Named("configurationManagementService")
@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
public class ConfigurationManagementServiceImpl implements ConfigurationManagementService {

    @Inject
    private ConfigurationDao configurationDao;

    @Override
    public void createConfiguration(ConfigurationPack configuration) {
        configurationDao.create(configuration);
    }

    @Override
    public void removeConfigurationById(ObjectId id) {
        configurationDao.deleteByObjectId(id);
    }

    @Override
    public void updateConfiguration(ConfigurationPack updatedConfiguration) {
        configurationDao.update(updatedConfiguration);
    }

    @Override
    public List<ConfigurationPack> getAllConfigurationsByApplication(ObjectId applicationId) {
        return configurationDao.findAll().stream()
                .filter(config -> config.getApplicationId().equals(applicationId))
                .collect(Collectors.toList());
    }

    @Override
    public ConfigurationPack findConfigurationById(ObjectId id) {
        return configurationDao.findById(id);
    }

    @Override
    public ConfigurationPack findOrCreateNewConfiguration(String configurationId) {
        ConfigurationPack configurationPack;
        if (configurationId == null || configurationId.isEmpty()) {
            configurationPack = new ConfigurationPack(false);
            configurationPack.setId(new ObjectId());
        } else {
            configurationPack = findConfigurationById(new ObjectId(configurationId));
        }
        return configurationPack;
    }
}
