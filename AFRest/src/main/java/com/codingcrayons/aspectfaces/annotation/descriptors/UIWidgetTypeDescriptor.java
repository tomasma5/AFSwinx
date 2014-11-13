package com.codingcrayons.aspectfaces.annotation.descriptors;

import java.util.ArrayList;
import java.util.List;

import com.codingcrayons.aspectfaces.annotation.AnnotationProvider;
import com.codingcrayons.aspectfaces.annotation.registration.AnnotationDescriptor;
import com.codingcrayons.aspectfaces.annotation.registration.pointCut.VariableJoinPoint;
import com.codingcrayons.aspectfaces.annotation.registration.pointCut.properties.Variable;
import com.tomscz.afrest.commons.SupportedProperties;
import com.tomscz.afrest.commons.SupportedWidgets;

public class UIWidgetTypeDescriptor implements AnnotationDescriptor, VariableJoinPoint{
    
    @Override
    public List<Variable> getVariables(AnnotationProvider annotationProvider) {
        List<Variable> variables = new ArrayList<Variable>();
        SupportedWidgets widget = (SupportedWidgets) annotationProvider.getValue(SupportedProperties.WIDGETTYPE.toString());
        variables.add(new Variable(SupportedProperties.WIDGETTYPE.toString(), widget));
        return variables;

    }

    @Override
    public String getAnnotationName() {
        return "com.codingcrayons.aspectfaces.annotations.UIWidgetType";
    }

}
