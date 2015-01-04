package com.tomscz.afswinx.swing.component;

import javax.swing.JCheckBox;

/**
 * This is AFSwinx implementation of check box component. It hold it own data object whose represent
 * actual data.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 * @param <E> object which represent data
 */
public class SwinxAFCheckBox<E> extends JCheckBox {

    private static final long serialVersionUID = 1L;

    public SwinxAFCheckBox(E dataHolder) {
        this.dataHolder = dataHolder;
    }

    private E dataHolder;

    public E getDataHolder() {
        return dataHolder;
    }

    public void setDataHolder(E dataHolder) {
        this.dataHolder = dataHolder;
    }

}
