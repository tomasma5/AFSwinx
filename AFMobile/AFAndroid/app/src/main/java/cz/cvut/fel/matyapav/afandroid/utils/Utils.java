package cz.cvut.fel.matyapav.afandroid.utils;

import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.EnumSet;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import cz.cvut.fel.matyapav.afandroid.components.AFComponent;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.ValidationRule;
import cz.cvut.fel.matyapav.afandroid.components.validators.AFValidator;
import cz.cvut.fel.matyapav.afandroid.components.validators.MaxCharsValidator;
import cz.cvut.fel.matyapav.afandroid.components.validators.MaxValueValidator;
import cz.cvut.fel.matyapav.afandroid.components.validators.MinValueValidator;
import cz.cvut.fel.matyapav.afandroid.components.validators.RequiredValidator;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedValidations;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedWidgets;
import cz.cvut.fel.matyapav.afandroid.rest.AFSwinxConnection;

/**
 * Created by Pavel on 25.12.2015.
 */
public class Utils {

    public static String generateKey(String currentKey, String fieldName){
        if(currentKey.isEmpty()){
            return fieldName;
        }
        return currentKey+"."+fieldName;
    }

    /** MARTINOVA METODA
     * This method create {@link Document} from input file. This document can be parsed by DOM or
     * SAX.
     *
     * @param is from which will be created document to parse
     * @return Document which will be parsed by DOM or SAX
     * @throws IllegalArgumentException If exception occurs during open file or parsing file then
     *         {@link IllegalArgumentException} is thrown, because input file given in parameter is
     *         illegal
     */
    public static Document buildDocumentFromFile(InputStream is) throws IllegalArgumentException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            is.close();
            return doc;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new IllegalArgumentException("This file cannot be parsed." + e.getMessage());
        }
    }

    /** MARTINOVA METODA
     * This method parse input string and replaced all #{value} values in hash and bracket by value
     * given in parameters. If value is not found then null is inserted. <br
     * / Example:
     * <ul>
     * <li>inputs : expressionToEvaluate/home/#{user}, parameters: user:martin; result /home/martin</li>
     * <li>inputs : expressionToEvaluate/home/#{user}/#{subdir}, parameters: user:martin,
     * subdir:backup; result /home/martin/backup</li>
     * <li>inputs : expressionToEvaluate/home/#{user}/#{subDir}, parameters: user:martin,
     * subdir:backup; result /home/martin/null</li>
     * <ul>
     * Please note that this parser can't handle with unpairs {} in input parameters
     *
     * @param expressionToEvaluate this is expression which will be processed and in which will all
     *        value in #{} replaced by value from has map given in second parameter
     * @param parameters which holds value which will be inserted to input string called
     *        expressionToEvaluate
     * @return string which has replaced all #{} values from hash map given as parameter. If value
     *         is not found in hash map then null is inserted
     */
    public static String evaluateElExpression(String expressionToEvaluate,
                                              HashMap<String, String> parameters) {
        // To chaining string use string builder
        StringBuilder replacedValue = new StringBuilder();
        // Split expression by #{ it gives you strings between and after value which should be
        // replaced
        String[] values = expressionToEvaluate.split("#\\{");
        boolean firstCycle = true;
        for (String value : values) {
            // in first cycle add everything because split is done by #{ which means that before
            // first char sequence #{ is plain text
            if (firstCycle) {
                replacedValue.append(value.substring(0, value.length()));
                firstCycle = false;
                continue;
            }
            // This are values behind string which will be replaced. in first position is string to
            // replaced
            String[] valuesBehind = value.split("\\}");
            // Find replaced value and append it
            String elValue = parameters.get(valuesBehind[0].substring(0, valuesBehind[0].length()));
            replacedValue.append(elValue);
            // If some values left - this means that there is more } brackets ex: #{value}/a}/a}
            // then append them too
            for (int i = 1; i < valuesBehind.length; i++) {
                replacedValue.append(valuesBehind[i]);
                char firstChar = valuesBehind[i].charAt(0);
                //Because split was done by } then it should not be there, then add it if left brackets
                if (firstChar == '{') {
                    replacedValue.append("}");
                }

            }
        }
        return replacedValue.toString();
    }

    /** MARTINOVA METODA
     * This method convert String to Integer if value is null then {@link IllegalArgumentException}
     * is thrown.
     *
     * @param value to convert to Integer
     * @return integer value of String value given in parameter
     * @throws IllegalArgumentException if value is null or conversion is unsuccessful
     */
    public static Integer convertStringToInteger(String value) throws IllegalArgumentException {
        Integer intValue = null;
        if (value == null) {
            throw new IllegalArgumentException();
        }
        intValue = Integer.parseInt(value);
        return intValue;
    }

    /** MARTINOVA METODA
     * This method return {@link Enum} which name values is equals to String, which is given as
     * parameter. If arguments are null or string value is empty or no match is found, then
     * {@link IllegalArgumentException} is thrown.
     *
     * @param enumClass {@link Enum} class in which will be found enumValue.
     * @param enumValue value of field which will be compare in field in enumClass.
     * @param compareByValue flag which determine if comparison is done against string value or enum
     *        value. <br />
     *        <ul>
     *        <li><b>True</b> comparison is done against string value of enum.</li>
     *        <li><b>False</b> comparison is done against enum real value </b></li>
     *        </ul>
     * @return Enum, which has name equals enumValue in enumClass.
     * @throws IllegalArgumentException if no match is found or arguments are null or empty.
     */
    public static <E extends Enum<E>> Enum<E> getEnumFromString(Class<E> enumClass,
                                                                String enumValue, boolean compareByValue) throws IllegalArgumentException {
        if (enumClass == null || enumValue == null || enumValue.isEmpty()) {
            throw new IllegalArgumentException(
                    "Class shouldn't be null and value to compare shouldn't be null or empty.");
        }
        for (E enumField : EnumSet.allOf(enumClass)) {
            if (compareByValue) {
                if (enumField.toString().equalsIgnoreCase(enumValue)) {
                    return enumField;
                }
            } else {
                if (enumField.name().equalsIgnoreCase(enumValue)) {
                    return enumField;
                }
            }
        }
        return null;
    }

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


    public static AFValidator getFieldValidator(ValidationRule rule){
        if (rule.getValidationType().equals(SupportedValidations.REQUIRED.getValidationType()) && Boolean.valueOf(rule.getValue())) {
            return new RequiredValidator();
        }
        if (rule.getValidationType().equals(SupportedValidations.MAXLENGTH.getValidationType())) {
            return new MaxCharsValidator();
        }
        if (rule.getValidationType().equals(SupportedValidations.MAX.getValidationType())){
            return new MaxValueValidator();
        }
        if (rule.getValidationType().equals(SupportedValidations.MIN.getValidationType())){
            return new MinValueValidator();
        }

        System.err.println("VALIDATOR FOR "+rule.getValidationType()+" NOT FOUND");
        return null;
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
        return connection.getProtocol()+"://"+connection.getAddress()+":"+connection.getPort()+connection.getParameters();
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
