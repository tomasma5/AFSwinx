package com.tomscz.afserver.manager;

import com.tomscz.afserver.manager.exceptions.BusinessException;

/**
 * This is Manager which provide operation as create, update, delete and findById database entity.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 * @param <T>
 */
public interface Manager<T> {

    public void createOrupdate(T entity) throws BusinessException;

    public void delete(T entity) throws BusinessException;

    public T findById(int id) throws BusinessException;

}
