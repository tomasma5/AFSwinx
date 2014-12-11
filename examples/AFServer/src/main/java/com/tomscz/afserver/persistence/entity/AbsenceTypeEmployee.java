package com.tomscz.afserver.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AbsenceTypeEmployee {

    @Id
    @GeneratedValue
    private Long id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
