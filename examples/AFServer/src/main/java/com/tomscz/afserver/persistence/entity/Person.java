package com.tomscz.afserver.persistence.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.codingcrayons.aspectfaces.annotations.UILayout;
import com.codingcrayons.aspectfaces.annotations.UIWidgetType;
import com.codingcrayons.aspectfaces.annotations.UiLabel;
import com.codingcrayons.aspectfaces.annotations.UiOrder;
import com.codingcrayons.aspectfaces.annotations.UiRequired;
import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.layout.definitions.LabelPosition;
import com.tomscz.afrest.layout.definitions.LayouDefinitions;
import com.tomscz.afrest.layout.definitions.LayoutOrientation;

@Entity
public class Person {

    @Id
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date hireDate;
    private boolean active;
    private int age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private boolean confidentialAgreement;
    @OneToOne
    private Address myAdress;

    public Person(){
        
    }
    
    public Person(int id,String firstName, String lastName, String email, Date hireDate, boolean active,
            int age, Gender gender, boolean confidentialAgreement) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hireDate = hireDate;
        this.active = active;
        this.age = age;
        this.gender = gender;
        this.confidentialAgreement = confidentialAgreement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.TWOCOLUMNSLAYOUT, layoutOrientation = LayoutOrientation.AXISY)
    @UiLabel("person.lastName")
    @UiOrder(value = 2)
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
    @Min(value=15)
    @Max(value=60)
    @UiRequired
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

    @UiOrder(value = 0)
    public Address getMyAdress() {
        return myAdress;
    }

    public void setMyAdress(Address myAdress) {
        this.myAdress = myAdress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

}
