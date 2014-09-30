package com.tomscz.afserver.persistence.entity;

import com.codingcrayons.aspectfaces.annotations.UILayout;
import com.codingcrayons.aspectfaces.annotations.UiRequired;

import com.tomscz.afswinx.layout.definitions.LayouDefinitions;


public class Person {

    private String firstName;
    private String lastName;
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @UILayout()
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @UiRequired
    @UILayout(layout=LayouDefinitions.BORDERLAYOUT)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


}
