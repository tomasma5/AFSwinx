package com.tomscz.afserver.manager;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.tomscz.afserver.manager.exceptions.BusinessException;
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
    
    @EJB
    CountryManager<Country> countryManager;
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
            Country czech = null; 
            try {
                czech = countryManager.findById(1);
            } catch (BusinessException e) {
                //Do nothing user wont have set up country 
            }
            Date date = sdf.parse("21/12/2012");
            Person person =
                    new Person(IdGenerator.getNextPersonId(), "sa","john","John", "Doe", "jdoe@toms-cz.com",
                            date, true, 30, Gender.MALE, true);
            if(czech != null){
                person.setCountry(czech);
            }
            person.addRole(UserRoles.USER);
            Address personAddress =
                    new Address(IdGenerator.getNextAddressId(), "Somewhere", "Some city", 48601, "Czech republic");
            em.persist(personAddress);
            person.setMyAddress(personAddress);
            em.persist(person);
            date = sdf.parse("1/1/2013");
            Person secondPerson =
                    new Person(IdGenerator.getNextPersonId(),"sa2","jaina", "Jaina", "Proudmore",
                            "jproud@toms-cz.com", date, true, 30, Gender.FEMALE, true);
            if(czech != null){
                secondPerson.setCountry(czech);
            }
            secondPerson.addRole(UserRoles.USER);
            secondPerson.addRole(UserRoles.ADMIN);
            personAddress =
                    new Address(IdGenerator.getNextAddressId(), "Nowhere", "Strakonice", 38601, "Czech republic");
            em.persist(personAddress);
            secondPerson.setMyAddress(personAddress);
            em.persist(secondPerson);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
