package com.tomscz.afswinx.marshal;

import com.tomscz.afswinx.common.SupportedProperties;
import com.tomscz.afswinx.common.SupportedWidgets;
import com.tomscz.afswinx.common.Utils;
import com.tomscz.afswinx.exception.MetamodelException;
import com.tomscz.afswinx.layout.Layout;
import com.tomscz.afswinx.layout.definitions.LabelPossition;
import com.tomscz.afswinx.layout.definitions.LayouDefinitions;
import com.tomscz.afswinx.marshal.utils.DataParserHelper;
import com.tomscz.afswinx.rest.dto.AFClassInfo;
import com.tomscz.afswinx.rest.dto.AFFieldInfo;
import com.tomscz.afswinx.rest.dto.AFMetaModelPack;
import com.tomscz.afswinx.rest.dto.AFValidationRule;
import com.tomscz.afswinx.validation.AFSwinxValidations;

public class FormBuilder implements ModelBuilder {

    private String metaModelInformation;

    public FormBuilder(String metamodelInformation) {
        this.metaModelInformation = metamodelInformation;
    }

    @Override
    public AFMetaModelPack buildModel() throws MetamodelException {
        if (metaModelInformation == null || metaModelInformation.isEmpty()) {
            throw new MetamodelException();
        }
        AFMetaModelPack model = new AFMetaModelPack();
        model.setClassInfo(transforDataToModel(metaModelInformation));
        return model;
    }

    private AFClassInfo transforDataToModel(String metaModelInfomation) throws MetamodelException {
        AFClassInfo classInfo = new AFClassInfo();
        String[] fields = metaModelInfomation.split(DataParserHelper.getFildSplitter());

        for (int i = 1; i < fields.length; i++) {
            AFFieldInfo fieldInfo = createFieldProperties(fields[i]);
            classInfo.addFieldInfo(fieldInfo);
        }
        return classInfo;
    }

    private AFFieldInfo createFieldProperties(String field) throws MetamodelException {
        String properties[] = field.split(DataParserHelper.getFieldPropertySplitter());
        AFFieldInfo fieldInfo = new AFFieldInfo();
        for (String property : properties) {
            if (property == null) {
                throw new MetamodelException();
            }
            String propertyArray[] = property.split(DataParserHelper.getPropertyAndValueSplitter());
            if (propertyArray.length < 2) {
                continue;
            }
            String propertyName = propertyArray[0].toLowerCase();
            boolean isSupported = false;
            SupportedProperties propertyType = null;
            for (SupportedProperties supportedProperty : SupportedProperties.values()) {
                if (propertyName.equals(supportedProperty.toString().toLowerCase())) {
                    isSupported = true;
                    propertyType = supportedProperty;
                    break;
                }
            }
            if (!isSupported) {
                throw new MetamodelException();
            }
            // Create field info
            if (propertyType.equals(SupportedProperties.WIDGETTYPE)) {
                String value = propertyArray[1].toLowerCase();
                for (SupportedWidgets supportedWidget : SupportedWidgets.values()) {
                    if (value.equals(supportedWidget.toString().toLowerCase())) {
                        fieldInfo.setWidgetType(supportedWidget);
                    }
                }
            }
            if (propertyType.equals(SupportedProperties.FIELDNAME)) {
                fieldInfo.setId(propertyArray[1]);
                continue;
            }
            if (propertyType.equals(SupportedProperties.LABEL)) {
                fieldInfo.setLabel(propertyArray[1]);
                continue;
            }
            if (propertyType.equals(SupportedProperties.REQUIRED)) {
                AFValidationRule requiredRule =
                        new AFValidationRule(AFSwinxValidations.REQUIRED, propertyArray[1]);
                fieldInfo.addRule(requiredRule);
                continue;
            }
            if (propertyType.equals(SupportedProperties.LAYOUT)) {
                String layoutType = propertyArray[1];
                Layout layout = fieldInfo.getLayout();
                layout.setLayoutDefinition((LayouDefinitions) Utils.getEnumFromString(
                        LayouDefinitions.class, layoutType));
                continue;
            }
            if (propertyType.equals(SupportedProperties.LABELPOSSTION)) {
                String labelPosstion = propertyArray[1];
                Layout layout = fieldInfo.getLayout();
                layout.setLabelPosstion((LabelPossition) Utils.getEnumFromString(
                        LabelPossition.class, labelPosstion));
                continue;
            }
        }
        return fieldInfo;
    }

}
