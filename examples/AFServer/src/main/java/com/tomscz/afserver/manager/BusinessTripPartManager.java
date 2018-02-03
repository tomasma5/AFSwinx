package com.tomscz.afserver.manager;

import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.BusinessTrip;
import com.tomscz.afserver.persistence.entity.BusinessTripPart;

import java.util.List;

public interface BusinessTripPartManager<T> extends Manager<T> {

    public List<BusinessTripPart> getAllBusinessTripParts(int businessTripId) throws BusinessException;
}
