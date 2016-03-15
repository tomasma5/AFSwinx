using AFWindowsPhone.builders.components.parts;
using AFWindowsPhone.builders.skins;
using AFWindowsPhone.enums;
using AFWindowsPhone.utils;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AFWindowsPhone.builders.widgets
{
    class WidgetBuilderFactory
    {
        private static WidgetBuilderFactory instance = null;

        public static WidgetBuilderFactory getInstance()
        {
            if (instance == null)
            {
                instance = new WidgetBuilderFactory();
            }
            return instance;
        }


        public AbstractWidgetBuilder getFieldBuilder(FieldInfo properties, Skin skin)
        {
            if (Utils.IsFieldWritable(properties.getWidgetType()))
            {
                return new TextWidgetBuilder(skin, properties);
            }
            if (properties.getWidgetType().Equals(SupportedWidgets.CALENDAR))
            {
                return new DateWidgetBuilder(skin, properties);
            }
            if (properties.getWidgetType().Equals(SupportedWidgets.OPTION))
            {
                return new OptionWidgetBuilder(skin, properties);
            }
            if (properties.getWidgetType().Equals(SupportedWidgets.DROPDOWNMENU))
            {
                return new DropDownWidgetBuilder(skin, properties);
            }
            if (properties.getWidgetType().Equals(SupportedWidgets.CHECKBOX))
            {
                return new CheckboxWidgetBuilder(skin, properties);
            }
            if (properties.getWidgetType().Equals(SupportedWidgets.PASSWORD))
            {
                return new PasswordWidgetBuilder(skin, properties);
            }
            Debug.WriteLine("BUILDER FOR " + properties.getWidgetType() + " NOT FOUND");
            return null;
        }
    }
}
