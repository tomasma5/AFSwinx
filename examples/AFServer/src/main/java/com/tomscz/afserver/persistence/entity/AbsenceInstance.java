package com.tomscz.afserver.persistence.entity;

import java.util.Date;

public class AbsenceInstance {

    private AbsenceType absenceType;
    private Date startDate;
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

}
