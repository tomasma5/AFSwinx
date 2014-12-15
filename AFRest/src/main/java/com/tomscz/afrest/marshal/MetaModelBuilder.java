package com.tomscz.afrest.marshal;

import java.util.Stack;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
 * This class create definition.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class MetaModelBuilder implements ModelBuilder {

    private Document metaModelInformation;

    public MetaModelBuilder(Document metamodelInformation) {
        this.metaModelInformation = metamodelInformation;
    }

    @Override
    public AFMetaModelPack buildModel() throws MetamodelException {
        if (metaModelInformation == null) {
            throw new MetamodelException(
                    "Model information were not initialize. Check your templates and class.");
        }
        AFMetaModelPack model = new AFMetaModelPack();
        model.setClassInfo(parse(metaModelInformation));
        return model;
    }

    /**
     * This method transform metamodel to data. Metamodel is in XML. This parser hold entity and
     * widget order. It use modificated DFS.
     * 
     * @param metaModelInfomation model to transform
     * @return {@link AFClassInfo} which holds all information about model.
     * @throws MetamodelException if error occur during building model, then
     *         {@link MetamodelException} is thrown
     */
    protected AFClassInfo parse(Document metaModelInfomation) throws MetamodelException {
        // There must exist root entity class
        NodeList nodeList = metaModelInfomation.getElementsByTagName(XMLParseUtils.ROOTCLASS);
        if (nodeList.getLength() <= 0) {
            return new AFClassInfo();
        }
        // Initialize stack by root entity. It is first parsed entity. This stack will hold node
        // which must be parsed
        Stack<NodeFieldClass> nodesToGo = new Stack<NodeFieldClass>();
        AFClassInfo rootAFClassInfo = new AFClassInfo();
        nodesToGo.add(new NodeFieldClass(nodeList.item(0), rootAFClassInfo));
        // Until there is something which must be transformed
        while (!nodesToGo.isEmpty()) {
            NodeFieldClass rootNodeHolder = nodesToGo.pop();
            Node rootNode = rootNodeHolder.node;
            // This class info will be set up
            AFClassInfo classInfo = rootNodeHolder.classInfo;
            NodeList childNodes = rootNode.getChildNodes();
            // This stack holds all entity which will be parsed in another iteration. The reason why
            // is there second stack is that it used to hold order
            Stack<NodeFieldClass> nodeStackOrder = new Stack<NodeFieldClass>();
            // Initialize layout even if layout could not be specify
            TopLevelLayout layout = new TopLevelLayout();
            classInfo.setLayout(layout);
            for (int i = 0; i < childNodes.getLength(); i++) {
                // Based on tag make parsing
                Node currentNode = childNodes.item(i);
                String nodeName = currentNode.getNodeName();
                if (nodeName.equals(XMLParseUtils.MAINLAYOUTORIENTATION)) {
                    String value = XMLParseUtils.getTextContent(currentNode);
                    if (value != null && !value.trim().isEmpty()) {
                        layout.setLayoutOrientation((LayoutOrientation) AFRestUtils
                                .getEnumFromString(LayoutOrientation.class, value, true));
                    }
                } else if (nodeName.equals(XMLParseUtils.MAINLAYOUT)) {
                    String value = XMLParseUtils.getTextContent(currentNode);
                    if (value != null && !value.trim().isEmpty()) {
                        layout.setLayoutDefinition((LayouDefinitions) AFRestUtils
                                .getEnumFromString(LayouDefinitions.class, value, true));
                    }

                } else if (nodeName.equals(XMLParseUtils.WIDGETROOT)) {
                    AFFieldInfo fieldInfo = createFieldProperties(currentNode);
                    classInfo.addFieldInfo(fieldInfo);
                }
                if (nodeName.equals(XMLParseUtils.ROOTCLASS)) {
                    // In this case, there is some another element which should be parsed create
                    // links and add them to stack
                    AFClassInfo child = new AFClassInfo();
                    // Set him as child node
                    classInfo.addInnerClass(child);
                    AFFieldInfo classFieldInfo = new AFFieldInfo();
                    classFieldInfo.setClassType(true);
                    Element nodeE = (Element) currentNode;
                    String id = nodeE.getAttribute("id");
                    classFieldInfo.setId(id);
                    classInfo.addFieldInfo(classFieldInfo);
                    nodeStackOrder.add(new NodeFieldClass(currentNode, child));
                }
                if (nodeName.equals(XMLParseUtils.ENTITYFIELD)) {
                    if (classInfo.getName() == null) {
                        classInfo.setName(XMLParseUtils.getTextContent(currentNode));
                    }
                }
                if (nodeName.equals(XMLParseUtils.FIELDNAME)) {
                    classInfo.setName(XMLParseUtils.getTextContent(currentNode));
                }
            }
            // The iteration is over, on this floor. Put all from this stack to first stack to
            // preserve order
            while (!nodeStackOrder.isEmpty()) {
                NodeFieldClass nodeFieldClass = nodeStackOrder.pop();
                nodesToGo.add(nodeFieldClass);
            }
        }
        return rootAFClassInfo;
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

    /**
     * This is helper class, which hold node and classInfo.
     * @author Martin Tomasek (martin@toms-cz.com)
     *
     * @since 1.0.0.
     */
    class NodeFieldClass {
        public Node node;
        public AFClassInfo classInfo;

        public NodeFieldClass(Node node, AFClassInfo classInfo) {
            this.node = node;
            this.classInfo = classInfo;
        }
    }

}
