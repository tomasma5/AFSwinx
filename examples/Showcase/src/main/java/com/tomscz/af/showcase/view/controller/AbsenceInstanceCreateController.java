package com.tomscz.af.showcase.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.tomscz.af.showcase.view.AbsenceInstanceCreateView;
import com.tomscz.af.showcase.view.BaseView;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;

public class AbsenceInstanceCreateController extends BaseController {

    public AbsenceInstanceCreateController(BaseView screen) {
        super(screen);
        registerListeners();
    }

    @Override
    protected void registerListeners() {
        AbsenceInstanceCreateView aiv = (AbsenceInstanceCreateView) view;
        aiv.addCreateButtonActionListener(onCreateAbsenceInstanceAL);
        super.registerListeners();
    }

    private ActionListener onCreateAbsenceInstanceAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (AFSwinx.getInstance()
                        .getExistedComponent(AbsenceInstanceCreateView.ABSENCE_INSTANCE_FORM)
                        .validateData()) {
                    AFSwinx.getInstance()
                            .getExistedComponent(AbsenceInstanceCreateView.ABSENCE_INSTANCE_FORM)
                            .sendData();
                    view.getDialogs().succes("absenceInstance.action.add.inform", "absenceInstance.action.add.inform.good","");
                }
            } catch (AFSwinxConnectionException e1) {
                view.getDialogs().failed("absenceInstance.action.add.inform",
                        "absenceInstance.action.add.inform.failed", e1.getMessage());
            }
        }
    };

}
