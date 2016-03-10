using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AFWindowsPhone.enums
{
    public sealed class SupportedWidgets
    {
        public static readonly SupportedWidgets TEXTFIELD = new SupportedWidgets("TEXTFIELD");
        public static readonly SupportedWidgets PASSWORD = new SupportedWidgets("PASSWORD");
        public static readonly SupportedWidgets NUMBERFIELD = new SupportedWidgets("NUMBERFIELD");
        public static readonly SupportedWidgets NUMBERDOUBLEFIELD = new SupportedWidgets("NUMBERDOUBLEFIELD");
        public static readonly SupportedWidgets CALENDAR = new SupportedWidgets("CALENDAR");
        public static readonly SupportedWidgets OPTION = new SupportedWidgets("OPTION");
        public static readonly SupportedWidgets DROPDOWNMENU = new SupportedWidgets("DROPDOWNMENU");
        public static readonly SupportedWidgets CHECKBOX = new SupportedWidgets("CHECKBOX");

        private String widgetName;

        SupportedWidgets(String widgetType)
        {
            this.widgetName = widgetType;
        }

        public String getWidgetName()
        {
            return widgetName;
        }

        internal bool equals(SupportedWidgets tEXTFIELD)
        {
            throw new NotImplementedException();
        }
    }
}
