package com.tomscz.afswinx.swing.component;

public class JRadioButton<E> extends javax.swing.JRadioButton {

    private static final long serialVersionUID = 1L;

    public JRadioButton(E dataHolder) {
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
