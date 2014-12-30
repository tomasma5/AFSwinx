package com.tomscz.afrest.commons;

import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.HashMap;

public class AFRestUtils {

    /**
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
        throw new IllegalArgumentException("No one of string value from enum class "
                + enumClass.getName() + " doestn match with " + enumValue);
    }
    
    public static HashMap<String, String> getEnumDataInClass(String fullClassName, String fieldName) {
        try {
            Object o = Class.forName(fullClassName).newInstance();
            @SuppressWarnings("rawtypes")
            Class clazz = o.getClass();
            for(Field field:clazz.getDeclaredFields()){
                if(field != null){
                    if(field.getName().equals(fieldName)){
                        return getDataInEnumClass(field.getType().getName());
                    }
                }
            }
        }
        catch(InstantiationException | ClassNotFoundException | IllegalAccessException e){
          //DO nothing, just return empty map in the end
        }
        return new HashMap<String, String>();
    }
    
    @SuppressWarnings("unchecked")
    public static HashMap<String, String> getDataInEnumClass(String enumClassName) {
        try {
            @SuppressWarnings("rawtypes")
            Class clazz = Class.forName(enumClassName);
            // Object enumObj = Class.forName(fullClassName).newInstance();
            if (clazz.isEnum()) {
                return getDataFromEnum(clazz);
            }

        } catch (ClassNotFoundException e) {
            //DO nothing, just return empty map in the end
        }
        return new HashMap<String, String>();
    }

    /**
     * This method return all enums in class.
     * @param enumClass from which will be extracted enums.
     * @return HashMap of value in enum. Key is enum name and concrete value is Enum.toString() result.
     */
    private static <E extends Enum<E>> HashMap<String, String> getDataFromEnum(Class<E> enumClass) {
        HashMap<String, String> enumValues = new HashMap<String, String>();
        for (E enumField : EnumSet.allOf(enumClass)) {
            String key = enumField.name();
            String value = enumField.toString();
            enumValues.put(key, value);
        }
        return enumValues;
    }

}
