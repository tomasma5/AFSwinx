using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.UI;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Media;

namespace AFWindowsPhone.builders.skins
{
    class DefaultSkin : Skin
    {
        public int getComponentMarginBottom()
        {
            return 0;
        }

        public int getComponentMarginLeft()
        {
            return 0;
        }

        public int getComponentMarginRight()
        {
            return 0;
        }

        public int getComponentMarginTop()
        {
            return 0;
        }

        public Color getFieldColor()
        {
            return Colors.Black;
        }

        public FontFamily getFieldFont()
        {
            return new FontFamily("Segoe UI");
        }

        public HorizontalAlignment getInputHorizontalAlignment()
        {
            return HorizontalAlignment.Stretch;
        }

        public int getInputWidth()
        {
            return 200;
        }

        public Color getLabelColor()
        {
            return Colors.Black;
        }

        public FontFamily getLabelFont()
        {
            return new FontFamily("Segoe UI");
        }

        public int getLabelHeight()
        {
            return -1; //see label verticall alignement
        }

        public HorizontalAlignment getLabelHorizontalAlignment()
        {
            return HorizontalAlignment.Left;
        }

        public VerticalAlignment getLabelVerticalAlignment()
        {
            return VerticalAlignment.Stretch;
        }

        public int getLabelWidth()
        {
            return 125;
        }

        public Color getListBorderColor()
        {
            return Colors.Black;
        }

        public float getListBorderWidth()
        {
            return 5;
        }

        public int getListContentWidth()
        {
            return -1; //see ListHorizontalAlignment
        }

        public int getListHeight()
        {
            return 200;
        }

        public HorizontalAlignment getListHorizontalAlignment()
        {
            return HorizontalAlignment.Stretch;
        }

        public Color getListItemBackgroundColor()
        {
            return Colors.Transparent;
        }

        public Color getListItemNameColor()
        {
            return Colors.Red;
        }

        public FontFamily getListItemNameFont()
        {
            return new FontFamily("Segoe UI");
        }

        public int getListItemNamePaddingBottom()
        {
            return 0;
        }

        public int getListItemNamePaddingLeft()
        {
            return 10;
        }

        public int getListItemNamePaddingRight()
        {
            return 0;
        }

        public int getListItemNamePaddingTop()
        {
            return 5;
        }

        public int getListItemNameSize()
        {
            return 16;
        }

        public int getListItemsTextSize()
        {
            return 10;
        }

        public Color getListItemTextColor()
        {
            return Colors.White;
        }

        public FontFamily getListItemTextFont()
        {
            return new FontFamily("Segoe UI");
        }

        public int getListItemTextPaddingBottom()
        {
            return 3;
        }

        public int getListItemTextPaddingLeft()
        {
            return 10;
        }

        public int getListItemTextPaddingRight()
        {
            return 5;
        }

        public int getListItemTextPaddingTop()
        {
            return 0;
        }

        public VerticalAlignment getListVerticalAlignment()
        {
            return VerticalAlignment.Stretch;
        }

        public int getListWidth()
        {
            return -1; //see ListHorizontal
        }

        public Color getValidationColor()
        {
            return Colors.Red;
        }

        public FontFamily getValidationFont()
        {
            return new FontFamily("Segoe UI");
        }

        public bool isListItemNameLabelVisible()
        {
            return true;
        }

        public bool isListItemTextLabelsVisible()
        {
            return true;
        }

        public bool isListScrollBarAlwaysVisible()
        {
            return false;
        }
    }
}
