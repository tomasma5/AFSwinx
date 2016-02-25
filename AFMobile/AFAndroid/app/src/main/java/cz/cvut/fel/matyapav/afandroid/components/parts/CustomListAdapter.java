package cz.cvut.fel.matyapav.afandroid.components.parts;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.skins.Skin;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;

/**
 * Created by Pavel on 25.02.2016.
 */
public class CustomListAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String, String>> records;
    private List<AFField> fields;
    private Skin skin;

    public CustomListAdapter(Context context, Skin skin, List<Map<String, String>> records, List<AFField> fields) {
        this.context = context;
        this.records = records;
        this.fields = fields;
        this.skin = skin;
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
        Map<String, String> recordValues = records.get(position);
        View v = new CustomAdapterView(this.context, recordValues, fields );
        return v;
    }

    class CustomAdapterView extends LinearLayout {

        public CustomAdapterView(Context context, Map<String,String> values, List<AFField> fields) {
            super(context);
            setOrientation(LinearLayout.HORIZONTAL);
            setPadding(0, 6, 0, 6);

            //vertical layer for text
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(skin.getListWidth(), LayoutParams.WRAP_CONTENT);
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setGravity(Gravity.BOTTOM);

            TextView textName = new TextView(context);
            textName.setTextSize(16);
            textName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

            int i = 0;
            for (AFField field: fields) {
                if(field.getFieldInfo().isVisible()) {
                    String label = Localization.translate(field.getFieldInfo().getLabel(), context);
                    if (i == 0) {
                        textName.setText(label + ": " + values.get(field.getFieldInfo().getId()));
                        layout.addView(textName);
                    } else {
                        TextView text = new TextView(context);
                        text.setTextSize(10);
                        text.setText(label + ": " + values.get(field.getFieldInfo().getId()));
                        layout.addView(text);
                    }
                    i++;
                }
            }
            addView(layout, params);
        }
    }
}
