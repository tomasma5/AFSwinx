package com.tomscz.af.showcase.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.application.ShowcaseConstants;
import com.tomscz.af.showcase.view.*;
import com.tomscz.af.showcase.view.model.AbsenceTypeManagementModel;
import com.tomscz.af.showcase.view.model.BaseModel;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxForm;
import com.tomscz.afswinx.component.AFSwinxTable;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataPack;

/**
 * This is abstract controller, which hold action in menu and localization bar. It has also some
 * support method.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public abstract class BaseController {

    protected BaseView view;
    private BaseModel model;

    public BaseController(BaseView screen) {
        this.view = screen;
    }

    /**
     * This method register all listeners for this controller.
     */
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
        view.addBusinessTripsListener(businessTripsListener);
        view.addVehiclesButtonListener(vehiclesButtonListener);
    }

    // This section register listeners from menu and localization

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
            List<AFComponent> ...
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
            MyAbsenceInstanceController controller = new MyAbsenceInstanceController(myAbsenceInstanceView);
            view.setVisible(false);
            view = null;
            myAbsenceInstanceView.setVisible(true);
        }
    };

    private ActionListener vehiclesButtonListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            AFSwinx.getInstance().removeAllComponents();
            AvailableVehiclesView vehiclesView = new AvailableVehiclesView();
            AvailableVehiclesController controller = new AvailableVehiclesController(vehiclesView);
            view.setVisible(false);
            view = null;
            vehiclesView.setVisible(true);
        }
    };

    private ActionListener businessTripsListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            AFSwinx.getInstance().removeAllComponents();
            BusinessTripEditView businessTripEditView = new BusinessTripEditView();
            BusinessTripEditController controller = new BusinessTripEditController(businessTripEditView);
            view.setVisible(false);
            view = null;
            businessTripEditView.setVisible(true);
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

    /**
     * This method get selected data in table and set it to form.
     * 
     * @param tableId id of table.
     * @param formId id of form.
     */
    protected void chooseDataInTableAndSetToForm(String tableId, String formId) {
        // Find table
        AFSwinxTable table = (AFSwinxTable) AFSwinx.getInstance().getExistedComponent(tableId);
        try {
            // Try obtained data.
            List<AFDataPack> datas = table.getSelectedData();
            // Find existed form
            AFSwinxForm form = (AFSwinxForm) AFSwinx.getInstance().getExistedComponent(formId);
            // Set data to form
            form.fillData(datas);
        } catch (IndexOutOfBoundsException exception) {
            // If this exception is catch, then no data were selected
            view.getDialogs().failed("afswinx.choose.table.choosed",
                    "afswinx.choose.table.outOfIndex", exception.getMessage());
        }
    }

    public BaseModel getModel() {
        return model;
    }

    public void setModel(BaseModel model) {
        this.model = model;
    }

}
