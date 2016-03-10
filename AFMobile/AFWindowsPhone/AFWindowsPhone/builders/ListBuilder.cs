using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using AFWindowsPhone.builders.components.types;
using Windows.UI.Xaml;
using AFWindowsPhone.enums;
using Windows.UI.Xaml.Controls;

namespace AFWindowsPhone.builders
{
    class ListBuilder : AFComponentBuilder<ListBuilder>
    {
        public override AFComponent createComponent()
        {
            initializeConnections();
            String modelResponse = getModelResponse();
            //create form from response
            AFList list = (AFList)buildComponent(modelResponse, SupportedComponents.LIST);
            //fill it with data (if there are some)
            String data = getDataResponse();
            if (data != null)
            {
                list.insertData(data);
            }
            AFWindowsPhone.getInstance().addCreatedComponent(getComponentKeyName(), list);
            return list;
        }

        protected override FrameworkElement buildComponentView(AFComponent component)
        {
            ListView listView = new ListView();
            listView.Width = getSkin().getListWidth();
            listView.Height = getSkin().getListHeight();
            listView.Margin = new Thickness(getSkin().getComponentMarginLeft(), getSkin().getComponentMarginTop(),
                getSkin().getComponentMarginRight(), getSkin().getComponentMarginBottom());
            //create border
            if (getSkin().getListBorderWidth() > 0)
            {
                RectShape rect = new RectShape();
                ShapeDrawable rectShapeDrawable = new ShapeDrawable(rect);
                Paint paint = rectShapeDrawable.getPaint();
                paint.setColor(getSkin().getListBorderColor());
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(getSkin().getListBorderWidth());
                listView.setBackground(rectShapeDrawable);
            }
            listView.S
            listView.setScrollbarFadingEnabled(!getSkin().isListScrollBarAlwaysVisible());
            //listView.setBackgroundColor(getSkin().getListBackgroundColor());
            ((AFList)component).setListView(listView);
            return listView;
        }
    }
}
