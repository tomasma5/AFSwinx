package cz.cvut.fel.matyapav.afandroid.utils;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

import com.tomscz.afswinx.rest.connection.AFSwinxConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.types.AFComponent;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedWidgets;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class Utils {

    /**
     * Converts input stream to string. Basically it reads whole input stream and appends read string
     * to a StringBuilder.
     *
     * @param inputStream given input stream
     * @return converted input stream in string format
     * @throws IOException thrown if input stream cannot be read
     */
    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    /**
     * Checks if field of given {@link SupportedWidgets} widget type is writable = if user can
     * write into it some value.
     *
     * @param widgetType given widget type
     * @return true if field is writable, false otherwise
     */
    public static boolean isFieldWritable(SupportedWidgets widgetType) {
        return widgetType.equals(SupportedWidgets.TEXTFIELD) || widgetType.equals(SupportedWidgets.NUMBERFIELD)
                || widgetType.equals(SupportedWidgets.NUMBERDOUBLEFIELD) || widgetType.equals(SupportedWidgets.PASSWORD);
    }

    /**
     * Checks if field is number type = only number values can be written into this field.
     *
     * @param field given field
     * @return true if field can contain only numbers, false otherwise
     */
    public static boolean isFieldNumberField(AFField field) {
        return field.getFieldInfo().getWidgetType().equals(SupportedWidgets.NUMBERFIELD)
                || field.getFieldInfo().getWidgetType().equals(SupportedWidgets.NUMBERDOUBLEFIELD);
    }

    /**
     * Checks if field can contain only integer values.
     *
     * @param field given field
     * @return true if field can contains only integer values, false otherwise
     */
    public static boolean isFieldIntegerField(AFField field) {
        return field.getFieldInfo().getWidgetType().equals(SupportedWidgets.NUMBERFIELD);
    }

    /**
     * Checks if field can contain numbers with floating point as well = float, double ...
     *
     * @param field given field
     * @return true if field can contain numbers with floating point, false otherwise
     */
    public static boolean isFieldDoubleField(AFField field) {
        return field.getFieldInfo().getWidgetType().equals(SupportedWidgets.NUMBERDOUBLEFIELD);
    }

    /**
     * Gets connection endpoint. Basically builds url of endpoint from given connection specification.
     *
     * @param connection given connection specification
     * @return built endpoint url
     */
    public static String getConnectionEndPoint(AFSwinxConnection connection) {
        StringBuilder endPointBuilder = new StringBuilder();
        if (connection.getProtocol() != null && !connection.getProtocol().isEmpty()) {
            endPointBuilder.append(connection.getProtocol());
            endPointBuilder.append("://");
        }
        if (connection.getAddress() != null && !connection.getAddress().isEmpty()) {
            endPointBuilder.append(connection.getAddress());
        }
        if (connection.getPort() != 0) {
            endPointBuilder.append(":");
            endPointBuilder.append(connection.getPort());
        }
        if (connection.getParameters() != null && !connection.getParameters().isEmpty()) {
            endPointBuilder.append(connection.getParameters());
        }
        return endPointBuilder.toString();
    }

    /**
     * Checks if field/column in component should be visible or invisible.
     *
     * @param column    id of field = column
     * @param component component
     * @return true = invisible, false = visible
     */
    public static boolean shouldBeInvisible(String column, AFComponent component) {
        for (AFField field : component.getFields()) {
            if (field.getId().equals(column)) {
                return !field.getFieldInfo().isVisible();
            }
        }
        return true;
    }

    /**
     * Sets layout parameters to a {@link TextView}
     *
     * @param cell given text view
     * @param gravity gravity of text view
     * @param paddingLeft left padding
     * @param paddingRight right padding
     * @param paddingTop top padding
     * @param paddingBottom bottom padding
     * @param borderWidth width of the border
     * @param borderColor color of the border
     */
    public static void setCellParams(TextView cell, int gravity, int paddingLeft, int paddingRight,
                                     int paddingTop, int paddingBottom, int borderWidth, int borderColor) {
        //create border
        ShapeDrawable rect = new ShapeDrawable(new RectShape());
        rect.getPaint().setStyle(Paint.Style.STROKE);
        rect.getPaint().setColor(borderColor);
        rect.getPaint().setStrokeWidth(borderWidth);

        cell.setGravity(gravity);
        cell.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        cell.setBackground(rect);
    }

    /**
     * Parses date from string using two possible formats. Uses default locale
     * Format 1: "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
     * Format 2: "dd.MM.yyyy"
     *
     * @param date date in some string format
     * @return If both formats cannot parse the date, null is returned. Otherwice parsed {@link Date} object is returned.
     */
    public static Date parseDate(String date) {
        String[] formats = {"yyyy-MM-dd'T'HH:mm:ss.SSSZ", "dd.MM.yyyy"};
        if (date != null) {
            for (String format : formats) {
                SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
                try {
                    return formatter.parse(date);
                } catch (ParseException e) {
                    System.err.println("Cannot parse date " + date + " using format " + format);
                }
            }
        }
        return null;
    }

    /**
     * Determine if device has screen diagonal size bigger than 6.5 inches, which should be size
     * for tablet. This uses window manager system service and if this service is not reachable
     * for some reason, this method returns false.
     *
     * @param context android context
     * @return true if diplay has 6.5 inches or more, false otherwise
     */
    public static boolean deviceHasTabletSize(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(metrics);
            float yInches = metrics.heightPixels / metrics.ydpi;
            float xInches = metrics.widthPixels / metrics.xdpi;
            double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
            return diagonalInches >= 6.5;
        }
        return false;
    }


}
