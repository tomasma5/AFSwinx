package com.tomscz.afserver.manager;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.Person;

@Stateless(name=PersonManagerImpl.name)
public class PersonManagerImpl extends BaseManager<Person> implements Serializable, PersonManager<Person>{

    private static final long serialVersionUID = 1L;

    public static final String name="PersonManager";

    @Override
    public Person findById(int id) throws BusinessException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> personQuery = cb.createQuery(Person.class);
        Root<Person> rootPersonQuery = personQuery.from(Person.class);
        Predicate idPredicate = cb.equal(rootPersonQuery.get("id"), id);
        personQuery.where(idPredicate);
        TypedQuery<Person> typedQuery = em.createQuery(personQuery);
        List<Person> resultList = typedQuery.getResultList();
        if(resultList.isEmpty()){
            return null;
        }
        return resultList.get(0);
    }

    @Override
    public Person findUser(String login) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> personQuery = cb.createQuery(Person.class);
        Root<Person> rootPersonQuery = personQuery.from(Person.class);
        Predicate loginPredicate = cb.equal(rootPersonQuery.get("login"), login);
        personQuery.where(loginPredicate);
        TypedQuery<Person> typedQuery = em.createQuery(personQuery);
        List<Person> resultList = typedQuery.getResultList();
        if(resultList.isEmpty()){
            return null;
        }
        return resultList.get(0);
    }

    @Override
    public Person findUser(String login, String password) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> personQuery = cb.createQuery(Person.class);
        Root<Person> rootPersonQuery = personQuery.from(Person.class);
        Predicate loginPredicate = cb.equal(rootPersonQuery.get("login"), login);
        Predicate passwordPredicate = cb.equal(rootPersonQuery.get("password"), password);
        personQuery.where(cb.and(loginPredicate,passwordPredicate));
        TypedQuery<Person> typedQuery = em.createQuery(personQuery);
        List<Person> resultList = typedQuery.getResultList();
        if(resultList.isEmpty()){
            return null;
        }
        return resultList.get(0);
    }

}
