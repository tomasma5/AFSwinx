package com.tomscz.afswinx.component.form;

import java.net.ConnectException;
import java.util.HashMap;

import com.tomscz.afswinx.common.SupportedComponents;
import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.dto.AFClassInfo;
import com.tomscz.afswinx.rest.dto.AFFieldInfo;
import com.tomscz.afswinx.rest.dto.AFMetaModelPack;
import com.tomscz.afswinx.rest.dto.data.AFData;
import com.tomscz.afswinx.rest.dto.data.AFDataPack;
import com.tomscz.afswinx.unmarshal.builders.FieldBuilder;
import com.tomscz.afswinx.unmarshal.factory.WidgetBuilderFactory;

public class AFSwinxForm extends AFSwinxTopLevelComponent {

    private static final long serialVersionUID = 1L;

    private AFSwinxConnection modelConnection;
    private AFSwinxConnection postConnection;
    private AFSwinxConnection dataConnection;
    HashMap<String, AFSwinxPanel> panels = new HashMap<String, AFSwinxPanel>();

    private SupportedComponents componentType;

    public AFSwinxForm(AFSwinxConnection modelConnection, AFSwinxConnection dataConnection,
            AFSwinxConnection postConnection) {
        this.modelConnection = modelConnection;
        this.dataConnection = dataConnection;
        this.postConnection = postConnection;
    }

    @Override
    protected SupportedComponents getComponentType() {
        return componentType;
    }

    @Override
    protected AFSwinxConnection getModelConnection() {
        return modelConnection;
    }

    @Override
    protected AFSwinxConnection getPostConnection() {
        return postConnection;
    }

    @Override
    protected AFSwinxConnection getDataConnection() {
        return dataConnection;
    }

    @Override
    public void buildComponent() throws ConnectException{
            AFMetaModelPack metaModelPack = getModel();
            AFClassInfo classInfo = metaModelPack.getClassInfo();
            for (AFFieldInfo fieldInfo : classInfo.getFieldInfo()) {
                FieldBuilder builder = WidgetBuilderFactory.createWidgetBuilder(fieldInfo);
                addComponent(builder.buildComponent(fieldInfo));
            }
    }

    public void addComponent(AFSwinxPanel panelToAdd) {
        this.panels.put(panelToAdd.getPanelId(), panelToAdd);
        this.add(panelToAdd);
    }

    @Override
    public void fillData() throws ConnectException {
        AFDataPack data = getData();
        for (AFData field : data.getData()) {
            String fieldName = field.getKey();
            AFSwinxPanel panelToSetData = panels.get(fieldName);
            FieldBuilder builder =
                    WidgetBuilderFactory.createWidgetBuilder(panelToSetData.getWidgetType());
            builder.setData(panelToSetData,field);
        }
    }
}
