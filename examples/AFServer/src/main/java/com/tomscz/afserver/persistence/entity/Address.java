package com.tomscz.afserver.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.codingcrayons.aspectfaces.annotations.UILayout;
import com.codingcrayons.aspectfaces.annotations.UiOrder;
import com.codingcrayons.aspectfaces.annotations.UiRequired;
import com.codingcrayons.aspectfaces.annotations.UiType;
import com.tomscz.afrest.layout.definitions.LabelPosition;
import com.tomscz.afrest.layout.definitions.LayouDefinitions;
import com.tomscz.afrest.layout.definitions.LayoutOrientation;

@Entity
public class Address {

    @Id
    private int id;
    private String street;
    private String city;
    private int postCode;
    private String country;

    public Address(){
        
    }
    
    public Address(int id, String street, String city, int postCode, String country) {
        super();
        this.id = id;
        this.street = street;
        this.city = city;
        this.postCode = postCode;
        this.country = country;
    }

    @UiRequired
    @UILayout(labelPossition=LabelPosition.BEFORE,layout=LayouDefinitions.ONECOLUMNLAYOUT,layoutOrientation=LayoutOrientation.AXISY)
    @UiOrder(value=1)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    
    @UiRequired
    @UILayout(labelPossition=LabelPosition.BEFORE,layout=LayouDefinitions.ONECOLUMNLAYOUT,layoutOrientation=LayoutOrientation.AXISY)
    @UiOrder(value=3)
    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }
    
    @UiRequired
    @UILayout(labelPossition=LabelPosition.BEFORE,layout=LayouDefinitions.ONECOLUMNLAYOUT,layoutOrientation=LayoutOrientation.AXISY)
    @UiOrder(value=2)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @UiRequired
    @UiType(value="country")
    @UILayout(labelPossition=LabelPosition.BEFORE,layout=LayouDefinitions.ONECOLUMNLAYOUT,layoutOrientation=LayoutOrientation.AXISY)
    @UiOrder(value=4)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @UiType(value="id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
