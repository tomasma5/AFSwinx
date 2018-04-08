package dao;

import model.afclassification.ConfigurationPack;

/**
 * Mongo DAO for classification behaviour configurations
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
public interface ConfigurationDao extends AbstractGenericDao<ConfigurationPack> {

    /**
     * Find configuration pack by its name.
     *
     * @param name the name
     * @return the configuration pack
     */
    public ConfigurationPack findByName(String name);

}
