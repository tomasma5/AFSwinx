package cz.cvut.fel.matyapav.showcase.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
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
import cz.cvut.fel.matyapav.showcase.skins.CreateAbsenceFormSkin;
import cz.cvut.fel.matyapav.showcase.utils.ShowCaseUtils;
import cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
 */
public class CreateAbsenceFragment extends BaseFragment {

    private View.OnClickListener onCreateAbsenceClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AFForm createAbsenceForm = (AFForm) AFAndroid.getInstance().getCreatedComponents()
                    .get(ShowcaseConstants.ABSENCE_ADD_FORM);
            if(createAbsenceForm != null && createAbsenceForm.validateData()){
                try {
                    createAbsenceForm.sendData();
                    Toast.makeText(getActivity(), Localization.translate(getContext(),"absence.create"),
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle(Localization.translate(getContext(), "absence.createFailed"));
                    alertDialog.setMessage(Localization.translate(getContext(), "error.reason") + e.getMessage());
                    alertDialog.show();
                    e.printStackTrace();
                }

            }
        }
    };

    @Override
    public View initialize(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.create_absence_fragment_layout, container, false);
        LinearLayout createAbsenceLayout = root.findViewById(R.id.createAbsenceLayout);
        HashMap<String, String> securityConstrains = ApplicationContext.getInstance().getSecurityContext().getUserCredentials();
        if (securityConstrains != null) {
            securityConstrains.put("user", ApplicationContext.getInstance().getSecurityContext().getUsername());
        }
        try {
            AFForm createAbsenceForm = getScreenDefinition()
                    .getFormBuilderByKey(ShowcaseConstants.ABSENCE_ADD_FORM)
                    .setConnectionParameters(securityConstrains)
                    .setSkin(new CreateAbsenceFormSkin(getContext()))
                    .createComponent();
            createAbsenceLayout.addView(createAbsenceForm.getView());
        } catch (Exception e) {
            ShowCaseUtils.showBuildingFailedDialog(getActivity(), e);
            e.printStackTrace();
        }

        Button button = new Button(getActivity());
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setText(Localization.translate(getContext(), "button.create"));
        button.setOnClickListener(onCreateAbsenceClick);
        createAbsenceLayout.addView(button);
        return root;
    }

}