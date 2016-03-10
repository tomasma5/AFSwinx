using AFWindowsPhone.builders.components.parts;
using AFWindowsPhone.builders.components.types;
using AFWindowsPhone.enums;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
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
            if (connection.getProtocol() != null && !connection.getProtocol().isEmpty())
            {
                endPointBuilder.Append(connection.getProtocol());
                endPointBuilder.Append("://");
            }
            if (connection.getAddress() != null && !connection.getAddress().isEmpty())
            {
                endPointBuilder.Append(connection.getAddress());
            }
            if (connection.getPort() != 0)
            {
                endPointBuilder.Append(":");
                endPointBuilder.Append(connection.getPort());
            }
            if (connection.getParameters() != null && !connection.getParameters().isEmpty())
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

        public static void setCellParams(TextView cell, int gravity, int paddingLeft, int paddingRight,
                                   int paddingTop, int paddingBottom, int borderWidth, int borderColor)
        {
            //create border
            ShapeDrawable rect = new ShapeDrawable(new RectShape());
            rect.getPaint().setStyle(Paint.Style.STROKE);
            rect.getPaint().setColor(borderColor);
            rect.getPaint().setStrokeWidth(borderWidth);

            cell.setGravity(gravity);
            cell.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            cell.setBackground(rect);
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
                    }
                }
            }
            return null;
        }
    }
}
