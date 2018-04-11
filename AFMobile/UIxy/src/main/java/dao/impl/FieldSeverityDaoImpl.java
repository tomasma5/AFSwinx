package dao.impl;

import dao.FieldSeverityDao;
import model.afclassification.BCFieldSeverity;

import javax.enterprise.context.ApplicationScoped;

/**
 * Implementation of DAO for field severities
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@ApplicationScoped
public class FieldSeverityDaoImpl extends AbstractGenericDaoImpl<BCFieldSeverity> implements FieldSeverityDao {

    public FieldSeverityDaoImpl() {
        super(BCFieldSeverity.class);
    }

}
