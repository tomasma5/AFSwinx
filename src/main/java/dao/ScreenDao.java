package dao;

import model.Screen;

/**
 * Mongo DAO for application screens
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
public interface ScreenDao extends GenericMongoDao<Screen> {

    // no need to implement any more method that is in abstractDao

}
