package cz.cvut.fel.matyapav.showcase.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.components.types.AFForm;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.showcase.R;
import cz.cvut.fel.matyapav.showcase.security.ApplicationContext;
import cz.cvut.fel.matyapav.showcase.utils.ShowCaseUtils;
import cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class ProfileFragment extends BaseFragment {

    private View.OnClickListener onPersonUpdateBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.PROFILE_FORM);
            if (form != null && form.validateData()) {
                try {
                    form.sendData();
                    ShowCaseUtils.refreshCurrentFragment(getActivity(), getScreenDefinition().getScreenUrl());
                    Toast.makeText(getActivity(), Localization.translate(getContext(), "person.updateSuccess"),
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    //update failed
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle(Localization.translate(getContext(), "person.updateFailed"));
                    alertDialog.setMessage(Localization.translate(getContext(), "error.reason") + e.getMessage());
                    alertDialog.show();
                    e.printStackTrace();
                }
            }
        }
    };

    private View.OnClickListener onResetBtnClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.PROFILE_FORM);
            if (form != null) {
                form.resetData();
            }
        }
    };

    @Override
    public View initialize(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.profile_fragment_layout, container, false);
        LinearLayout layout = root.findViewById(R.id.profileLayout);
        HashMap<String, String> securityConstrains = ApplicationContext.getInstance().getSecurityContext().getUserCredentials();

        try {
            AFForm form = getScreenDefinition()
                    .getFormBuilderByKey(ShowcaseConstants.PROFILE_FORM)
                    .setConnectionParameters(securityConstrains)
                    .createComponent();
            layout.addView(form.getView());
        } catch (Exception e) {
            ShowCaseUtils.showBuildingFailedDialog(getActivity(), e);
            System.err.println("BUILDING FAILED");
            e.printStackTrace();
        }

        Button btn = new Button(getActivity());
        btn.setText(Localization.translate(getContext(), "button.update"));
        btn.setOnClickListener(onPersonUpdateBtnClick);

        Button reset = new Button(getActivity());
        reset.setText(Localization.translate(getContext(), "button.reset"));
        reset.setOnClickListener(onResetBtnClick);

        LinearLayout btns = new LinearLayout(getActivity());
        btns.setOrientation(LinearLayout.HORIZONTAL);
        btns.setGravity(Gravity.CENTER);
        btns.addView(btn);
        btns.addView(reset);
        layout.addView(btns);

        return root;
    }

}
