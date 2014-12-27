package com.tomscz.afswinx.swing.component.model;

import javax.swing.table.DefaultTableModel;

public class UnEditableTableModel extends DefaultTableModel {

    private static final long serialVersionUID = 1L;

    public UnEditableTableModel(Object[][] data, Object[] columnNames) {
        super.setDataVector(data, columnNames);
    }
    
    @Override
    public boolean isCellEditable(int row, int column){  
        return false;  
    }

}
