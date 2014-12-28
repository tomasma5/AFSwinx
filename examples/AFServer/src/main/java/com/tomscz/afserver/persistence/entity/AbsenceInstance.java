package com.tomscz.afserver.persistence.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class AbsenceInstance {

    @Id
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    private AbsenceType absenceType;
    @Temporal(value = TemporalType.DATE)
    private Date startDate;
    @Temporal(value = TemporalType.DATE)
    private Date endDate;
    @Enumerated(EnumType.STRING)
    private AbsenceInstanceState status;
    private int duration;
    @ManyToOne(fetch = FetchType.LAZY)
    private Person affectedPerson;

    public AbsenceType getAbsenceType() {
        return absenceType;
    }

    public void setAbsenceType(AbsenceType absenceType) {
        this.absenceType = absenceType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public AbsenceInstanceState getStatus() {
        return status;
    }

    public void setStatus(AbsenceInstanceState status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getAffectedPerson() {
        return affectedPerson;
    }

    public void setAffectedPerson(Person affectedPerson) {
        this.affectedPerson = affectedPerson;
    }

}
