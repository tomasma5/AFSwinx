package cz.cvut.fel.matyapav.afandroid.parsers;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.cvut.fel.matyapav.afandroid.components.parts.ClassDefinition;
import cz.cvut.fel.matyapav.afandroid.components.parts.FieldInfo;
import cz.cvut.fel.matyapav.afandroid.components.parts.FieldOption;
import cz.cvut.fel.matyapav.afandroid.components.parts.LayoutProperties;
import cz.cvut.fel.matyapav.afandroid.components.parts.ValidationRule;
import cz.cvut.fel.matyapav.afandroid.enums.LabelPosition;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutDefinitions;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedWidgets;
import cz.cvut.fel.matyapav.afandroid.utils.Constants;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class JSONDefinitionParser implements JSONParser {

    @Override
    public ClassDefinition parse(JSONObject classInfo) {
        ClassDefinition definition = null;

        try {
            //Parse class name and create data pack with this class name
            System.err.println("PARSING CLASS " + classInfo.getString(Constants.CLASS_NAME));
            definition = new ClassDefinition(classInfo.getString(Constants.CLASS_NAME));

            //Parse layout
            JSONObject layout = classInfo.optJSONObject(Constants.LAYOUT);
            definition.setLayout(createLayoutProperties(layout));

            //Parse fields
            JSONArray fields = classInfo.optJSONArray(Constants.FIELD_INFO);
            if (fields != null) {
                for (int i = 0; i < fields.length(); i++) {
                    JSONObject field = fields.getJSONObject(i);
                    definition.addFieldInfo(parseFieldInfo(field));
                }
            }
            JSONArray innerClasses = classInfo.optJSONArray(Constants.INNER_CLASSES);
            if (innerClasses != null) {
                for (int i = 0; i < innerClasses.length(); i++) {
                    JSONObject innerClass = innerClasses.getJSONObject(i);
                    definition.addInnerClass(parse(innerClass)); //recursion;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return definition;
    }

    private FieldInfo parseFieldInfo(JSONObject field) throws JSONException {
        System.out.println("PARSING FIELD " + field.getString(Constants.ID));
        FieldInfo fieldInfo = new FieldInfo();
        try {
            String widgetType = field.isNull(Constants.WIDGET_TYPE) ? null : field.getString(Constants.WIDGET_TYPE);
            if (widgetType != null) {
                fieldInfo.setWidgetType(SupportedWidgets.valueOf(widgetType));
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getLocalizedMessage());
        }
        fieldInfo.setId(field.getString(Constants.ID));

        fieldInfo.setLabelText(field.isNull(Constants.LABEL) ? null : field.getString(Constants.LABEL));
        fieldInfo.setIsClass(Boolean.valueOf(field.getString(Constants.CLASS_TYPE)));
        fieldInfo.setVisible(Boolean.valueOf(field.getString(Constants.VISIBLE)));
        fieldInfo.setReadOnly(Boolean.valueOf(field.getString(Constants.READ_ONLY)));
        //field layout
        fieldInfo.setLayout(createLayoutProperties(field.getJSONObject(Constants.LAYOUT)));
        //rules
        JSONArray rules = field.optJSONArray(Constants.RULES);
        if (rules != null) {
            for (int i = 0; i < rules.length(); i++) {
                fieldInfo.addRule(createRule(rules.getJSONObject(i)));
            }
        }
        //options
        JSONArray options = field.optJSONArray(Constants.OPTIONS);
        if (options != null) {
            for (int i = 0; i < options.length(); i++) {
                fieldInfo.addOption(createOption(options.getJSONObject(i)));
            }
        }

        return fieldInfo;
    }

    private LayoutProperties createLayoutProperties(JSONObject layoutJson) throws JSONException {
        LayoutProperties layoutProp = new LayoutProperties();
        if (layoutJson == null) {
            layoutProp.setLayoutDefinition(LayoutDefinitions.ONECOLUMNLAYOUT);
            layoutProp.setLayoutOrientation(LayoutOrientation.AXISX);
            layoutProp.setLabelPosition(LabelPosition.BEFORE);
            return layoutProp; //with default values
        }

        String layDefName = layoutJson.isNull(Constants.LAYOUT_DEF) ? null : layoutJson.optString(Constants.LAYOUT_DEF, null);
        if (layDefName != null) {
            LayoutDefinitions layDef = LayoutDefinitions.valueOf(layDefName);
            layoutProp.setLayoutDefinition(layDef);
        }
        String orientation = layoutJson.isNull(Constants.LAYOUT_ORIENT) ? null : layoutJson.optString(Constants.LAYOUT_ORIENT, null);
        if (orientation != null) {
            LayoutOrientation layOrient = LayoutOrientation.valueOf(orientation);
            layoutProp.setLayoutOrientation(layOrient);
        }
        String position = layoutJson.isNull(Constants.LABEL_POS) ? null : layoutJson.optString(Constants.LABEL_POS, null);
        if (position != null) {
            LabelPosition labelPos = LabelPosition.valueOf(position);
            layoutProp.setLabelPosition(labelPos);
        }

        return layoutProp;
    }

    private ValidationRule createRule(JSONObject ruleJson) throws JSONException {
        ValidationRule rule = new ValidationRule();
        rule.setValidationType(ruleJson.getString(Constants.VALIDATION_TYPE));
        rule.setValue(ruleJson.getString(Constants.VALUE));
        return rule;
    }

    private FieldOption createOption(JSONObject optionJson) throws JSONException {
        FieldOption option = new FieldOption();
        option.setKey(optionJson.getString(Constants.KEY));
        option.setValue(optionJson.getString(Constants.VALUE));
        return option;
    }
}
