package cz.cvut.fel.matyapav.afandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import cz.cvut.fel.matyapav.afandroid.builders.FormBuilder;
import cz.cvut.fel.matyapav.afandroid.components.AFForm;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;

/**
 * Created by Pavel on 16.02.2016.
 */
public class AbsenceManagementFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String host = "192.168.1.39:8080";
        final String url = "http://"+host+"/AFServer/rest/absenceInstance/definitionManaged/sa2";

        View root = inflater.inflate(R.layout.absence_management_fragment, container, false);
        LinearLayout layout = (LinearLayout) root.findViewById(R.id.absenceManagementLayout);
        FormBuilder builder = new FormBuilder(getActivity());
        final AFForm form = builder.createForm(url);
        if(form != null) {
            Button button = builder.buildSubmitButton(Localization.translate("button.perform", getActivity()), form);
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


