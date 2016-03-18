using System;
using System.Collections.Generic;
using AFWinPhone.enums;

namespace AFWinPhone.components.parts
{
    public class FieldInfo
    {
        private SupportedWidgets widgetType;
        private String id;
        private String labelText;
        private bool innerClass;
        private bool readOnly;
        private bool visible;
        private LayoutProperties layout;
        private List<ValidationRule> rules;
        private List<FieldOption> options;

        public void addRule(ValidationRule rule)
        {
            if (rules == null)
            {
                rules = new List<ValidationRule>();
            }
            rules.Add(rule);
        }

        public void addOption(FieldOption option)
        {
            if (options == null)
            {
                options = new List<FieldOption>();
            }
            options.Add(option);
        }

        public SupportedWidgets getWidgetType()
        {
            return this.widgetType;
        }

        public void setWidgetType(SupportedWidgets widgetType)
        {
            this.widgetType = widgetType;
        }

        public String getId()
        {
            return this.id;
        }

        public void setId(String id)
        {
            this.id = id;
        }

        public String getLabelText()
        {
            return this.labelText;
        }

        public void setLabelText(String labelText)
        {
            this.labelText = labelText;
        }

        public bool isInnerClass()
        {
            return this.innerClass;
        }

        public void setIsClass(bool isClass)
        {
            this.innerClass = isClass;
        }

        public bool isReadOnly()
        {
            return this.readOnly;
        }

        public void setReadOnly(bool readOnly)
        {
            this.readOnly = readOnly;
        }

        public bool isVisible()
        {
            return this.visible;
        }

        public void setVisible(bool visible)
        {
            this.visible = visible;
        }

        public LayoutProperties getLayout()
        {
            return this.layout;
        }

        public void setLayout(LayoutProperties layout)
        {
            this.layout = layout;
        }

        public List<ValidationRule> getRules()
        {
            return this.rules;
        }

        public List<FieldOption> getOptions()
        {
            return this.options;
        }

    }
}
