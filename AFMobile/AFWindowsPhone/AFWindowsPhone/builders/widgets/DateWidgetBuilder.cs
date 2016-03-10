using AFWindowsPhone.builders.components.parts;
using AFWindowsPhone.builders.skins;
using AFWindowsPhone.utils;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Media;

namespace AFWindowsPhone.builders.widgets
{
    class DateWidgetBuilder : BasicBuilder
    {
        private String dateFormat;

        public DateWidgetBuilder(Skin skin, FieldInfo properties) : base(skin, properties)
        {
            this.dateFormat = "dd.MM.yyyy"; //Default date format
        }


        public override FrameworkElement buildFieldView()
        {
            StackPanel dateLayout = new StackPanel();
            dateLayout.Orientation = Orientation.Horizontal;

            TextBox dateText = new TextBox();
            dateText.VerticalAlignment = VerticalAlignment.Top;
            dateText.HorizontalAlignment = HorizontalAlignment.Stretch;
            //dateText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            dateText.Foreground = new SolidColorBrush(getSkin().getFieldColor());
            dateText.FontFamily = getSkin().getFieldFont();
            dateText.PlaceholderText = dateFormat;
            /*dateText.setFocusable(false);
            dateText.setClickable(true);*/

            //TODO umoznit clear
            //TODO kdyz nastavim ve swingu datum tak se zobrazuje datum o jeden den nizsi
            dateText.Tapped += async (sender, args) =>
            {
                //TODO datepicker
            };


            if (getProperties().isReadOnly())
            {
                dateText.IsEnabled = false;
                dateText.Foreground = new SolidColorBrush(Colors.LightGray);
            }
            return dateText;
        }

        public override void setData(AFField field, Object value)
        {
            TextBox dateText = (TextBox)field.getFieldView();
            DateTime? date = Utils.parseDate(value.ToString());
            if (date != null)
            {
                dateText.Text = date.Value.ToString("dd.MM.yyyy", CultureInfo.InvariantCulture);
                field.setActualData(date.Value.ToString("dd.MM.yyyy", CultureInfo.InvariantCulture));
            }
            else {
                //parsing totally failed maybe exception
            }
        }

        public override Object getData(AFField field)
        {
            TextBox dateText = (TextBox)field.getFieldView();
            DateTime? date = Utils.parseDate(dateText.Text);
            if (date != null)
            {
                return date.Value.ToString("yyyy-MM-dd'T'HH:mm:ss.SSSZ", CultureInfo.InvariantCulture);
            }
            return null;
        }
    }
}
