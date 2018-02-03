package com.tomscz.afserver.persistence.entity;

import com.codingcrayons.aspectfaces.annotations.*;
import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.layout.definitions.LabelPosition;
import com.tomscz.afrest.layout.definitions.LayouDefinitions;
import com.tomscz.afrest.layout.definitions.LayoutOrientation;

import javax.persistence.*;

@Entity
public class Vehicle {

    @Id
    private int id;
    private String name;
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
    private double fuelConsumption;
    private String note;
    private boolean available;

    public Vehicle() {
    }

    public Vehicle(int id, String name, VehicleType vehicleType, double fuelConsumption, String note, boolean active) {
        this.id = id;
        this.name = name;
        this.vehicleType = vehicleType;
        this.fuelConsumption = fuelConsumption;
        this.note = note;
        this.available = active;
    }

    @UiType(value="id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @UiRequired
    @UiOrder(value = 0)
    @UiLabel(value = "vehicle.name")
    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation = LayoutOrientation.AXISY)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @UiRequired
    @UiOrder(value = 1)
    @UiLabel(value = "vehicle.type")
    @UIWidgetType(widgetType = SupportedWidgets.DROPDOWNMENU)
    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation = LayoutOrientation.AXISY)
    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    @UiRequired
    @UiOrder(value = 2)
    @UiLabel(value = "vehicle.fuelConsumption")
    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation = LayoutOrientation.AXISY)
    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    @UiRequired
    @UiOrder(value = 3)
    @UiLabel(value = "vehicle.fuelConsumption")
    @UIWidgetType(widgetType = SupportedWidgets.TEXTAREA)
    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation = LayoutOrientation.AXISY)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @UiRequired
    @UiLabel(value="vehicle.isAvailable")
    @UiOrder(value=4)
    @UILayout(labelPossition=LabelPosition.BEFORE,layout=LayouDefinitions.ONECOLUMNLAYOUT,layoutOrientation=LayoutOrientation.AXISY)
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean active) {
        this.available = active;
    }

    @Override
    public String toString() {
        return "["+vehicleType.toString() + "] " + name + ", " + fuelConsumption + "l/100km";
    }


}
