package dao.impl;

import dao.BusinessCaseDao;
import model.Screen;
import model.afclassification.BusinessCase;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

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

    @Override
    public List<BusinessCase> getBusinessCasesWithLoadedPhases() {
        try {
            Query query = getEntityManager().createQuery(
                    "SELECT DISTINCT bc FROM BusinessCase bc left join fetch bc.phases");
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
