package com.tomscz.afserver.manager;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.Response.Status;

import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.IdGenerator;
import com.tomscz.afserver.persistence.entity.AbsenceType;
import com.tomscz.afserver.persistence.entity.Country;

@Stateless(name = AbsenceTypeManagerImpl.NAME)
public class AbsenceTypeManagerImpl extends BaseManager<AbsenceType>
        implements
            AbsenceTypeManager<AbsenceType>,
            Serializable {

    @Inject
    EntityManager em;

    @EJB
    CountryManager<Country> countryManager;

    private static final long serialVersionUID = 1L;

    public static final String NAME = "AbsenceTypeManager";

    @Override
    public AbsenceType findById(int id) throws BusinessException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AbsenceType> personQuery = cb.createQuery(AbsenceType.class);
        Root<AbsenceType> rootPersonQuery = personQuery.from(AbsenceType.class);
        Predicate idPredicate = cb.equal(rootPersonQuery.get("id"), id);
        personQuery.where(idPredicate);
        TypedQuery<AbsenceType> typedQuery = em.createQuery(personQuery);
        List<AbsenceType> resultList = typedQuery.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    @Override
    public List<AbsenceType> findAbsenceTypeInCountry(int countryId) throws BusinessException {
        Country country = countryManager.findById(countryId);
        if(country == null){
            throw new BusinessException(Status.BAD_REQUEST);
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AbsenceType> personQuery = cb.createQuery(AbsenceType.class);
        Root<AbsenceType> rootPersonQuery = personQuery.from(AbsenceType.class);
        Predicate passwordPredicate = cb.equal(rootPersonQuery.get("country"), country);
        personQuery.where(passwordPredicate);
        TypedQuery<AbsenceType> typedQuery = em.createQuery(personQuery);
        List<AbsenceType> resultList = typedQuery.getResultList();
        return resultList;
    }

    @Override
    public void updateAbsenceTypeBasedOnCountry(AbsenceType absenceType,int countryId) throws BusinessException {
        Country country = countryManager.findById(countryId);
        if(country == null){
            throw new BusinessException(Status.BAD_REQUEST);
        }
        absenceType.setCountry(country);
        if(absenceType.getId() == 0){
            absenceType.setId(IdGenerator.getNextAbsenceTypeId());
        }
        createOrupdate(absenceType);
    }

}
