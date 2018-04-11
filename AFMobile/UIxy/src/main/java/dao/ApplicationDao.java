package dao;

import model.Application;

/**
 * Mongo DAO for applications
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
public interface ApplicationDao extends AbstractGenericDao<Application> {

    public Application findByName(String name);

    public Application findByUuid(String uuid);

}
