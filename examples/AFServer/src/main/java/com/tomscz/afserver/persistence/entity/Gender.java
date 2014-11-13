package com.tomscz.afserver.persistence.entity;

public enum Gender {

    MALE("gender.male"), FEMALE("gender.female");

    private final String name;

    private Gender(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return name;
    }
}
