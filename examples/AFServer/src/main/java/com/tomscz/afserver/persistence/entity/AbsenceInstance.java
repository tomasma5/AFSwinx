package com.tomscz.afserver.persistence.entity;

public class AbsenceInstance {

    private AbsenceType absenceType;
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

}
