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
    interface Skin
    {

        int getComponentMarginLeft();
        int getComponentMarginRight();
        int getComponentMarginTop();
        int getComponentMarginBottom();

        //forms
        Color getLabelColor();

        Color getFieldColor();

        Color getValidationColor();

        int getInputWidth();
        HorizontalAlignment getInputHorizontalAlignment();

        FontFamily getValidationFont();

        FontFamily getFieldFont();

        FontFamily getLabelFont();

        int getLabelWidth();
        HorizontalAlignment getLabelHorizontalAlignment();
        int getLabelHeight();
        VerticalAlignment getLabelVerticalAlignment();

        //lists
        int getListWidth();
        int getListHeight();

        HorizontalAlignment getListHorizontalAlignment();
        VerticalAlignment getListVerticalAlignment();
  
        Color getListItemBackgroundColor();
        Color getListItemNameColor();
        Color getListItemTextColor();
        FontFamily getListItemNameFont();
        FontFamily getListItemTextFont();
        int getListItemNameSize();
        int getListItemsTextSize();
        bool isListItemNameLabelVisible();
        bool isListItemTextLabelsVisible();

        bool isListScrollBarAlwaysVisible();

        int getListItemTextPaddingLeft();
        int getListItemTextPaddingRight();
        int getListItemTextPaddingTop();
        int getListItemTextPaddingBottom();

        int getListItemNamePaddingLeft();

        int getListItemNamePaddingRight();

        int getListItemNamePaddingTop();

        int getListItemNamePaddingBottom();

        int getListContentWidth();

        Color getListBorderColor();
        float getListBorderWidth();
    }
}
