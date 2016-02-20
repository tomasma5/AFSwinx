package cz.cvut.fel.matyapav.afandroid.showcase;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

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
public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        InputStream connectionResource = getResources().openRawResource(R.raw.connection);

        View root = inflater.inflate(R.layout.profile_fragment_layout, container, false);
        LinearLayout layout = (LinearLayout) root.findViewById(R.id.profileLayout);
        HashMap<String, String> securityConstrains = ShowCaseUtils.getUserCredentials(getActivity()); //TODO can be null
        FormBuilder builder = AFAndroid.getInstance().getFormBuilder()
                .initBuilder(getActivity(), "profileForm", connectionResource, "personProfile", securityConstrains);
        final AFForm form;
        try {
            form = builder.createComponent();
            if(form != null) {
                Button button = builder.buildSubmitButton(Localization.translate("button.update", getActivity()), form);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(form.validateData()) {
                            try {
                                form.sendData();
                                Toast.makeText(getActivity(), "Update successful", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                //update failed
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                                alertDialog.setTitle("Update failed");
                                alertDialog.setMessage("Reason " + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    }
                });
                layout.addView(form.getView());
                layout.addView(button);
            }
        } catch (Exception e) {
            System.err.println("BUILDING FAILED");
            e.printStackTrace();
        }
        return root;
    }
}
