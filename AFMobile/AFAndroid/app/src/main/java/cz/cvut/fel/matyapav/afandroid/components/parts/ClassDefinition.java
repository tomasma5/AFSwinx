package cz.cvut.fel.matyapav.afandroid.components.parts;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
 */
public class ClassDefinition {

    private String className;
    private LayoutProperties layout;
    private List<FieldInfo> fieldInfos;
    private List<ClassDefinition> innerClasses;

    public ClassDefinition(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public LayoutProperties getLayout() {
        return layout;
    }

    public void setLayout(LayoutProperties layout) {
        this.layout = layout;
    }

    public List<FieldInfo> getFieldInfos() {
        return fieldInfos;
    }

    public void setFieldInfos(List<FieldInfo> fieldInfos) {
        this.fieldInfos = fieldInfos;
    }

    public void addFieldInfo(FieldInfo field){
        if(fieldInfos == null){
            fieldInfos = new ArrayList<>();
        }
        fieldInfos.add(field);
    }

    public void addInnerClass(ClassDefinition innerClass){
        if(innerClasses == null){
            innerClasses = new ArrayList<>();
        }
        innerClasses.add(innerClass);
    }

    public List<ClassDefinition> getInnerClasses() {
        return innerClasses;
    }
}
