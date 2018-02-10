package com.tomscz.af.showcase.view.controller;

import com.tomscz.af.showcase.view.BaseView;
import com.tomscz.af.showcase.view.BusinessTripDetailView;
import com.tomscz.af.showcase.view.BusinessTripEditView;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BusinessTripDetailController extends BaseController {

    public BusinessTripDetailController(BaseView screen, int businessTripId) {
        super(screen);
        registerListeners();
    }

    @Override
    protected void registerListeners() {
        BusinessTripDetailView businessTripDetailView = (BusinessTripDetailView) view;
        businessTripDetailView.addChooseButtonActionListener(onChooseBusinessTripListener);
        businessTripDetailView.addPerformButtonActionListener(onBusinessTripPartEditListener);
        super.registerListeners();
    }

    private ActionListener onChooseBusinessTripListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            chooseDataInTableAndSetToForm(BusinessTripDetailView.BUSINESS_TRIP_PARTS_TABLE,
                    BusinessTripDetailView.BUSINESS_TRIP_PARTS_FORM);
        }
    };

    private ActionListener onBusinessTripPartEditListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (AFSwinx.getInstance()
                        .getExistedComponent(BusinessTripDetailView.BUSINESS_TRIP_PARTS_FORM)
                        .validateData()) {
                    AFSwinx.getInstance()
                            .getExistedComponent(BusinessTripDetailView.BUSINESS_TRIP_PARTS_FORM)
                            .sendData();
                    view.getMainFrame().getContentPane().removeAll();
                    view.intialize();
                    registerListeners();
                    view.getMainFrame().getContentPane().repaint();
                    view.getDialogs().succes("action.succes",
                            "businessTrip.action.addOrUpdate.success", "");
                }
            } catch (AFSwinxConnectionException e1) {
                view.getDialogs().failed("avaiableCountryVeiw.button.add",
                        "businessTrip.button.add.failed", e1.getMessage());
            }
        }
    };


    //TODO

}
