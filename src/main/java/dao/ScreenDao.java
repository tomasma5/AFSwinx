package dao;

import model.Screen;
import org.bson.types.ObjectId;

/**
 * Mongo DAO for application screens
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
public interface ScreenDao extends GenericMongoDao<Screen> {

    public Screen findByName(String name);

}
