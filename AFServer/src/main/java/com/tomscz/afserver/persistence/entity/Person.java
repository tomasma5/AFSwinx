package com.tomscz.afserver.persistence.entity;

import com.codingcrayons.aspectfaces.annotations.UILayout;
import com.codingcrayons.aspectfaces.annotations.UiLabel;
import com.codingcrayons.aspectfaces.annotations.UiRequired;
import com.codingcrayons.aspectfaces.annotations.UiType;


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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @UiRequired
    @UILayout(value="Border")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


}
