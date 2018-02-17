package dao;

import model.Application;

/**
 * Mongo DAO for applications
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
public interface ApplicationDao extends GenericMongoDao<Application> {

    // no need to implement any more method that is in abstractDao

}
