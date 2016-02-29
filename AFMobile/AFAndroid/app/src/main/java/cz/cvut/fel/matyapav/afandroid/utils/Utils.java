package cz.cvut.fel.matyapav.afandroid.utils;

import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.widget.TextView;

import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import cz.cvut.fel.matyapav.afandroid.components.types.AFComponent;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedWidgets;

/**
 * Created by Pavel on 25.12.2015.
 */
public class Utils {


    /// moje metody

    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    public static boolean isFieldWritable(SupportedWidgets widgetType){
        return widgetType.equals(SupportedWidgets.TEXTFIELD) || widgetType.equals(SupportedWidgets.NUMBERFIELD)
                || widgetType.equals(SupportedWidgets.NUMBERDOUBLEFIELD) || widgetType.equals(SupportedWidgets.PASSWORD);
    }

    public static boolean isFieldNumberField(AFField field){
        return field.getFieldInfo().getWidgetType().equals(SupportedWidgets.NUMBERFIELD)
                || field.getFieldInfo().getWidgetType().equals(SupportedWidgets.NUMBERDOUBLEFIELD);
    }

    public static String getConnectionEndPoint(AFSwinxConnection connection){
        StringBuilder endPointBuilder = new StringBuilder();
        if(connection.getProtocol() != null && !connection.getProtocol().isEmpty()){
            endPointBuilder.append(connection.getProtocol());
            endPointBuilder.append("://");
        }
        if(connection.getAddress() != null && !connection.getAddress().isEmpty()){
            endPointBuilder.append(connection.getAddress());
        }
        if(connection.getPort() != 0){
            endPointBuilder.append(":");
            endPointBuilder.append(connection.getPort());
        }
        if(connection.getParameters() != null && !connection.getParameters().isEmpty()){
            endPointBuilder.append(connection.getParameters());
        }
        return endPointBuilder.toString();
    }

    public static boolean shouldBeInvisible(String column, AFComponent component) {
        for(AFField field: component.getFields()){
            if(field.getId().equals(column)){
                return !field.getFieldInfo().isVisible();
            }
        }
        return true;
    }

    public static void setCellParams(TextView cell, int gravity, int paddingLeft, int paddingRight,
                               int paddingTop, int paddingBottom, int borderWidth, int borderColor){
        //create border
        ShapeDrawable rect = new ShapeDrawable(new RectShape());
        rect.getPaint().setStyle(Paint.Style.STROKE);
        rect.getPaint().setColor(borderColor);
        rect.getPaint().setStrokeWidth(borderWidth);

        cell.setGravity(gravity);
        cell.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        cell.setBackground(rect);
    }


}
