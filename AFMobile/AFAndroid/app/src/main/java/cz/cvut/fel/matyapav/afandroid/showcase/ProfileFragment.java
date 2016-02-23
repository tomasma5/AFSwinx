package cz.cvut.fel.matyapav.afandroid.showcase;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
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
        HashMap<String, String> securityConstrains = ShowCaseUtils.getUserCredentials(getActivity());

        FormBuilder builder = AFAndroid.getInstance().getFormBuilder()
                .initBuilder(getActivity(), "profileForm", connectionResource, "personProfile", securityConstrains);
        final AFForm form;
        try {
            form = builder.createComponent();
            if(form != null) {
                //Dynamicky vytvarene buttony
                Button btn = new Button(getActivity());
                btn.setText(Localization.translate("button.update", getActivity()));
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (form.validateData()) {
                            try {
                                form.sendData();
                                ShowCaseUtils.refreshCurrentFragment(getActivity());
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
                Button reset = new Button(getActivity());
                reset.setText("RESET");
                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        form.resetData();
                    }
                });
                layout.addView(form.getView());
                LinearLayout btns = new LinearLayout(getActivity());
                btns.setOrientation(LinearLayout.HORIZONTAL);
                btns.setGravity(Gravity.CENTER);
                btns.addView(btn);
                btns.addView(reset);
                layout.addView(btns);
            }
        } catch (Exception e) {
            System.err.println("BUILDING FAILED");
            e.printStackTrace();
        }
        return root;
    }

    public Fragment getThisFragment(){
        return this;
    }
}
