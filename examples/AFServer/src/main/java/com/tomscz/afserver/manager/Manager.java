package com.tomscz.afserver.manager;

import com.tomscz.afserver.manager.exceptions.BusinessException;

public interface Manager<T> {

    public void createOrupdate(T entity) throws BusinessException;
    
    public void delete(T entity) throws BusinessException;
    
    public T findById(int id) throws BusinessException;
    
    
}
