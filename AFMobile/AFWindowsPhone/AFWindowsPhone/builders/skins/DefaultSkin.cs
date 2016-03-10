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
            return 
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
            throw new NotImplementedException();
        }

        public int getLabelHeight()
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
            return HorizontalAlignment.Stretch;
        }

        public int getListHeight()
        {
            return 200;
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
            throw new NotImplementedException();
        }

        public int getListItemNamePaddingBottom()
        {
            throw new NotImplementedException();
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
            throw new NotImplementedException();
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

        public int getListWidth()
        {
            return 0;
        }

        public Color getValidationColor()
        {
            throw new NotImplementedException();
        }

        public FontFamily getValidationFont()
        {
            throw new NotImplementedException();
        }

        public bool isListItemNameLabelVisible()
        {
            throw new NotImplementedException();
        }

        public bool isListItemTextLabelsVisible()
        {
            throw new NotImplementedException();
        }

        public bool isListScrollBarAlwaysVisible()
        {
            throw new NotImplementedException();
        }
    }
}
