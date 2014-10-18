package com.tomscz.afrest.commons;

import java.util.EnumSet;

public class AFRestUtils {

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

}
