package dao.impl;

import dao.ConnectionPackDao;
import model.ComponentConnectionPack;

import javax.enterprise.context.ApplicationScoped;

/**
 * Implementation of DAO for connection packs
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@ApplicationScoped
public class ConnectionPackDaoImpl extends AbstractGenericDaoImpl<ComponentConnectionPack> implements ConnectionPackDao {

    public ConnectionPackDaoImpl() {
        super(ComponentConnectionPack.class);
    }

}
