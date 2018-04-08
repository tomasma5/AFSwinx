package dao.impl;

import dao.BusinessFieldsDao;
import dao.BusinessPhaseDao;
import model.afclassification.BCField;
import model.afclassification.BCPhase;

import javax.enterprise.context.ApplicationScoped;

/**
 * Implementation of Mongo DAO for business fields
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@ApplicationScoped
public class BusinessFieldsDaoImpl extends AbstractGenericDaoImpl<BCField> implements BusinessFieldsDao {

    public BusinessFieldsDaoImpl() {
        super(BCField.class);
    }

}
