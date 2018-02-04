package com.tomscz.af.showcase.view.controller;

import com.tomscz.af.showcase.view.AvaiableCountryView;
import com.tomscz.af.showcase.view.AvailableVehiclesView;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxForm;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is controller which hold action in avaiable vehicles view.
 * 
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 * 
 * @since 1.0.0.
 */
public class AvailableVehiclesController extends BaseController {

    public AvailableVehiclesController(AvailableVehiclesView screen) {
        super(screen);
        registerListeners();
    }

    @Override
    protected void registerListeners() {
        // Register specific button in view
        AvailableVehiclesView ws = (AvailableVehiclesView) view;
        ws.addAddVehicleListener(onAddVehicleListener);
        ws.addChooseVehicleListener(onChooseVehicleListener);
        ws.addResetForm(onResetForm);
        super.registerListeners();
    }

    /**
     * This method perform action on addButton is clicked.
     */
    private ActionListener onAddVehicleListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Try validate
                if (AFSwinx.getInstance().getExistedComponent(AvailableVehiclesView.VEHICLES_FORM).validateData()) {
                    // Send data to server
                    AFSwinx.getInstance().getExistedComponent(AvailableVehiclesView.VEHICLES_FORM).sendData();
                    // reload view.
                    view.getMainFrame().getContentPane().removeAll();
                    view.intialize();
                    registerListeners();
                    view.getMainFrame().getContentPane().repaint();
                    view.getDialogs().succes("action.succes",
                            "vehicles.action.addOrUpdate.success", "");
                }
            } catch (AFSwinxConnectionException e1) {
                view.getDialogs().failed("avaiableCountryVeiw.button.add",
                        "vehicles.action.addOrUpdate.success", e1.getMessage());
            }
        }
    };

    /**
     * This action listener is called when choose button is clicked.
     */
    private ActionListener onChooseVehicleListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Set selected data to form
            chooseDataInTableAndSetToForm(AvailableVehiclesView.VEHICLES_TABLE,
                    AvailableVehiclesView.VEHICLES_FORM);
        }
    };

    /**
     * This action is called when reset button is clicked.
     */
    private ActionListener onResetForm = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get existed form and reset it
            AFSwinxForm form =
                    (AFSwinxForm) AFSwinx.getInstance().getExistedComponent(
                            AvailableVehiclesView.VEHICLES_FORM);
            form.clearData();
        }
    };

}
