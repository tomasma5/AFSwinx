package com.tomscz.afserver.manager;

import java.util.List;

import javax.ejb.Local;

import com.tomscz.afserver.persistence.entity.Country;

@Local
public interface CountryManager<T> extends Manager<T> {

    public List<Country> findAllCountry();
    
    public Country findByName(String name);
}
