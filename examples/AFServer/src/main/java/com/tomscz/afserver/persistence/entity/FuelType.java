package com.tomscz.afserver.persistence.entity;

public enum FuelType {

    OIL_FUEL("fuelType.oilFuel"),
    PETROL("fuelType.petrol"),
    LPG("fuelType.lpg"),
    PLANE_FUEL("fuelType.planeFuel");

    private final String name;

    FuelType(String name){
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return (otherName != null) && name.equals(otherName);
    }

    public String toString() {
        return name;
    }

}
