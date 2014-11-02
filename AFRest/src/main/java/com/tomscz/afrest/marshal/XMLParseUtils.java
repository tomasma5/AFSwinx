package com.tomscz.afrest.marshal;

import org.w3c.dom.Node;

import com.tomscz.afrest.commons.AFRestUtils;
import com.tomscz.afrest.commons.SupportedLayoutsProperties;
import com.tomscz.afrest.commons.SupportedProperties;
import com.tomscz.afrest.commons.SupportedValidations;
import com.tomscz.afrest.commons.SupportedValidationsProperties;
import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.layout.Layout;
import com.tomscz.afrest.layout.definitions.LabelPosition;
import com.tomscz.afrest.layout.definitions.LayouDefinitions;
import com.tomscz.afrest.layout.definitions.LayoutOrientation;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.AFValidationRule;

/**
 * This class holds all string value of XML tags which are allowed in AFRest and not inside enum
 * {@link SupportedLayoutsProperties}, {@link SupportedValidationsProperties},
 * {@link SupportedProperties} class, which holds supported variables.
 * 
 * This constants should reflect XSD
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
class XMLParseUtils {

    // ROOT OF XML
    public static final String AFRESTROOT = "afRestEntity";
    public static final String ENTITYFIELD = "entityField";
    public static final String MAINLAYOUT = "mainLayoutDefinition";
    public static final String MAINLAYOUTORIENTATION = "mainLayoutOrientation";
    public static final String WIDGETROOT = "widget";

    // Subsections of root.
    public static final String WIDGETVALIDATIONS = "validations";
    public static final String WIDGETLAYOUT = "fieldLayout";

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
     * This method set layout property to fieldInfo. It set only one property. All parameters must be not null.
     * @param fieldInfo to which will be property set
     * @param propertyName name of property which will be localized in {@link SupportedLayoutsProperties}
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
     * This method set validation property to fieldInfo. It set only one property. All parameters must be not null.
     * @param fieldInfo to which will be property set
     * @param propertyName name of property which will be localized in {@link SupportedValidationsProperties}
     * @param propertyValue value of property which will be set to concrete property
     */
    public static void setValidationsProperties(AFFieldInfo fieldInfo, String propertyName,
            String propertyValue) {
        SupportedValidationsProperties propertyType =
                (SupportedValidationsProperties) AFRestUtils.getEnumFromString(
                        SupportedValidationsProperties.class, propertyName, true);
        if (propertyType == null) {
            return;
        }
        if (propertyType.equals(SupportedValidationsProperties.REQUIRED)) {
            AFValidationRule requiredRule =
                    new AFValidationRule(SupportedValidations.REQUIRED, propertyValue);
            fieldInfo.addRule(requiredRule);
        }
    }
    
    /**
     * This method set root property to fieldInfo. It set only one property. All parameters must be not null.
     * @param fieldInfo to which will be property set
     * @param propertyName name of property which will be localized in {@link SupportedProperties}
     * @param propertyValue value of property which will be set to concrete property
     */
    public static void setRootProperties(AFFieldInfo fieldInfo, String propertyName, String propertyValue) {
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
        } else if (propertyType.equals(SupportedProperties.FIELDNAME)) {
            fieldInfo.setId(propertyValue);
        } else if (propertyType.equals(SupportedProperties.LABEL)) {
            fieldInfo.setLabel(propertyValue);
        }
    }

}
