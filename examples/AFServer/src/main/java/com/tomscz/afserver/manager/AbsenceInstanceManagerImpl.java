package com.tomscz.afserver.manager;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.Response.Status;

import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.IdGenerator;
import com.tomscz.afserver.persistence.entity.AbsenceInstance;
import com.tomscz.afserver.persistence.entity.AbsenceInstanceState;
import com.tomscz.afserver.persistence.entity.AbsenceType;
import com.tomscz.afserver.persistence.entity.Person;
import com.tomscz.afserver.persistence.entity.UserRoles;
import com.tomscz.afserver.ws.security.AFSecurityContext;

@Stateless(name = AbsenceInstanceManagerImpl.NAME)
public class AbsenceInstanceManagerImpl extends BaseManager<AbsenceInstance>
        implements
            AbsenceInstanceManager<AbsenceInstance>,
            Serializable {

    @EJB
    PersonManager<Person> personManager;

    @EJB
    AbsenceTypeManager<AbsenceType> absenceTypeManager;

    private static final long serialVersionUID = 1L;

    public static final String NAME = "AbsenceInstanceManager";

    @Override
    public AbsenceInstance findById(int id) throws BusinessException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AbsenceInstance> absenceInstanceQuery = cb.createQuery(AbsenceInstance.class);
        Root<AbsenceInstance> rootAbsenceInstanceQuery =
                absenceInstanceQuery.from(AbsenceInstance.class);
        Predicate personPredicate = cb.equal(rootAbsenceInstanceQuery.get("id"), id);
        absenceInstanceQuery.where(personPredicate);
        TypedQuery<AbsenceInstance> typedQuery = em.createQuery(absenceInstanceQuery);
        List<AbsenceInstance> resultList = typedQuery.getResultList();
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }

    @Override
    public List<AbsenceInstance> findInstanceByUser(String username,
            AFSecurityContext securityContext) throws BusinessException {
        if (!securityContext.isUserInRole(UserRoles.ADMIN)
                && !securityContext.getLoggedUserName().equals(username)) {
            throw new BusinessException(Status.FORBIDDEN);
        }
        Person person = personManager.findUser(username);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AbsenceInstance> absenceInstanceQuery = cb.createQuery(AbsenceInstance.class);
        Root<AbsenceInstance> rootAbsenceInstanceQuery =
                absenceInstanceQuery.from(AbsenceInstance.class);
        Predicate personPredicate =
                cb.equal(rootAbsenceInstanceQuery.get("affectedPerson"), person);
        absenceInstanceQuery.where(personPredicate);
        TypedQuery<AbsenceInstance> typedQuery = em.createQuery(absenceInstanceQuery);
        List<AbsenceInstance> resultList = typedQuery.getResultList();
        return resultList;
    }

    @Override
    public void createOrUpdate(AbsenceInstance absenceInstance, String username,
            AFSecurityContext securityContext) throws BusinessException {
        if (!securityContext.isUserInRole(UserRoles.ADMIN)) {
            if (!username.equals(securityContext.getLoggedUserName())) {
                throw new BusinessException(Status.FORBIDDEN);
            }
        }
        if (absenceInstance.getId() == 0) {
            absenceInstance = createNewAbsenceInstance(absenceInstance, username);
        } else {
            absenceInstance = updateAbsenceInstance(absenceInstance, username, securityContext);
        }
        Date startDate = absenceInstance.getStartDate();
        Date endData = absenceInstance.getEndDate();
        long diffInMillies = endData.getTime() - startDate.getTime();
        int days = (int) (diffInMillies / (1000 * 60 * 60 * 24));
        absenceInstance.setDuration(days);
        super.createOrupdate(absenceInstance);
    }

    private AbsenceInstance createNewAbsenceInstance(AbsenceInstance absenceInstance,
            String username) throws BusinessException {
        AbsenceType typeOfAbsence =
                absenceTypeManager.findById(absenceInstance.getAbsenceType().getId());
        absenceInstance.setAbsenceType(typeOfAbsence);
        absenceInstance.setId(IdGenerator.getNextAbsenceInstanceId());
        absenceInstance.setStatus(AbsenceInstanceState.REQUESTED);
        Person person = personManager.findUser(username);
        absenceInstance.setAffectedPerson(person);
        return absenceInstance;
    }

    private AbsenceInstance updateAbsenceInstance(AbsenceInstance absenceInstance, String username,
            AFSecurityContext securityContext) throws BusinessException {
        AbsenceInstance existedAbsenceInstance = findById(absenceInstance.getId());
        if (!securityContext.isUserInRole(UserRoles.ADMIN)
                && (!absenceInstance.getStatus().equals(AbsenceInstanceState.REQUESTED) || !absenceInstance
                        .getStatus().equals(AbsenceInstanceState.CANCELLED))) {
            throw new BusinessException(Status.BAD_REQUEST);
        }
        existedAbsenceInstance.setStatus(absenceInstance.getStatus());
        existedAbsenceInstance.setStartDate(absenceInstance.getStartDate());
        existedAbsenceInstance.setEndDate(absenceInstance.getEndDate());
        return existedAbsenceInstance;
    }

    @Override
    public List<AbsenceInstance> findEditableInstanceByUser(String username,
            AFSecurityContext securityContext) throws BusinessException {
        if (securityContext.isUserInRole(UserRoles.ADMIN)) {
            return findEditableByAdmin(username);
        } else if (securityContext.isUserInRole(UserRoles.USER)
                && securityContext.getLoggedUserName().equals(username)) {
            return findEditableByCommonUser(username);
        }
        throw new BusinessException(Status.FORBIDDEN);
    }

    private List<AbsenceInstance> findEditableByCommonUser(String username)
            throws BusinessException {
        Person person = personManager.findUser(username);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AbsenceInstance> absenceInstanceQuery = cb.createQuery(AbsenceInstance.class);
        Root<AbsenceInstance> rootAbsenceInstanceQuery =
                absenceInstanceQuery.from(AbsenceInstance.class);
        Predicate personPredicate =
                cb.equal(rootAbsenceInstanceQuery.get("affectedPerson"), person);
        Predicate statusPredicate =
                cb.or(cb.equal(rootAbsenceInstanceQuery.get("status"),
                        AbsenceInstanceState.REQUESTED), cb.equal(
                        rootAbsenceInstanceQuery.get("status"), AbsenceInstanceState.CANCELLED));
        absenceInstanceQuery.where(cb.and(personPredicate, statusPredicate));
        TypedQuery<AbsenceInstance> typedQuery = em.createQuery(absenceInstanceQuery);
        List<AbsenceInstance> resultList = typedQuery.getResultList();
        return resultList;
    }

    private List<AbsenceInstance> findEditableByAdmin(String username) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AbsenceInstance> absenceInstanceQuery = cb.createQuery(AbsenceInstance.class);
        TypedQuery<AbsenceInstance> typedQuery = em.createQuery(absenceInstanceQuery);
        List<AbsenceInstance> resultList = typedQuery.getResultList();
        return resultList;
    }

}
