package com.tomscz.af.showcase.view.controller;

import com.tomscz.af.showcase.view.*;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxForm;
import com.tomscz.afswinx.component.AFSwinxTable;
import com.tomscz.afswinx.component.uiproxy.AFProxyScreenDefinition;
import com.tomscz.afswinx.component.uiproxy.ScreenPreparedListener;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;
import com.tomscz.afswinx.rest.rebuild.holder.AFData;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataPack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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
        businessTripEditView.addDetailButtonActionListener(onBusinessTripDetailButtonClicked);
        //TODO dodelat delete
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
                            "businessTrip.action.addOrUpdate.success", "");
                }
            } catch (AFSwinxConnectionException e1) {
                view.getDialogs().failed("avaiableCountryVeiw.button.add",
                        "businessTrip.button.add.failed", e1.getMessage());
            }
        }
    };

    private ScreenPreparedListener onBusinessTripDetailButtonClicked = new ScreenPreparedListener() {

        @Override
        public void onScreenPrepared(AFProxyScreenDefinition afProxyScreenDefinition) {
            AFSwinxTable table = (AFSwinxTable) AFSwinx.getInstance().getExistedComponent(BusinessTripEditView.BUSINESS_TRIP_EDIT_TABLE);
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                List<AFData> datas = table.getSelectedData().get(0).getData();
                int businessTripId = -1;
                String dateFrom = null;
                String dateTo = null;
                for (AFData data : datas) {
                    if (data.getKey().equals("id")) {
                        businessTripId = Integer.parseInt(data.getValue());
                    }
                    if (data.getKey().equals("startDate")) {
                        dateFrom = sdf.format(fromFormat.parse(data.getValue()));
                    }
                    if (data.getKey().equals("endDate")) {
                        dateTo = sdf.format(fromFormat.parse(data.getValue()));
                    }
                }
                if (businessTripId != -1 && dateFrom != null && dateTo != null) {
                    AFSwinx.getInstance().removeAllComponents();
                    BusinessTripDetailView detailView = new BusinessTripDetailView(afProxyScreenDefinition, businessTripId, dateFrom, dateTo);
                    BusinessTripDetailController controller = new BusinessTripDetailController(detailView, businessTripId);
                    view.removeAll();
                    view.setVisible(false);
                    view = null;
                    detailView.setVisible(true);
                }
            } catch (IndexOutOfBoundsException exception) {
                // If this exception is catch, then no data were selected
                if (view != null) {
                    view.getDialogs().failed("afswinx.choose.table.choosed",
                            "afswinx.choose.table.outOfIndex", exception.getMessage());
                }
            } catch (ParseException e1) {
                view.getDialogs().failed("Error while loading trip deatail", "Unable to parse date formats");
            }
        }
    };

}
