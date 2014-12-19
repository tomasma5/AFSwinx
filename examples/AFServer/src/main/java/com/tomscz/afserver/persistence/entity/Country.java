package com.tomscz.afserver.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.codingcrayons.aspectfaces.annotations.UILayout;
import com.codingcrayons.aspectfaces.annotations.UiLabel;
import com.codingcrayons.aspectfaces.annotations.UiOrder;
import com.codingcrayons.aspectfaces.annotations.UiRequired;
import com.codingcrayons.aspectfaces.annotations.UiType;
import com.tomscz.afrest.layout.definitions.LabelPosition;
import com.tomscz.afrest.layout.definitions.LayouDefinitions;
import com.tomscz.afrest.layout.definitions.LayoutOrientation;

@Entity
public class Country {

    @Id
    private int id;
    private String name;
    private String shortCut;
    private boolean active;
  
    public Country(){
        super();
    }
    
    public Country(int id, String name, String shortCut, boolean active){
        this.id = id;
        this.name = name;
        this.shortCut = shortCut;
        this.active = active;
    }

    @UiLabel(value="country.isActive")
    @UiOrder(value=3)
    @UiRequired
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @UiLabel(value="country.shortCut")
    @UILayout(labelPossition=LabelPosition.BEFORE,layout=LayouDefinitions.TWOCOLUMNSLAYOUT,layoutOrientation=LayoutOrientation.AXISY)
    @UiOrder(value=1)
    @UiRequired
    public String getShortCut() {
        return shortCut;
    }

    public void setShortCut(String shortCut) {
        this.shortCut = shortCut;
    }

    @UiLabel(value="country.name")
    @UiOrder(value=0)
    @UiRequired
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @UiType(value="id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
