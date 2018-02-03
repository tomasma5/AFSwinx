package com.tomscz.afserver.ws.resources;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.tomscz.afserver.manager.*;
import com.tomscz.afserver.persistence.entity.*;
import com.tomscz.afserver.utils.Utils;

/**
 * This class provide all managers to its children. It also has abstract method called
 * getResourceUrl. All children should implement this method.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public abstract class BaseResource {

    @SuppressWarnings("unchecked")
    protected CountryManager<Country> getCountryManager() throws NamingException {
        Context ctx = new InitialContext();
        CountryManager<Country> cmm =
                (CountryManager<Country>) ctx.lookup(Utils.getJNDIName(CountryManagerImp.name));
        return cmm;
    }

    @SuppressWarnings("unchecked")
    protected PersonManager<Person> getPersonManager() throws NamingException {
        Context ctx = new InitialContext();
        PersonManager<Person> personManager =
                (PersonManager<Person>) ctx.lookup(Utils.getJNDIName(PersonManagerImpl.name));
        return personManager;
    }

    @SuppressWarnings("unchecked")
    protected AbsenceTypeManager<AbsenceType> getAbsenceTypeManager() throws NamingException {
        Context ctx = new InitialContext();
        AbsenceTypeManager<AbsenceType> absenceTypeManager =
                (AbsenceTypeManager<AbsenceType>) ctx.lookup(Utils
                        .getJNDIName(AbsenceTypeManagerImpl.NAME));
        return absenceTypeManager;
    }

    @SuppressWarnings("unchecked")
    protected AbsenceInstanceManager<AbsenceInstance> getAbsenceInstantManager()
            throws NamingException {
        Context ctx = new InitialContext();
        AbsenceInstanceManager<AbsenceInstance> absenceTypeManager =
                (AbsenceInstanceManager<AbsenceInstance>) ctx.lookup(Utils
                        .getJNDIName(AbsenceInstanceManagerImpl.NAME));
        return absenceTypeManager;
    }

    @SuppressWarnings("unchecked")
    protected VehicleManager<Vehicle> getVehicleManager() throws NamingException {
        Context ctx = new InitialContext();
        VehicleManager<Vehicle> vehicleManager =
                (VehicleManager<Vehicle>) ctx.lookup(Utils.getJNDIName(VehicleManagerImpl.name));
        return vehicleManager;
    }

    public abstract String getResourceUrl();

}
