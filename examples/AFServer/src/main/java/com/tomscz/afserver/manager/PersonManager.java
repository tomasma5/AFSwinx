package com.tomscz.afserver.manager;

import java.util.List;

import com.tomscz.afserver.manager.exceptions.BusinessException;

public interface PersonManager<T> extends Manager<T> {

    public T findUser(String login) throws BusinessException;

    public List<T> findUsersByCountry(int countryId) throws BusinessException;
    
    public T findUserByCountry(String countryName) throws BusinessException;
    
    public T findUser(String login, String password) throws BusinessException;
    
    public List<T> findAllUsers();

}
