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
import com.tomscz.afrest.rest.dto.data.AFData;
import com.tomscz.afrest.rest.dto.data.AFDataPack;
import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.component.builders.ComponentDataPacker;
import com.tomscz.afswinx.component.factory.WidgetBuilderFactory;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.component.widget.builder.WidgetBuilder;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataHolder;

public class AFSwinxTable extends AFSwinxTopLevelComponent {

    private static final long serialVersionUID = 1L;

    private JTable table;
    private JScrollPane scrollPanel;
    private boolean dynamicSize = false;
    private boolean fitSize = false;
    private int tableWidth;
    private int tableHeight;
    private int[] columsWidth;
    private static final int PLUS_HEIGHT = 30;


    private List<HashMap<String, String>> tableRow = new ArrayList<HashMap<String, String>>();

    public AFSwinxTable(AFSwinxConnection modelConnection, AFSwinxConnection dataConnection,
            AFSwinxConnection postConnection) {
        this.modelConnection = modelConnection;
        this.dataConnection = dataConnection;
        this.postConnection = postConnection;
    }

    @Override
    public void fillData(List<AFDataPack> dataPack) {
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
                builder.setData(panelToSetData, field);
                row.put(fieldName, (String) builder.getPlainData(panelToSetData));
            }
            tableRow.add(row);
        }
    }

    @Override
    public boolean validateData() {
        throw new UnsupportedOperationException("This opertion is not supported yet");
    }

    @Override
    public AFDataHolder resealize() {
        throw new UnsupportedOperationException("This opertion is not supported yet");
    }

    @Override
    public SupportedComponents getComponentType() {
        return SupportedComponents.TABLE;
    }

    private void setColumnsWidths() {
        int columnCount = table.getColumnCount();
        columsWidth = new int[table.getColumnCount()];
        TableModel model = table.getModel();
        for (int count = 0; count < model.getRowCount(); count++) {
            for (int column = 0; column < columnCount; column++) {
                // TODO this may not work if there will be some different component
                Object columnValue = model.getValueAt(count, column);
                if (columnValue != null) {
                    String value = columnValue.toString();
                    int width = value.length() * 5 + 20;
                    if (width > columsWidth[column]) columsWidth[column] = width;
                }
            }
        }
        TableColumnModel columnModel = table.getTableHeader().getColumnModel();
        for (int column = 0; column < columnCount; column++) {
            Object columnValue = columnModel.getColumn(column).getHeaderValue();
            if (columnValue != null) {
                String value = columnValue.toString();
                int width = value.length() * 5 + 20;
                if (width > columsWidth[column]) columsWidth[column] = width;
            }
        }
    }

    public void resize() {
        if(tableHeight != 0 && tableWidth != 0){
            scrollPanel.setPreferredSize(new Dimension(tableWidth, tableHeight));
        }
        else if (fitSize) {
            if (columsWidth == null) {
                setColumnsWidths();
            }
            int columnWidthAll = 0;
            int tableHeigth = table.getRowHeight() * table.getRowCount() + PLUS_HEIGHT;
            for (int i = 0; i < columsWidth.length; i++) {
                TableColumn column = table.getColumnModel().getColumn(i);
                int columnWidth = columsWidth[i];
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
        }
    }

    public List<HashMap<String, String>> getTableRow() {
        return tableRow;
    }

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

    public int getTableWidth() {
        return tableWidth;
    }

    public void setTableWidth(int tableWidth) {
        this.tableWidth = tableWidth;
    }

    public int getTableHeight() {
        return tableHeight;
    }

    public void setTableHeight(int tableHeight) {
        this.tableHeight = tableHeight;
    }

}
