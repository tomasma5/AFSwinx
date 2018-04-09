package dao.impl;

import dao.ConfigurationPackDao;
import model.afclassification.ConfigurationPack;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;


/**
 * Implementation of Mongo DAO for classification behaviour configuration packs
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@ApplicationScoped
public class ConfigurationPackDaoImpl extends AbstractGenericDaoImpl<ConfigurationPack> implements ConfigurationPackDao {

    public ConfigurationPackDaoImpl() {
        super(ConfigurationPack.class);
    }

    @Override
    public ConfigurationPack findByName(String name) {
        String query = ConfigurationPack.CONFIG_PACK_NAME + " = :name";
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);

        return getByWhereConditionSingleResult(query, params);
    }
}
