package cz.cvut.fel.matyapav.afandroid.components.parts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 25.12.2015.
 */
public class ClassDefinition {

    private String className;
    private LayoutProperties layout;
    private List<FieldInfo> fields;
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

    public List<FieldInfo> getFields() {
        return fields;
    }

    public void setFields(List<FieldInfo> fields) {
        this.fields = fields;
    }

    public void addField(FieldInfo field){
        if(fields == null){
            fields = new ArrayList<FieldInfo>();
        }
        fields.add(field);
    }

    public void addInnerClass(ClassDefinition innerClass){
        if(innerClasses == null){
            innerClasses = new ArrayList<ClassDefinition>();
        }
        innerClasses.add(innerClass);
    }

    public List<ClassDefinition> getInnerClasses() {
        return innerClasses;
    }
}
