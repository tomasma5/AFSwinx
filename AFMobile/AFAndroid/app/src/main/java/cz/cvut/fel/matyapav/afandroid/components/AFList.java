package cz.cvut.fel.matyapav.afandroid.components;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cz.cvut.fel.matyapav.afandroid.builders.widgets.FieldBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.FieldBuilderFactory;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.types.AbstractBuilder;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.CustomListAdapter;
import cz.cvut.fel.matyapav.afandroid.components.skins.Skin;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutDefinitions;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedWidgets;
import cz.cvut.fel.matyapav.afandroid.rest.AFSwinxConnection;
import cz.cvut.fel.matyapav.afandroid.rest.AFSwinxConnectionPack;
import cz.cvut.fel.matyapav.afandroid.rest.BaseRestBuilder;
import cz.cvut.fel.matyapav.afandroid.rest.RestBuilderFactory;
import cz.cvut.fel.matyapav.afandroid.rest.holder.AFDataHolder;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * Created by Pavel on 24.02.2016.
 */
public class AFList extends AFComponent {

    private ListView listView;
    private List<Map<String, String>> rows;

    public AFList(Activity activity, AFSwinxConnectionPack connectionPack, Skin skin) {
        super(activity, connectionPack, skin);
    }

    public AFList(String name, ViewGroup view, LayoutDefinitions layoutDefinitions, LayoutOrientation layoutOrientation) {
        super(name, view, layoutDefinitions, layoutOrientation);
    }

    @Override
    void insertData(String dataResponse, StringBuilder road) {
        try {
            JSONArray jsonArray = new JSONArray(dataResponse);
            for(int i=0; i<jsonArray.length(); i++){
                Map<String, String> row = new HashMap<>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                insertObject(jsonObject, road, row);
                addRow(row);
            }
            getListView().setAdapter(new CustomListAdapter(getActivity(), getSkin(), this));
        } catch (JSONException e) {
            System.err.println("CANNOT PARSE DATA");
            e.printStackTrace();
        }
    }

    private void insertObject (JSONObject jsonObject, StringBuilder road, Map<String, String> row) throws JSONException {
        Iterator<String> keys = jsonObject.keys();
        while(keys.hasNext()){
            String key = keys.next();
            if(jsonObject.get(key) instanceof JSONObject){
                String roadBackup = road.toString();
                road.append(key);
                road.append(".");
                insertObject(jsonObject.getJSONObject(key), road, row); //parse class types
                road = new StringBuilder(roadBackup.toString());
            }else {
                AFField field = getFieldById(road + key);
                if (field != null) {
                    String data = jsonObject.get(key).toString();
                    AbstractBuilder builder = FieldBuilderFactory.getInstance().getFieldBuilder(field.getFieldInfo(), getSkin());
                    builder.setData(field, data);
                    System.err.println(road+key+" = " + field.getActualData());
                    row.put(road+key, field.getActualData());
                }
            }
        }
    }

    @Override
    SupportedComponents getComponentType() {
        return SupportedComponents.LIST;
    }

    @Override
    boolean validateData() {
        throw new UnsupportedOperationException("List is read only");
    }

    @Override
    AFDataHolder reserialize() {
        throw new UnsupportedOperationException("List is read only");
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public void addRow(Map<String, String> values){
        if(rows == null){
            rows = new ArrayList<>();
        }
        rows.add(values);
    }

    public ListView getListView() {
        return listView;
    }

    public Object getData(int position){
        AFSwinxConnection sendConnection = getSendConnection();
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
            System.err.println("ROWS "+ rows);
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
