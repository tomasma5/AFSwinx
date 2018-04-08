package service.servlet.impl;

import dao.ConfigurationDao;
import model.afclassification.ConfigurationPack;
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
    public void createOrUpdate(ConfigurationPack configuration) {
        configurationDao.createOrUpdate(configuration);
    }

    @Override
    public void removeConfigurationById(ConfigurationPack pack) {
        configurationDao.delete(pack);
    }

    @Override
    public List<ConfigurationPack> getAllConfigurationsByApplication(int applicationId) {
        return configurationDao.getAll().stream()
                .filter(config -> config.getApplication().getId() == applicationId)
                .collect(Collectors.toList());
    }

    @Override
    public ConfigurationPack findConfigurationById(int id) {
        return configurationDao.getById(id);
    }

    @Override
    public ConfigurationPack findOrCreateNewConfiguration(String configurationId) {
        ConfigurationPack configurationPack;
        if (configurationId == null || configurationId.isEmpty()) {
            configurationPack = new ConfigurationPack(false);
        } else {
            configurationPack = findConfigurationById(Integer.parseInt(configurationId));
        }
        return configurationPack;
    }
}
