package com.tomscz.af.showcase.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import com.tomscz.af.showcase.view.AvaiableCountryView;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxForm;
import com.tomscz.afswinx.component.AFSwinxTable;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataPack;

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
                    view.getDialogs().succes("action.succes", "avaiableCountryView.action.addOrUpdate.succes","");
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
                AFSwinxTable table = (AFSwinxTable) AFSwinx.getInstance().getExistedComponent(AvaiableCountryView.COUNTRY_TABLE);
                List<AFDataPack> datas = table.getSelectedData();
                AFSwinxForm form = (AFSwinxForm) AFSwinx.getInstance().getExistedComponent(AvaiableCountryView.COUNTRY_FORM);
                form.fillData(datas);
        }
    };
    
    private ActionListener onResetForm = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
                AFSwinxForm form = (AFSwinxForm) AFSwinx.getInstance().getExistedComponent(AvaiableCountryView.COUNTRY_FORM);
                form.clearData();
        }
    };

}
