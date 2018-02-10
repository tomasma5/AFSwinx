package com.tomscz.afserver.persistence.entity;

import com.codingcrayons.aspectfaces.annotations.*;
import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.layout.definitions.LabelPosition;
import com.tomscz.afrest.layout.definitions.LayouDefinitions;
import com.tomscz.afrest.layout.definitions.LayoutOrientation;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class BusinessTrip {

    @Id
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;
    @Temporal(value = TemporalType.DATE)
    private Date startDate;
    @Temporal(value = TemporalType.DATE)
    private Date endDate;
    @OneToOne
    private Address startPlace;
    @OneToOne
    private Address endPlace;
    @Enumerated(EnumType.STRING)
    private BusinessTripState status;
    private String description;
    private String vehicle;
    @OneToMany(mappedBy = "businessTrip")
    private List<BusinessTripPart> tripParts;

    private double totalDistance;

    public BusinessTrip() {
    }

    public BusinessTrip(int id, Person person, Date startDate, Date endDate, Address startPlace, Address endPlace,
                        BusinessTripState status, String description, String vehicle,
                        List<BusinessTripPart> tripParts, double totalDistance) {
        this.id = id;
        this.person = person;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.status = status;
        this.description = description;
        this.vehicle = vehicle;
        this.tripParts = tripParts;
        this.totalDistance = totalDistance;
    }

    @UiType(value = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @UiRequired
    @UiOrder(value = 0)
    @UILessThan(value = "endDate")
    @UiLabel(value = "businessTrip.startDate")
    @UIWidgetType(widgetType = SupportedWidgets.CALENDAR)
    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation = LayoutOrientation.AXISY)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @UiRequired
    @UiOrder(value = 1)
    @UiLabel(value = "businessTrip.endDate")
    @UIWidgetType(widgetType = SupportedWidgets.CALENDAR)
    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation = LayoutOrientation.AXISY)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @UiRequired
    @UiOrder(value = 2)
    @UiLabel(value = "businessTrip.startPlace")
    public Address getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(Address startPlace) {
        this.startPlace = startPlace;
    }

    @UiRequired
    @UiOrder(value = 3)
    @UiLabel(value = "businessTrip.endPlace")
    public Address getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(Address endPlace) {
        this.endPlace = endPlace;
    }

    @UiRequired
    @UiLabel(value = "businessTrip.state")
    @UiOrder(value = 6)
    @UIWidgetType(widgetType = SupportedWidgets.DROPDOWNMENU)
    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation = LayoutOrientation.AXISY)
    public BusinessTripState getStatus() {
        return status;
    }

    public void setStatus(BusinessTripState status) {
        this.status = status;
    }


    @UiLabel(value = "businessTrip.description")
    @UiOrder(value = 5)
    @UIWidgetType(widgetType = SupportedWidgets.TEXTAREA)
    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation = LayoutOrientation.AXISY)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @UiOrder(value = 4)
    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    @UiIgnore
    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    @UiIgnore
    public List<BusinessTripPart> getTripParts() {
        return tripParts;
    }

    public void setTripParts(List<BusinessTripPart> tripParts) {
        this.tripParts = tripParts;
    }
}
