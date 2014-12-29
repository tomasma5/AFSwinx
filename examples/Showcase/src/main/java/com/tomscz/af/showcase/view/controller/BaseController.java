package com.tomscz.af.showcase.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.tomscz.af.showcase.view.AbsenceInstanceCreateView;
import com.tomscz.af.showcase.view.AbsenceInstanceEditView;
import com.tomscz.af.showcase.view.AbsenceTypManagementView;
import com.tomscz.af.showcase.view.AvaiableCountryView;
import com.tomscz.af.showcase.view.BaseScreen;
import com.tomscz.af.showcase.view.MyAbsenceInstanceView;
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
        view.addAbsenceAddListener(absenceInstanceCreateListener);
        view.addMyAbsencesListener(myAbsenceInstanceListener);
        view.addAbsencesInstanceEditListener(absenceInstanceEditListener);
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
            WelcomeScreen welcomeScreen = new WelcomeScreen();
            WelcomeScreenController controller = new WelcomeScreenController(welcomeScreen);
            view.setVisible(false);
            view = null;
            welcomeScreen.setVisible(true);
        }
    };

    private ActionListener avaiableCountryPublicListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            AFSwinx.getInstance().removeAllComponents();
            AvaiableCountryView avaiableCountry = new AvaiableCountryView();
            AvaiableCountryController controller = new AvaiableCountryController(avaiableCountry);
            view.setVisible(false);
            view = null;
            avaiableCountry.setVisible(true);
        }
    };

    private ActionListener myProfileListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            AFSwinx.getInstance().removeAllComponents();
            PersonView personView = new PersonView();
            PersonController controller = new PersonController(personView);
            view.setVisible(false);
            personView.setVisible(true);
        }
    };

    private ActionListener absenceTypeListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            AFSwinx.getInstance().removeAllComponents();
            AbsenceTypeManagementModel model = new AbsenceTypeManagementModel();
            AbsenceTypManagementView absenceTypeManagementView = new AbsenceTypManagementView();
            AbsenceTypeManagementController controller =
                    new AbsenceTypeManagementController(absenceTypeManagementView);
            controller.setModel(model);
            view.setVisible(false);
            view = null;
            absenceTypeManagementView.setVisible(true);
        }
    };

    private ActionListener absenceInstanceCreateListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            AFSwinx.getInstance().removeAllComponents();
            AbsenceInstanceCreateView absenceInstanceCreateView = new AbsenceInstanceCreateView();
            AbsenceInstanceCreateController controller =
                    new AbsenceInstanceCreateController(absenceInstanceCreateView);
            view.setVisible(false);
            view = null;
            absenceInstanceCreateView.setVisible(true);
        }
    };
    
    private ActionListener myAbsenceInstanceListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            AFSwinx.getInstance().removeAllComponents();
            MyAbsenceInstanceView myAbsenceInstanceView = new MyAbsenceInstanceView();
            MyAbsenceInstanceController controller =
                    new MyAbsenceInstanceController(myAbsenceInstanceView);
            view.setVisible(false);
            view = null;
            myAbsenceInstanceView.setVisible(true);
        }
    };
    
    private ActionListener absenceInstanceEditListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            AFSwinx.getInstance().removeAllComponents();
            AbsenceInstanceEditView absenceInstanceEditView = new AbsenceInstanceEditView();
            AbsenceInstanceEditController controller =
                    new AbsenceInstanceEditController(absenceInstanceEditView);
            view.setVisible(false);
            view = null;
            absenceInstanceEditView.setVisible(true);
        }
    };
    
}
