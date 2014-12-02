package com.tomscz.afswinx.swing.component;

import javax.swing.JCheckBox;

public class SwinxAFCheckBox<E> extends JCheckBox{

    private static final long serialVersionUID = 1L;
    
    public SwinxAFCheckBox(E dataHolder){
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
