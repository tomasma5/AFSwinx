package com.tomscz.afrest.marshal;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tomscz.afrest.exception.MetamodelException;
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

    private String metaModelInformation;

    public FormBuilder(String metamodelInformation) {
        this.metaModelInformation = metamodelInformation;
    }

    @Override
    public AFMetaModelPack buildModel() throws MetamodelException {
        if (metaModelInformation == null || metaModelInformation.isEmpty()) {
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
    private AFClassInfo transforDataToModel(String metaModelInfomation) throws MetamodelException {
        // Try build document builder based on metamodelInformation
        Document doc;
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(metaModelInfomation));
            doc = db.parse(is);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new MetamodelException(
                    "Error during intialize DOM. Check input data or templates. String to parse is: "
                            + metaModelInfomation + " Exception is: " + e.getLocalizedMessage());
        }

        // Create class info, and set top root information
        AFClassInfo classInfo = new AFClassInfo();
        NodeList nodeList = doc.getElementsByTagName(XMLParseUtils.ENTITYFIELD);
        if (nodeList.getLength() >= 0) {
            Node n = nodeList.item(0);
            classInfo.setName(XMLParseUtils.getTextContent(n));
        }

        nodeList = doc.getElementsByTagName(XMLParseUtils.MAINLAYOUT);
        nodeList = doc.getElementsByTagName(XMLParseUtils.MAINLAYOUTORIENTATION);
        // TODO set main layout
        nodeList = doc.getElementsByTagName(XMLParseUtils.WIDGETROOT);
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
