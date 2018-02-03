package com.tomscz.afserver.manager;

import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.Vehicle;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

@Stateless(name = VehicleManagerImpl.name)
public class VehicleManagerImpl extends BaseManager<Vehicle> implements Serializable, VehicleManager<Vehicle> {

    public static final String name = "VehicleManager";

    private static final long serialVersionUID = 1L;

    @Override
    public List<Vehicle> findAllVehicles() {
        return em.createQuery("select v from Vehicle v", Vehicle.class).getResultList();
    }

    @Override
    public Vehicle findByName(String name) {
        TypedQuery<Vehicle> query = em.createQuery("SELECT v FROM Vehicle v WHERE v.name = :name", Vehicle.class);
        List<Vehicle> foundVehicles = query.setParameter("name", name).getResultList();
        if(!foundVehicles.isEmpty()){
            return foundVehicles.get(0);
        }
        return null;
    }

    @Override
    public Vehicle findById(int id) throws BusinessException {
        TypedQuery<Vehicle> query = em.createQuery("SELECT v FROM Vehicle v WHERE v.id = :id", Vehicle.class);
        List<Vehicle> foundVehicles = query.setParameter("id", id).getResultList();
        if(!foundVehicles.isEmpty()){
            return foundVehicles.get(0);
        }
        return null;
    }
}
