using System;

namespace AFWinPhone.enums
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
    }
}
