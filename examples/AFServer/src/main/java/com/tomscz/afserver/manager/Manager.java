package com.tomscz.afserver.manager;

import com.tomscz.afserver.manager.exceptions.BusinessException;

public interface Manager<T> {

    public void createOrupdate(T T) throws BusinessException;
    
    public void delete(T T) throws BusinessException;
    
    
}
