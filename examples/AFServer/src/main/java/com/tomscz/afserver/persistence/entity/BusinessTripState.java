package com.tomscz.afserver.persistence.entity;

public enum BusinessTripState {

    REQUESTED("businessTripState.requested"),
    ACCEPTED("businessTripState.accepted"),
    CANCELLED("businessTripState.cancelled"),
    DENIED("businessTripState.denied"),
    INPROGRESS("businessTripState.inprogress"),
    FINISHED("businessTripState.finished");

    private final String name;

    BusinessTripState(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return (otherName != null) && name.equals(otherName);
    }

    public String toString() {
        return name;
    }

}
