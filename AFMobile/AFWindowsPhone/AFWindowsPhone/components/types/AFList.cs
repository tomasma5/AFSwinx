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
using Windows.UI.Xaml.Controls;

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
                    JsonObject jsonObject = jsonArray[i].GetObject();
                    insertDataObject(jsonObject, road, row);
                    addRow(row);
                    road = new StringBuilder();
                }
                //set list adapter
                ListAdapter listAdapter = new CustomListAdapter(getActivity(), getSkin(), this);
                getListView().setAdapter(listAdapter);
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
                    road = new StringBuilder(roadBackup.ToString());
                }else {
                    AFField field = getFieldById(road + key);
                    if (field != null)
                    {
                        String data = jsonObject[key].GetString();
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
            Object data = dataBuilder.reselialize(createFormDataFromList(position));
            Debug.WriteLine("DATA " + data);
            return data;
        }

        private AFDataHolder createFormDataFromList(int position)
        {
            AFDataHolder dataHolder = new AFDataHolder();
            foreach (AFField field in getFields())
            {
                String propertyName = field.getId();
                Object data = rows[position][propertyName];
                // Based on dot notation determine road. Road is used to add object to its right place
                String[] roadTrace = propertyName.Split(new String[] {"\\."}, StringSplitOptions.None);
                if (roadTrace.Length > 1)
                {
                    AFDataHolder startPoint = dataHolder;
                    for (int i = 0; i < roadTrace.Length; i++)
                    {
                        String roadPoint = roadTrace[i];
                        // If road end then add this property as inner propety
                        if (i + 1 == roadTrace.Length)
                        {
                            startPoint.addPropertyAndValue(roadPoint, (String)data);
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
                    dataHolder.addPropertyAndValue(propertyName, (String)data);
                }
            }
            return dataHolder;
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
