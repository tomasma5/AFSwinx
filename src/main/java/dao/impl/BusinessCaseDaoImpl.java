package dao.impl;

import dao.BusinessCaseDao;
import model.afclassification.BusinessCase;
import javax.enterprise.context.ApplicationScoped;

/**
 * Implementation of Mongo DAO for business cases
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@ApplicationScoped
public class BusinessCaseDaoImpl extends AbstractGenericDaoImpl<BusinessCase> implements BusinessCaseDao {

    public BusinessCaseDaoImpl() {
        super(BusinessCase.class);
    }

}
