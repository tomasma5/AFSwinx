package com.tomscz.afserver.manager;

import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.BusinessTrip;
import com.tomscz.afserver.persistence.entity.BusinessTripPart;
import com.tomscz.afserver.persistence.entity.Person;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

@Stateless(name = BusinessTripPartManagerImpl.name)
public class BusinessTripPartManagerImpl extends BaseManager<BusinessTripPart>
        implements BusinessTripPartManager<BusinessTripPart>, Serializable {

    @EJB
    private BusinessTripManager<BusinessTrip> businessTripManager;

    public static final String name = "BusinessTripPartManager";

    private static final long serialVersionUID = 1L;

    @Override
    public BusinessTripPart findById(int id) throws BusinessException {
        TypedQuery<BusinessTripPart> query = em.createQuery(
                "SELECT btp FROM BusinessTripPart btp WHERE btp.id = :id", BusinessTripPart.class);
        List<BusinessTripPart> businessTripParts = query.setParameter("id", id).getResultList();
        if (!businessTripParts.isEmpty()) {
            return businessTripParts.get(0);
        }
        return null;
    }

    @Override
    public List<BusinessTripPart> getAllBusinessTripParts(int businessTripId) throws BusinessException {
        try {
            BusinessTrip businessTrip = businessTripManager.findById(businessTripId);
            if (businessTrip != null) {
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<BusinessTripPart> btpQuery = cb.createQuery(BusinessTripPart.class);
                Root<BusinessTripPart> rootBtpQuery = btpQuery.from(BusinessTripPart.class);
                Predicate businessTripPredicate = cb.equal(rootBtpQuery.get("businessTrip"), businessTrip);
                btpQuery.where(businessTripPredicate);
                TypedQuery<BusinessTripPart> typedQuery = em.createQuery(btpQuery);
                return typedQuery.getResultList();
            }
        } catch (BusinessException e) {
            throw e;
        }
        return null;
    }
}
