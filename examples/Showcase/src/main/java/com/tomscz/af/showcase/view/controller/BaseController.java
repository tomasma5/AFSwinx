package com.tomscz.af.showcase.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.application.ShowcaseConstants;
import com.tomscz.af.showcase.utils.ProxyConstants;
import com.tomscz.af.showcase.view.*;
import com.tomscz.af.showcase.view.model.AbsenceTypeManagementModel;
import com.tomscz.af.showcase.view.model.BaseModel;
import com.tomscz.afrest.commons.SupportedComponents;
import com.tomscz.afswinx.component.*;
import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.component.uiproxy.AFProxyComponentDefinition;
import com.tomscz.afswinx.component.uiproxy.AFProxyScreenDefinition;
import com.tomscz.afswinx.component.uiproxy.ScreenPreparedListener;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataPack;

import javax.sql.rowset.serial.SerialRef;

/**
 * This is abstract controller, which hold action in menu and localization bar. It has also some
 * support method.
 *
 * @author Martin Tomasek (martin@toms-cz.com)
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
        view.addCzechButtonListener(onCzechButtonExec);
        view.addEnglishButtonListener(onEnglishButtonExec);
        view.addLogoutButtonMenuListener(logoutButtonListener);
        Map<String, AFSwinxScreenButton> menuButtons = view.getSwinxMenu().getMenuButtons();
        try {
            menuButtons.get(ProxyConstants.BTN_KEY_LOGIN).setScreenPreparedListener(loginButtonListener);
            menuButtons.get(ProxyConstants.BTN_KEY_COUNTRIES).setScreenPreparedListener(avaiableCountryPublicListener);
            menuButtons.get(ProxyConstants.BTN_KEY_VEHICLES).setScreenPreparedListener(vehiclesButtonListener);
            menuButtons.get(ProxyConstants.BTN_KEY_PROFILE).setScreenPreparedListener(myProfileListener);
            menuButtons.get(ProxyConstants.BTN_KEY_ABSENCE_TYPE).setScreenPreparedListener(absenceTypeListener);
            menuButtons.get(ProxyConstants.BTN_KEY_CREATE_ABSENCE).setScreenPreparedListener(absenceInstanceCreateListener);
            menuButtons.get(ProxyConstants.BTN_KEY_MY_ABSENCES).setScreenPreparedListener(myAbsenceInstanceListener);
            menuButtons.get(ProxyConstants.BTN_KEY_ABSENCE_MANAGEMENT).setScreenPreparedListener(absenceInstanceEditListener);
            menuButtons.get(ProxyConstants.BTN_KEY_BUSINESS_TRIPS).setScreenPreparedListener(businessTripsListener);
        } catch (NullPointerException ex) {
            System.err.println("One of the menu buttons were not found. Please check the exception\n");
            ex.printStackTrace();
        }
    }

    // This section register listeners from menu and localization

    private ScreenPreparedListener loginButtonListener = new ScreenPreparedListener() {

        @Override
        public void onScreenPrepared(AFProxyScreenDefinition afProxyScreenDefinition) {
            AFSwinx.getInstance().removeAllComponents();
            WelcomeScreen loginScreen = new WelcomeScreen(afProxyScreenDefinition);
            WelcomeScreenController controller = new WelcomeScreenController(loginScreen);
            view.setVisible(false);
            view = null;
            loginScreen.setVisible(true);
        }

    };

    private ScreenPreparedListener logoutButtonListener = new ScreenPreparedListener() {

        @Override
        public void onScreenPrepared(AFProxyScreenDefinition afProxyScreenDefinition) {
            ApplicationContext.getInstance().setSecurityContext(null);
            WelcomeScreen welcomeScreen = new WelcomeScreen(afProxyScreenDefinition);
            WelcomeScreenController controller = new WelcomeScreenController(welcomeScreen);
            view.setVisible(false);
            view = null;
            welcomeScreen.setVisible(true);
        }

    };

    private ScreenPreparedListener avaiableCountryPublicListener = new ScreenPreparedListener() {
        @Override
        public void onScreenPrepared(AFProxyScreenDefinition afProxyScreenDefinition) {
            AFSwinx.getInstance().removeAllComponents();
            AvaiableCountryView avaiableCountry = new AvaiableCountryView(afProxyScreenDefinition);
            AvaiableCountryController controller = new AvaiableCountryController(avaiableCountry);
            view.setVisible(false);
            view = null;
            avaiableCountry.setVisible(true);
        }
    };

    private ScreenPreparedListener myProfileListener = new ScreenPreparedListener() {

        @Override
        public void onScreenPrepared(AFProxyScreenDefinition afProxyScreenDefinition) {
            AFSwinx.getInstance().removeAllComponents();
            PersonView personView = new PersonView(afProxyScreenDefinition);
            PersonController controller = new PersonController(personView);
            view.setVisible(false);
            personView.setVisible(true);
        }
    };

    private ScreenPreparedListener absenceTypeListener = new ScreenPreparedListener() {

        @Override
        public void onScreenPrepared(AFProxyScreenDefinition afProxyScreenDefinition) {
            AFSwinx.getInstance().removeAllComponents();
            AbsenceTypeManagementModel model = new AbsenceTypeManagementModel();
            AbsenceTypManagementView absenceTypeManagementView = new AbsenceTypManagementView(afProxyScreenDefinition);
            AbsenceTypeManagementController controller =
                    new AbsenceTypeManagementController(absenceTypeManagementView);
            controller.setModel(model);
            view.setVisible(false);
            view = null;
            absenceTypeManagementView.setVisible(true);
        }
    };

    private ScreenPreparedListener absenceInstanceCreateListener = new ScreenPreparedListener() {

        @Override
        public void onScreenPrepared(AFProxyScreenDefinition afProxyScreenDefinition) {
            AFSwinx.getInstance().removeAllComponents();
            AbsenceInstanceCreateView absenceInstanceCreateView = new AbsenceInstanceCreateView(afProxyScreenDefinition);
            AbsenceInstanceCreateController controller =
                    new AbsenceInstanceCreateController(absenceInstanceCreateView);
            view.setVisible(false);
            view = null;
            absenceInstanceCreateView.setVisible(true);
        }

    };

    private ScreenPreparedListener myAbsenceInstanceListener = new ScreenPreparedListener() {

        @Override
        public void onScreenPrepared(AFProxyScreenDefinition afProxyScreenDefinition) {
            AFSwinx.getInstance().removeAllComponents();
            MyAbsenceInstanceView myAbsenceInstanceView = new MyAbsenceInstanceView(afProxyScreenDefinition);
            MyAbsenceInstanceController controller = new MyAbsenceInstanceController(myAbsenceInstanceView);
            view.setVisible(false);
            view = null;
            myAbsenceInstanceView.setVisible(true);
        }

    };

    private ScreenPreparedListener vehiclesButtonListener = new ScreenPreparedListener() {

        @Override
        public void onScreenPrepared(AFProxyScreenDefinition afProxyScreenDefinition) {
            AFSwinx.getInstance().removeAllComponents();
            AvailableVehiclesView vehiclesView = new AvailableVehiclesView(afProxyScreenDefinition);
            AvailableVehiclesController controller = new AvailableVehiclesController(vehiclesView);
            view.setVisible(false);
            view = null;
            vehiclesView.setVisible(true);
        }
    };

    private ScreenPreparedListener businessTripsListener = new ScreenPreparedListener() {

        @Override
        public void onScreenPrepared(AFProxyScreenDefinition afProxyScreenDefinition) {
            AFSwinx.getInstance().removeAllComponents();
            BusinessTripEditView businessTripEditView = new BusinessTripEditView(afProxyScreenDefinition);
            BusinessTripEditController controller = new BusinessTripEditController(businessTripEditView);
            view.setVisible(false);
            view = null;
            businessTripEditView.setVisible(true);
        }

    };

    private ScreenPreparedListener absenceInstanceEditListener = new ScreenPreparedListener() {

        @Override
        public void onScreenPrepared(AFProxyScreenDefinition afProxyScreenDefinition) {
            AFSwinx.getInstance().removeAllComponents();
            AbsenceInstanceEditView absenceInstanceEditView = new AbsenceInstanceEditView(afProxyScreenDefinition);
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
     * @param formId  id of form.
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
