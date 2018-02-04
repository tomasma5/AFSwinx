package com.tomscz.afserver.persistence.entity;

import com.codingcrayons.aspectfaces.annotations.*;
import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.layout.definitions.LabelPosition;
import com.tomscz.afrest.layout.definitions.LayouDefinitions;
import com.tomscz.afrest.layout.definitions.LayoutOrientation;

import javax.persistence.*;
import java.util.Date;

@Entity
public class BusinessTripPart {

    @Id
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private BusinessTrip businessTrip;
    @Temporal(value = TemporalType.DATE)
    private Date startDate;
    @Temporal(value = TemporalType.DATE)
    private Date endDate;
    @OneToOne
    private Address startPlace;
    @OneToOne
    private Address endPlace;
    private double distance;

    public BusinessTripPart() {
    }

    public BusinessTripPart(int id, BusinessTrip businessTrip, Date startDate, Date endDate, Address startPlace,
                            Address endPlace, double distance) {
        this.id = id;
        this.businessTrip = businessTrip;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.distance = distance;
    }

    @UiIgnore
    public BusinessTrip getBusinessTrip() {
        return businessTrip;
    }

    public void setBusinessTrip(BusinessTrip businessTrip) {
        this.businessTrip = businessTrip;
    }

    @UiType(value = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @UiRequired
    @UiOrder(value = 0)
    @UILessThan(value = "endDate")
    @UiLabel(value = "businessTripPart.startDate")
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
    @UiLabel(value = "businessTripPart.endDate")
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
    @UiLabel(value = "businessTripPart.startPlace")
    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation = LayoutOrientation.AXISY)
    public Address getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(Address startPlace) {
        this.startPlace = startPlace;
    }

    @UiRequired
    @UiOrder(value = 3)
    @UiLabel(value = "businessTripPart.endPlace")
    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation = LayoutOrientation.AXISY)
    public Address getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(Address endPlace) {
        this.endPlace = endPlace;
    }

    @UiRequired
    @UiOrder(value = 4)
    @UiLabel(value = "businessTripPart.distance")
    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation = LayoutOrientation.AXISY)
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
