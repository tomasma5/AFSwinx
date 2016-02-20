package cz.cvut.fel.matyapav.afandroid.builders.widgets;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.cvut.fel.matyapav.afandroid.R;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;

/**
 * Created by Pavel on 14.02.2016.
 */
public class DateFieldBuilder implements BasicBuilder{


    private String dateFormat;

    public DateFieldBuilder(){
        this.dateFormat = "dd.MM.yyyy"; //Default date format
    }

    public DateFieldBuilder(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public View buildFieldView(final Activity activity) {
        LinearLayout dateLayout = new LinearLayout(activity);
        dateLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        dateLayout.setOrientation(LinearLayout.HORIZONTAL);
        final EditText dateText = new EditText(activity);
        dateText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        dateText.setHint(dateFormat);
        dateText.setFocusable(false);
        dateText.setClickable(true);

        //TODO umoznit clear
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat, Locale.getDefault());
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog fromDatePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        dateText.setText(dateFormatter.format(newDate.getTime()));
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                fromDatePickerDialog.show();
            }
        });
        //dateLayout.addView(dateText);
        return dateText;
    }

    @Override
    public void setData(AFField field, Object value) {
        EditText dateText = (EditText) field.getFieldView();
        SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        SimpleDateFormat outputFormatter = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date date = inputFormatter.parse(String.valueOf(value));
            dateText.setText(outputFormatter.format(date));

        } catch (ParseException e) {
            System.err.println("DATE PARSING FAILED");
            e.printStackTrace();
        }
    }

    @Override
    public Object getData(AFField field) {
        EditText dateText = (EditText) field.getFieldView();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat serverFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        try {
            Date date = formatter.parse(dateText.getText().toString());
            return serverFormatter.format(date);
        } catch (ParseException e) {
            System.err.println("DATE PARSING FAILED");
            e.printStackTrace();
        }
        return null;
    }
}
