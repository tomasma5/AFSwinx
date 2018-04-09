package service.servlet.impl;

import dao.ApplicationDao;
import dao.ConfigurationDao;
import dao.ConfigurationPackDao;
import model.Application;
import model.afclassification.Behavior;
import model.afclassification.Configuration;
import model.afclassification.ConfigurationPack;
import service.servlet.ConfigurationManagementService;
import servlet.ParameterNames;
import utils.Utils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
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
    private ConfigurationPackDao configurationPackDao;

    @Inject
    private ConfigurationDao configurationDao;

    @Inject
    private ApplicationDao applicationDao;

    @Override
    public void createOrUpdate(ConfigurationPack configuration) {
        configurationPackDao.createOrUpdate(configuration);
    }

    @Override
    public void removeConfigurationById(Integer packId) {
        configurationPackDao.delete(configurationPackDao.getById(packId));
    }

    @Override
    public List<ConfigurationPack> getAllConfigurationsByApplication(int applicationId) {
        return configurationPackDao.getAll().stream()
                .filter(config -> config.getApplication().getId() == applicationId)
                .collect(Collectors.toList());
    }

    @Override
    public ConfigurationPack findConfigurationById(int id) {
        return configurationPackDao.getById(id);
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

    @Override
    public void saveConfigurationPackFromRequest(HttpServletRequest req, String configId, String appIdString, String configMapRecordsCount) {
        ConfigurationPack configuration = findOrCreateNewConfiguration(configId);
        updateConfigurationProperties(req, appIdString, configuration, Integer.parseInt(configMapRecordsCount));
        createOrUpdate(configuration);
    }

    private void updateConfigurationProperties(
            HttpServletRequest req, String appIdString, ConfigurationPack configurationPack, int configPropertiesCount
    ) {
        int appId = Integer.parseInt(appIdString);
        Application application = applicationDao.getById(appId);
        configurationPack.setApplication(application);
        String configurationName = Utils.trimString(req.getParameter(ParameterNames.CONFIGURATION_NAME));
        configurationPack.setConfigurationName(configurationName);
        for (int i = 0; i < configPropertiesCount; i++) {
            String behaviour = Utils.trimString(req.getParameter(ParameterNames.CONFIGURATION_BEHAVIOUR + i));
            String thresholdStart = Utils.trimString(req.getParameter(ParameterNames.CONFIGURATION_THRESHOLD_START + i));
            String thresholdEnd = Utils.trimString(req.getParameter(ParameterNames.CONFIGURATION_THRESHOLD_END + i));
            Configuration config = findOrCreateConfiguration(configurationPack, Behavior.valueOf(behaviour));
            config.setThresholdStart(Double.parseDouble(thresholdStart));
            config.setThresholdEnd(Double.parseDouble(thresholdEnd));
            config.setConfigurationPack(configurationPack);
            configurationDao.createOrUpdate(config);
            configurationPack.addConfiguration(config);
        }
    }

    private Configuration findOrCreateConfiguration(ConfigurationPack pack, Behavior behavior) {
        if (pack.getConfigurations() != null) {
            for (Configuration configuration : pack.getConfigurations()) {
                if (configuration.getBehavior().equals(behavior)) {
                    return configuration;
                }
            }
        }
        Configuration configuration = new Configuration();
        configuration.setBehavior(behavior);
        return configuration;
    }
}
