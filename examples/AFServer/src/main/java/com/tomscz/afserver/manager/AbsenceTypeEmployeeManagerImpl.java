package com.tomscz.afserver.manager;

import java.io.Serializable;

import javax.ejb.Stateless;

import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.AbsenceTypeEmployee;

@Stateless(name = AbsenceTypeEmployeeManagerImpl.NAME)
public class AbsenceTypeEmployeeManagerImpl extends BaseManager<AbsenceTypeEmployee>
        implements
            AbsenceTypeEmployeeManager<AbsenceTypeEmployee>,
            Serializable {

    private static final long serialVersionUID = 1L;

    public static final String NAME = "AbsenceTypeEmployeeManager";

    @Override
    public AbsenceTypeEmployee findById(int id) throws BusinessException {
        throw new UnsupportedOperationException("Not supported yet");
    }


}
