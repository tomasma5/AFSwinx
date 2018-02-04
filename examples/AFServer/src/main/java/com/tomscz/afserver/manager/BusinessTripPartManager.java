package com.tomscz.afserver.manager;

import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.BusinessTripPart;
import com.tomscz.afserver.ws.security.AFSecurityContext;

import java.util.List;

public interface BusinessTripPartManager<T> extends Manager<T> {

    public List<BusinessTripPart> getAllBusinessTripParts(int businessTripId) throws BusinessException;

    public List<BusinessTripPart> getBusinessTripsPartsForPerson(String username, int businessTripId, AFSecurityContext securityContext) throws BusinessException;

    public void createOrUpdate(BusinessTripPart businessTripPart, int businessTripId, String username, AFSecurityContext securityContext) throws BusinessException;

    public void remove(int businessTripPartId, String username, AFSecurityContext securityContext) throws BusinessException;

}
