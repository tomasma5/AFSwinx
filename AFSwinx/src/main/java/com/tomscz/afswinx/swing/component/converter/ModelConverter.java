package com.tomscz.afswinx.swing.component.converter;

import java.util.ResourceBundle;

/**
 * This interface specify converters.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 * @param <T> Source type
 * @param <E> Destination type
 */
public interface ModelConverter<T, E> {

    /**
     * This method convert source element to destination element. Localization is also used.
     * 
     * @param source element which will be convert to E.
     * @param localization which will be used.
     * @return It returns element which were created based on source element.
     */
    public E convert(T source, ResourceBundle localization);
    
}
