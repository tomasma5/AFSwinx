package dao.impl;

import dao.ApplicationDao;
import model.Application;

import javax.enterprise.context.ApplicationScoped;

/**
 * Implementation of Mongo DAO for devices with status and its nearby devices
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@ApplicationScoped
public class ApplicationDaoImpl extends GenericMongoDaoImpl<Application> implements ApplicationDao {

    public ApplicationDaoImpl() {
    }

    @Override
    public Class getModelClass() {
        return Application.class;
    }

    @Override
    public String getCollectionName() {
        return "applications";
    }

}
