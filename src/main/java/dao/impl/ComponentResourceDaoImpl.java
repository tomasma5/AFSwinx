package dao.impl;

import dao.ApplicationDao;
import dao.ComponentResourceDao;
import model.Application;
import model.ComponentResource;
import model.Screen;

import javax.enterprise.context.ApplicationScoped;

/**
 * Implementation of DAO for component resources
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@ApplicationScoped
public class ComponentResourceDaoImpl extends AbstractGenericDaoImpl<ComponentResource> implements ComponentResourceDao {

    public ComponentResourceDaoImpl() {
        super(ComponentResource.class);
    }

}
