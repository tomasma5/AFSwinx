package com.tomscz.afswinx.component.widget.builder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JFormattedTextField;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.component.widget.builder.abstraction.BaseWidgetBuilder;
import com.tomscz.afswinx.rest.rebuild.holder.AFData;
import com.tomscz.afswinx.swing.component.SwinxAFDatePicker;

/**
 * This is builder which can build data picker component to pick data.
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public class DateBuilder extends BaseWidgetBuilder {

    // Default date formatter it used ISO8601
    final static String ISO8601DATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public DateBuilder() {
        widgetType = SupportedWidgets.CALENDAR;
    }

    @Override
    public AFSwinxPanel buildComponent(AFFieldInfo field) throws IllegalArgumentException,
            AFSwinxBuildException {
        super.buildBase(field);
        // Add input calendar
        SwinxAFDatePicker swingDatePicker = new SwinxAFDatePicker();
        customizeComponent(swingDatePicker, field);
        layoutBuilder.addComponent(swingDatePicker.getVisibleComponent());
        coreComponent = swingDatePicker;
        // Create panel which holds all necessary informations
        AFSwinxPanel afPanel =
                new AFSwinxPanel(field.getId(), field.getWidgetType(), swingDatePicker, fieldLabel,
                        message);
        afPanel.setEnabled(false);
        // Build layout on that panel
        layoutBuilder.buildLayout(afPanel);
        // Add validations
        super.crateValidators(afPanel, field);
        return afPanel;
    }

    @Override
    public void setData(AFSwinxPanel panel, AFData data) {
        if (panel.getDataHolder() != null && !panel.getDataHolder().isEmpty()) {
            SwinxAFDatePicker swingDatepicker = (SwinxAFDatePicker) panel.getDataHolder().get(0);
            String value = data.getValue();
            Calendar calendar = Calendar.getInstance();
            JFormattedTextField textField = swingDatepicker.getVisibleTextField();
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
            SwinxAFDatePicker dataPicker = (SwinxAFDatePicker) panel.getDataHolder().get(0);
            JFormattedTextField textField = dataPicker.getVisibleTextField();
            GregorianCalendar calendar = (GregorianCalendar) textField.getValue();
            if (calendar != null) {
                Date date = calendar.getTime();
                DateFormat df = new SimpleDateFormat(ISO8601DATEFORMAT);
                String nowAsISO = df.format(date);
                return nowAsISO;
            }
        }
        return null;
    }

    @Override
    public Object getPlainData(AFSwinxPanel panel) {
        if (panel.getDataHolder() != null && !panel.getDataHolder().isEmpty()) {
            SwinxAFDatePicker dataPicker = (SwinxAFDatePicker) panel.getDataHolder().get(0);
            JFormattedTextField textField = dataPicker.getVisibleTextField();
            return textField.getText();
        }
        return null;
    }

}
