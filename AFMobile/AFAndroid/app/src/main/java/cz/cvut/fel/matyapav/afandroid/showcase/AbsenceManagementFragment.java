package cz.cvut.fel.matyapav.afandroid.showcase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.InputStream;
import java.util.HashMap;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.R;
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
        View root = inflater.inflate(R.layout.absence_management_fragment, container, false);
        LinearLayout layout = (LinearLayout) root.findViewById(R.id.absenceManagementLayout);

        //get connection.xml as stream
        InputStream connectionResource = getResources().openRawResource(R.raw.connection);

        //init builder
        HashMap<String, String> securityConstrains = ShowCaseUtils.getUserCredentials(getActivity());
        FormBuilder builder = AFAndroid.getInstance().getFormBuilder()
                .initBuilder(getActivity(), "absenceInstaceEditFormConnection", connectionResource,
                        "absenceInstaceEditFormConnection", securityConstrains);
        try {
            final AFForm form = builder.createComponent();
            Button button = new Button(getActivity());
            button.setText(Localization.translate("button.perform", getActivity()));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(form.validateData()){
                        try {
                            form.sendData();
                            //povedlo se neco udelej
                        } catch (Exception e) {
                            // TODO: nepodarilo seodeslat formular
                            e.printStackTrace();
                        }
                    }
                }
            });
            layout.addView(form.getView());
            layout.addView(button);
        } catch (Exception e) {
            //TODO exception
            e.printStackTrace();
        }
        return root;
    }

}


