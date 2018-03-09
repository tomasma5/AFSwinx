package cz.cvut.fel.matyapav.afandroid.builders.widgets;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.cvut.fel.matyapav.afandroid.builders.skins.Skin;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.FieldInfo;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
 */
public class DateWidgetBuilder extends BasicBuilder {

    private String dateFormat;

    DateWidgetBuilder(Context context, Skin skin, FieldInfo properties) {
        super(context, skin, properties);
        this.dateFormat = "dd.MM.yyyy"; //Default date format
    }

    @Override
    public View buildFieldView(final Context context) {
        LinearLayout dateLayout = new LinearLayout(context);
        dateLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        dateLayout.setOrientation(LinearLayout.HORIZONTAL);
        final EditText dateText = new EditText(context);
        dateText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        dateText.setTextColor(getSkin().getFieldColor());
        dateText.setTypeface(getSkin().getFieldFont());
        dateText.setHint(dateFormat);
        dateText.setFocusable(false);
        dateText.setClickable(true);

        //TODO umoznit clear
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat, Locale.getDefault());
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog fromDatePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
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

        if(getProperties().isReadOnly()){
            dateText.setInputType(InputType.TYPE_NULL);
            dateText.setTextColor(Color.LTGRAY);
        }
        return dateText;
    }

    @Override
    public void setData(AFField field, Object value) {
        EditText dateText = (EditText) field.getFieldView();
        SimpleDateFormat outputFormatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        Date date = Utils.parseDate(String.valueOf(value));
        if(date != null) {
            dateText.setText(outputFormatter.format(date));
            field.setActualData(outputFormatter.format(date));
        } else {
            System.err.println("Date " + value + " parsing failed.");
        }
    }

    @Override
    public Object getData(AFField field) {
        EditText dateText = (EditText) field.getFieldView();
        SimpleDateFormat serverFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault());
        Date date = Utils.parseDate(dateText.getText().toString());
        if(date != null) {
            return serverFormatter.format(date);
        }
        return null;
    }


}
