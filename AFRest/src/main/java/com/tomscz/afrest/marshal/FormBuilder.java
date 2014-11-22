package com.tomscz.afrest.marshal;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tomscz.afrest.commons.AFRestUtils;
import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.layout.TopLevelLayout;
import com.tomscz.afrest.layout.definitions.LayouDefinitions;
import com.tomscz.afrest.layout.definitions.LayoutOrientation;
import com.tomscz.afrest.rest.dto.AFClassInfo;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;

/**
 * This class create form definition.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class FormBuilder implements ModelBuilder {

    private Document metaModelInformation;

    public FormBuilder(Document metamodelInformation) {
        this.metaModelInformation = metamodelInformation;
    }

    @Override
    public AFMetaModelPack buildModel() throws MetamodelException {
        if (metaModelInformation == null) {
            throw new MetamodelException(
                    "Model information were not initialize. Check your templates and class.");
        }
        AFMetaModelPack model = new AFMetaModelPack();
        model.setClassInfo(transforDataToModel(metaModelInformation));
        return model;
    }

    /**
     * This method transform metamodel to data. Metamodel should be XML
     * 
     * @param metaModelInfomation model to transform
     * @return {@link AFClassInfo} which holds all information about model.
     * @throws MetamodelException if error occur during building model, then
     *         {@link MetamodelException} is thrown
     */
    private AFClassInfo transforDataToModel(Document metaModelInfomation) throws MetamodelException {
        // Create class info, and set top root information
        AFClassInfo classInfo = new AFClassInfo();
        NodeList nodeList = metaModelInfomation.getElementsByTagName(XMLParseUtils.ENTITYFIELD);
        Node n;
        if (nodeList != null && nodeList.getLength() >= 0) {
            n = nodeList.item(0);
            classInfo.setName(XMLParseUtils.getTextContent(n));
        }

        nodeList = metaModelInfomation.getElementsByTagName(XMLParseUtils.MAINLAYOUTORIENTATION);
        TopLevelLayout layout = new TopLevelLayout();
        if (nodeList != null && nodeList.getLength() >= 0) {
            n = nodeList.item(0);
            String value = XMLParseUtils.getTextContent(n);
            if(value != null && !value.trim().isEmpty()){
                layout.setLayoutOrientation((LayoutOrientation)AFRestUtils.getEnumFromString(LayoutOrientation.class,value,true));
            }
        }
        nodeList = metaModelInfomation.getElementsByTagName(XMLParseUtils.MAINLAYOUT);
        if (nodeList != null && nodeList.getLength() >= 0) {
            n = nodeList.item(0);
            String value = XMLParseUtils.getTextContent(n);
            if(value != null && !value.trim().isEmpty()){
                layout.setLayoutDefinition((LayouDefinitions)AFRestUtils.getEnumFromString(LayouDefinitions.class,value,true));
            }
           
        }
        classInfo.setLayout(layout);
        // TODO set main layout
        nodeList = metaModelInfomation.getElementsByTagName(XMLParseUtils.WIDGETROOT);
        // For each widget build fieldInfo
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node widget = nodeList.item(i);
            AFFieldInfo fieldInfo = createFieldProperties(widget);
            classInfo.addFieldInfo(fieldInfo);
        }
        return classInfo;
    }

    /**
     * This method build concrete field info based on widget.
     * 
     * @param widget which will be transformed to field info
     * @return {@link AFFieldInfo} which holds all necessary information to build widget
     * @throws MetamodelException if widget can not be transformed then exception is thrown
     */
    private AFFieldInfo createFieldProperties(Node widget) throws MetamodelException {
        NodeList properties = widget.getChildNodes();
        AFFieldInfo fieldInfo = new AFFieldInfo();
        // For every widget parameter
        for (int i = 1; i < properties.getLength(); i++) {
            Node propertyNode = properties.item(i);
            String propertyName = propertyNode.getNodeName();
            String propertyValue = XMLParseUtils.getTextContent(propertyNode);
            if (propertyValue == null || propertyName == null) {
                continue;
            }
            // If its widgetLayout or validations, then data are in their children, parse them
            // different
            if (propertyName.equals(XMLParseUtils.WIDGETLAYOUT)
                    || propertyName.equals(XMLParseUtils.WIDGETVALIDATIONS)) {
                NodeList widgetChilds = propertyNode.getChildNodes();
                for (int j = 1; j < widgetChilds.getLength(); j++) {
                    Node fieldNode = widgetChilds.item(j);
                    String propertyFieldName = fieldNode.getNodeName();
                    String propertyFieldValue = XMLParseUtils.getTextContent(fieldNode);
                    // If value is not specify then continue
                    if (propertyFieldValue == null || propertyFieldValue.trim().isEmpty()
                            || propertyFieldValue == null || propertyFieldValue.trim().isEmpty()) {
                        continue;
                    }
                    // Set property based on type
                    if (propertyName.equals(XMLParseUtils.WIDGETLAYOUT)) {
                        XMLParseUtils.setLayoutProperties(fieldInfo, propertyFieldName,
                                propertyFieldValue);
                    } else if (propertyName.equals(XMLParseUtils.WIDGETVALIDATIONS)) {
                        XMLParseUtils.setValidationsProperties(fieldInfo, propertyFieldName,
                                propertyFieldValue);
                    }
                }
            } else {
                if (propertyValue.trim().isEmpty()) {
                    continue;
                }
                // If there are not children then it must be root property set it then
                XMLParseUtils.setRootProperties(fieldInfo, propertyName, propertyValue);
            }

        }
        return fieldInfo;
    }

}
