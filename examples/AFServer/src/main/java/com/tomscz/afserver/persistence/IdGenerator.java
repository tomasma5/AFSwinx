package com.tomscz.afserver.persistence;

public class IdGenerator {

    private static int personId=0;
    private static int addressId=0;
    private static int countryId=0;
    private static int absenceInstanceId=0;
    private static int absenceTypeId=0;
    private static int absenceTypeEmployeeId=0;
    private static int userRoleId=0;
    
    public static synchronized int getNextPersonId(){
        return personId++;
    }
    
    public static synchronized int getNextAddressId(){
        return addressId++;
    }
    
    
    public static synchronized int getNextCountryId(){
        return countryId++;
    }
    
    public static synchronized int getNextAbsenceInstanceId(){
        return absenceInstanceId++;
    }
    
    public static synchronized int getNextAbsenceTypeId(){
        return absenceTypeId++;
    }
    
    public static synchronized int getNextAbsenceTypeEmployeeId(){
        return absenceTypeEmployeeId++;
    }
    
    public static synchronized int getNextUserRoleId(){
        return userRoleId++;
    }
}
