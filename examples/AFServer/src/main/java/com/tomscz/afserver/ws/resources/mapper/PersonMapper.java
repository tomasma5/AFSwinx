package com.tomscz.afserver.ws.resources.mapper;

import java.io.Serializable;

public class PersonMapper implements Serializable {

    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private int age;

    public PersonMapper() {}

    public PersonMapper(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
