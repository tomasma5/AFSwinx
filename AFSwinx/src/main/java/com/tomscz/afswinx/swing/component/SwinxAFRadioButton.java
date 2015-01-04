package com.tomscz.afswinx.swing.component;

import javax.swing.JRadioButton;

/**
 * This is radio button implementation in AFSwinx. It has his own object to store data.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 * @param <E> object which hold data.
 */
public class SwinxAFRadioButton<E> extends JRadioButton {

    private static final long serialVersionUID = 1L;

    public SwinxAFRadioButton(E dataHolder) {
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
