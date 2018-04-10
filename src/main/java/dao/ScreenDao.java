package dao;

import model.Screen;

import java.util.List;

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

    /**
     * Gets all screens with loadede components - can be used with lazily loaded collection of components
     *
     * @return list of screens with loaded components
     */
    public List<Screen> getScreensWithLoadedComponents();

    /**
     * Get screen with loaded components by id - can be used with lazily loaded collection of components
     *
     * @param screenId screen id
     * @return screen with loaded components
     */
    public Screen getScreenByIdWithLoadedComponents(Integer screenId);

    /**
     * Get screen with loaded components by screen key - can be used with lazily loaded collection of components
     *
     * @param screenKey screen key
     * @return screen with loaded components
     */
    public Screen getScreenByKeyWithLoadedComponents(String screenKey);

}
