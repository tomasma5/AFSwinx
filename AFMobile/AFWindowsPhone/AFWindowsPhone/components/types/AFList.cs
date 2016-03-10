using AFWindowsPhone.builders.components.parts;
using AFWindowsPhone.builders.skins;
using AFWindowsPhone.builders.widgets;
using AFWindowsPhone.enums;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
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
                JSONArray jsonArray = new JSONArray(dataResponse);
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    Dictionary<String, String> row = new Dictionary<String, String><>();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    insertDataObject(jsonObject, road, row);
                    addRow(row);
                    road = new StringBuilder();
                }
                //set list adapter
                ListAdapter listAdapter = new CustomListAdapter(getActivity(), getSkin(), this);
                getListView().setAdapter(listAdapter);
            }
            catch (JSONException e)
            {
                Debug.WriteLine("CANNOT PARSE DATA");
                e.printStackTrace();
            }
        }

        private void insertDataObject(JSONObject jsonObject, StringBuilder road, Dictionary<String, String> row)
        {
            Iterator<String> keys = jsonObject.keys();
            while(keys.hasNext()){
                String key = keys.next();
                if (jsonObject.get(key) is JSONObject){
                    String roadBackup = road.ToString();
                    road.Append(key);
                    road.Append(".");
                    insertDataObject(jsonObject.getJSONObject(key), road, row); //parse class types
                    road = new StringBuilder(roadBackup.ToString());
                }else {
                    AFField field = getFieldById(road + key);
                    if (field != null)
                    {
                        String data = jsonObject.get(key).ToString();
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
            System.err.println("DATA " + data);
            return data;
        }

        private AFDataHolder createFormDataFromList(int position)
        {
            AFDataHolder dataHolder = new AFDataHolder();
            for (AFField field : getFields())
            {
                String propertyName = field.getId();
                Object data = rows.get(position).get(propertyName);
                // Based on dot notation determine road. Road is used to add object to its right place
                String[] roadTrace = propertyName.split("\\.");
                if (roadTrace.length > 1)
                {
                    AFDataHolder startPoint = dataHolder;
                    for (int i = 0; i < roadTrace.length; i++)
                    {
                        String roadPoint = roadTrace[i];
                        // If road end then add this property as inner propety
                        if (i + 1 == roadTrace.length)
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
