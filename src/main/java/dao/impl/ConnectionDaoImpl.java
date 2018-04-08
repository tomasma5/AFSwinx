package dao.impl;

import dao.ConnectionDao;
import dao.ConnectionPackDao;
import model.ComponentConnection;
import model.ComponentConnectionPack;

import javax.enterprise.context.ApplicationScoped;

/**
 * Implementation of Mongo DAO for component connections
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@ApplicationScoped
public class ConnectionDaoImpl extends AbstractGenericDaoImpl<ComponentConnection> implements ConnectionDao {

    public ConnectionDaoImpl() {
        super(ComponentConnection.class);
    }

}
