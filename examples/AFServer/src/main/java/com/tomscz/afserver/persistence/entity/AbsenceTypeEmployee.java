package com.tomscz.afserver.persistence.entity;

public class AbsenceTypeEmployee {

    private Person person;
    private AbsenceType absenceType;
    private double transefedFromPreviousYear;

    public double getTransefedFromPreviousYear() {
        return transefedFromPreviousYear;
    }

    public void setTransefedFromPreviousYear(double transefedFromPreviousYear) {
        this.transefedFromPreviousYear = transefedFromPreviousYear;
    }

    public AbsenceType getAbsenceType() {
        return absenceType;
    }

    public void setAbsenceType(AbsenceType absenceType) {
        this.absenceType = absenceType;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }


}
