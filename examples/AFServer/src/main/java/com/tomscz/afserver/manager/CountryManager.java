package com.tomscz.afserver.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.Country;


public class CountryManager implements Serializable, Manager<Country> {

    private static final long serialVersionUID = 1L;

    public List<Country> findAllCountry() {
        List<Country> resultList = new ArrayList<Country>();
        Country country = new Country("Germany","GER",true);
        resultList.add(country);
        country = new Country("Czech republic","CZ",true);
        resultList.add(country);
        country = new Country("Uganda","UG",false);
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

}
