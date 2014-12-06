package com.tomscz.afserver.view.loginForm;

import java.io.Serializable;

import com.codingcrayons.aspectfaces.annotations.UIWidgetType;
import com.codingcrayons.aspectfaces.annotations.UiLabel;
import com.codingcrayons.aspectfaces.annotations.UiOrder;
import com.codingcrayons.aspectfaces.annotations.UiRequired;
import com.codingcrayons.aspectfaces.annotations.UiType;
import com.tomscz.afrest.commons.SupportedWidgets;

public class LoginFormDefinitions implements Serializable{

    public static final String LOGIN_FORM = "loginForm";
    
    private static final long serialVersionUID = 1L;
    
    private String username;
    private String password;
    
    @UiRequired
    @UiOrder(value=2)
    @UiLabel(value = "login.password")
    @UiType(value="password")
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @UiRequired
    @UiOrder(value=1)
    @UiLabel(value = "login.userName")
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
}
