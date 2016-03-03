package com.tomscz.afswinx.validation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.factory.WidgetBuilderFactory;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.localization.AFSwinxLocaleConstants;
import com.tomscz.afswinx.localization.LocalizationUtils;
import com.tomscz.afswinx.validation.exception.ValidationException;

public class LessThanValidator extends AFBaseValidator {

    private SupportedWidgets widgetType;
    private AFSwinxPanel otherPanel;
    
    // Default date formatter it used ISO8601
    final static String ISO8601DATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	
	public LessThanValidator(SupportedWidgets widgetType, AFSwinxPanel otherPanel) {
		priority = AFValidatorPriority.LESS_THAN_PRIORITY;
		this.widgetType = widgetType;
		this.otherPanel = otherPanel;
	}

	@Override
	public void validate(AFSwinx swinxInstance, AFSwinxPanel parentPanel, Object value) throws ValidationException {
		if (value == null) {
            return;
        }
		//validation for calendar widget type
		if(widgetType.equals(SupportedWidgets.CALENDAR)){
	        Object anotherValue =
	                WidgetBuilderFactory.getInstance()
	                        .createWidgetBuilder(otherPanel.getWidgetType())
	                        .getData(otherPanel);
	        DateFormat df = new SimpleDateFormat(ISO8601DATEFORMAT);
	        try {
				Date anotherDate = df.parse(anotherValue.toString());
				Date date = df.parse(value.toString());
				if (date.after(anotherDate)) {
		            throw new ValidationException(LocalizationUtils.getTextValueFromLocalOrExtendBundle(
		                    AFSwinxLocaleConstants.VALIDATION_LESS_THAN, localization) + " " + otherPanel.getLabelHolder().getText());
		        }
			} catch (ParseException e) {
				System.err.println(e.getLocalizedMessage()); //cannot parse date
			}
		    
		}
	}

}
