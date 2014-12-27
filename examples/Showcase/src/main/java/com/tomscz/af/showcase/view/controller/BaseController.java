package com.tomscz.af.showcase.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.tomscz.af.showcase.view.forms.AvaiableCountryView;
import com.tomscz.af.showcase.view.forms.BaseScreen;
import com.tomscz.af.showcase.view.forms.PersonView;
import com.tomscz.af.showcase.view.forms.WelcomeScreen;
import com.tomscz.afswinx.component.AFSwinx;

public abstract class BaseController {

    protected BaseScreen view;
    
    public BaseController(BaseScreen screen){
        this.view = screen;
    }
    
    protected void registerListeners(){
        view.addLoginButtonListener(loginButtonListener);
        view.addAvaiableCountryListener(avaiableCountryPublicListener);
        view.addMyProfileListener(myProfileListener);
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
            AFSwinx.getInstance().removeAllComponents();
            view.setVisible(false);
            AvaiableCountryView avaiableCountry = new AvaiableCountryView();
            AvaiableCountryController controller = new AvaiableCountryController(avaiableCountry);
            avaiableCountry.setVisible(true);
        }
    };
    
    private ActionListener myProfileListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            AFSwinx.getInstance().removeAllComponents();
            view.setVisible(false);
            PersonView personView = new PersonView();
            PersonController controller = new PersonController(personView);
            personView.setVisible(true);
        }
    };    
    
}
