package com.tomscz.afswinx.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tomscz.afrest.commons.SupportedComponents;
import com.tomscz.afrest.rest.dto.data.AFData;
import com.tomscz.afrest.rest.dto.data.AFDataPack;
import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.component.builders.ComponentDataPacker;
import com.tomscz.afswinx.component.factory.WidgetBuilderFactory;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.component.widget.builder.FieldBuilder;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataHolder;

public class AFSwinxTable extends AFSwinxTopLevelComponent {

    private static final long serialVersionUID = 1L;

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
                if(dataPacker == null){
                    continue;
                }
                AFSwinxPanel panelToSetData = dataPacker.getComponent();
                FieldBuilder builder =
                        WidgetBuilderFactory.getInstance().createWidgetBuilder(
                                panelToSetData.getWidgetType());
                builder.setData(panelToSetData, field);
                row.put(fieldName, (String)builder.getPlainData(panelToSetData));
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

    public List<HashMap<String, String>> getTableRow() {
        return tableRow;
    }

}
