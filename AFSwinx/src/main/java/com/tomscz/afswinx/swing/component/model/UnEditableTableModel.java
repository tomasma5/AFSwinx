package com.tomscz.afswinx.swing.component.model;

import javax.swing.table.DefaultTableModel;

import com.tomscz.afswinx.component.AFSwinxTable;

/**
 * This is model, which is used in {@link AFSwinxTable}. It is uneditable but enebaled.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class UnEditableTableModel extends DefaultTableModel {

    private static final long serialVersionUID = 1L;

    public UnEditableTableModel(Object[][] data, Object[] columnNames) {
        super.setDataVector(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}
