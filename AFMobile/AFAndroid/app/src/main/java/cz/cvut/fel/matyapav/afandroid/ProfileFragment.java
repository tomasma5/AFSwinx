package cz.cvut.fel.matyapav.afandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import cz.cvut.fel.matyapav.afandroid.builders.FormBuilder;
import cz.cvut.fel.matyapav.afandroid.components.AFForm;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;

/**
 * Created by Pavel on 16.02.2016.
 */
public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String host = "192.168.1.39:8080";
        final String url = "http://"+host+"/AFServer/rest/users/profile";

        View root = inflater.inflate(R.layout.profile_fragment_layout, container, false);
        LinearLayout layout = (LinearLayout) root.findViewById(R.id.profileLayout);
        FormBuilder builder = new FormBuilder(getActivity());
        final AFForm form = builder.createForm(url);
        if(form != null) {
            Button button = builder.buildSubmitButton(Localization.translate("button.update", getActivity()), form);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (form.validateForm()) {
                        //send data
                    }
                }
            });
            layout.addView(form.getView());
            layout.addView(button);
        }
        return root;
    }
}
