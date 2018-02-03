package com.tomscz.afserver.persistence.entity;

import java.util.ArrayList;
import java.util.List;
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
import com.codingcrayons.aspectfaces.annotations.UiIgnore;
import com.codingcrayons.aspectfaces.annotations.UiLabel;
import com.codingcrayons.aspectfaces.annotations.UiOrder;
import com.codingcrayons.aspectfaces.annotations.UiRequired;
import com.codingcrayons.aspectfaces.annotations.UiType;
import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.layout.definitions.LabelPosition;
import com.tomscz.afrest.layout.definitions.LayouDefinitions;
import com.tomscz.afrest.layout.definitions.LayoutOrientation;

@Entity
public class Person {

    @Id
    private int id;
    @Enumerated(EnumType.STRING)
    private List<UserRoles> userRole;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String email;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date hireDate;
    private boolean active;
    private int age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private boolean confidentialAgreement;
    @OneToOne
    private Address myAddress;
    @OneToOne
    private Country country;

    public Person() {

    }

    public Person(int id, String login, String password, String firstName, String lastName,
            String email, Date hireDate, boolean active, int age, Gender gender,
            boolean confidentialAgreement) {
        super();
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hireDate = hireDate;
        this.active = active;
        this.age = age;
        this.gender = gender;
        this.confidentialAgreement = confidentialAgreement;
    }

    @UiType(value = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation = LayoutOrientation.AXISY)
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
    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation = LayoutOrientation.AXISY)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @UiLabel(value = "person.age")
    @UiOrder(value = 3)
    @Min(value = 15)
    @Max(value = 60)
    @UiRequired
    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation = LayoutOrientation.AXISY)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @UIWidgetType(widgetType = SupportedWidgets.OPTION)
    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation = LayoutOrientation.AXISY)
    @UiRequired
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation = LayoutOrientation.AXISY)
    @UiType(value = "confidentianlAgreement")
    public boolean getConfidentialAgreement() {
        return confidentialAgreement;
    }

    public void setConfidentialAgreement(boolean confidentialAgreement) {
        this.confidentialAgreement = confidentialAgreement;
    }

    @UiOrder(value = 4)
    public Address getMyAddress() {
        return myAddress;
    }

    public void setMyAddress(Address myAddress) {
        this.myAddress = myAddress;
    }

    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation = LayoutOrientation.AXISY)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation = LayoutOrientation.AXISY)
    @UiType(value = "option")
    @UiRequired
    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation = LayoutOrientation.AXISY)
    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    
    public List<UserRoles> getUserRole() {
        return userRole;
    }

    public void setUserRole(List<UserRoles> userRole) {
        this.userRole = userRole;
    }

    public void addRole(UserRoles userRole) {
        if (this.userRole == null) {
            this.userRole = new ArrayList<UserRoles>();
        }
        if (!this.userRole.contains(userRole)) {
            this.userRole.add(userRole);
        }
    }

    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation = LayoutOrientation.AXISY)
    @UiOrder(value = -1)
    @UiType(value = "password")
    @UiRequired
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @UILayout(labelPossition = LabelPosition.BEFORE, layout = LayouDefinitions.ONECOLUMNLAYOUT, layoutOrientation = LayoutOrientation.AXISY)
    @UiOrder(value = -2)
    @UiRequired
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @UiIgnore
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

}
