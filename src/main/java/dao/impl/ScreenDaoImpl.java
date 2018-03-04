package dao.impl;

import dao.ApplicationDao;
import dao.ScreenDao;
import model.Application;
import model.Screen;
import org.bson.types.ObjectId;

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
    public Screen findByName(String name) {
        return collection.find(eq("heading", name)).first();
    }
}
