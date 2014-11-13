package com.tomscz.afrest.utils;

public enum Direction {
    NOWAY("noWay"), BADWAY("badWay");

    private final String name;

    private Direction(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return name;
    }
    
}
