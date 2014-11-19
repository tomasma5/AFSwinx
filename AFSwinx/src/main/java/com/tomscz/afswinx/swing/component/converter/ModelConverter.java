package com.tomscz.afswinx.swing.component.converter;

import java.util.ResourceBundle;

public interface ModelConverter<T, E> {

    public E convert(T source, ResourceBundle localization);
}
