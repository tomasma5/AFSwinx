package com.tomscz.afrest.rest.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * This class is main definition class which will be used to store definition.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AFMetaModelPack implements Serializable {

    private static final long serialVersionUID = 1L;
    private AFClassInfo classInfo;

    public AFMetaModelPack() {

    }

    public AFMetaModelPack(AFClassInfo classInfo) {
        this.setClassInfo(classInfo);
    }

    public AFClassInfo getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(AFClassInfo classInfo) {
        this.classInfo = classInfo;
    }

    /**
     * This method set options to field.
     * 
     * @param options options which will be set to field. Key will be key and displayed value to
     *        user will be value, which is stored under concrete key.
     * @param fieldId id of field. It should has this notation: person.country.name, which mean that
     *        on variable person in root class and in their inner variable country and in their
     *        inner variable name will be set options.
     */
    public void setOptionsToFields(HashMap<String, String> options, String fieldId) {
        if (classInfo != null) {
            classInfo.setOptionsToField(options, fieldId);
        }
    }

    /**
     * This method set options to field.
     * 
     * @param options options which will be set to field. Key and value which will be displayed to
     *        user will be the same.
     * @param fieldId id of field. It should has this notation: person.country.name, which mean that
     *        on variable person in root class and in their inner variable country and in their
     *        inner variable name will be set options.
     */
    public void setOptionsToFields(List<String> options, String fieldId) {
        if (classInfo != null) {
            classInfo.setOptionsToFields(options, fieldId);
        }
    }

}
