package com.codingcrayons.aspectfaces.annotation.descriptors;

import java.util.ArrayList;
import java.util.List;

import com.codingcrayons.aspectfaces.annotation.AnnotationProvider;
import com.codingcrayons.aspectfaces.annotation.registration.AnnotationDescriptor;
import com.codingcrayons.aspectfaces.annotation.registration.pointCut.VariableJoinPoint;
import com.codingcrayons.aspectfaces.annotation.registration.pointCut.properties.Variable;
import com.tomscz.afswinx.layout.definitions.LabelPossition;
import com.tomscz.afswinx.layout.definitions.LayouDefinitions;

public class UILayoutDescriptor implements AnnotationDescriptor, VariableJoinPoint {

    public static final String LABEL_POSSTION_AF_VARIABLE = "labelPossition";
    public static final String LAYOUT_AF_VARIABLE = "layout";
    
    @Override
    public List<Variable> getVariables(AnnotationProvider annotationProvider) {
        List<Variable> variables = new ArrayList<Variable>();
        LayouDefinitions layout =(LayouDefinitions) annotationProvider.getValue(LAYOUT_AF_VARIABLE);
        LabelPossition labelPosstion = (LabelPossition) annotationProvider.getValue(LABEL_POSSTION_AF_VARIABLE);
        variables.add(new Variable(LAYOUT_AF_VARIABLE, layout));
        variables.add(new Variable(LABEL_POSSTION_AF_VARIABLE, labelPosstion));
        return variables;
    }

    @Override
    public String getAnnotationName() {
        return "com.codingcrayons.aspectfaces.annotations.UILayout";
    }

}
