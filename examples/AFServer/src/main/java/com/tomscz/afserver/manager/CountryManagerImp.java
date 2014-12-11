package com.tomscz.afserver.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.Country;
import com.tomscz.afserver.persistence.entity.Person;

@Stateless(name=CountryManagerImp.name)
public class CountryManagerImp implements Serializable, CountryManager<Country> {

    @Inject
    EntityManager em;
    
    public static final String name="CountryManager";
    
    private static final long serialVersionUID = 1L;
    
    @Override
    public List<Country> findAllCountry() {
        List<Country> c = em.createQuery("select c from Country c", Country.class).getResultList();
        List<Person> cp = em.createQuery("select c from Person c", Person.class).getResultList();
        List<Country> resultList = new ArrayList<Country>();
        Country country = new Country("Germany","GER",true);
//        country.setId(23L);
//        em.persist(country);
//        em.flush();
        c = em.createQuery("select c from Country c", Country.class).getResultList();
        resultList.add(country);
        country = new Country("Czech republic","CZ",false);
        resultList.add(country);
        country = new Country("Uganda","UG",true);
        resultList.add(country);
        return resultList;
    }


    @Override
    public void createOrupdate(Country T) throws BusinessException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(Country T) throws BusinessException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Country findById(int id) throws BusinessException {
        // TODO Auto-generated method stub
        return null;
    }

}
