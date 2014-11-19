package com.tomscz.afswinx.swing.component;

public class JCheckBox<E> extends javax.swing.JCheckBox{

    private static final long serialVersionUID = 1L;

    public JCheckBox(){
        
    }
    
    public JCheckBox(E dataHolder){
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
