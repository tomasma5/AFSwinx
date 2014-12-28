package com.tomscz.afserver.manager;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.Response.Status;

import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.AbsenceInstance;
import com.tomscz.afserver.persistence.entity.Person;
import com.tomscz.afserver.persistence.entity.UserRoles;
import com.tomscz.afserver.ws.security.AFSecurityContext;

public class AbsenceInstanceManagerImpl extends BaseManager<AbsenceInstance>
        implements
            AbsenceInstanceManager<AbsenceInstance>,
            Serializable {

    @EJB
    PersonManager<Person> personManager;

    private static final long serialVersionUID = 1L;

    public static final String NAME = "AbsenceInstanceManager";

    @Override
    public AbsenceInstance findById(int id) throws BusinessException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AbsenceInstance> absenceInstanceQuery = cb.createQuery(AbsenceInstance.class);
        Root<AbsenceInstance> rootAbsenceInstanceQuery = absenceInstanceQuery.from(AbsenceInstance.class);
        Predicate personPredicate = cb.equal(rootAbsenceInstanceQuery.get("id"), id);
        absenceInstanceQuery.where(personPredicate);
        TypedQuery<AbsenceInstance> typedQuery = em.createQuery(absenceInstanceQuery);
        List<AbsenceInstance> resultList = typedQuery.getResultList();
        if(!resultList.isEmpty()){
            return resultList.get(0);
        }
        return null;
    }

    @Override
    public List<AbsenceInstance> findInstanceByUser(String userName, AFSecurityContext securityContext) throws BusinessException {
        if(!securityContext.isUserInRole(UserRoles.ADMIN) && !securityContext.getLoggedUserName().equals(userName)){
            throw new BusinessException(Status.FORBIDDEN);
        }
        Person person = personManager.findUser(userName);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AbsenceInstance> absenceInstanceQuery = cb.createQuery(AbsenceInstance.class);
        Root<AbsenceInstance> rootAbsenceInstanceQuery = absenceInstanceQuery.from(AbsenceInstance.class);
        Predicate personPredicate = cb.equal(rootAbsenceInstanceQuery.get("person"), person);
        absenceInstanceQuery.where(personPredicate);
        TypedQuery<AbsenceInstance> typedQuery = em.createQuery(absenceInstanceQuery);
        List<AbsenceInstance> resultList = typedQuery.getResultList();
        return resultList;
    }

}
