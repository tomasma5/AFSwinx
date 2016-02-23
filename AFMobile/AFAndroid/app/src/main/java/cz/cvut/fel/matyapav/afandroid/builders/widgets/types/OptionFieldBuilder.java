package cz.cvut.fel.matyapav.afandroid.builders.widgets.types;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.FieldInfo;

/**
 * Created by Pavel on 15.02.2016.
 */
public class OptionFieldBuilder implements BasicBuilder {

    private FieldInfo properties;

    public OptionFieldBuilder(FieldInfo properties) {
        this.properties = properties;
    }

    @Override
    public View buildFieldView(Activity activity) {
        RadioGroup radioGroup = new RadioGroup(activity);
        radioGroup.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        radioGroup.setOrientation(RadioGroup.VERTICAL); //TODO change according to layout parameters
        int numberOfOptions;
        if(properties.getOptions() != null && !properties.getOptions().isEmpty()) {
            numberOfOptions = properties.getOptions().size();
            RadioButton[] options = new RadioButton[numberOfOptions];
            for (int i = 0; i < numberOfOptions; i++) {
                options[i] = new RadioButton(activity);
                options[i].setText(properties.getOptions().get(i).getValue());
                options[i].setId(i + 100);
                radioGroup.addView(options[i]);
            }
        }else{
            //YES NO OPTIONS
            numberOfOptions = 2;
            RadioButton[] options = new RadioButton[numberOfOptions];
            options[0] = new RadioButton(activity);
            options[0].setText("true");
            options[1] = new RadioButton(activity);
            options[1].setText("false");
            radioGroup.addView(options[0]);
            radioGroup.addView(options[1]);
        }
        return radioGroup;
    }

    //TODO REWRITE
    @Override
    public void setData(AFField field, Object value) {

        RadioGroup group = (RadioGroup) field.getFieldView();
        if(value == null){
            group.clearCheck();
            return;
        }
        for (int i = 0; i < group.getChildCount(); i++) { //TODO toto se mi nelibi
            RadioButton btn = (RadioButton) group.getChildAt(i);
            if(btn.getText().equals(value)
                    || (Boolean.valueOf(value.toString()) == true && i==0)
                    || (Boolean.valueOf(value.toString()) == false && i==1)){
                btn.setChecked(true);
                break;
            }
        };
    }

    @Override
    public Object getData(AFField field) {
        RadioGroup group = (RadioGroup) field.getFieldView();
        group.getCheckedRadioButtonId();
        for (int i = 0; i < group.getChildCount(); i++) { //TODO toto se mi nelibi
            RadioButton btn = (RadioButton) group.getChildAt(i);
            if(btn.isChecked()){

                return btn.getText().toString();
            }
        }
        return null; //nothing is checked
    }
}
