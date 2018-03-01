package com.tomscz.afswinx.component.builders;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.tomscz.afrest.commons.SupportedComponents;
import com.tomscz.afrest.rest.dto.AFClassInfo;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxTable;
import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.component.skin.Skin;
import com.tomscz.afswinx.component.widget.builder.abstraction.BaseLayoutBuilder;
import com.tomscz.afswinx.localization.LocalizationUtils;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;
import com.tomscz.afswinx.rest.rebuild.BaseRestBuilder;
import com.tomscz.afswinx.rest.rebuild.RestBuilderFactory;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataPack;
import com.tomscz.afswinx.swing.component.model.UnEditableTableModel;


public class AFSwinxTableBuilder extends BaseComponentBuilder<AFSwinxTableBuilder> {

    private int componentPossition = 0;
    private boolean fitSize = false;
    private boolean dynamicSize = true;
    private List<String> components = new ArrayList<String>();

    @Override
    public AFSwinxTable buildComponent() throws AFSwinxBuildException {
        super.initializeConnections();
        AFSwinxTable afSwinxTable = new AFSwinxTable(modelConnection, dataConnection, sendConnection);
        afSwinxTable.setLayout(new GridLayout(1,1));
        afSwinxTable.setDynamicSize(dynamicSize);
        afSwinxTable.setFitSize(fitSize);
        try {
            AFMetaModelPack metaModelPack = afSwinxTable.getModel();
            AFClassInfo classInfo = metaModelPack.getClassInfo();
            if (classInfo != null) {
                buildFields(classInfo, null,afSwinxTable, "");
            }
            String columns[] = new String[components.size()];
            int i = 0;
            for (String key : components) {
                ComponentDataPacker dataPacker = afSwinxTable.getPanels().get(key);
                String label = dataPacker.getComponent().getLabelHolder().getText();
                columns[i] = label;
                i++;
            }
            Object o = afSwinxTable.getData();
            BaseRestBuilder dataBuilder =
                    RestBuilderFactory.getInstance().getBuilder(afSwinxTable.getSendConnection());
            List<AFDataPack> dataPack = dataBuilder.serialize(o);
            // Fill data to table
            afSwinxTable.fillData(dataPack);

            Object row[][] = new String[dataPack.size()][components.size()];
            for (i = 0; i < row.length; i++) {
                HashMap<String, String> rowData = afSwinxTable.getTableRow().get(i);
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
            TableModel model = new UnEditableTableModel(row, columns);
            JTable table = new JTable(model);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
            table.setRowSorter(sorter);
            JScrollPane pane = new JScrollPane(table);
            table.setFillsViewportHeight(true);
//            table.setEnabled(false);
            afSwinxTable.setTable(table);
            afSwinxTable.setScrollPanel(pane);
            afSwinxTable.resize();
            afSwinxTable.add(pane);
            AFSwinx.getInstance().addComponent(afSwinxTable, componentKeyName);
        } catch (AFSwinxConnectionException e) {
            throw new AFSwinxBuildException(e.getMessage());
        }
        catch (NullPointerException e) {
            throw new AFSwinxBuildException(e.getMessage());
        }

        return afSwinxTable;
    }

    @Override
    public SupportedComponents getBuiltComponentType() {
        return SupportedComponents.TABLE;
    }

    @Override
    protected void addComponent(AFSwinxPanel panelToAdd, BaseLayoutBuilder layoutBuilder,
            AFSwinxTopLevelComponent component) {
        if(panelToAdd.isVisible()){
            components.add(panelToAdd.getPanelId());
            ComponentDataPacker dataPacker =
                    new ComponentDataPacker(componentPossition++, panelToAdd.getPanelId(), panelToAdd);
            component.getPanels().put(dataPacker.getId(), dataPacker);
            // component.add(panelToAdd);
            panelToAdd.setAfParent(component);
        }
    }

    public AFSwinxTableBuilder setDynamicSize(boolean dynamicSize) {
        this.dynamicSize = dynamicSize;
        return this;
    }

    public AFSwinxTableBuilder setFitSize(boolean fitSize) {
        this.fitSize = fitSize;
        return this;
    }

    @Override
    public AFSwinxTableBuilder setSkin(Skin skin) {
        this.skin = skin;
        return this;
    }

}
