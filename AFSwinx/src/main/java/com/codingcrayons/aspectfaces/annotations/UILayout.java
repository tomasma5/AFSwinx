package com.codingcrayons.aspectfaces.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.tomscz.afswinx.layout.definitions.LabelPosition;
import com.tomscz.afswinx.layout.definitions.LayouDefinitions;

/**
 * This annotation provide information about {@link LayouDefinitions} and {@link LabelPosition}
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface UILayout {
    public LayouDefinitions layout() default LayouDefinitions.FLOWLAYOUT; 
    public LabelPosition labelPossition() default LabelPosition.LEFT;
}
