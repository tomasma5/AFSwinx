package com.codingcrayons.aspectfaces.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.tomscz.afswinx.layout.definitions.LabelPossition;
import com.tomscz.afswinx.layout.definitions.LayouDefinitions;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface UILayout {
    public LayouDefinitions layout() default LayouDefinitions.FLOWLAYOUT; 
    public LabelPossition labelPossition() default LabelPossition.LEFT;
}
