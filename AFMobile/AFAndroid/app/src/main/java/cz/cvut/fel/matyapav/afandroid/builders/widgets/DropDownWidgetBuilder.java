package cz.cvut.fel.matyapav.afandroid.builders.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fel.matyapav.afandroid.R;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.FieldInfo;
import cz.cvut.fel.matyapav.afandroid.components.parts.FieldOption;
import cz.cvut.fel.matyapav.afandroid.builders.skins.Skin;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
 */
public class DropDownWidgetBuilder extends BasicBuilder {


    DropDownWidgetBuilder(Context context, Skin skin, FieldInfo properties) {
        super(context, skin, properties);
    }

    @Override
    public View buildFieldView(Context context) {
        Spinner spinner = new Spinner(context);
        if(convertOptionsIntoList() != null) {
            ArrayAdapter<String> dataAdapter = new MySpinnerAdapter<>(context,
                    R.layout.support_simple_spinner_dropdown_item,
                    convertOptionsIntoList(), getSkin());
            spinner.setAdapter(dataAdapter);
        }
        if(getProperties().isReadOnly()){
            spinner.setEnabled(false);
        }
        return spinner;
    }

    @Override
    public void setData(AFField field, Object value) {
        Spinner spinner = (Spinner) field.getFieldView();
        if(value == null){
            spinner.setSelection(0);
            field.setActualData(spinner.getSelectedItem());
            return;
        }
        if(value.toString().equals("true")){
            value = Localization.translate(getContext(), "option.yes");
        }else if(value.toString().equals("false")){
            value = Localization.translate(getContext(), "option.no");
        }
        if(field.getFieldInfo().getOptions() != null) {
            for (FieldOption option : field.getFieldInfo().getOptions()) {
                if(option.getKey().equals(value)){
                    value = Localization.translate(getContext(), option.getValue());
                    break;
                }
            }
        }
        for (int i = 0; i < spinner.getCount(); i++) {
            if(spinner.getItemAtPosition(i).toString().equals(value)){
                spinner.setSelection(i);
                field.setActualData(spinner.getSelectedItem());
                return;
            }
        }
        field.setActualData(value);
    }

    @Override
    public Object getData(AFField field) {
        Spinner spinner = (Spinner) field.getFieldView();
        if(field.getFieldInfo().getOptions() != null) {
            for (FieldOption option : field.getFieldInfo().getOptions()) {
                if(Localization.translate(getContext(), option.getValue()).equals(spinner.getSelectedItem().toString())){
                    return option.getKey();
                }
            }
        }
        if(spinner.getSelectedItem().toString().equals(Localization.translate(getContext(), "option.yes"))){
            return true;
        }else if(spinner.getSelectedItem().toString().equals(Localization.translate(getContext(), "option.no"))){
            return false;
        }else {
            return spinner.getSelectedItem().toString();
        }
    }

    private List<String> convertOptionsIntoList(){
        List<String> list = new ArrayList<>();
        int i = 0;
        if(getProperties().getOptions() != null){
            for (FieldOption option : getProperties().getOptions()) {
                if(option.getValue().equals("true")){
                    list.add(Localization.translate(getContext(), "option.yes"));
                }else if(option.getValue().equals("false")){
                    list.add(Localization.translate(getContext(), "option.no"));
                }else {
                    list.add(Localization.translate(getContext(), option.getValue()));
                }
            }
            return list;
        }
        return null;
    }

    private static class MySpinnerAdapter<T> extends ArrayAdapter<T> {

        Skin skin;

        private MySpinnerAdapter(Context context, int resource, List<T> items, Skin skin) {
            super(context, resource, items);
            this.skin = skin;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setTextColor(skin.getFieldColor());
            view.setTypeface(skin.getFieldFont());
            return view;
        }

        // Affects opened state of the spinner
        @Override
        public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setTextColor(skin.getFieldColor());
            view.setTypeface(skin.getFieldFont());
            return view;
        }
    }
}
