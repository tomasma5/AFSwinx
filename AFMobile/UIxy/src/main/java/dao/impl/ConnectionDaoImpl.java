package dao.impl;

import dao.ConnectionDao;
import model.ComponentConnection;

import javax.enterprise.context.ApplicationScoped;

/**
 * Implementation of DAO for component connections
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
