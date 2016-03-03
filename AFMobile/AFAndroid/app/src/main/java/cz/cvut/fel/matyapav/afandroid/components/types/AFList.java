package cz.cvut.fel.matyapav.afandroid.components.types;

import android.app.Activity;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionPack;
import com.tomscz.afswinx.rest.rebuild.BaseRestBuilder;
import com.tomscz.afswinx.rest.rebuild.RestBuilderFactory;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cz.cvut.fel.matyapav.afandroid.builders.widgets.WidgetBuilderFactory;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.AbstractWidgetBuilder;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.CustomListAdapter;
import cz.cvut.fel.matyapav.afandroid.builders.skins.Skin;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;

/**
 * Created by Pavel on 24.02.2016.
 */
public class AFList extends AFComponent {

    private ListView listView;
    private List<Map<String, String>> rows;

    public AFList() {
        rows = new ArrayList<>();
    }

    public AFList(Activity activity, AFSwinxConnectionPack connectionPack, Skin skin) {
        super(activity, connectionPack, skin);
        rows = new ArrayList<>();
    }

    @Override
    public void insertData(String dataResponse, StringBuilder road) {
        try {
            JSONArray jsonArray = new JSONArray(dataResponse);
            for(int i=0; i<jsonArray.length(); i++){
                Map<String, String> row = new HashMap<>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                insertDataObject(jsonObject, road, row);
                addRow(row);
                road = new StringBuilder();
            }
            //set list adapter
            ListAdapter listAdapter = new CustomListAdapter(getActivity(), getSkin(), this);
            getListView().setAdapter(listAdapter);
        } catch (JSONException e) {
            System.err.println("CANNOT PARSE DATA");
            e.printStackTrace();
        }
    }

    private void insertDataObject(JSONObject jsonObject, StringBuilder road, Map<String, String> row) throws JSONException {
        Iterator<String> keys = jsonObject.keys();
        while(keys.hasNext()){
            String key = keys.next();
            if(jsonObject.get(key) instanceof JSONObject){
                String roadBackup = road.toString();
                road.append(key);
                road.append(".");
                insertDataObject(jsonObject.getJSONObject(key), road, row); //parse class types
                road = new StringBuilder(roadBackup.toString());
            }else {
                AFField field = getFieldById(road + key);
                if (field != null) {
                    String data = jsonObject.get(key).toString();
                    AbstractWidgetBuilder builder = WidgetBuilderFactory.getInstance().getFieldBuilder(field.getFieldInfo(), getSkin());
                    builder.setData(field, data);
                    row.put(road + key, field.getActualData().toString());
                }
            }
        }
    }

    @Override
    public SupportedComponents getComponentType() {
        return SupportedComponents.LIST;
    }

    @Override
    public boolean validateData() {
        throw new UnsupportedOperationException("List is read only");
    }

    @Override
    public AFDataHolder reserialize() {
        throw new UnsupportedOperationException("List is read only");
    }

    public ListView getListView() {
        return listView;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public void addRow(Map<String, String> values){
        rows.add(values);
    }

    public Object getDataFromItemOnPosition(int position){
        AFSwinxConnection sendConnection = getConnectionPack().getSendConnection();
        // Generate send connection based on which will be retrieve data. The send connection is
        // used to generate data in this case it will be generated JSON
        if (sendConnection == null) {
            sendConnection = new AFSwinxConnection("", 0, "");
        }

        BaseRestBuilder dataBuilder = RestBuilderFactory.getInstance().getBuilder(sendConnection);
        Object data = dataBuilder.reselialize(createFormDataFromList(position));
        System.err.println("DATA " + data);
        return data;
    }

    private AFDataHolder createFormDataFromList(int position) {
        AFDataHolder dataHolder = new AFDataHolder();
        for (AFField field : getFields()) {
            String propertyName = field.getId();
            Object data = rows.get(position).get(propertyName);
            // Based on dot notation determine road. Road is used to add object to its right place
            String[] roadTrace = propertyName.split("\\.");
            if (roadTrace.length > 1) {
                AFDataHolder startPoint = dataHolder;
                for (int i = 0; i < roadTrace.length; i++) {
                    String roadPoint = roadTrace[i];
                    // If road end then add this property as inner propety
                    if (i + 1 == roadTrace.length) {
                        startPoint.addPropertyAndValue(roadPoint, (String) data);
                    } else {
                        // Otherwise it will be inner class so add if doesn't exist continue.
                        AFDataHolder roadHolder = startPoint.getInnerClassByKey(roadPoint);
                        if (roadHolder == null) {
                            roadHolder = new AFDataHolder();
                            roadHolder.setClassName(roadPoint);
                            startPoint.addInnerClass(roadHolder);
                        }
                        // Set start point on current possition in tree
                        startPoint = roadHolder;
                    }
                }
            } else {
                dataHolder.addPropertyAndValue(propertyName, (String) data);
            }
        }
        return dataHolder;
    }

    public List<Map<String, String>> getRows() {
        return rows;
    }
}
