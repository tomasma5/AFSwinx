package cz.cvut.fel.matyapav.afandroid.builders;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import cz.cvut.fel.matyapav.afandroid.AFField;
import cz.cvut.fel.matyapav.afandroid.AFForm;
import cz.cvut.fel.matyapav.afandroid.ClassDefinition;
import cz.cvut.fel.matyapav.afandroid.FieldInfo;
import cz.cvut.fel.matyapav.afandroid.JSONDefinitionParser;
import cz.cvut.fel.matyapav.afandroid.JSONParser;
import cz.cvut.fel.matyapav.afandroid.LayoutProperties;

/**
 * Builds for from class definition
 * Created by Pavel on 26.12.2015.
 */
public class FormBuilder {

    public AFForm buildForm(String response, Context context, String lang){
        AFForm form = new AFForm();
        LinearLayout formView = null;
        try {
            JSONParser parser = new JSONDefinitionParser();
            JSONObject jsonObj = new JSONObject(response);
            ClassDefinition classDef = parser.parse(jsonObj);
            if(classDef != null) {
                formView = (LinearLayout) buildLayout(classDef.getLayout(), context);
                InputFieldBuilder builder = new InputFieldBuilder();
                for (FieldInfo field : classDef.getFields()) {
                    AFField affield = builder.buildField(field, context, lang);
                    form.addField(affield);
                    formView.addView(affield.getView());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            formView = (LinearLayout) buildError("Cannot build form"+e.getMessage(), context);
        }
        form.setFormView(formView);
        return form;
    }

    private View buildError(String errorMsg, Context context) {
        LinearLayout err = new LinearLayout(context);
        err.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        TextView msg = new TextView(context);
        msg.setText(errorMsg);
        err.addView(msg);
        return err;
    }

    private View buildLayout(LayoutProperties properties, Context context){
        LinearLayout form = new LinearLayout(context);
        /*
        if(properties.getLayoutOrientation().getName().equals(LayoutOrientation.AXISY.getName())) {
            form.setOrientation(LinearLayout.VERTICAL);
        }else if(properties.getLayoutOrientation().getName().equals(LayoutOrientation.AXISX.getName())){
            form.setOrientation(LinearLayout.HORIZONTAL);
        }
        */
        //TODO onecolumn twocolumn properties
        //TODO axisX axisY properties
        form.setOrientation(LinearLayout.VERTICAL);
        form.setGravity(View.TEXT_ALIGNMENT_CENTER);
        form.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return form;

    }

    public Button buildSumbitButton(Context context){
        Button btn = new Button(context);
        btn.setText("Submit");
        btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return btn;
    }
}
