package com.tomscz.afserver.persistence.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.codingcrayons.aspectfaces.annotations.UiOrder;
import com.codingcrayons.aspectfaces.annotations.UiRequired;
import com.codingcrayons.aspectfaces.annotations.UiType;

@Entity
public class AbsenceType {

    @Id
    private int id;
    private double maxDaysPerYear;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.PERSIST})
    private Country country;

    @UiOrder(value=1)
    @UiRequired
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @UiType(value = "id")
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

    @UiOrder(value=2)
    @UiRequired
    public double getMaxDaysPerYear() {
        return maxDaysPerYear;
    }

    public void setMaxDaysPerYear(double maxDaysPerYear) {
        this.maxDaysPerYear = maxDaysPerYear;
    }
}
