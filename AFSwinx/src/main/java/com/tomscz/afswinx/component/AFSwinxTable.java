package com.tomscz.afswinx.component;

import java.util.HashMap;

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
    
    private HashMap<String, String> tableRow = new HashMap<String, String>();

    public AFSwinxTable(AFSwinxConnection modelConnection, AFSwinxConnection dataConnection,
            AFSwinxConnection postConnection) {
        this.modelConnection = modelConnection;
        this.dataConnection = dataConnection;
        this.postConnection = postConnection;
    }

    @Override
    public void fillData(AFDataPack dataPack) {
        if (dataPack.getClassName().isEmpty()) {
            return;
        }
        for (AFData field : dataPack.getData()) {
            String fieldName = field.getKey();
            tableRow.put(fieldName, field.getValue());
            ComponentDataPacker dataPacker = getPanels().get(fieldName);
            AFSwinxPanel panelToSetData = dataPacker.getComponent();
            FieldBuilder builder =
                    WidgetBuilderFactory.getInstance().createWidgetBuilder(
                            panelToSetData.getWidgetType());
            builder.setData(panelToSetData, field);
        }

    }

    @Override
    public boolean validateData() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public AFDataHolder resealize() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SupportedComponents getComponentType() {
        return SupportedComponents.TABLE;
    }
    
    public HashMap<String, String> getTableRow() {
        return tableRow;
    }
    
}
