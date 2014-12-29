package com.tomscz.afserver.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.codingcrayons.aspectfaces.annotations.UILayout;
import com.codingcrayons.aspectfaces.annotations.UiLabel;
import com.codingcrayons.aspectfaces.annotations.UiOrder;
import com.codingcrayons.aspectfaces.annotations.UiRequired;
import com.codingcrayons.aspectfaces.annotations.UiType;
import com.tomscz.afrest.layout.definitions.LabelPosition;
import com.tomscz.afrest.layout.definitions.LayouDefinitions;
import com.tomscz.afrest.layout.definitions.LayoutOrientation;

@Entity
public class AbsenceType {

    @Id
    private int id;
    private double maxDaysPerYear;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.PERSIST})
    private Country country;
    private boolean active;

    @UiOrder(value=1)
    @UiRequired
    @UiLabel(value="absenceType.name")
    @UILayout(labelPossition=LabelPosition.BEFORE, layout=LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation=LayoutOrientation.AXISY)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @UiType(value = "id")
    @UiRequired
    @UILayout(labelPossition=LabelPosition.BEFORE, layout=LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation=LayoutOrientation.AXISY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @UiOrder(value = 4)
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @UiOrder(value=2)
    @UiRequired
    @UiLabel(value="absenceType.maxDaysPerYear")
    @UILayout(labelPossition=LabelPosition.BEFORE, layout=LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation=LayoutOrientation.AXISY)
    public double getMaxDaysPerYear() {
        return maxDaysPerYear;
    }

    public void setMaxDaysPerYear(double maxDaysPerYear) {
        this.maxDaysPerYear = maxDaysPerYear;
    }

    @UiOrder(value=3)
    @UiRequired
    @UiLabel(value="absenceType.active")
    @UILayout(labelPossition=LabelPosition.BEFORE, layout=LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation=LayoutOrientation.AXISY)
    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
