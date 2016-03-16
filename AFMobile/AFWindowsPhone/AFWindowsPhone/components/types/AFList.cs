using AFWindowsPhone.builders.components.parts;
using AFWindowsPhone.builders.skins;
using AFWindowsPhone.builders.widgets;
using AFWindowsPhone.enums;
using AFWindowsPhone.rest.connection;
using AFWindowsPhone.rest.holder;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Windows.Data.Json;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Media;
using AFWindowsPhone.utils;

namespace AFWindowsPhone.builders.components.types
{
    class AFList : AFComponent
    {
        private ListView listView;
        private List<Dictionary<String, String>> rows;

        public AFList()
        {
            rows = new List<Dictionary<String, String>>();
        }

        public AFList(AFSwinxConnectionPack connectionPack, Skin skin) : base(connectionPack, skin)
        {
            rows = new List<Dictionary<String, String>>();
        }


        public override SupportedComponents getComponentType()
        {
            return SupportedComponents.LIST;
        }

        public override void insertData(string dataResponse, StringBuilder road)
        {
            try
            {
                JsonArray jsonArray = JsonArray.Parse(dataResponse);
                for (int i = 0; i < jsonArray.Count; i++)
                {
                    Dictionary<String, String> row = new Dictionary<String, String>();
                    JsonObject jsonObject = (JsonObject) Utils.TryToGetValueFromJson(jsonArray[i]);
                    insertDataObject(jsonObject, road, row);
                    addRow(row);
                    road = new StringBuilder();
                }
                for (int i = 0; i < getRows().Count; i++)
                {
                    getListView().Items.Add(createCustomListItem(i));
                }
                
            }
            catch (Exception e)
            {
                Debug.WriteLine("CANNOT PARSE DATA");
                Debug.WriteLine(e.StackTrace);
            }
        }

        private void insertDataObject(JsonObject jsonObject, StringBuilder road, Dictionary<String, String> row)
        {
            foreach(String key in jsonObject.Keys){
                if (jsonObject[key].ValueType == JsonValueType.Object){
                    String roadBackup = road.ToString();
                    road.Append(key);
                    road.Append(".");
                    insertDataObject(jsonObject[key].GetObject(), road, row); //parse class types
                    road = new StringBuilder(roadBackup);
                }else {
                    AFField field = getFieldById(road + key);
                    if (field != null)
                    {
                        Object data = Utils.TryToGetValueFromJson(jsonObject[key]);
                        AbstractWidgetBuilder builder = WidgetBuilderFactory.getInstance().getFieldBuilder(field.getFieldInfo(), getSkin());
                        builder.setData(field, data);
                        row.Add(road + key, field.getActualData().ToString());
                    }
                }
            }
        }

        public override AFDataHolder reserialize()
        {
            throw new NotImplementedException("List is read only");
        }

        public override bool validateData()
        {
            throw new NotImplementedException("List is read only");
        }

        public void addRow(Dictionary<String, String> values)
        {
            rows.Add(values);
        }

        public Object getDataFromItemOnPosition(int position)
        {
            AFSwinxConnection sendConnection = getConnectionPack().getSendConnection();
            // Generate send connection based on which will be retrieve data. The send connection is
            // used to generate data in this case it will be generated JSON
            if (sendConnection == null)
            {
                sendConnection = new AFSwinxConnection("", 0, "");
            }

            BaseRestBuilder dataBuilder = RestBuilderFactory.getInstance().getBuilder(sendConnection);
            JsonObject data = (JsonObject) dataBuilder.reselialize(createFormDataFromList(position));
            Debug.WriteLine("DATA " + data.Stringify());
            return data.Stringify();
        }

        private AFDataHolder createFormDataFromList(int position)
        {
            AFDataHolder dataHolder = new AFDataHolder();
            foreach (AFField field in getFields())
            {
                String propertyName = field.getId();
                Object data = rows[position][propertyName];
                // Based on dot notation determine road. Road is used to add object to its right place
                String[] roadTrace = propertyName.Split(new [] {"."}, StringSplitOptions.None);
                if (roadTrace.Length > 1)
                {
                    AFDataHolder startPoint = dataHolder;
                    for (int i = 0; i < roadTrace.Length; i++)
                    {
                        String roadPoint = roadTrace[i];
                        // If road end then add this property as inner propety
                        if (i + 1 == roadTrace.Length)
                        {
                            startPoint.addPropertyAndValue(roadPoint, (String) data);
                        }
                        else {
                            // Otherwise it will be inner class so add if doesn't exist continue.
                            AFDataHolder roadHolder = startPoint.getInnerClassByKey(roadPoint);
                            if (roadHolder == null)
                            {
                                roadHolder = new AFDataHolder();
                                roadHolder.setClassName(roadPoint);
                                startPoint.addInnerClass(roadHolder);
                            }
                            // Set start point on current possition in tree
                            startPoint = roadHolder;
                        }
                    }
                }
                else {
                    dataHolder.addPropertyAndValue(propertyName, (String) data);
                }
            }
            return dataHolder;
        }

        private StackPanel createCustomListItem(int position)
        {         
            StackPanel panel = new StackPanel();
            panel.Orientation = Orientation.Horizontal;
            if (getSkin().getListContentWidth() < 0)
            {
                panel.HorizontalAlignment = getSkin().getListContentHorizontalAlignment();
            }
            else
            { 
                panel.Width = getSkin().getListContentWidth();
            }
            panel.Background = new SolidColorBrush(getSkin().getListItemBackgroundColor());
            //vertical layer for text
            StackPanel layout = new StackPanel();
            if (getLayoutOrientation().Equals(LayoutOrientation.AXISX))
            {
                layout.Orientation = Orientation.Vertical;;
            }
            else {
                layout.Orientation = Orientation.Horizontal;
            }

            TextBlock textName = new TextBlock();
            textName.FontSize = getSkin().getListItemNameSize();
            textName.FontFamily = getSkin().getListItemNameFont();
            textName.Foreground = new SolidColorBrush(getSkin().getListItemNameColor());
            textName.Margin = new Thickness(getSkin().getListItemNamePaddingLeft(), getSkin().getListItemNamePaddingTop(),
                    getSkin().getListItemNamePaddingRight(), getSkin().getListItemNamePaddingBottom());

            //if it will be twocolumns layout prepare orientation
            Orientation setOfFieldsOrientation;
            if (getLayoutOrientation().Equals(LayoutOrientation.AXISX))
            { //AXIS X
                setOfFieldsOrientation = Orientation.Horizontal;
            }
            else { //AXIS Y
                setOfFieldsOrientation = Orientation.Vertical;
            }

            int i = 0;
            StackPanel setOfFields = null;
            foreach (AFField field in getFields())
            {
                if (!field.getFieldInfo().isVisible())
                {
                    continue;
                }
                String label = "";
                if (i == 0)
                {
                    if (field.getFieldInfo().getLabelText() != null)
                    {
                        label = getSkin().isListItemNameLabelVisible() ? Localization.translate(field.getFieldInfo().getLabelText()) + ": " : "";
                    }
                    textName.Text = label + getRows()[position][field.getId()];
                    layout.Children.Add(textName);
                }
                else {
                    if (field.getFieldInfo().getLabelText() != null)
                    {
                        label = getSkin().isListItemTextLabelsVisible() ? Localization.translate(field.getFieldInfo().getLabelText()) + ": " : "";
                    }
                    TextBlock text = new TextBlock();
                    text.FontSize = getSkin().getListItemsTextSize();
                    text.Foreground = new SolidColorBrush(getSkin().getListItemTextColor());
                    text.FontFamily = getSkin().getListItemTextFont();
                    text.Text = label + getRows()[position][field.getId()];
                    text.Margin = new Thickness(getSkin().getListItemTextPaddingLeft(), getSkin().getListItemTextPaddingTop(),
                            getSkin().getListItemTextPaddingRight(), getSkin().getListItemTextPaddingBottom());

                    int numberOfColumns = getLayoutDefinitions().getNumberOfColumns();

                    if ((i - 1) % numberOfColumns == 0)
                    {
                        if (setOfFields != null)
                        {
                            layout.Children.Add(setOfFields);
                        }
                        setOfFields = new StackPanel();
                        setOfFields.Orientation = setOfFieldsOrientation;
                        setOfFields.HorizontalAlignment = HorizontalAlignment.Stretch;
                    }
                    //TODO jak predelat toto 
                    /*if (setOfFieldsOrientation == Orientation.Horizontal)
                    {
                        text.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f / numberOfColumns));
                    }
                    else {
                        text.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    }*/
                    setOfFields.Children.Add(text);

                    if (i == getVisibleFieldsCount() - 1)
                    {
                        layout.Children.Add(setOfFields);
                    }
                }
                i++;
            }
            panel.Children.Add(layout);
            return panel;
        }

        //GETTERS AND SETTERS

        public List<Dictionary<String, String>> getRows()
        {
            return this.rows;
        }

        public ListView getListView()
        {
            return listView;
        }

        public void setListView(ListView listView)
        {
            this.listView = listView;
        }
    }
}
