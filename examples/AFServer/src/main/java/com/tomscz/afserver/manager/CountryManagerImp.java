package com.tomscz.afserver.manager;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.Country;

@Stateless(name=CountryManagerImp.name)
public class CountryManagerImp implements Serializable, CountryManager<Country> {

    @Inject
    EntityManager em;
    
    public static final String name="CountryManager";
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public List<Country> findAllCountry() {
        List<Country> resultList = em.createQuery("select c from Country c", Country.class).getResultList();
        return resultList;
    }


    @Override
    public void createOrupdate(Country entity) throws BusinessException {
       em.merge(entity);
    }

    @Override
    public void delete(Country entity) throws BusinessException {
        em.merge(entity);
        em.remove(entity);
    }

    @Override
    public Country findById(int id) throws BusinessException {
        TypedQuery<Country> query =
                em.createQuery("SELECT c FROM Country c WHERE c.id = :id",
                        Country.class);
        Country country = query.setParameter("id", id).getSingleResult();
        return country;
    }

}
