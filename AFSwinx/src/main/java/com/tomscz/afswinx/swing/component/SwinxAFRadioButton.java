package com.tomscz.afswinx.swing.component;

import javax.swing.JRadioButton;

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
