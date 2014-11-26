package com.tomscz.afserver.persistence.entity;

public class AbsenceType {

    private int id;
    private double maxDaysPerYear;
    private String name;
    private Country country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public double getMaxDaysPerYear() {
        return maxDaysPerYear;
    }

    public void setMaxDaysPerYear(double maxDaysPerYear) {
        this.maxDaysPerYear = maxDaysPerYear;
    }
}
