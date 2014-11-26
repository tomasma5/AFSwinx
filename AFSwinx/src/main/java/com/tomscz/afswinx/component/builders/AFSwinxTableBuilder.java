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
import com.tomscz.afswinx.component.widget.builder.WidgetBuilder;
import com.tomscz.afswinx.component.widget.builder.abstraction.BaseLayoutBuilder;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;
import com.tomscz.afswinx.rest.rebuild.BaseRestBuilder;
import com.tomscz.afswinx.rest.rebuild.RestBuilderFactory;


public class AFSwinxTableBuilder extends BaseComponentBuilder<AFSwinxTableBuilder> {

    private int componentPossition = 0;
    private List<String> components = new ArrayList<String>();

    @Override
    public AFSwinxTable buildComponent() throws AFSwinxBuildException {
        super.initializeConnections();
        AFSwinxTable table = new AFSwinxTable(modelConnection, dataConnection, postConnection);

        try {
            AFMetaModelPack metaModelPack = table.getModel();
            AFClassInfo classInfo = metaModelPack.getClassInfo();
            if (classInfo != null) {
                buildFields(classInfo, table, "");
            }
            String columns[] = new String[components.size()];
            int i = 0;
            for (String key : components) {
                ComponentDataPacker dataPacker = table.getPanels().get(key);
                columns[i] = dataPacker.getComponent().getLabelHolder().getText();
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
                    Object data = value;
                    row[i][j] = data;
                }
            }
            TableModel model = new DefaultTableModel(row, columns);
            JTable tableimp = new JTable(model);
            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
            tableimp.setRowSorter(sorter);
            JScrollPane pane = new JScrollPane(tableimp);
            table.add(pane);
            AFSwinx.getInstance().addComponent(table, componentKeyName);
        } catch (AFSwinxConnectionException e) {
            throw new AFSwinxBuildException(e.getMessage());
        }

        return table;
    }


    /**
     * This method set data to form. It build for each widget his field
     * 
     * @param classInfo which will be inspected
     * @param layoutBuilder layout builder which will be used
     * @param form which is builded
     * @param key of current field. It is used to determine which class belongs to which fields
     */
    private void buildFields(AFClassInfo classInfo, AFSwinxTable table, String key) {
        // For each field
        for (String fieldId : classInfo.getFieldInfo().keySet()) {
            AFFieldInfo fieldInfo = classInfo.getFieldInfo().get(fieldId);
            // If its class then inspect it recursively
            if (fieldInfo.getClassType()) {
                for (AFClassInfo classInfoChildren : classInfo.getInnerClasses()) {
                    // There could be more inner class choose the right one
                    if (classInfoChildren.getName() != null
                            && classInfoChildren.getName().equals(fieldInfo.getId())) {
                        // Recursively call this method with new key, which will specify unique link
                        // on parent
                        buildFields(classInfoChildren, table, Utils.generateKey(key, fieldId));
                    }
                }
            } else {
                // Build field
                WidgetBuilder builder =
                        WidgetBuilderFactory.getInstance().createWidgetBuilder(fieldInfo);
                if (localization == null) {
                    localization = AFSwinx.getInstance().getLocalization();
                }
                builder.setLocalization(localization);
                // Use generated key
                String uniquieKey = Utils.generateKey(key, fieldId);
                fieldInfo.setId(uniquieKey);
                AFSwinxPanel panelToAdd = builder.buildComponent(fieldInfo);
                this.addComponent(panelToAdd, null, table);
            }
        }
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

}
