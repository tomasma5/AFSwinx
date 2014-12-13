package com.tomscz.afserver.ws.resources;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.tomscz.afserver.manager.CountryManager;
import com.tomscz.afserver.manager.CountryManagerImp;
import com.tomscz.afserver.manager.PersonManager;
import com.tomscz.afserver.manager.PersonManagerImpl;
import com.tomscz.afserver.persistence.entity.Country;
import com.tomscz.afserver.persistence.entity.Person;

public abstract class BaseResource {

    private String getJNDIName(String lookupClassName){
        return "java:global/AFServer/"+lookupClassName;
    }
    
    @SuppressWarnings("unchecked")
    protected CountryManager<Country> getCountryManager() throws NamingException {
        Context ctx = new InitialContext();
        CountryManager<Country> cmm =
                (CountryManager<Country>) ctx.lookup(getJNDIName(CountryManagerImp.name));
        return cmm;
    }
    
    @SuppressWarnings("unchecked")
    protected PersonManager<Person> getPersonManager() throws NamingException {
        Context ctx = new InitialContext();
        PersonManager<Person> personManager =
                (PersonManager<Person>) ctx.lookup(getJNDIName(PersonManagerImpl.name));
        return personManager;
    }
}
