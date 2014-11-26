package com.tomscz.afserver.persistence.entity;

import java.util.Date;

public class AbsenceInstance {

    private AbsenceType absenceType;
    private Date startDate;
    private Date endDate;
    private AbsenceInstanceState status;
    private int duration;

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

}
