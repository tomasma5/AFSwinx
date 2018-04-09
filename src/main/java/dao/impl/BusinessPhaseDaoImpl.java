package dao.impl;

import dao.BusinessPhaseDao;
import model.afclassification.BCPhase;
import model.afclassification.BusinessCase;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

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

    @Override
    public List<BCPhase> getBusinessPhasesWithLoadedScreens() {
        try {
            Query query = getEntityManager().createQuery(
                    "SELECT DISTINCT bp FROM BCPhase bp left join fetch bp.linkedScreens");
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public BCPhase getBusinessPhaseByIdWithLoadedScreens(Integer bpId) {
        try {
            Query query = getEntityManager().createQuery(
                    "SELECT DISTINCT bp FROM BCPhase bp left join fetch bp.linkedScreens where bp.id = :bpId")
                    .setParameter("bpId", bpId);
            return (BCPhase) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
