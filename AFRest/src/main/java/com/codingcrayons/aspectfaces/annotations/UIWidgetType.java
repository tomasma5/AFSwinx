package com.codingcrayons.aspectfaces.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.tomscz.afrest.commons.SupportedWidgets;

/**
 * This annotation specify widget type. Use it if you want to propagade concrete widget type.
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */

@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface UIWidgetType {
    
    public SupportedWidgets widgetType();

}
