package com.tomscz.af.showcase.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.tomscz.af.showcase.view.AbsenceInstanceEditView;
import com.tomscz.af.showcase.view.BaseView;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;

public class AbsenceInstanceEditController extends BaseController {

    public AbsenceInstanceEditController(BaseView screen) {
        super(screen);
        registerListeners();
    }

    @Override
    protected void registerListeners() {
        AbsenceInstanceEditView absenceInstanceEditView = (AbsenceInstanceEditView) view;
        absenceInstanceEditView.addChooseButtonActionListener(onChooseAbsenceInstanceExec);
        absenceInstanceEditView.addPerformButtonActionListener(onAbsenceInstanceEditExec);
        super.registerListeners();
    }

    private ActionListener onChooseAbsenceInstanceExec = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            chooseDataInTableAndSetToForm(AbsenceInstanceEditView.ABSENCE_INSTANCE_EDIT_TABLE,
                    AbsenceInstanceEditView.ABSENCE_INSTANCE_EDIT_FORM);
        }
    };

    private ActionListener onAbsenceInstanceEditExec = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (AFSwinx.getInstance()
                        .getExistedComponent(AbsenceInstanceEditView.ABSENCE_INSTANCE_EDIT_FORM)
                        .validateData()) {
                    AFSwinx.getInstance()
                            .getExistedComponent(AbsenceInstanceEditView.ABSENCE_INSTANCE_EDIT_FORM)
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
