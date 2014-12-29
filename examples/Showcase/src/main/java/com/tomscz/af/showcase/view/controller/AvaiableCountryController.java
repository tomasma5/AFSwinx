package com.tomscz.af.showcase.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.tomscz.af.showcase.view.AvaiableCountryView;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxForm;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;

public class AvaiableCountryController extends BaseController {

    public AvaiableCountryController(AvaiableCountryView screen) {
        super(screen);
        registerListeners();
    }

    @Override
    protected void registerListeners() {
        AvaiableCountryView ws = (AvaiableCountryView) view;
        ws.addAddCountryListener(onAddCountryExec);
        ws.addChooseCountryListener(onChooseCountryExec);
        ws.addResetForm(onResetForm);
        super.registerListeners();
    }

    private ActionListener onAddCountryExec = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (AFSwinx.getInstance().getExistedComponent(AvaiableCountryView.COUNTRY_FORM)
                        .validateData()) {
                    AFSwinx.getInstance().getExistedComponent(AvaiableCountryView.COUNTRY_FORM)
                            .sendData();
                    view.getContentPane().removeAll();
                    view.intialize();
                    registerListeners();
                    view.getContentPane().repaint();
                    view.getDialogs().succes("action.succes",
                            "avaiableCountryView.action.addOrUpdate.succes", "");
                }
            } catch (AFSwinxConnectionException e1) {
                view.getDialogs().failed("avaiableCountryVeiw.button.add",
                        "avaiableCountryVeiw.button.add.failed", e1.getMessage());
            }
        }
    };

    private ActionListener onChooseCountryExec = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            chooseDataInTableAndSetToForm(AvaiableCountryView.COUNTRY_TABLE,
                    AvaiableCountryView.COUNTRY_FORM);
        }
    };

    private ActionListener onResetForm = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            AFSwinxForm form =
                    (AFSwinxForm) AFSwinx.getInstance().getExistedComponent(
                            AvaiableCountryView.COUNTRY_FORM);
            form.clearData();
        }
    };

}
