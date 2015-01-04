package com.tomscz.afserver.persistence.entity;

public enum AbsenceInstanceState {

    REQUESTED("absenceInstances.requested"), ACCEPTED("absenceInstances.accepted"), CANCELLED(
            "absenceInstances.cancelled"),DENIED("absenceInstances.denied");

    private final String name;

    private AbsenceInstanceState(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return name;
    }
    
}
