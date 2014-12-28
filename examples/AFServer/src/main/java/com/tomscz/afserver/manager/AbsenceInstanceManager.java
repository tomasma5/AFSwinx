package com.tomscz.afserver.manager;

import java.util.List;

import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.ws.security.AFSecurityContext;

public interface AbsenceInstanceManager <T> extends Manager<T> {

    public List<T> findInstanceByUser(String userName, AFSecurityContext securityContext) throws BusinessException;
    
}
