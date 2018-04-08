package dao.impl;

import dao.BusinessPhaseDao;
import model.afclassification.BCPhase;
import model.afclassification.BusinessCase;

import javax.enterprise.context.ApplicationScoped;

/**
 * Implementation of Mongo DAO for business phases
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@ApplicationScoped
public class BusinessPhaseDaoImpl extends AbstractGenericDaoImpl<BCPhase> implements BusinessPhaseDao {

    public BusinessPhaseDaoImpl() {
        super(BCPhase.class);
    }

}
