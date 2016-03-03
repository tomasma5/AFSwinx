package com.tomscz.afswinx.component.builders;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.tomscz.afrest.layout.Layout;
import com.tomscz.afrest.rest.dto.AFClassInfo;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxForm;
import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.component.skin.Skin;
import com.tomscz.afswinx.component.widget.builder.abstraction.BaseLayoutBuilder;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;
import com.tomscz.afswinx.rest.rebuild.BaseRestBuilder;
import com.tomscz.afswinx.rest.rebuild.RestBuilderFactory;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataPack;
import com.tomscz.afswinx.validation.LessThanValidator;

/**
 * This is form builder. This class is responsible for create {@link AFSwinxForm} component.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AFSwinxFormBuilder extends BaseComponentBuilder<AFSwinxFormBuilder> {

    @Override
    public AFSwinxForm buildComponent() throws AFSwinxBuildException {
        super.initializeConnections();
        AFSwinxForm form = new AFSwinxForm(modelConnection, dataConnection, sendConnection);
        try {
            // Build component
            this.buildComponent(form);
            // Get data
            Object data = form.getData();
            // Based on data type make serialization
            BaseRestBuilder dataBuilder =
                    RestBuilderFactory.getInstance().getBuilder(form.getSendConnection());
            List<AFDataPack> dataPack = dataBuilder.serialize(data);
            // Fill data to form
            form.fillData(dataPack);
            AFSwinx.getInstance().addComponent(form, componentKeyName);
        } catch (Exception e) {
            throw new AFSwinxBuildException(e.getMessage());
        }
        return form;
    }

    /**
     * This method will build form based on metamodel
     * 
     * @param form which will be build
     * @throws AFSwinxConnectionException if exception during retrieve metamodel ocurre
     */
    private void buildComponent(AFSwinxForm form) throws AFSwinxConnectionException,
            AFSwinxBuildException {
        AFMetaModelPack metaModelPack = form.getModel();
        AFClassInfo classInfo = metaModelPack.getClassInfo();
        if (classInfo != null) {
            // Convert TopLevelLayout to layout
            Layout layout = null;
            if (classInfo.getLayout() != null) {
                layout = new Layout();
                layout.setLayoutDefinition(classInfo.getLayout().getLayoutDefinition());
                layout.setLayoutOrientation(classInfo.getLayout().getLayoutOrientation());
            }
            // Initialize layout builder
            BaseLayoutBuilder layoutBuilder = new BaseLayoutBuilder(layout);
            buildFields(classInfo, layoutBuilder, form, "");
            
            //add LessThan validations - all fields needs to be created
            Iterator<Entry<String, ComponentDataPacker>> it = form.getPanels().entrySet().iterator();
            while (it.hasNext()) {
            	Entry<String, ComponentDataPacker> pair = (Entry<String, ComponentDataPacker>) it.next();
            	AFSwinxPanel panel = pair.getValue().getComponent();
            	String otherPanelId = panel.getCompareByLessThanWith();
            	if(otherPanelId != null){
            		AFSwinxPanel otherPanel = form.getPanels().get(otherPanelId).getComponent();
            		LessThanValidator validator = new LessThanValidator(panel.getWidgetType(), otherPanel);
            		panel.addValidator(validator);
                }
            }
            
            // Build layout
            layoutBuilder.buildLayout(form);
        }
    }

    @Override
    protected void addComponent(AFSwinxPanel panelToAdd, BaseLayoutBuilder layoutBuilder,
            AFSwinxTopLevelComponent component) {
        ComponentDataPacker dataPacker =
                new ComponentDataPacker(0, panelToAdd.getPanelId(), panelToAdd);
        component.getPanels().put(dataPacker.getId(), dataPacker);
        panelToAdd.setAfParent(component);
        if(panelToAdd.isVisible()){
            component.add(panelToAdd);
            layoutBuilder.addComponent(panelToAdd); 
        }
    }

    @Override
    public AFSwinxFormBuilder setSkin(Skin skin) {
        this.skin = skin;
        return this;
    }

}
