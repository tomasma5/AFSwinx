package com.codingcrayons.aspectfaces.annotation.descriptors;

import java.util.ArrayList;
import java.util.List;

import com.codingcrayons.aspectfaces.annotation.AnnotationProvider;
import com.codingcrayons.aspectfaces.annotation.registration.AnnotationDescriptor;
import com.codingcrayons.aspectfaces.annotation.registration.pointCut.VariableJoinPoint;
import com.codingcrayons.aspectfaces.annotation.registration.pointCut.properties.Variable;
import com.codingcrayons.aspectfaces.annotations.UILayout;
import com.tomscz.afrest.layout.definitions.LabelPosition;
import com.tomscz.afrest.layout.definitions.LayouDefinitions;
import com.tomscz.afrest.layout.definitions.LayoutOrientation;

/**
 * This descriptor parse annotation {@link UILayout}.
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public class UILayoutDescriptor implements AnnotationDescriptor, VariableJoinPoint {

    //Annotation key which holds labelPossition value
    public static final String LABEL_POSSTION_AF_VARIABLE = "labelPossition";
    //Annotation key which holds layout value
    public static final String LAYOUT_AF_VARIABLE = "layout";    
    //Annotation key which holds layout orienatation
    public static final String LAYOUT_ORIENTATION_VARIABLE = "layoutOrientation";
    
    @Override
    public List<Variable> getVariables(AnnotationProvider annotationProvider) {
        List<Variable> variables = new ArrayList<Variable>();
        LayouDefinitions layout =(LayouDefinitions) annotationProvider.getValue(LAYOUT_AF_VARIABLE);
        LabelPosition labelPosstion = (LabelPosition) annotationProvider.getValue(LABEL_POSSTION_AF_VARIABLE);
        LayoutOrientation layoutOrientation = (LayoutOrientation) annotationProvider.getValue(LAYOUT_ORIENTATION_VARIABLE);
        variables.add(new Variable(LAYOUT_AF_VARIABLE, layout));
        variables.add(new Variable(LABEL_POSSTION_AF_VARIABLE, labelPosstion));
        variables.add(new Variable(LAYOUT_ORIENTATION_VARIABLE, layoutOrientation));
        return variables;
    }

    @Override
    public String getAnnotationName() {
        return "com.codingcrayons.aspectfaces.annotations.UILayout";
    }

}
