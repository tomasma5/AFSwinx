package com.tomscz.af.showcase.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import com.tomscz.af.showcase.view.AbsenceTypManagementView;
import com.tomscz.af.showcase.view.BaseView;
import com.tomscz.af.showcase.view.model.AbsenceTypeManagementModel;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxForm;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;
import com.tomscz.afswinx.rest.rebuild.holder.AFData;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataHolder;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataPack;

public class AbsenceTypeManagementController extends BaseController {

    public AbsenceTypeManagementController(BaseView screen) {
        super(screen);
        registerListeners();
    }

    @Override
    protected void registerListeners() {
        AbsenceTypManagementView ws = (AbsenceTypManagementView) view;
        ws.addPerformButtonActionListener(onPerformActionExec);
        ws.addChooseButtonActionListener(onChooseAbsenceTypeExec);
        ws.addResetFormActionListener(onResetFormExec);
        ws.addChooseCountryButtonActionListener(onChooseCountryExec);
        super.registerListeners();
    }

    private ActionListener onPerformActionExec = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (AFSwinx.getInstance()
                        .getExistedComponent(AbsenceTypManagementView.ABSENCY_TYPE_FORM)
                        .validateData()) {
                    AFSwinx.getInstance()
                            .getExistedComponent(AbsenceTypManagementView.ABSENCY_TYPE_FORM)
                            .sendData();
                    rebuildView();
                    view.getDialogs().succes("action.succes", "absencetype.action.addOrModify", "");
                }
            } catch (AFSwinxConnectionException e1) {
                view.getDialogs().failed("avaiableCountryVeiw.button.add",
                        "avaiableCountryVeiw.button.add.failed", e1.getMessage());
            }
        }
    };

    private ActionListener onChooseAbsenceTypeExec = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            chooseDataInTableAndSetToForm(AbsenceTypManagementView.ABSENCE_TYPE_TABLE,
                    AbsenceTypManagementView.ABSENCY_TYPE_FORM);
        }
    };

    private ActionListener onChooseCountryExec = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            AFSwinxForm form =
                    (AFSwinxForm) AFSwinx.getInstance().getExistedComponent(
                            AbsenceTypManagementView.COUNTRY_FORM);
            AFDataHolder data = form.resealize();
            String countryId = data.getPropertiesAndValues().get("country");
            int id = Integer.parseInt(countryId);
            AbsenceTypeManagementModel model = (AbsenceTypeManagementModel) getModel();
            model.setCountryId(id);
            AFSwinx.getInstance().removeAllComponents();
            AbsenceTypManagementView absenceTypeView = (AbsenceTypManagementView) view;
            absenceTypeView.setDisplayAdditionalsField(true);
            absenceTypeView.setSelectedCountry(id);
            rebuildView();
        }
    };

    private ActionListener onResetFormExec = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            AFSwinxForm form =
                    (AFSwinxForm) AFSwinx.getInstance().getExistedComponent(
                            AbsenceTypManagementView.ABSENCY_TYPE_FORM);
            form.clearData();
        }
    };

    private void rebuildView() {
        view.getMainFrame().getContentPane().removeAll();
        view.intialize();
        registerListeners();
        view.getMainFrame().getContentPane().repaint();
        AFSwinxForm form =
                (AFSwinxForm) AFSwinx.getInstance().getExistedComponent(
                        AbsenceTypManagementView.COUNTRY_FORM);
        List<AFDataPack> dataPacks = new ArrayList<AFDataPack>();
        AFDataPack dataPack = new AFDataPack("");
        AbsenceTypeManagementModel model = (AbsenceTypeManagementModel) getModel();
        AFData countryData = new AFData("country", String.valueOf(model.getCountryId()));
        dataPack.addData(countryData);
        dataPacks.add(dataPack);
        form.fillData(dataPacks);
    }

}
