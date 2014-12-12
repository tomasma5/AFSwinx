package com.tomscz.afserver.manager;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.tomscz.afserver.manager.exceptions.BusinessException;

public abstract class BaseManager<T> implements Manager<T>{

    @Inject
    EntityManager em;

    @Override
    public void createOrupdate(T entity) throws BusinessException {
        em.merge(entity);    
    }

    @Override
    public void delete(T entity) throws BusinessException {
        entity = em.merge(entity);
        em.remove(entity);   
        em.flush();
    }
    
}
