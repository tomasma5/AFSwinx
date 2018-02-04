package com.tomscz.af.showcase.view.controller;

import com.tomscz.af.showcase.view.AbsenceInstanceEditView;
import com.tomscz.af.showcase.view.BaseView;
import com.tomscz.af.showcase.view.BusinessTripEditView;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BusinessTripEditController extends BaseController {

    public BusinessTripEditController(BaseView screen) {
        super(screen);
        registerListeners();
    }

    @Override
    protected void registerListeners() {
        BusinessTripEditView businessTripEditView = (BusinessTripEditView) view;
        businessTripEditView.addChooseButtonActionListener(onChooseBusinessTripListener);
        businessTripEditView.addPerformButtonActionListener(onBussinesTripEditListener);
        super.registerListeners();
    }

    private ActionListener onChooseBusinessTripListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            chooseDataInTableAndSetToForm(BusinessTripEditView.BUSINESS_TRIP_EDIT_TABLE,
                    BusinessTripEditView.BUSINESS_TRIP_EDIT_FORM);
        }
    };

    private ActionListener onBussinesTripEditListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (AFSwinx.getInstance()
                        .getExistedComponent(BusinessTripEditView.BUSINESS_TRIP_EDIT_FORM)
                        .validateData()) {
                    AFSwinx.getInstance()
                            .getExistedComponent(BusinessTripEditView.BUSINESS_TRIP_EDIT_FORM)
                            .sendData();
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

}
