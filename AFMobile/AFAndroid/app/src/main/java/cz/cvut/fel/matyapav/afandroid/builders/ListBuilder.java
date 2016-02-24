package cz.cvut.fel.matyapav.afandroid.builders;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.R;
import cz.cvut.fel.matyapav.afandroid.components.AFComponent;
import cz.cvut.fel.matyapav.afandroid.components.AFForm;
import cz.cvut.fel.matyapav.afandroid.components.AFList;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;

/**
 * Created by Pavel on 24.02.2016.
 */
public class ListBuilder extends AFComponentBuilder<ListBuilder> {

    @Override
    public AFList createComponent() throws Exception {
        initializeConnections();
        String modelResponse = getModelResponse();
        //create form from response
        AFList list = (AFList) buildComponent(modelResponse, SupportedComponents.LIST);
        //fill it with data (if there are some)
        String data = getDataResponse();
        if(data != null) {
            insertData(data, list, new StringBuilder());
        }
        AFAndroid.getInstance().addCreatedComponent(getComponentKeyName(), list);
        return list;
    }

    @Override
    protected void insertData(String dataResponse, AFComponent list, StringBuilder road) {
        try {
            JSONArray jsonArray = new JSONArray(dataResponse);
            List<List<String>> records = new ArrayList<>();
            for(int i=0; i<jsonArray.length(); i++){
                List<String> recordValues = new ArrayList<>();
                Iterator<String> recordKeys = ((JSONObject) jsonArray.get(i)).keys();
                while(recordKeys.hasNext()) {
                    String key = recordKeys.next();
                    if(shouldBeInvisible(key, list)){
                        continue; //do not bother with invisible columns
                    }
                    recordValues.add(Localization.translate(((JSONObject) jsonArray.get(i)).get(key).toString(), getActivity()));
                }
                records.add(recordValues);
            }
            ((AFList) list).getListView().setAdapter(new CustomAdapter(getActivity(), records, list.getFields()));
        } catch (JSONException e) {
            System.err.println("CANNOT PARSE DATA");
            e.printStackTrace();
        }
    }

    @Override
    protected View buildComponentView(AFComponent component) {
        ListView listView = new ListView(getActivity());
        listView.setLayoutParams(new AbsListView.LayoutParams(getSkin().getListWidth(), getSkin().getListHeight()));
        listView.setAdapter(null);
        ((AFList)component).setListView(listView);
        return listView;
    }

    class CustomAdapterView extends LinearLayout {

        public CustomAdapterView(Context context, List<String> values, List<AFField> fields) {
            super(context);
            setOrientation(LinearLayout.HORIZONTAL);
            setPadding(0, 6, 0, 6);

            //vertical layer for text
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getSkin().getListWidth(), LayoutParams.WRAP_CONTENT);
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setGravity(Gravity.BOTTOM);

            TextView textName = new TextView(context);
            textName.setTextSize(16);
            textName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

            int i=0;
            for (AFField field: fields) {
                if(field.getFieldInfo().isVisible()) {
                    String label = Localization.translate(fields.get(i).getFieldInfo().getLabel(), context);
                    if(i == 0){
                        textName.setText(label+": "+values.get(i));
                        layout.addView(textName);
                    }else{
                        TextView text = new TextView(context);
                        text.setTextSize(10);
                        text.setText(label+": "+values.get(i));
                        layout.addView(text);
                    }
                    i++;
                }
            }
            addView(layout, params);
        }
    }

     class CustomAdapter extends BaseAdapter{

        private Context context;
        private List<List<String>> records;
        private List<AFField> fields;

        public CustomAdapter(Context context, List<List<String>> records, List<AFField> fields) {
            this.context = context;
            this.records = records;
            this.fields = fields;
        }

        public int getCount() {
            return records.size();
        }

        public Object getItem(int position) {
            return records.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            List<String> recordValues = records.get(position);
            View v = new CustomAdapterView(this.context, recordValues, fields );
            return v;
        }
    }
}
