package com.tomscz.afserver.persistence;

/**
 * // * This class is sequence generator. Because this server is Showcase and it used in-memory its
 * not necessary to have it in database.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class IdGenerator {

    private static int personId = 1;
    private static int addressId = 1;
    private static int countryId = 1;
    private static int absenceInstanceId = 1;
    private static int absenceTypeId = 1;
    private static int absenceTypeEmployeeId = 1;
    private static int userRoleId = 1;

    public static synchronized int getNextPersonId() {
        return personId++;
    }

    public static synchronized int getNextAddressId() {
        return addressId++;
    }


    public static synchronized int getNextCountryId() {
        return countryId++;
    }

    public static synchronized int getNextAbsenceInstanceId() {
        return absenceInstanceId++;
    }

    public static synchronized int getNextAbsenceTypeId() {
        return absenceTypeId++;
    }

    public static synchronized int getNextAbsenceTypeEmployeeId() {
        return absenceTypeEmployeeId++;
    }

    public static synchronized int getNextUserRoleId() {
        return userRoleId++;
    }
}
