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

import com.codingcrayons.aspectfaces.annotations.UILayout;
import com.codingcrayons.aspectfaces.annotations.UIWidgetType;
import com.codingcrayons.aspectfaces.annotations.UiLabel;
import com.codingcrayons.aspectfaces.annotations.UiOrder;
import com.codingcrayons.aspectfaces.annotations.UiRequired;
import com.codingcrayons.aspectfaces.annotations.UiType;
import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.layout.definitions.LabelPosition;
import com.tomscz.afrest.layout.definitions.LayouDefinitions;
import com.tomscz.afrest.layout.definitions.LayoutOrientation;

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
    @UILayout(labelPossition=LabelPosition.BEFORE, layout=LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation=LayoutOrientation.AXISY)
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @UiRequired
    @UiOrder(value=0)
    @UiLabel(value="absenceInstance.startDate")
    @UILayout(labelPossition=LabelPosition.BEFORE, layout=LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation=LayoutOrientation.AXISY)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @UiRequired
    @UiOrder(value=1)
    @UiLabel(value="absenceInstance.endDate")
    @UILayout(labelPossition=LabelPosition.BEFORE, layout=LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation=LayoutOrientation.AXISY)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    @UiLabel(value="absenceInstance.state")
    @UiRequired
    @UIWidgetType(widgetType = SupportedWidgets.DROPDOWNMENU)
    @UILayout(labelPossition=LabelPosition.BEFORE, layout=LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation=LayoutOrientation.AXISY)
    public AbsenceInstanceState getStatus() {
        return status;
    }

    public void setStatus(AbsenceInstanceState status) {
        this.status = status;
    }

    @UiType(value = "id")
    @UILayout(labelPossition=LabelPosition.BEFORE, layout=LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation=LayoutOrientation.AXISY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @UiOrder(value = -1)
    public Person getAffectedPerson() {
        return affectedPerson;
    }

    public void setAffectedPerson(Person affectedPerson) {
        this.affectedPerson = affectedPerson;
    }

}
