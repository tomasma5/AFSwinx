package dao;

import model.ComponentResource;

import java.util.List;

/**
 * DAO for component resources
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
public interface ComponentResourceDao extends AbstractGenericDao<ComponentResource> {

    // no need to implement any more method that is in abstractDao

    public List<ComponentResource> getComponentsWithLoadedScreens();
}
