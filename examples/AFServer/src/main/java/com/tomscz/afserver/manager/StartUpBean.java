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
import javax.persistence.Id;

import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.IdGenerator;
import com.tomscz.afserver.persistence.entity.*;

/**
 * This is start up bean which is used to initialize database.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
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
            generateVehicles();
        } catch (Exception e) {
            // Just print stack trace, this mean, that there are no data initialized
            e.printStackTrace();
        }
    }

    private void generateCountries() {
        em.persist(new Country(IdGenerator.getNextCountryId(), "Czech republic", "CZE", true));
        em.persist(new Country(IdGenerator.getNextCountryId(), "Denmark", "DEN", true));
        em.persist(new Country(IdGenerator.getNextCountryId(), "Switzerland", "SWISS", true));
        em.persist(new Country(IdGenerator.getNextCountryId(), "Slovakia", "SVK", false));
    }

    private void generateUsers() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Country czech = null;
            try {
                czech = countryManager.findById(1);
            } catch (BusinessException e) {
                // Do nothing user wont have set up country
            }
            Date date = sdf.parse("21/12/2012");
            Person person =
                    new Person(IdGenerator.getNextPersonId(), "sa", "john", "John", "Doe",
                            "jdoe@toms-cz.com", date, true, 30, Gender.MALE, true);
            if (czech != null) {
                person.setCountry(czech);
            }
            person.addRole(UserRoles.USER);
            Address personAddress =
                    new Address(IdGenerator.getNextAddressId(), "Somewhere", "Some city", 48601,
                            "Czech republic");
            em.persist(personAddress);
            person.setMyAddress(personAddress);
            em.persist(person);
            date = sdf.parse("1/1/2013");
            Person secondPerson =
                    new Person(IdGenerator.getNextPersonId(), "sa2", "jaina", "Jaina", "Proudmore",
                            "jproud@toms-cz.com", date, true, 30, Gender.FEMALE, true);
            if (czech != null) {
                secondPerson.setCountry(czech);
            }
            secondPerson.addRole(UserRoles.USER);
            secondPerson.addRole(UserRoles.ADMIN);
            personAddress =
                    new Address(IdGenerator.getNextAddressId(), "Nowhere", "Strakonice", 38601,
                            "Czech republic");
            em.persist(personAddress);
            secondPerson.setMyAddress(personAddress);
            em.persist(secondPerson);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void generateVehicles(){
        em.persist(new Vehicle(IdGenerator.getNextVehicleId(), "Renault Clio", VehicleType.CAR, FuelType.PETROL, 6.5, 20000, "Romanovo clio", true));
        em.persist(new Vehicle(IdGenerator.getNextVehicleId(), "Škoda Karoq", VehicleType.CAR, FuelType.PETROL, 6.5,  10500.3, "Podstatně dražší než yeti, ale univerzálnější", true));
        em.persist(new Vehicle(IdGenerator.getNextVehicleId(), "Škoda Octavia 1.9 TDI 2004", VehicleType.CAR, FuelType.OIL_FUEL, 5.5, 125012.9, "", true));
        em.persist(new Vehicle(IdGenerator.getNextVehicleId(), "Setra S 515 HD", VehicleType.BUS, FuelType.OIL_FUEL, 18.3, 145798, "Spotřeba uvedena pro celý autobus = nerozpočítána na cestující", true));
        em.persist(new Vehicle(IdGenerator.getNextVehicleId(), "Airbus 318", VehicleType.PLANE, FuelType.PLANE_FUEL, 5.5, 5000000, "Spotřeba je brána na 1 osobu na 100km", true));
        em.persist(new Vehicle(IdGenerator.getNextVehicleId(), "S 1000 XR", VehicleType.MOTOCYCLE, FuelType.PETROL, 6.5, 12300, "Mimořádná výbava: Alarm proti krádeži", true));
        em.persist(new Vehicle(IdGenerator.getNextVehicleId(), "Inactive car", VehicleType.CAR, FuelType.LPG, 12.5, 74699,"Neaktivní auto pro ilustraci", false));
    }

}
