package com.tomscz.afrest.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tomscz.afrest.layout.TopLevelLayout;

/**
 * This class holds information about root and child classes. Each class has more classes and
 * fields. It also stored name and layout which should be used.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AFClassInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private TopLevelLayout layout;
    private List<AFFieldInfo> fieldInfo;
    private List<AFClassInfo> innerClasses;

    public void addFieldInfo(AFFieldInfo fieldInfoToAdd) {
        if (this.fieldInfo == null) {
            this.fieldInfo = new ArrayList<AFFieldInfo>();
        }
        this.fieldInfo.add(fieldInfoToAdd);

    }

    public void addInnerClass(AFClassInfo classInfoToAdd) {
        if (innerClasses == null) {
            innerClasses = new ArrayList<AFClassInfo>();
        }
        innerClasses.add(classInfoToAdd);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AFFieldInfo> getFieldInfo() {
        return fieldInfo;
    }

    public void setFieldInfo(List<AFFieldInfo> fieldInfo) {
        this.fieldInfo = fieldInfo;
    }

    public TopLevelLayout getLayout() {
        return layout;
    }

    public void setLayout(TopLevelLayout layout) {
        this.layout = layout;
    }

    /**
     * This method fill be set options to fields. Options are converted to {@link AFOptions}.
     * 
     * @param options based on which will be create {@link AFOptions}.
     * @param fieldId id of field. It should has this notation: person.country.name, which mean that
     *        on variable person in root class and in their inner variable country and in their
     *        inner variable name will be set options.
     */
    public void assignOptionsToField(HashMap<String, String> options, String fieldId) {
        ArrayList<AFOptions> afOptions = new ArrayList<AFOptions>();
        for (String key : options.keySet()) {
            afOptions.add(new AFOptions(key, options.get(key)));
        }
        assignOptionsToField(afOptions, fieldId);
    }

    /**
     * This method set options to field. This method can find concrete field based on path to this
     * field.
     * 
     * @param options options which will be set to field. These are concrete transformed options.
     *        See {@link AFOptions}.
     * @param fieldId id of field. It should has this notation: person.country.name, which mean that
     *        on variable person in root class and in their inner variable country and in their
     *        inner variable name will be set options.
     */
    private void assignOptionsToField(List<AFOptions> options, String fieldId) {
        String[] path = fieldId.split("\\.");
        String fieldInfoId = "";
        boolean findInClasses = false;
        if (path.length > 1) {
            fieldInfoId = path[0];
            findInClasses = true;
        }
        if (findInClasses) {
            // If there are no inner classes that fieldId might be wrong or inspection for this
            // class was not setup, simply do nothing and return
            if (innerClasses == null || innerClasses.isEmpty()) {
                return;
            }
            for (AFClassInfo currentClass : innerClasses) {
                if (currentClass.name.equals(fieldInfoId)) {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 1; i < path.length; i++) {
                        sb.append(path[i]);
                    }
                    currentClass.assignOptionsToField(options, sb.toString());
                    return;
                }
            }
        } else {
            for (AFFieldInfo fiedlInfo : fieldInfo) {
                if (fiedlInfo != null && fiedlInfo.getId().equals(fieldId)) {
                    fiedlInfo.addOption(options);
                }
            }
        }
    }

    /**
     * This method set options to field.
     * 
     * @param options options which will be set to field. These are concrete transformed options.
     *        See {@link AFOptions}.
     * @param fieldId id of field. It should has this notation: person.country.name, which mean that
     *        on variable person in root class and in their inner variable country and in their
     *        inner variable name will be set options.
     */
    public void assingOptionsToFields(List<String> options, String fieldId) {
        ArrayList<AFOptions> afOtions = new ArrayList<AFOptions>();
        for (String option : options) {
            afOtions.add(new AFOptions(option, option));
        }
        assignOptionsToField(afOtions, fieldId);
    }

    public List<AFClassInfo> getInnerClasses() {
        return innerClasses;
    }

    public void setInnerClasses(List<AFClassInfo> innerClasses) {
        this.innerClasses = innerClasses;
    }

}
