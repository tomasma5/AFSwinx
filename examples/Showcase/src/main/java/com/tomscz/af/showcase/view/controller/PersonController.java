package com.tomscz.af.showcase.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.tomscz.af.showcase.view.BaseView;
import com.tomscz.af.showcase.view.PersonView;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;

public class PersonController extends BaseController {

    public PersonController(BaseView screen) {
        super(screen);
        registerListeners();
    }

    @Override
    protected void registerListeners() {
        PersonView ws = (PersonView) view;
        ws.addUpdateButtonListener(onUpdatePersonExec);
        super.registerListeners();
    }
    
    private ActionListener onUpdatePersonExec = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (AFSwinx.getInstance().getExistedComponent(PersonView.PERSON_FORM)
                        .validateData()) {
                    AFSwinx.getInstance().getExistedComponent(PersonView.PERSON_FORM)
                            .sendData();
                    view.getDialogs().succes("action.succes", "personView.button.update.succes","");
                }
            } catch (AFSwinxConnectionException e1) {
                view.getDialogs().failed("avaiableCountryVeiw.button.add",
                        "avaiableCountryVeiw.button.add.failed", e1.getMessage());
            }
        }
    };

}
