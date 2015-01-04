package com.tomscz.afserver.manager;

import java.util.List;

import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.Address;
import com.tomscz.afserver.persistence.entity.Country;

public interface AddressManager<T> extends Manager<T>{

    public List<Address> findAddressInCountry(Country c) throws BusinessException;
    
}
