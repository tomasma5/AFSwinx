package com.tomscz.afserver.manager;

import java.util.List;

import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.AbsenceType;

public interface AbsenceTypeManager <T> extends Manager<T>{

    public List<T> findAbsenceTypeInCountry(int countryId) throws BusinessException;
    
    public void updateAbsenceTypeBasedOnCountry(AbsenceType absenceType, int countryId) throws BusinessException;
    
}
