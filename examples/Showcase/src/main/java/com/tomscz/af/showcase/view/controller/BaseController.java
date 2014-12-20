package com.tomscz.af.showcase.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.tomscz.af.showcase.view.forms.BaseScreen;
import com.tomscz.af.showcase.view.forms.WelcomeScreen;
import com.tomscz.afswinx.component.AFSwinx;

public abstract class BaseController {

    protected BaseScreen view;
    
    public BaseController(BaseScreen screen){
        this.view = screen;
        view.addLoginButtonListener(loginButtonListener);
    }
    
    private ActionListener loginButtonListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            AFSwinx.getInstance().removeAllComponents();
            view.setVisible(false);
            WelcomeScreen welcomeScreen = new WelcomeScreen();
            WelcomeScreenController controller = new WelcomeScreenController(welcomeScreen);
            welcomeScreen.setVisible(true);
        }
    };
    
    private ActionListener avaiableCountryPublicListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            
        }
    };
    
    
    
}
