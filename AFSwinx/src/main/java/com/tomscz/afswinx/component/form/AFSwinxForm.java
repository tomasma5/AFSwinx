package com.tomscz.afswinx.component.form;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import com.tomscz.afswinx.common.SupportedComponents;
import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.dto.AFClassInfo;
import com.tomscz.afswinx.rest.dto.AFFieldInfo;
import com.tomscz.afswinx.rest.dto.AFMetaModelPack;
import com.tomscz.afswinx.unmarshal.builders.FieldBuilder;
import com.tomscz.afswinx.unmarshal.factory.WidgetBuilderFactory;

public class AFSwinxForm extends AFSwinxTopLevelComponent {

    private static final long serialVersionUID = 1L;

    private AFSwinxConnection modelConnection;
    private AFSwinxConnection postConnection;
    private AFSwinxConnection dataConnection;
    List<AFSwinxPanel> panels = new ArrayList<AFSwinxPanel>();

    private SupportedComponents componentType;

    public AFSwinxForm(AFSwinxConnection modelConnection, AFSwinxConnection postConnection,
            AFSwinxConnection dataConnection) {
        this.modelConnection = modelConnection;
        this.postConnection = postConnection;
        this.dataConnection = dataConnection;
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
    public void buildComponent() {
        try {
            AFMetaModelPack metaModelPack = getModel();
            AFClassInfo classInfo = metaModelPack.getClassInfo();
            for(AFFieldInfo fieldInfo : classInfo.getFieldInfo()){
                FieldBuilder builder = WidgetBuilderFactory.createWidgetBuilder(fieldInfo);
                this.add(builder.buildComponent(fieldInfo));
            }
        } catch (ConnectException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    public void add(AFSwinxPanel panelToAdd){
        this.panels.add(panelToAdd);
        this.add(panelToAdd);
    }
}
