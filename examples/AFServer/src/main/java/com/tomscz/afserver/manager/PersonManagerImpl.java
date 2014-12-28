package com.tomscz.afserver.manager;

import java.io.Serializable;
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
import com.tomscz.afserver.persistence.entity.Address;
import com.tomscz.afserver.persistence.entity.Country;
import com.tomscz.afserver.persistence.entity.Person;
import com.tomscz.afserver.persistence.entity.UserRoles;
import com.tomscz.afserver.ws.security.AFSecurityContext;

@Stateless(name = PersonManagerImpl.name)
public class PersonManagerImpl extends BaseManager<Person>
        implements
            Serializable,
            PersonManager<Person> {

    @EJB
    CountryManager<Country> countryManager;

    @EJB
    AddressManager<Address> addressManager;

    private static final long serialVersionUID = 1L;

    public static final String name = "PersonManager";

    @Override
    public Person findById(int id) throws BusinessException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> personQuery = cb.createQuery(Person.class);
        Root<Person> rootPersonQuery = personQuery.from(Person.class);
        Predicate idPredicate = cb.equal(rootPersonQuery.get("id"), id);
        personQuery.where(idPredicate);
        TypedQuery<Person> typedQuery = em.createQuery(personQuery);
        List<Person> resultList = typedQuery.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    @Override
    public Person findUser(String login) throws BusinessException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> personQuery = cb.createQuery(Person.class);
        Root<Person> rootPersonQuery = personQuery.from(Person.class);
        Predicate loginPredicate = cb.equal(rootPersonQuery.get("login"), login);
        personQuery.where(loginPredicate);
        TypedQuery<Person> typedQuery = em.createQuery(personQuery);
        List<Person> resultList = typedQuery.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    @Override
    public Person findUser(String login, String password) throws BusinessException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> personQuery = cb.createQuery(Person.class);
        Root<Person> rootPersonQuery = personQuery.from(Person.class);
        Predicate loginPredicate = cb.equal(rootPersonQuery.get("login"), login);
        Predicate passwordPredicate = cb.equal(rootPersonQuery.get("password"), password);
        personQuery.where(cb.and(loginPredicate, passwordPredicate));
        TypedQuery<Person> typedQuery = em.createQuery(personQuery);
        List<Person> resultList = typedQuery.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    @Override
    public List<Person> findAllUsers() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> personQuery = cb.createQuery(Person.class);
        TypedQuery<Person> typedQuery = em.createQuery(personQuery);
        List<Person> resultList = typedQuery.getResultList();
        return resultList;
    }

    @Override
    public List<Person> findUsersByCountry(int countryId) throws BusinessException {
        try {
            Country country = countryManager.findById(countryId);
            if (country != null) {
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<Person> personQuery = cb.createQuery(Person.class);
                Root<Person> rootPersonQuery = personQuery.from(Person.class);
                Predicate countryPredicate = cb.equal(rootPersonQuery.get("country"), country);
                personQuery.where(countryPredicate);
                TypedQuery<Person> typedQuery = em.createQuery(personQuery);
                List<Person> resultList = typedQuery.getResultList();
                return resultList;
            }
        } catch (BusinessException e) {
            throw e;
        }
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Person findUserByCountry(String countryName) {
        throw new UnsupportedOperationException("This operation is not supported");
    }

    @Override
    public void updateExistedUser(Person personToUpdate, AFSecurityContext securityContext)
            throws BusinessException {
        if (personToUpdate.getLogin().equals(securityContext.getLoggedUserName())
                || securityContext.isUserInRole(UserRoles.ADMIN)) {
            Person existedPerson = findUser(personToUpdate.getLogin());
            if(existedPerson.getId() != personToUpdate.getId()){
                throw new BusinessException(Status.BAD_REQUEST);
            }
            for(UserRoles role : existedPerson.getUserRole()){
                personToUpdate.addRole(role);
            }
            Country c = countryManager.findByName(personToUpdate.getMyAddress().getCountry());
            if(c == null){
                throw new BusinessException(Status.BAD_REQUEST);
            }
            if(c.getId() != existedPerson.getCountry().getId()){
                personToUpdate.setCountry(c);
            }
            createOrupdate(personToUpdate);
            addressManager.createOrupdate(personToUpdate.getMyAddress());
        } else {
            throw new BusinessException(Status.FORBIDDEN);
        }
    }

}
