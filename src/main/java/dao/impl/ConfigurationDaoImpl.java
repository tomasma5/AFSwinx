package dao.impl;

import dao.ConfigurationDao;
import model.afclassification.ConfigurationPack;
import servlet.ParameterNames;

import javax.enterprise.context.ApplicationScoped;

import static com.mongodb.client.model.Filters.eq;

/**
 * Implementation of Mongo DAO for classification behaviour configurations
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@ApplicationScoped
public class ConfigurationDaoImpl extends GenericMongoDaoImpl<ConfigurationPack> implements ConfigurationDao {

    public ConfigurationDaoImpl() {
    }

    @Override
    public Class getModelClass() {
        return ConfigurationPack.class;
    }

    @Override
    public String getCollectionName() {
        return "configurations";
    }

    @Override
    public ConfigurationPack findByName(String name) {
        return collection.find(eq(ParameterNames.CONFIGURATION_NAME, name)).first();
    }
}
