package com.tomscz.afswinx.component.widget.builder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JFormattedTextField;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.data.AFData;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.component.widget.builder.abstraction.BaseComponentsBuilder;

public class DateBuilder extends BaseComponentsBuilder {

    // TODO
    // Default date formatter we will parametrize it
    final static String ISO8601DATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public DateBuilder(){
        widgetType = SupportedWidgets.CALENDAR;
    }
    
    @Override
    public AFSwinxPanel buildComponent(AFFieldInfo field) throws IllegalArgumentException {
        super.buildBase(field);
        // And input calendar field
        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
        layoutBuilder.addComponent(datePicker);
        coreComponent = datePicker;
        // Create panel which holds all necessary informations
        AFSwinxPanel afPanel =
                new AFSwinxPanel(field.getId(), field.getWidgetType(), datePicker, fieldLabel,
                        message);
        // Build layout on that panel
        layoutBuilder.buildLayout(afPanel);
        // Add validations
        super.crateValidators(afPanel, field);
        return afPanel;
    }

    @Override
    public void setData(AFSwinxPanel panel, AFData data) {
        if (panel.getDataHolder() != null && !panel.getDataHolder().isEmpty()) {
            JDatePickerImpl dataPicker = (JDatePickerImpl) panel.getDataHolder().get(0);
            JFormattedTextField textField = dataPicker.getJFormattedTextField();
            String value = data.getValue();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateformat = new SimpleDateFormat(ISO8601DATEFORMAT);
            try {
                Date date = dateformat.parse(value);
                calendar.setTime(date);
                textField.setValue(calendar);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object getData(AFSwinxPanel panel) {
        if (panel.getDataHolder() != null && !panel.getDataHolder().isEmpty()) {
            JDatePickerImpl dataPicker = (JDatePickerImpl) panel.getDataHolder().get(0);
            JFormattedTextField textField = dataPicker.getJFormattedTextField();
            GregorianCalendar calendar = (GregorianCalendar) textField.getValue();
            Date date = calendar.getTime();
            DateFormat df = new SimpleDateFormat(ISO8601DATEFORMAT);
            String nowAsISO = df.format(date);
            return nowAsISO;
        }
        return null;
    }

    @Override
    public Object getPlainData(AFSwinxPanel panel) {
        if (panel.getDataHolder() != null && !panel.getDataHolder().isEmpty()) {
            JDatePickerImpl dataPicker = (JDatePickerImpl) panel.getDataHolder().get(0);
            JFormattedTextField textField = dataPicker.getJFormattedTextField();
            return textField.getText();
        }
        return null;
    }

}
