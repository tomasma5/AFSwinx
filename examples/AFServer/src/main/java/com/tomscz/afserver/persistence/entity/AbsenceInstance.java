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

import com.codingcrayons.aspectfaces.annotations.UiLabel;
import com.codingcrayons.aspectfaces.annotations.UiOrder;
import com.codingcrayons.aspectfaces.annotations.UiRequired;
import com.codingcrayons.aspectfaces.annotations.UiType;

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

    @UiOrder(value=2)
    public AbsenceType getAbsenceType() {
        return absenceType;
    }

    public void setAbsenceType(AbsenceType absenceType) {
        this.absenceType = absenceType;
    }

    @UiLabel(value="absenceInstance.duration")
    @UiType(value = "readOnly")
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @UiRequired
    @UiOrder(value=0)
    @UiLabel(value="absenceInstance.startDate")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @UiRequired
    @UiOrder(value=1)
    @UiLabel(value="absenceInstance.endDate")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    @UiLabel(value="absenceInstance.state")
    public AbsenceInstanceState getStatus() {
        return status;
    }

    public void setStatus(AbsenceInstanceState status) {
        this.status = status;
    }

    @UiType(value = "id")
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
