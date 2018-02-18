package dao.impl;

import dao.ApplicationDao;
import dao.ScreenDao;
import model.Application;
import model.Screen;

import javax.enterprise.context.ApplicationScoped;

/**
 * Implementation of Mongo DAO for application screens
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@ApplicationScoped
public class ScreenDaoImpl extends GenericMongoDaoImpl<Screen> implements ScreenDao {

    public ScreenDaoImpl() {
    }

    @Override
    public Class getModelClass() {
        return Screen.class;
    }

    @Override
    public String getCollectionName() {
        return "screens";
    }

}
