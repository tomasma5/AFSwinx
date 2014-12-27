package com.tomscz.afserver.manager;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.tomscz.afserver.manager.exceptions.BusinessException;

public abstract class BaseManager<T> implements Manager<T>{

    @Inject
    EntityManager em;

    @Override
    public void createOrupdate(T entity) throws BusinessException {
        try{
        entity = em.merge(entity);
        em.persist(entity);
        em.flush();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(T entity) throws BusinessException {
        entity = em.merge(entity);
        em.remove(entity);   
        em.flush();
    }
    
}
