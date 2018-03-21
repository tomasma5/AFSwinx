package dao.impl;

import dao.ApplicationDao;
import model.Application;
import servlet.ParameterNames;

import javax.enterprise.context.ApplicationScoped;

import static com.mongodb.client.model.Filters.eq;

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

    @Override
    public Application findByName(String name) {
        return collection.find(eq(ParameterNames.APPLICATION_NAME, name)).first();
    }

    @Override
    public Application findByUuid(String uuid) {
        return collection.find(eq(ParameterNames.APPLICATION_UUID, uuid)).first();
    }
}
