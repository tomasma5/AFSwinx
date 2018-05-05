package com.tomscz.afswinx.component;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.tomscz.afrest.commons.SupportedComponents;
import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.component.builders.ComponentDataPacker;
import com.tomscz.afswinx.component.factory.WidgetBuilderFactory;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.component.widget.builder.WidgetBuilder;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.rebuild.holder.AFData;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataHolder;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataPack;
import com.tomscz.afswinx.swing.component.model.UnEditableTableModel;

/**
 * This class represents table. This components hold widget and is able to get model and set data to
 * model. This table is scrollable.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AFSwinxTable extends AFSwinxTopLevelComponent {

    private static final long serialVersionUID = 1L;

    private JTable table;
    private JScrollPane scrollPanel;
    private boolean dynamicSize = false;
    private boolean fitSize = false;
    private int[] columsWidth;
    private int totalWidth = -1;
    private static final int PLUS_HEIGHT = 30;

    private List<AFDataPack> receivedData;
    private List<HashMap<String, String>> tableRow = new ArrayList<HashMap<String, String>>();

    public AFSwinxTable(AFSwinxConnection modelConnection, AFSwinxConnection dataConnection,
            AFSwinxConnection sendConnection) {
        this.modelConnection = modelConnection;
        this.dataConnection = dataConnection;
        this.sendConnection = sendConnection;
    }

    @Override
    public void fillData(List<AFDataPack> dataPack) {
        receivedData = dataPack;
        for (AFDataPack data : dataPack) {
            HashMap<String, String> row = new HashMap<String, String>();
            for (AFData field : data.getData()) {
                String fieldName = field.getKey();
                ComponentDataPacker dataPacker = getPanels().get(fieldName);
                if (dataPacker == null) {
                    continue;
                }
                AFSwinxPanel panelToSetData = dataPacker.getComponent();
                WidgetBuilder builder =
                        WidgetBuilderFactory.getInstance().createWidgetBuilder(
                                panelToSetData.getWidgetType());
                builder.setLocalization(localization);
                builder.setData(panelToSetData, field);
                row.put(fieldName, (String) builder.getPlainData(panelToSetData));
            }
            tableRow.add(row);
        }
    }

    @Override
    public boolean validateData() {
        //Table is readonly, so validate data has no reason.
        throw new UnsupportedOperationException("This opertion is not supported yet");
    }

    @Override
    public AFDataHolder resealize() {
        //This operation wont be supported in this version.
        throw new UnsupportedOperationException("This opertion is not supported yet");
    }

    @Override
    public SupportedComponents getComponentType() {
        return SupportedComponents.TABLE;
    }

    /**
     * This method set columns width based on longest value
     */
    private void setColumnsWidths() {
        int columnMultiplyConst = 5;
        int colimnAdditionalWidth = 20;
        int columnCount = table.getColumnCount();
        columsWidth = new int[table.getColumnCount()];
        TableModel model = table.getModel();
        // First calculate width of longest data in each column
        for (int count = 0; count < model.getRowCount(); count++) {
            for (int column = 0; column < columnCount; column++) {
                Object columnValue = model.getValueAt(count, column);
                if (columnValue != null) {
                    String value = columnValue.toString();
                    int width = value.length() * columnMultiplyConst + colimnAdditionalWidth;
                    if (width > columsWidth[column]) columsWidth[column] = width;
                }
            }
        }
        TableColumnModel columnModel = table.getTableHeader().getColumnModel();
        // Then calculate width of longest header text in each column
        for (int column = 0; column < columnCount; column++) {
            Object columnValue = columnModel.getColumn(column).getHeaderValue();
            if (columnValue != null) {
                String value = columnValue.toString();
                int width = value.length() * columnMultiplyConst + colimnAdditionalWidth;
                if (width > columsWidth[column]) columsWidth[column] = width;
            }
        }
    }

    /**
     * This method resize table. It works only if fit size attribute is set to true see
     * {@link AFSwinxTable#setFitSize(boolean)} . It also work with dynamic size attribute see
     * {@link AFSwinxTable#setDynamicSize(boolean)}. If it is true then maximal size of table is set
     * to 2000 and minimal on 15 otherwise are used attributes from current calculation.
     */
    public void resize() {
        if (fitSize) {
            if (columsWidth == null) {
                setColumnsWidths();
            }
            int columnWidthAll = 0;
            int tableHeigth = table.getRowHeight() * table.getRowCount() + PLUS_HEIGHT;
            for (int i = 0; i < columsWidth.length; i++) {
                TableColumn column = table.getColumnModel().getColumn(i);
                int columnWidth = columsWidth[i];
                // If dynamic size is enable then
                if (dynamicSize) {
                    column.setMinWidth(15);
                    column.setMaxWidth(2000);
                } else {
                    column.setMinWidth(columnWidth);
                    column.setMaxWidth(columnWidth);
                }
                column.setPreferredWidth(columnWidth);
                columnWidthAll += columnWidth;
            }
            scrollPanel.setPreferredSize(new Dimension(columnWidthAll, tableHeigth));
            this.totalWidth = columnWidthAll;
        }
    }

    public List<HashMap<String, String>> getTableRow() {
        return tableRow;
    }

    /**
     * This method return actual data in selected row. If no row is selected then
     * {@link IndexOutOfBoundsException} is thrown. This method could be used after sorting.
     * 
     * @return Actual data in selected data.
     * @throws IndexOutOfBoundsException if not row is selected then this exception is thrown.
     */
    public List<AFDataPack> getSelectedData() throws IndexOutOfBoundsException {
        int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
        List<AFDataPack> datas = new ArrayList<AFDataPack>();
        datas.add(receivedData.get(selectedRow));
        return datas;
    }

    /**
     * This method return actual table.
     * @return Table which were created.
     */
    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public boolean isFitSize() {
        return fitSize;
    }

    public void setFitSize(boolean fitSize) {
        this.fitSize = fitSize;
    }

    public boolean isDynamicSize() {
        return dynamicSize;
    }

    public void setDynamicSize(boolean dynamicSize) {
        this.dynamicSize = dynamicSize;
    }

    public JScrollPane getScrollPanel() {
        return scrollPanel;
    }

    public void setScrollPanel(JScrollPane scrollPanel) {
        this.scrollPanel = scrollPanel;
    }

    public int getTotalWidth() {
        return totalWidth;
    }

    @Override
    public void clearData() {
        TableModel model = new UnEditableTableModel(new Object[0][0], new Object[0]);
        table.setModel(model);
    }

}
