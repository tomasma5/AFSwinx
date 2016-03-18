﻿using System;
using System.Threading.Tasks;
using AFWinPhone.components.types;
using Windows.UI.Xaml;
using AFWinPhone.enums;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Media;
using AFWinPhone.components;

namespace AFWinPhone.builders
{
    public class ListBuilder : AFComponentBuilder<ListBuilder>
    {
        public override AFComponent createComponent()
        {
            initializeConnections();
            var modelTask = Task.Run(getModelResponse);
            modelTask.Wait();
            String modelResponse = modelTask.Result;
            //create list from response
            AFList list = (AFList)buildComponent(modelResponse, SupportedComponents.LIST);
            //fill it with data (if there are some)
            var dataTask = Task.Run(getDataResponse);
            dataTask.Wait();
            String data = dataTask.Result;
            if (data != null)
            {
                list.insertData(data);
            }
            AfWindowsPhone.getInstance().addCreatedComponent(getComponentKeyName(), list);
            return list;
        }

        protected override FrameworkElement buildComponentView(AFComponent component)
        {
            ListView listView = new ListView();
            if (getSkin().getListWidth() < 0)
            {
                listView.HorizontalAlignment = getSkin().getListHorizontalAlignment();
            }
            else {
                listView.Width = getSkin().getListWidth();
            }
            if(getSkin().getListHeight() < 0)
            {
                listView.VerticalAlignment = getSkin().getListVerticalAlignment();
            }
            else
            {
                listView.Height = getSkin().getListHeight();
            }

            //strech all items too width of list view. ItemContainerStrechStyle can be found in App.xaml
            listView.ItemContainerStyle = Application.Current.Resources["ItemContainerStretchStyle"] as Style;
      
            listView.Margin = new Thickness(getSkin().getComponentMarginLeft(), getSkin().getComponentMarginTop(),
                getSkin().getComponentMarginRight(), getSkin().getComponentMarginBottom());
            //create border
            if (getSkin().getListBorderWidth() > 0)
            {
                listView.BorderBrush = new SolidColorBrush(getSkin().getListBorderColor());
                listView.BorderThickness = new Thickness(getSkin().getListBorderWidth());
            }
           
            //TODO not working 
            //set scroll bar visibility
            ScrollViewer.SetVerticalScrollBarVisibility(listView,
                getSkin().isListScrollBarAlwaysVisible() ? ScrollBarVisibility.Visible : ScrollBarVisibility.Auto);

            //listView.setBackgroundColor(getSkin().getListBackgroundColor());
            ((AFList)component).setListView(listView);
            return listView;
        }
    }
}
