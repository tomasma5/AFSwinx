package com.tomscz.afserver.manager;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.IdGenerator;
import com.tomscz.afserver.persistence.entity.Country;
import com.tomscz.afserver.persistence.entity.Person;

@Stateless(name = CountryManagerImp.name)
public class CountryManagerImp extends BaseManager<Country>
        implements
            Serializable,
            CountryManager<Country> {

    @EJB
    private PersonManager<Person> personManager;

    public static final String name = "CountryManager";

    private static final long serialVersionUID = 1L;

    @Override
    public List<Country> findAllCountry() {
        List<Country> resultList =
                em.createQuery("select c from Country c", Country.class).getResultList();
        return resultList;
    }

    @Override
    public Country findById(int id) throws BusinessException {
        TypedQuery<Country> query =
                em.createQuery("SELECT c FROM Country c WHERE c.id = :id", Country.class);
        List<Country> countries = query.setParameter("id", id).getResultList();
        if (!countries.isEmpty()) {
            return countries.get(0);
        }
        return null;
    }

    @Override
    public Country findByName(String name) {
        TypedQuery<Country> query =
                em.createQuery("SELECT c FROM Country c WHERE c.name = :name", Country.class);
        List<Country> countries = query.setParameter("name", name).getResultList();
        if (!countries.isEmpty()) {
            return countries.get(0);
        }
        return null;
    }

    @Override
    public void createOrupdate(Country entity) throws BusinessException {
        // If entity has no id then it is new entity, so generate new id.
        if (entity.getId() == 0) {
            entity.setId(IdGenerator.getNextCountryId());
        } else {
            // Otherwise it is existed entity, so merge it.
            List<Person> personsInCountry = personManager.findUsersByCountry(entity.getId());
            // For each person in this country update it
            for (Person p : personsInCountry) {
                if (p.getMyAddress() != null && !p.getMyAddress().getCountry().isEmpty()) {
                    p.getMyAddress().setCountry(entity.getName());
                    personManager.createOrupdate(p);
                }
            }
        }
        super.createOrupdate(entity);
    }

}
