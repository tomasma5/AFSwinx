using AFWinPhone.components.parts;
using AFWinPhone.components.types;
using AFWinPhone.enums;
using AFWinPhone.rest.connection;
using System;
using System.Diagnostics;
using System.Globalization;
using System.Reflection;
using System.Text;
using System.Xml.Linq;
using Windows.Data.Json;
using Windows.Data.Xml.Dom;

namespace AFWinPhone.utils
{
    class Utils
    {

        public static bool IsFieldWritable(SupportedWidgets widgetType)
        {
            return widgetType.Equals(SupportedWidgets.TEXTFIELD) || widgetType.Equals(SupportedWidgets.NUMBERFIELD)
                    || widgetType.Equals(SupportedWidgets.NUMBERDOUBLEFIELD);
        }

        public static bool IsFieldNumberField(AFField field)
        {
            return field.getFieldInfo().getWidgetType().Equals(SupportedWidgets.NUMBERFIELD)
                    || field.getFieldInfo().getWidgetType().Equals(SupportedWidgets.NUMBERDOUBLEFIELD);
        }

        public static String GetConnectionEndPoint(AFSwinxConnection connection)
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

        public static bool ShouldBeInvisible(String column, AFComponent component)
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

        public static DateTime? ParseDate(String date)
        {
            
            String[] formats = { "ISO8601", "dd.MM.yyyy" };
            if (date != null)
            {
                foreach (String format in formats)
                {
                    try
                    {
                        if (format.Equals("ISO8601"))
                        {
                            return DateTime.Parse(date);
                        }
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

        public  static XmlDocument BuildDocumentFromFile(String pathToFile)
        {
            XmlDocument doc = new XmlDocument();
            try { 
            doc.LoadXml(XDocument.Load(pathToFile).ToString());
            }
            catch (Exception e)
            {
                Debug.WriteLine("File not found");
                Debug.WriteLine(e.StackTrace);
            }
            return doc;
        }

        public static T ValueOf<T>(Type clazz, String value)
        {
            foreach (System.Reflection.FieldInfo field in clazz.GetRuntimeFields())
            {
                try
                {
                    T enumeration = (T) field.GetValue(null);
                    foreach (System.Reflection.FieldInfo enumField in enumeration.GetType().GetRuntimeFields())
                    {
                        if (enumField.GetValue(enumeration).Equals(value))
                        {
                            return enumeration;
                        }

                    }
                }
                catch (Exception e)
                {
                    Debug.WriteLine(e.Message); //do nothing only inform about it
                }

            }
            return default(T); //not found in enum

        }

        public static Object TryToGetValueFromJson(IJsonValue value)
        {
            switch (value.ValueType)
            {
                case JsonValueType.Null:
                    return null;
                case JsonValueType.String:
                    return value.GetString();
                case JsonValueType.Boolean:
                    return value.GetBoolean();
                case JsonValueType.Object:
                    return value.GetObject();
                case JsonValueType.Number:
                    return value.GetNumber();
                case JsonValueType.Array:
                    return value.GetArray();
                default:
                    return null;
            }
        }

        public static bool TryToConvertIntoBoolean(String value)
        {
            try
            {
                return Convert.ToBoolean(value);
            }
            catch (Exception)
            {
                return false;
            }
        }
    }
}
