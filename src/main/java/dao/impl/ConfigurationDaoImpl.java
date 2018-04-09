package dao.impl;

import dao.ConfigurationDao;
import dao.ConfigurationPackDao;
import dao.ConnectionDao;
import model.afclassification.Configuration;
import model.afclassification.ConfigurationPack;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;


/**
 * Implementation of DAO for classification behaviour configurations
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@ApplicationScoped
public class ConfigurationDaoImpl extends AbstractGenericDaoImpl<Configuration> implements ConfigurationDao {

    public ConfigurationDaoImpl() {
        super(Configuration.class);
    }

}
