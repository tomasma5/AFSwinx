package com.tomscz.afserver.persistence.entity;

import com.codingcrayons.aspectfaces.annotations.UILayout;
import com.codingcrayons.aspectfaces.annotations.UiLabel;
import com.codingcrayons.aspectfaces.annotations.UiRequired;
import com.tomscz.afrest.layout.definitions.LabelPosition;
import com.tomscz.afrest.layout.definitions.LayouDefinitions;
import com.tomscz.afrest.layout.definitions.LayoutOrientation;

public class Person {

    private String firstName;
    private String lastName;
    private Long id;
    private int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.TWOCOLUMNSLAYOUT, layoutOrientation = LayoutOrientation.AXISX)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @UiRequired
    @UiLabel(value = "Jmeno")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @UiLabel(value = "Vek")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
