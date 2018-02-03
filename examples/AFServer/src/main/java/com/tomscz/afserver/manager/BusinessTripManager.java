package com.tomscz.afserver.manager;

import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.AbsenceInstance;
import com.tomscz.afserver.persistence.entity.BusinessTrip;
import com.tomscz.afserver.ws.security.AFSecurityContext;

import java.util.List;

public interface BusinessTripManager<T> extends Manager<T> {

    public List<BusinessTrip> getAllBusinessTrips();

    public List<BusinessTrip> getBusinessTripsForPerson(String username, AFSecurityContext securityContext) throws BusinessException;

    public void createOrUpdate(BusinessTrip businessTrip, String username, AFSecurityContext securityContext) throws BusinessException;

}
