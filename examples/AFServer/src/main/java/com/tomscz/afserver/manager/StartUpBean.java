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

import com.tomscz.afserver.persistence.IdGenerator;
import com.tomscz.afserver.persistence.entity.Address;
import com.tomscz.afserver.persistence.entity.Country;
import com.tomscz.afserver.persistence.entity.Gender;
import com.tomscz.afserver.persistence.entity.Person;
import com.tomscz.afserver.persistence.entity.UserRoles;

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
            // Just print stack trace, this mean, that there are no data initialized
            e.printStackTrace();
        }
    }

    private void generateCountries() {
        em.persist(new Country(IdGenerator.getNextCountryId(), "Czech republic", "CR", true));
        em.persist(new Country(IdGenerator.getNextCountryId(), "Denmark", "DNK", true));
        em.persist(new Country(IdGenerator.getNextCountryId(), "Switzerland", "CHE", true));
        em.persist(new Country(IdGenerator.getNextCountryId(), "Slovakia", "SLO", false));
    }

    private void generateUsers() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = sdf.parse("21/12/2012");
            Person person =
                    new Person(IdGenerator.getNextPersonId(), "sa","john","John", "Doe", "jdoe@toms-cz.com,",
                            date, true, 30, Gender.MALE, true);
            person.addRole(UserRoles.USER);
            Address personAddress =
                    new Address(IdGenerator.getNextAddressId(), "Somewhere", "Some city", 22222, "Czech republic");
            em.persist(personAddress);
            person.setMyAdress(personAddress);
            em.persist(person);
            Person secondPerson =
                    new Person(IdGenerator.getNextPersonId(),"sa2","jaina", "Jaina", "Proudmore",
                            "jproud@toms-cz.com,", date, true, 30, Gender.FEMALE, true);
            secondPerson.addRole(UserRoles.USER);
            secondPerson.addRole(UserRoles.ADMIN);
            secondPerson.setMyAdress(personAddress);
            em.persist(secondPerson);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
