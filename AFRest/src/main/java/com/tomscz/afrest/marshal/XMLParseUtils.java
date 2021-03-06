package com.tomscz.afrest.marshal;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tomscz.afrest.commons.AFRestUtils;
import com.tomscz.afrest.commons.SupportedLayoutsProperties;
import com.tomscz.afrest.commons.SupportedProperties;
import com.tomscz.afrest.commons.SupportedValidations;
import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.layout.Layout;
import com.tomscz.afrest.layout.definitions.LabelPosition;
import com.tomscz.afrest.layout.definitions.LayouDefinitions;
import com.tomscz.afrest.layout.definitions.LayoutOrientation;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.AFValidationRule;

/**
 * This class holds all string value of XML tags which are allowed in AFRest and not inside enum
 * {@link SupportedLayoutsProperties}, {@link SupportedProperties} class, which holds supported
 * variables.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class XMLParseUtils {

    // ROOT OF XML
    public static final String AFRESTROOT = "afRestEntity";
    public static final String ENTITYFIELD = "entityName";
    public static final String MAINLAYOUT = "mainLayoutDefinition";
    public static final String MAINLAYOUTORIENTATION = "mainLayoutOrientation";
    public static final String WIDGETROOT = "widget";
    public static final String ENTITYCLASS = "entityClass";
    public static final String ROOTCLASS = "entity";

    // Subsection of entity class
    public static final String ENTITYFIELDTYPE = "entityFieldType";
    public static final String FIELDNAME = "fieldName";

    // Subsections of root.
    public static final String WIDGETVALIDATIONS = "validations";
    public static final String WIDGETLAYOUT = "fieldLayout";
    public static final String OPTIONS = "options";

    /**
     * This method return text value of node. Null if node is null.
     * 
     * @param node which value will be returned
     * @return String value of node given in parameter. Null if node is null
     */
    public static String getTextContent(Node node) {
        if (node != null) {
            return node.getTextContent();
        }
        return null;
    }

    /**
     * This method set layout property to fieldInfo. It set only one property. All parameters must
     * be not null.
     * 
     * @param fieldInfo to which will be property set
     * @param propertyName name of property which will be localized in
     *        {@link SupportedLayoutsProperties}
     * @param propertyValue value of property which will be set to concrete property
     */
    public static void setLayoutProperties(AFFieldInfo fieldInfo, String propertyName,
            String propertyValue) {
        SupportedLayoutsProperties propertyType =
                (SupportedLayoutsProperties) AFRestUtils.getEnumFromString(
                        SupportedLayoutsProperties.class, propertyName, true);
        if (propertyType == null) {
            return;
        }
        if (propertyType.equals(SupportedLayoutsProperties.LAYOUT)) {
            Layout layout = fieldInfo.getLayout();
            layout.setLayoutDefinition((LayouDefinitions) AFRestUtils.getEnumFromString(
                    LayouDefinitions.class, propertyValue, false));
        } else if (propertyType.equals(SupportedLayoutsProperties.LABELPOSSTION)) {
            Layout layout = fieldInfo.getLayout();
            layout.setLabelPosstion((LabelPosition) AFRestUtils.getEnumFromString(
                    LabelPosition.class, propertyValue, false));
        } else if (propertyType.equals(SupportedLayoutsProperties.LAYOUTORIENTATION)) {
            fieldInfo.getLayout().setLayoutOrientation(
                    (LayoutOrientation) AFRestUtils.getEnumFromString(LayoutOrientation.class,
                            propertyValue, false));
        }
    }

    /**
     * This method set validation property to fieldInfo. It set only one property. All parameters
     * must be not null.
     * 
     * @param fieldInfo to which will be property set
     * @param propertyName name of property which will be localized in
     * @param propertyValue value of property which will be set to concrete property
     */
    public static void setValidationsProperties(AFFieldInfo fieldInfo, String propertyName,
            String propertyValue) {
        SupportedValidations propertyType =
                (SupportedValidations) AFRestUtils.getEnumFromString(SupportedValidations.class,
                        propertyName, true);
        if (propertyType == null || propertyValue == null || propertyValue.isEmpty()) {
            return;
        }
        AFValidationRule rule = new AFValidationRule(propertyType, propertyValue);
        fieldInfo.addRule(rule);
    }

    /**
     * This method set root property to fieldInfo. It set only one property. All parameters must be
     * not null.
     * 
     * @param fieldInfo to which will be property set
     * @param propertyName name of property which will be localized in {@link SupportedProperties}
     * @param propertyValue value of property which will be set to concrete property
     */
    public static void setRootProperties(AFFieldInfo fieldInfo, String propertyName,
            String propertyValue) {
        SupportedProperties propertyType =
                (SupportedProperties) AFRestUtils.getEnumFromString(SupportedProperties.class,
                        propertyName, true);
        if (propertyType == null) {
            return;
        }
        if (propertyType.equals(SupportedProperties.WIDGETTYPE)) {
            String value = propertyValue.toLowerCase();
            for (SupportedWidgets supportedWidget : SupportedWidgets.values()) {
                if (value.equals(supportedWidget.toString().toLowerCase())) {
                    fieldInfo.setWidgetType(supportedWidget);
                }
            }
            return;
        }
        if (propertyType.equals(SupportedProperties.FIELDNAME)) {
            fieldInfo.setId(propertyValue);
            return;
        }
        if (propertyType.equals(SupportedProperties.LABEL)) {
            fieldInfo.setLabel(propertyValue);
            return;
        }
        if (propertyType.equals(SupportedProperties.OPTIONS)) {
            // TODO set it to class
        }
        if (propertyType.equals(SupportedProperties.READNOLY)) {
            //readonly is default false
            if (propertyValue != null && !propertyValue.isEmpty()) {
                if (propertyValue.toLowerCase().equals("true")) {
                    fieldInfo.setReadOnly(true);
                    return;
                }
            }
            fieldInfo.setReadOnly(false);
            return;
        }
        if (propertyType.equals(SupportedProperties.VISIBLE)) {
            // Visible is default true
            if (propertyValue != null && !propertyValue.isEmpty()) {
                if (propertyValue.toLowerCase().equals("false")) {
                    fieldInfo.setVisible(false);
                    return;
                }
            }
            fieldInfo.setVisible(true);
        }
    }

    /**
     * This method transform input string to {@link Document} which can be used to search via DOM,
     * SAX or XPath,etc.
     * 
     * @param stringToXml string which will be converted to XML
     * @return converted document to XML.
     * @throws MetamodelException if transformation is not possible then this exception is thrown
     */
    public static Document transformStringToXml(String stringToXml) throws MetamodelException {
        Document doc;
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(stringToXml));
            doc = db.parse(is);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new MetamodelException(
                    "Error during intialize DOM. Check input data or templates. String to parse is: "
                            + stringToXml + " Exception is: " + e.getLocalizedMessage());
        }
        return doc;
    }

}
