package cz.cvut.fel.matyapav.afandroid.builders.widgets;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import cz.cvut.fel.matyapav.afandroid.builders.skins.Skin;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.FieldInfo;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
 */
public class CheckboxWidgetBuilder extends BasicBuilder {


    CheckboxWidgetBuilder(Context context, Skin skin, FieldInfo properties) {
        super(context, skin, properties);
    }

    @Override
    public View buildFieldView(Context context) {
        CheckBox checkBox = new CheckBox(context);
        checkBox.setTextColor(getSkin().getFieldColor());
        checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if(getProperties().isReadOnly()){
            checkBox.setEnabled(false);
        }
        return checkBox;
    }

    @Override
    public void setData(AFField field, Object value) {
        CheckBox box = (CheckBox) field.getFieldView();
        box.setChecked(Boolean.valueOf(value.toString()));
        field.setActualData(value);
    }

    @Override
    public Object getData(AFField field) {
        CheckBox box = (CheckBox) field.getFieldView();
        return String.valueOf(box.isChecked());
    }
}
