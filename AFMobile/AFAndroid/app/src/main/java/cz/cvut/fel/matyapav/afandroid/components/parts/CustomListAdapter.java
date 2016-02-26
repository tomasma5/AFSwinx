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

import cz.cvut.fel.matyapav.afandroid.components.AFList;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.skins.Skin;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutDefinitions;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;

/**
 * Created by Pavel on 25.02.2016.
 */
public class CustomListAdapter extends BaseAdapter {

    private Context context;
    private AFList list;
    private Skin skin;

    public CustomListAdapter(Context context, Skin skin, AFList list) {
        this.context = context;
        this.list = list;
        this.skin = skin;
    }

    public int getCount() {
        return list.getRows().size();
    }

    public Object getItem(int position) {
        return list.getRows().get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = new CustomAdapterView(this.context, list, position);
        return v;
    }

    class CustomAdapterView extends LinearLayout {

        public CustomAdapterView(Context context, AFList list, int position) {
            super(context);
            setOrientation(LinearLayout.HORIZONTAL);
            setPadding(0, 6, 0, 6);

            //vertical layer for text
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(skin.getListContentWidth(), LayoutParams.WRAP_CONTENT);
            LinearLayout layout = new LinearLayout(context);
            if (list.getLayoutOrientation().equals(LayoutOrientation.AXISX)) {
                layout.setOrientation(LinearLayout.VERTICAL);
            } else {
                layout.setOrientation(LinearLayout.HORIZONTAL);
            }
            layout.setGravity(Gravity.BOTTOM);
            layout.setBackgroundColor(skin.getListItemBackgroundColor());


            TextView textName = new TextView(context);
            textName.setTextSize(skin.getListItemNameSize());
            textName.setTypeface(skin.getListItemNameFont());
            textName.setTextColor(skin.getListItemNameColor());
            textName.setPadding(skin.getListItemNamePaddingLeft(), skin.getListItemNamePaddingTop(),
                    skin.getListItemNamePaddingRight(), skin.getListItemNamePaddingBottom());

            //if it will be twocolumns layout prepare orientation
            int coupleOrientation;
            if (list.getLayoutOrientation().equals(LayoutOrientation.AXISX)) { //AXIS X
                coupleOrientation = LinearLayout.HORIZONTAL;
            } else { //AXIS Y
                coupleOrientation = LinearLayout.VERTICAL;
            }

            int i = 0;
            LinearLayout couple = null;
            for (AFField field : list.getFields()) {
                if (!field.getFieldInfo().isVisible()) {
                    continue;
                }
                if (i == 0) {
                    String label = skin.isListItemNameLabelVisible() ? Localization.translate(field.getFieldInfo().getLabel(), context) + ": " : "";
                    textName.setText(label + list.getRows().get(position).get(field.getId()));
                    layout.addView(textName);
                } else {
                    String label = skin.isListItemTextLabelsVisible() ? Localization.translate(field.getFieldInfo().getLabel(), context) + ": " : "";
                    TextView text = new TextView(context);
                    text.setTextSize(skin.getListItemsTextSize());
                    text.setTextColor(skin.getListItemTextColor());
                    text.setTypeface(skin.getListItemTextFont());
                    text.setText(label + list.getRows().get(position).get(field.getId()));
                    text.setPadding(skin.getListItemTextPaddingLeft(), skin.getListItemTextPaddingTop(),
                            skin.getListItemTextPaddingRight(), skin.getListItemTextPaddingBottom());
                    if (list.getLayoutDefinitions().equals(LayoutDefinitions.ONECOLUMNLAYOUT)) {
                        layout.addView(text);
                    } else { //TWOCOLUMNSLAYOUT //TODO nevim jestli to je ok pro vsechny pripady
                        if ((i-1) % 2 == 0) {
                            if (couple != null) {
                                layout.addView(couple); //add couple
                            }
                            couple = new LinearLayout(getContext());
                            couple.setOrientation(coupleOrientation);
                            couple.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        }
                        if(coupleOrientation == HORIZONTAL) {
                            text.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f)); //occupies half space
                        }else{
                            text.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        }
                        couple.addView(text);
                        if(i == list.getVisibleFieldsCount() - 1){
                            layout.addView(couple);
                        }
                    }
                }
                i++;
            }
            addView(layout, params);
        }
    }
}
