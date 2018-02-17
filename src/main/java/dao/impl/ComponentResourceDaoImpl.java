package dao.impl;

import dao.ApplicationDao;
import dao.ComponentResourceDao;
import model.Application;
import model.ComponentResource;
import model.Screen;

import javax.enterprise.context.ApplicationScoped;

/**
 * Implementation of Mongo DAO for component resources
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@ApplicationScoped
public class ComponentResourceDaoImpl extends GenericMongoDaoImpl<ComponentResource> implements ComponentResourceDao {

    @Override
    public Class getModelClass() {
        return Screen.class;
    }

    @Override
    public String getCollectionName() {
        return "Screens";
    }

}
