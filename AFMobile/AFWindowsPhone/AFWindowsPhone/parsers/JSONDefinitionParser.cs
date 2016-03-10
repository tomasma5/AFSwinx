using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using AFWindowsPhone.builders.components.parts;
using System.Diagnostics;
using AFWindowsPhone.enums;
using AFWindowsPhone.utils;
using Windows.Data.Json;

namespace AFWindowsPhone.parsers
{
    class JSONDefinitionParser : JSONParser
    {
        public ClassDefinition parse(JsonObject classInfo)
        {
            ClassDefinition definition = null;

            try
            {
                //Parse class name and create data pack with this class name
                Debug.WriteLine("PARSING CLASS " + classInfo[Constants.CLASS_NAME]);
                definition = new ClassDefinition(classInfo[Constants.CLASS_NAME].GetString());

                //Parse layout
                JsonObject layout = classInfo[Constants.LAYOUT].GetObject();
                definition.setLayout(createLayoutProperties(layout));

                //Parse fields
                JsonArray fields = classInfo[Constants.FIELD_INFO].GetArray();
                if (fields != null)
                {
                    for (int i = 0; i < fields.Count; i++)
                    {
                        JsonObject field = fields[i].GetObject();
                        definition.addFieldInfo(parseFieldInfo(field));
                    }
                }
                JsonArray innerClasses = classInfo[Constants.INNER_CLASSES].GetArray();
                if (innerClasses != null)
                {
                    for (int i = 0; i < innerClasses.Count; i++)
                    {
                        JsonObject innerClass = innerClasses[i].GetObject();
                        definition.addInnerClass(parse(innerClass)); //recursion;
                    }
                }
            }
            catch (Exception e)
            {
                Debug.WriteLine(e.StackTrace);
            }

            return definition;
        }

        private FieldInfo parseFieldInfo(JsonObject field)
        {
            Debug.WriteLine("PARSING FIELD " + field[Constants.ID].GetString());
            FieldInfo fieldInfo = new FieldInfo();
            try
            {
                fieldInfo.setWidgetType(SupportedWidgets.valueOf(field[Constants.WIDGET_TYPE].GetString()));
            }
            catch (Exception e)
            {
                Debug.WriteLine(e.Message);
            }
            fieldInfo.setId(field[Constants.ID].GetString());

            fieldInfo.setLabelText(field[Constants.LABEL].Equals(null) ? null : field[Constants.LABEL].ToString());
            fieldInfo.setIsClass(Convert.ToBoolean(field[Constants.CLASS_TYPE].GetString()));
            fieldInfo.setVisible(Convert.ToBoolean(field[Constants.VISIBLE].GetString()));
            fieldInfo.setReadOnly(Convert.ToBoolean(field[Constants.READ_ONLY].GetString()));
            //field layout
            fieldInfo.setLayout(createLayoutProperties(field[Constants.LAYOUT].GetObject()));
            //rules
            JsonArray rules = field[Constants.RULES].GetArray();
            if (rules != null)
            {
                for (int i = 0; i < rules.Count; i++)
                {
                    fieldInfo.addRule(createRule(rules[i].GetObject()));
                }
            }
            //options
            JsonArray options = field[Constants.OPTIONS].GetArray();
            if (options != null)
            {
                for (int i = 0; i < options.Count; i++)
                {
                    fieldInfo.addOption(createOption(options[i].GetObject()));
                }
            }

            return fieldInfo;
        }


        private LayoutProperties createLayoutProperties(JsonObject layoutJson)
        {
            LayoutProperties layoutProp = new LayoutProperties();
            if (layoutJson == null)
            {
                layoutProp.setLayoutDefinition(LayoutDefinitions.ONECOLUMNLAYOUT);
                layoutProp.setLayoutOrientation(LayoutOrientation.AXISX);
                layoutProp.setLabelPosition(LabelPosition.BEFORE);
                return layoutProp; //with default values
            }

            try
            {
                String layDefName = layoutJson[Constants.LAYOUT_DEF].GetString();
                LayoutDefinitions layDef = LayoutDefinitions.valueOf(layDefName);
                if (layDef != null)
                {
                    layoutProp.setLayoutDefinition(layDef);
                }

                String orientation = layoutJson[Constants.LAYOUT_ORIENT].GetString();
                LayoutOrientation layOrient = LayoutOrientation.valueOf(orientation);
                if (layOrient != null)
                {
                    layoutProp.setLayoutOrientation(layOrient);
                }

                String position = layoutJson[Constants.LABEL_POS].GetString();

                LabelPosition labelPos = LabelPosition.valueOf(position);
                if (labelPos != null)
                {
                    layoutProp.setLabelPosition(labelPos);
                }
            }
            catch (Exception e)
            {
                Debug.WriteLine(e.Message);
                //e.printStackTrace();
            }
            return layoutProp;
        }

        private ValidationRule createRule(JsonObject ruleJson)
        {
            ValidationRule rule = new ValidationRule();
            rule.setValidationType(ruleJson[Constants.VALIDATION_TYPE].GetString());
            rule.setValue(ruleJson[Constants.VALUE].GetString());
            return rule;
        }

        private FieldOption createOption(JsonObject optionJson)
        {
            FieldOption option = new FieldOption();
            option.setKey(optionJson[Constants.KEY].GetString());
            option.setValue(optionJson[Constants.VALUE].GetString());
            return option;
        }
    }
}
