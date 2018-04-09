package dao.impl;

import dao.ApplicationDao;
import dao.ComponentResourceDao;
import model.Application;
import model.ComponentResource;
import model.Screen;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

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

    @Override
    public List<ComponentResource> getComponentsWithLoadedScreens() {
        try {
            Query query = getEntityManager().createQuery(
                    "SELECT DISTINCT c FROM ComponentResource c left join fetch c.referencedScreens");
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
