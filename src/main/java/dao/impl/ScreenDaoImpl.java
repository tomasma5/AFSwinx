package dao.impl;

import dao.ScreenDao;
import model.Screen;
import servlet.ParameterNames;

import javax.enterprise.context.ApplicationScoped;

import static com.mongodb.client.model.Filters.eq;

/**
 * Implementation of Mongo DAO for application screens
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@ApplicationScoped
public class ScreenDaoImpl extends GenericMongoDaoImpl<Screen> implements ScreenDao {

    public ScreenDaoImpl() {
    }

    @Override
    public Class getModelClass() {
        return Screen.class;
    }

    @Override
    public String getCollectionName() {
        return "screens";
    }

    @Override
    public Screen findByKey(String key) {
        return collection.find(eq(ParameterNames.SCREEN_KEY, key)).first();
    }
}
