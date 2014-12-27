package com.tomscz.afserver.manager;

import java.util.List;

import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.Person;
import com.tomscz.afserver.ws.security.AFSecurityContext;

public interface PersonManager<T> extends Manager<T> {

    public T findUser(String login) throws BusinessException;

    public List<T> findUsersByCountry(int countryId) throws BusinessException;
    
    public T findUserByCountry(String countryName) throws BusinessException;
    
    public T findUser(String login, String password) throws BusinessException;
    
    public List<T> findAllUsers();
    
    public void updateExistedUser(Person personToUpdate, AFSecurityContext securityContext) throws BusinessException;

}
