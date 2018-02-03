package com.tomscz.afserver.manager;

import com.tomscz.afserver.persistence.entity.Vehicle;

import java.util.List;

public interface VehicleManager<T> extends Manager<T> {

    public List<Vehicle> findAllVehicles();

    public Vehicle findByName(String name);
}
