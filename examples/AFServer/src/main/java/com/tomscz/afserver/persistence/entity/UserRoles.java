package com.tomscz.afserver.persistence.entity;

/**
 * This enum holds all user role in application.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public enum UserRoles {

    ADMIN("admin"), USER("user");

    private final String name;

    private UserRoles(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return name;
    }

}
