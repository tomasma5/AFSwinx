package cz.cvut.fel.matyapav.afandroid.builders.widgets;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

import cz.cvut.fel.matyapav.afandroid.components.parts.FieldInfo;
import cz.cvut.fel.matyapav.afandroid.components.parts.FieldOption;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;

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
            options[0].setText(Localization.translate("option.yes",activity));
            options[1] = new RadioButton(activity);
            options[1].setText(Localization.translate("option.no", activity));
            radioGroup.addView(options[0]);
            radioGroup.addView(options[1]);
        }
        return radioGroup;
    }
}
