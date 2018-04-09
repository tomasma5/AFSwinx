package dao;

import model.afclassification.ConfigurationPack;

import java.util.List;

/**
 * Mongo DAO for classification behaviour configurations packs
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
public interface ConfigurationPackDao extends AbstractGenericDao<ConfigurationPack> {

    /**
     * Find configuration pack by its name.
     *
     * @param name the name
     * @return the configuration pack
     */
    public ConfigurationPack findByName(String name);

    public List<ConfigurationPack> getAllWithLoadedConfigurations();

    public ConfigurationPack getByIdWithLoadedConfigurations(Integer configId);
}
