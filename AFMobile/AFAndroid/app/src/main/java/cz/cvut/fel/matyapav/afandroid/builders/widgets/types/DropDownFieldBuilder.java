package cz.cvut.fel.matyapav.afandroid.builders.widgets.types;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.FieldInfo;
import cz.cvut.fel.matyapav.afandroid.components.parts.FieldOption;
import cz.cvut.fel.matyapav.afandroid.components.skins.Skin;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;

/**
 * Created by Pavel on 15.02.2016.
 */
public class DropDownFieldBuilder extends BasicBuilder {

    private FieldInfo properties;

    public DropDownFieldBuilder(Skin skin,FieldInfo properties) {
        super(skin);
        this.properties = properties;
    }

    @Override
    public View buildFieldView(Activity activity) {
        //TODO nejaka chyba kdyz je tabulka prazdna
        Spinner spinner = new Spinner(activity);
        TextView spinnerItem = new TextView(activity);
        if(convertOptionsIntoList(activity) != null) {
            ArrayAdapter<String> dataAdapter = new MySpinnerAdapter<>(activity,
                    android.support.design.R.layout.support_simple_spinner_dropdown_item,
                    convertOptionsIntoList(activity), getSkin());
            spinner.setAdapter(dataAdapter);
        }
        return spinner;
    }

    @Override
    public void setData(AFField field, Object value) {
        System.out.println("NASTAVUJI DATA "+ value.toString());
        if(field.getFieldInfo().getOptions() != null) {
            for (FieldOption option : field.getFieldInfo().getOptions()) {
                if(option.getKey().equals(value)){
                    value = Localization.translate(option.getValue(),field.getFieldView().getContext());
                    break;
                }
            }
        }
        Spinner spinner = (Spinner) field.getFieldView();
        if(value == null){ //TODO asi se muze stat ze spinner nebude mit ani jednu polozku
            spinner.setSelection(0);
            field.setActualData(spinner.getSelectedItem().toString());
            return;
        }
        for (int i = 0; i < spinner.getCount(); i++) {
            if(spinner.getItemAtPosition(i).toString().equals(value)){
                spinner.setSelection(i);
                field.setActualData(spinner.getSelectedItem().toString());
                return;
            }
        }
        field.setActualData(value.toString());
    }

    @Override
    public Object getData(AFField field) {
        Spinner spinner = (Spinner) field.getFieldView();
        if(field.getFieldInfo().getOptions() != null) {
            for (FieldOption option : field.getFieldInfo().getOptions()) {
                if(Localization.translate(option.getValue(), field.getFieldView().getContext()).equals(spinner.getSelectedItem().toString())){
                    return option.getKey();
                }
            }
        }
       return spinner.getSelectedItem().toString();
    }

    private List<String> convertOptionsIntoList(Activity activity){
        List<String> list = new ArrayList<>();
        int i = 0;
        if(properties.getOptions() != null){
            for (FieldOption option : properties.getOptions()) {
                list.add(Localization.translate(option.getValue(), activity));
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
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setTextColor(skin.getFieldColor());
            view.setTypeface(skin.getFieldFont());
            return view;
        }

        // Affects opened state of the spinner
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setTextColor(skin.getFieldColor());
            view.setTypeface(skin.getFieldFont());
            return view;
        }
    }
}
