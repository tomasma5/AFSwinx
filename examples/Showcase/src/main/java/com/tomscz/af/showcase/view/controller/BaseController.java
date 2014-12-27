package com.tomscz.af.showcase.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.tomscz.af.showcase.view.AbsenceTypManagementView;
import com.tomscz.af.showcase.view.AvaiableCountryView;
import com.tomscz.af.showcase.view.BaseScreen;
import com.tomscz.af.showcase.view.PersonView;
import com.tomscz.af.showcase.view.WelcomeScreen;
import com.tomscz.af.showcase.view.model.AbsenceTypeManagementModel;
import com.tomscz.af.showcase.view.model.BaseModel;
import com.tomscz.afswinx.component.AFSwinx;

public abstract class BaseController {

    protected BaseScreen view;
    private BaseModel model;

    public BaseController(BaseScreen screen) {
        this.view = screen;
    }

    protected void registerListeners() {
        view.addLoginButtonListener(loginButtonListener);
        view.addAvaiableCountryListener(avaiableCountryPublicListener);
        view.addMyProfileListener(myProfileListener);
        view.addAbsenceTypeListener(absenceTypeListener);
    }

    public BaseModel getModel() {
        return model;
    }

    public void setModel(BaseModel model) {
        this.model = model;
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

    private ActionListener absenceTypeListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            AFSwinx.getInstance().removeAllComponents();
            view.setVisible(false);
            AbsenceTypeManagementModel model = new AbsenceTypeManagementModel();
            AbsenceTypManagementView absenceTypeManagementView = new AbsenceTypManagementView();
            AbsenceTypeManagementController controller =
                    new AbsenceTypeManagementController(absenceTypeManagementView);
            controller.setModel(model);
            absenceTypeManagementView.setVisible(true);
        }
    };

}
