package com.tomscz.afserver.persistence.entity;

import java.util.List;

import com.codingcrayons.aspectfaces.annotations.UILayout;
import com.codingcrayons.aspectfaces.annotations.UIWidgetType;
import com.codingcrayons.aspectfaces.annotations.UiLabel;
import com.codingcrayons.aspectfaces.annotations.UiOrder;
import com.codingcrayons.aspectfaces.annotations.UiRequired;
import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.layout.definitions.LabelPosition;
import com.tomscz.afrest.layout.definitions.LayouDefinitions;
import com.tomscz.afrest.layout.definitions.LayoutOrientation;

public class Person {

    private String firstName;
    private String lastName;
    private Long id;
    private int age;
    private Gender gender;
    private boolean confidentialAgreement;
    private Address myAdress;
    private List<Address> adresses;
    private AbsenceInstance absence;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.TWOCOLUMNSLAYOUT, layoutOrientation = LayoutOrientation.AXISX)
    @UiLabel("person.lastName")
    @UiOrder(value = 2)
    @UIWidgetType(widgetType = SupportedWidgets.TEXTAREA)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @UiRequired
    @UiLabel(value = "person.firstName")
    @UiOrder(value = 1)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @UiLabel(value = "person.age")
    @UiOrder(value = 3)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @UIWidgetType(widgetType = SupportedWidgets.CHECKBOX)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isConfidentialAgreement() {
        return confidentialAgreement;
    }

    public void setConfidentialAgreement(boolean confidentialAgreement) {
        this.confidentialAgreement = confidentialAgreement;
    }

    public List<Address> getAdresses() {
        return adresses;
    }

    public void setAdresses(List<Address> adresses) {
        this.adresses = adresses;
    }

    public AbsenceInstance getAbsence() {
        return absence;
    }

    public void setAbsence(AbsenceInstance absence) {
        this.absence = absence;
    }

    @UiOrder(value = 0)
    public Address getMyAdress() {
        return myAdress;
    }

    public void setMyAdress(Address myAdress) {
        this.myAdress = myAdress;
    }

}
