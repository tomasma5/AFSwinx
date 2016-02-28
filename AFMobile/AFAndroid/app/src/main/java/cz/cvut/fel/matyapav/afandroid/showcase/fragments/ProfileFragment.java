package cz.cvut.fel.matyapav.afandroid.showcase.fragments;

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
import cz.cvut.fel.matyapav.afandroid.components.AFForm;
import cz.cvut.fel.matyapav.afandroid.showcase.utils.ShowCaseUtils;
import cz.cvut.fel.matyapav.afandroid.showcase.utils.ShowcaseConstants;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;

/**
 * Created by Pavel on 16.02.2016.
 */
public class ProfileFragment extends Fragment {



    private View.OnClickListener onPersonUpdateBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.PROFILE_FORM);
            if (form != null && form.validateData()) {
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
    };

    private View.OnClickListener onResetBtnClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.PROFILE_FORM);
            if(form != null){
                form.resetData();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        InputStream connectionResource = getResources().openRawResource(R.raw.connection);

        View root = inflater.inflate(R.layout.profile_fragment_layout, container, false);
        LinearLayout layout = (LinearLayout) root.findViewById(R.id.profileLayout);
        HashMap<String, String> securityConstrains = ShowCaseUtils.getUserCredentials(getActivity());

        try {
            AFForm form = AFAndroid.getInstance().getFormBuilder().initBuilder(getActivity(),
                    ShowcaseConstants.PROFILE_FORM, getResources().openRawResource(R.raw.connection),
                    ShowcaseConstants.PROFILE_FORM_CONNECTION_KEY, securityConstrains).createComponent();
            layout.addView(form.getView());

            Button btn = new Button(getActivity());
            btn.setText(Localization.translate("button.update", getActivity()));
            btn.setOnClickListener(onPersonUpdateBtnClick);

            Button reset = new Button(getActivity());
            reset.setText("RESET"); //// TODO translate
            reset.setOnClickListener(onResetBtnClick);

            LinearLayout btns = new LinearLayout(getActivity());
            btns.setOrientation(LinearLayout.HORIZONTAL);
            btns.setGravity(Gravity.CENTER);
            btns.addView(btn);
            btns.addView(reset);
            layout.addView(btns);
        } catch (Exception e) {
            System.err.println("BUILDING FAILED");
            e.printStackTrace();
        }
        return root;
    }

}
