package com.tomscz.afswinx.component.builders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.tomscz.afrest.rest.dto.AFClassInfo;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afrest.rest.dto.data.AFDataPack;
import com.tomscz.afswinx.common.Utils;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxTable;
import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.component.factory.WidgetBuilderFactory;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.component.skin.BaseSkin;
import com.tomscz.afswinx.component.skin.Skin;
import com.tomscz.afswinx.component.widget.builder.WidgetBuilder;
import com.tomscz.afswinx.component.widget.builder.abstraction.BaseLayoutBuilder;
import com.tomscz.afswinx.localization.LocalizationUtils;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;
import com.tomscz.afswinx.rest.rebuild.BaseRestBuilder;
import com.tomscz.afswinx.rest.rebuild.RestBuilderFactory;


public class AFSwinxTableBuilder extends BaseComponentBuilder<AFSwinxTableBuilder> {

    private int componentPossition = 0;
    private boolean fitSize = false;
    private boolean dynamicSize = true;
    private int width;
    private int height;
    private List<String> components = new ArrayList<String>();

    @Override
    public AFSwinxTable buildComponent() throws AFSwinxBuildException {
        super.initializeConnections();
        AFSwinxTable table = new AFSwinxTable(modelConnection, dataConnection, postConnection);
        table.setDynamicSize(dynamicSize);
        table.setFitSize(fitSize);
        table.setTableHeight(width);
        table.setTableWidth(height);
        try {
            AFMetaModelPack metaModelPack = table.getModel();
            AFClassInfo classInfo = metaModelPack.getClassInfo();
            if (classInfo != null) {
                buildFields(classInfo, null,table, "");
            }
            String columns[] = new String[components.size()];
            int i = 0;
            for (String key : components) {
                ComponentDataPacker dataPacker = table.getPanels().get(key);
                String label = dataPacker.getComponent().getLabelHolder().getText();
                columns[i] = label;
                i++;
            }
            Object o = table.getData();
            BaseRestBuilder dataBuilder =
                    RestBuilderFactory.getInstance().getBuilder(table.getDataConnection());
            List<AFDataPack> dataPack = dataBuilder.serialize(o);
            // Fill data to table
            table.fillData(dataPack);

            Object row[][] = new String[dataPack.size()][components.size()];
            for (i = 0; i < row.length; i++) {
                HashMap<String, String> rowData = table.getTableRow().get(i);
                for (int j = 0; j < row[i].length; j++) {
                    String key = components.get(j);
                    String value = rowData.get(key);
                    if (value != null) {
                        if (value.equals("true") || value.equals("false")) {
                            value =
                                    LocalizationUtils.getTextFromExtendBundle(value, localization,
                                            null);
                        }
                    }
                    Object data = value;
                    row[i][j] = data;
                }
            }
            TableModel model = new DefaultTableModel(row, columns);
            JTable tableimp = new JTable(model);
            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
            tableimp.setRowSorter(sorter);
            JScrollPane pane = new JScrollPane();
            pane.setViewportView(tableimp);;
            table.setTable(tableimp);
            table.setScrollPanel(pane);
            table.resize();
            table.add(pane);
            AFSwinx.getInstance().addComponent(table, componentKeyName);
        } catch (AFSwinxConnectionException e) {
            throw new AFSwinxBuildException(e.getMessage());
        }

        return table;
    }

    @Override
    protected void addComponent(AFSwinxPanel panelToAdd, BaseLayoutBuilder layoutBuilder,
            AFSwinxTopLevelComponent component) {
        components.add(panelToAdd.getPanelId());
        ComponentDataPacker dataPacker =
                new ComponentDataPacker(componentPossition++, panelToAdd.getPanelId(), panelToAdd);
        component.getPanels().put(dataPacker.getId(), dataPacker);
        // component.add(panelToAdd);
        panelToAdd.setAfParent(component);
    }

    public AFSwinxTableBuilder setDynamicSize(boolean dynamicSize) {
        this.dynamicSize = dynamicSize;
        return this;
    }

    public AFSwinxTableBuilder setFitSize(boolean fitSize) {
        this.fitSize = fitSize;
        return this;
    }

    public AFSwinxTableBuilder setHeight(int size) {
        this.height = size;
        return this;
    }

    public AFSwinxTableBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    @Override
    public AFSwinxTableBuilder setSkin(Skin skin) {
        this.skin = skin;
        return this;
    }

}
