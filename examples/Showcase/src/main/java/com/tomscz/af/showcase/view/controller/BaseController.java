package com.tomscz.af.showcase.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.application.ShowcaseConstants;
import com.tomscz.af.showcase.view.AbsenceInstanceCreateView;
import com.tomscz.af.showcase.view.AbsenceInstanceEditView;
import com.tomscz.af.showcase.view.AbsenceTypManagementView;
import com.tomscz.af.showcase.view.AvaiableCountryView;
import com.tomscz.af.showcase.view.BaseView;
import com.tomscz.af.showcase.view.MyAbsenceInstanceView;
import com.tomscz.af.showcase.view.PersonView;
import com.tomscz.af.showcase.view.WelcomeScreen;
import com.tomscz.af.showcase.view.model.AbsenceTypeManagementModel;
import com.tomscz.af.showcase.view.model.BaseModel;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxForm;
import com.tomscz.afswinx.component.AFSwinxTable;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataPack;

public abstract class BaseController {

    protected BaseView view;
    private BaseModel model;

    public BaseController(BaseView screen) {
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
        view.addCzechButtonListener(onCzechButtonExec);
        view.addEnglishButtonListener(onEnglishButtonExec);
        view.addLogoutButtonMenuListener(logoutButtonListener);
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
    
    private ActionListener logoutButtonListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            ApplicationContext.getInstance().setSecurityContext(null);
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
    
    private ActionListener onEnglishButtonExec = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ApplicationContext.getInstance().changeLocalization(
                        ShowcaseConstants.ENGLISH_BUNDLE);
                view.getMainFrame().getContentPane().removeAll();
                view.intialize();
                registerListeners();
                view.getMainFrame().getContentPane().repaint();
            } catch (FileNotFoundException e1) {
                view.getDialogs().failed("localization.action", "localization.action.failed", "");
            }

        }
    };

    private ActionListener onCzechButtonExec = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ApplicationContext.getInstance().changeLocalization(ShowcaseConstants.CZECH_BUNDLE);
                view.getMainFrame().getContentPane().removeAll();
                view.intialize();
                registerListeners();
                view.getMainFrame().getContentPane().repaint();
            } catch (FileNotFoundException e1) {
                view.getDialogs().failed("localization.action", "localization.action.failed", "");
            }
        }
    };
    
    protected void chooseDataInTableAndSetToForm(String tableId, String formId){
        AFSwinxTable table =
                (AFSwinxTable) AFSwinx.getInstance().getExistedComponent(
                    tableId);
        try{
        List<AFDataPack> datas = table.getSelectedData();
        AFSwinxForm form =
                (AFSwinxForm) AFSwinx.getInstance().getExistedComponent(
                    formId);
        form.fillData(datas);
        }
        catch(IndexOutOfBoundsException exception){
            view.getDialogs().failed("afswinx.choose.table.choosed", "afswinx.choose.table.outOfIndex",
                exception.getMessage());
        }
    }
        
}
