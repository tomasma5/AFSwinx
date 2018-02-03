package com.tomscz.afserver.persistence.entity;

public enum  VehicleType {

    CAR("vehicleType.car"),
    BUS("vehicleType.bus"),
    PLANE("vehicleType.plane");

    private final String name;

    VehicleType(String name){
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return (otherName != null) && name.equals(otherName);
    }

    public String toString() {
        return name;
    }

}
