package com.tomscz.afserver.manager;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.tomscz.afserver.persistence.IdGenerator;
import com.tomscz.afserver.persistence.entity.Address;
import com.tomscz.afserver.persistence.entity.Country;
import com.tomscz.afserver.persistence.entity.Gender;
import com.tomscz.afserver.persistence.entity.Person;

@Singleton
@Startup
public class StartUpBean implements Serializable {

    @Inject
    EntityManager em;
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @PostConstruct
    public void initApp() {
        try {
            generateCountries();
            generateUsers();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void generateCountries() {
        em.persist(new Country(IdGenerator.getNextCountryId(), "Czech republic", "CR", true));
        em.persist(new Country(IdGenerator.getNextCountryId(), "Denmark", "DNK", true));
        em.persist(new Country(IdGenerator.getNextCountryId(), "Switzerland", "CHE", true));
        em.persist(new Country(IdGenerator.getNextCountryId(), "Slovakia", "SLO", true));
    }

    private void generateUsers() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = sdf.parse("21/12/2012");
            Person person =
                    new Person(IdGenerator.getNextPersonId(), "John", "Doe", "jdoe@toms-cz.com,",
                            date, true, 30, Gender.MALE, true);
            TypedQuery<Country> query =
                    em.createQuery("SELECT c FROM Country c WHERE c.shortCut = :shortCut",
                            Country.class);
            Country c = query.setParameter("shortCut", "CR").getSingleResult();
            Address personAddress =
                    new Address(IdGenerator.getNextAddressId(), "Somewhere", "Some city", 22222, c);
            em.persist(personAddress);
            person.setMyAdress(personAddress);
            em.persist(person);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
