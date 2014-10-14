package com.tomscz.afswinx.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.EnumSet;

/**
 * This class provide utilize methods.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public final class Utils {

    /**
     * This method return {@link Enum} which name values is equals to String, which is given as
     * parameter. If arguments are null or string value is empty or no match is found, then
     * {@link IllegalArgumentException} is thrown.
     * 
     * @param enumClass {@link Enum} class in which will be found enumValue.
     * @param enumValue value of field which will be compare in field in enumClass
     * @return Enum, which has name equals enumValue in enumClass.
     * @throws IllegalArgumentException if no match is found or arguments are null or empty.
     */
    public static <E extends Enum<E>> Enum<E> getEnumFromString(Class<E> enumClass, String enumValue)
            throws IllegalArgumentException {
        if (enumClass == null || enumValue == null || enumValue.isEmpty()) {
            throw new IllegalArgumentException(
                    "Class shouldn't be null and value to compare shouldn't be null or empty.");
        }
        for (E enumField : EnumSet.allOf(enumClass)) {
            if (enumField.name().equalsIgnoreCase(enumValue)) {
                return enumField;
            }
        }
        throw new IllegalArgumentException("Noone of string value from enum class "
                + enumClass.getName() + " doestn match with " + enumValue);
    }

    /**
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

    /**
     * This method read all from input stream and return {@link StringBuilder} which holds all line
     * form input stream
     * 
     * @param inputStream input stream which holds data to read
     * @return StringBuilder which contains all data from input stream
     * @throws IOException if during creation phase or read is thrown error
     */
    public static StringBuilder readInputSteam(InputStream inputStream) throws IOException {
        StringBuilder responseStrBuilder = new StringBuilder();
        BufferedReader streamReader;
        streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String line;
        // Read data
        while ((line = streamReader.readLine()) != null) {
            responseStrBuilder.append(line);
        }
        return responseStrBuilder;
    }

}
