package com.tomscz.af.showcase.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.tomscz.af.showcase.view.AvaiableCountryView;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxForm;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;

/**
 * This is controller which hold action in avaiable country view.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AvaiableCountryController extends BaseController {

    public AvaiableCountryController(AvaiableCountryView screen) {
        super(screen);
        registerListeners();
    }

    @Override
    protected void registerListeners() {
        // Register specific button in view
        AvaiableCountryView ws = (AvaiableCountryView) view;
        ws.addAddCountryListener(onAddCountryExec);
        ws.addChooseCountryListener(onChooseCountryExec);
        ws.addResetForm(onResetForm);
        super.registerListeners();
    }

    /**
     * This method perform action on addButton is clicked.
     */
    private ActionListener onAddCountryExec = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Try validate
                if (AFSwinx.getInstance().getExistedComponent(AvaiableCountryView.COUNTRY_FORM)
                        .validateData()) {
                    // Send data to server
                    AFSwinx.getInstance().getExistedComponent(AvaiableCountryView.COUNTRY_FORM)
                            .sendData();
                    // reload view.
                    view.getMainFrame().getContentPane().removeAll();
                    view.intialize();
                    registerListeners();
                    view.getMainFrame().getContentPane().repaint();
                    view.getDialogs().succes("action.succes",
                            "avaiableCountryView.action.addOrUpdate.succes", "");
                }
            } catch (AFSwinxConnectionException e1) {
                view.getDialogs().failed("avaiableCountryVeiw.button.add",
                        "avaiableCountryVeiw.button.add.failed", e1.getMessage());
            }
        }
    };

    /**
     * This action listener is called when choose button is clicked.
     */
    private ActionListener onChooseCountryExec = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Set selected data to form
            chooseDataInTableAndSetToForm(AvaiableCountryView.COUNTRY_TABLE,
                    AvaiableCountryView.COUNTRY_FORM);
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
                            AvaiableCountryView.COUNTRY_FORM);
            form.clearData();
        }
    };

}
