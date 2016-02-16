package cz.cvut.fel.matyapav.afandroid.parsers;

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
import cz.cvut.fel.matyapav.afandroid.utils.Constants;

/**
 * Created by Pavel on 17.12.2015.
 */
public class JSONDefinitionParser implements JSONParser {

    @Override
    public ClassDefinition parse(JSONObject classInfo){
        ClassDefinition definition = null;

        try {
            //Parse class name and create data pack with this class name
            System.err.println("PARSING CLASS "+ classInfo.getString(Constants.CLASS_NAME));
            definition = new ClassDefinition(classInfo.getString(Constants.CLASS_NAME));

            //Parse layout
            JSONObject layout = classInfo.getJSONObject(Constants.LAYOUT); //TODO consider changing to optJSONarray
            definition.setLayout(createLayoutProperties(layout));

            //Parse fields
            JSONArray fields = classInfo.getJSONArray(Constants.FIELD_INFO); //TODO consider changing to optJSONarray
            if(fields != null){
                for (int i = 0; i < fields.length(); i++) {
                    JSONObject field = fields.getJSONObject(i);
                    definition.addFieldInfo(parseFieldInfo(field));
                }
            }
            JSONArray innerClasses = classInfo.optJSONArray(Constants.INNER_CLASSES);
            if(innerClasses != null){
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
        System.err.println("PARSING FIELD "+field.getString(Constants.ID));
        FieldInfo fieldInfo = new FieldInfo();
        fieldInfo.setWidgetType(field.getString(Constants.WIDGET_TYPE));
        fieldInfo.setId(field.getString(Constants.ID));
        fieldInfo.setLabel(field.getString(Constants.LABEL));
        fieldInfo.setIsClass(Boolean.valueOf(field.getString(Constants.CLASS_TYPE)));
        fieldInfo.setVisible(Boolean.valueOf(field.getString(Constants.VISIBLE)));
        fieldInfo.setReadOnly(Boolean.valueOf(field.getString(Constants.READ_ONLY)));
        //field layout
        fieldInfo.setLayout(createLayoutProperties(field.getJSONObject(Constants.LAYOUT)));
        //rules
        JSONArray rules = field.optJSONArray(Constants.RULES);
        if(rules != null) {
            for (int i = 0; i < rules.length(); i++) {
                fieldInfo.addRule(createRule(rules.getJSONObject(i)));
            }
        }
        //options
        JSONArray options = field.optJSONArray(Constants.OPTIONS);
        if(options != null){
            for (int i = 0; i < options.length(); i++) {
                fieldInfo.addOption(createOption(options.getJSONObject(i)));
            }
        }

        return fieldInfo;
    }

    private LayoutProperties createLayoutProperties(JSONObject layoutJson) throws JSONException {
        LayoutProperties layoutProp = new LayoutProperties();
        try {
            String layDefName = layoutJson.optString(Constants.LAYOUT_DEF);
            LayoutDefinitions layDef = LayoutDefinitions.valueOf(layDefName);
            if(layDef != null){
                layoutProp.setLayoutDefinition(layDef);
            }

            String orientation = layoutJson.optString(Constants.LAYOUT_ORIENT);
            LayoutOrientation layOrient = LayoutOrientation.valueOf(orientation);
            if(layOrient != null){
                layoutProp.setLayoutOrientation(layOrient);
            }

            String position  = layoutJson.optString(Constants.LABEL_POS);

            LabelPosition labelPos = LabelPosition.valueOf(position);
            if (labelPos != null) {
                layoutProp.setLabelPossition(labelPos);
            }
        }catch(IllegalArgumentException e){
            System.err.println(e.getLocalizedMessage());
            //e.printStackTrace();
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
