package dao;

import model.Screen;

/**
 * Mongo DAO for application screens
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
public interface ScreenDao extends AbstractGenericDao<Screen> {

    /**
     * Find screen by key.
     *
     * @param name the name
     * @return the screen
     */
    public Screen findByKey(String name);

}
