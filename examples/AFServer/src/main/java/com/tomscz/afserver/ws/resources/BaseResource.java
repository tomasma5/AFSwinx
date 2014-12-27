package com.tomscz.afserver.ws.resources;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.tomscz.afserver.manager.AbsenceTypeManager;
import com.tomscz.afserver.manager.AbsenceTypeManagerImpl;
import com.tomscz.afserver.manager.CountryManager;
import com.tomscz.afserver.manager.CountryManagerImp;
import com.tomscz.afserver.manager.PersonManager;
import com.tomscz.afserver.manager.PersonManagerImpl;
import com.tomscz.afserver.persistence.entity.AbsenceType;
import com.tomscz.afserver.persistence.entity.Country;
import com.tomscz.afserver.persistence.entity.Person;
import com.tomscz.afserver.utils.Utils;

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
                (AbsenceTypeManager<AbsenceType>) ctx.lookup(Utils.getJNDIName(AbsenceTypeManagerImpl.NAME));
        return absenceTypeManager;
    }
}
