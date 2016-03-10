using AFWindowsPhone.builders.components.parts;
using AFWindowsPhone.builders.components.types;
using AFWindowsPhone.enums;
using AFWindowsPhone.rest.connection;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.Data.Xml.Dom;
using Windows.Storage.Streams;

namespace AFWindowsPhone.utils
{
    class Utils
    {

        public static bool isFieldWritable(SupportedWidgets widgetType)
        {
            return widgetType.Equals(SupportedWidgets.TEXTFIELD) || widgetType.Equals(SupportedWidgets.NUMBERFIELD)
                    || widgetType.Equals(SupportedWidgets.NUMBERDOUBLEFIELD);
        }

        public static bool isFieldNumberField(AFField field)
        {
            return field.getFieldInfo().getWidgetType().Equals(SupportedWidgets.NUMBERFIELD)
                    || field.getFieldInfo().getWidgetType().Equals(SupportedWidgets.NUMBERDOUBLEFIELD);
        }

        public static String getConnectionEndPoint(AFSwinxConnection connection)
        {
            StringBuilder endPointBuilder = new StringBuilder();
            if (!String.IsNullOrEmpty(connection.getProtocol()))
            {
                endPointBuilder.Append(connection.getProtocol());
                endPointBuilder.Append("://");
            }
            if (!String.IsNullOrEmpty(connection.getAddress()))
            {
                endPointBuilder.Append(connection.getAddress());
            }
            if (connection.getPort() != 0)
            {
                endPointBuilder.Append(":");
                endPointBuilder.Append(connection.getPort());
            }
            if (!String.IsNullOrEmpty(connection.getParameters()))
            {
                endPointBuilder.Append(connection.getParameters());
            }
            return endPointBuilder.ToString();
        }

        public static bool shouldBeInvisible(String column, AFComponent component)
        {
            foreach (AFField field in component.getFields())
            {
                if (field.getId().Equals(column))
                {
                    return !field.getFieldInfo().isVisible();
                }
            }
            return true;
        }

        public static DateTime? parseDate(String date)
        {
            String[] formats = { "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "dd.MM.yyyy" };
            if (date != null)
            {
                foreach (String format in formats)
                {
                    try
                    {
                        return DateTime.ParseExact(date, format, CultureInfo.InvariantCulture);
                    }
                    catch (FormatException e)
                    {
                        Debug.WriteLine("Cannot parse date " + date + " using format " + format);
                        Debug.WriteLine(e.StackTrace);
                    }
                }
            }
            return null;
        }

        public static void getEnumByValue()
        {

        }

        public static XmlDocument buildDocumentFromFile(String pathToFile) 
        {
            XmlDocument doc = new XmlDocument();
            doc.LoadXml(pathToFile);
            return doc;
        }
    }
}
