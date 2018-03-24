package cz.cvut.fel.matyapav.showcase.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.HashMap;
import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.components.types.AFForm;
import cz.cvut.fel.matyapav.afandroid.components.types.AFList;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.showcase.R;
import cz.cvut.fel.matyapav.showcase.security.ApplicationContext;
import cz.cvut.fel.matyapav.showcase.skins.AbsenceManagementFormSkin;
import cz.cvut.fel.matyapav.showcase.skins.AbsenceManagementListSkin;
import cz.cvut.fel.matyapav.showcase.utils.ShowCaseUtils;
import cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class AbsenceManagementFragment extends BaseFragment {

    private View.OnClickListener onPerformButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents()
                    .get(ShowcaseConstants.ABSENCE_INSTANCE_EDIT_FORM);
            if (form != null && form.validateData()) {
                try {
                    form.sendData();
                    ShowCaseUtils.refreshCurrentFragment(getActivity(), getScreenDefinition().getScreenUrl(), getScreenDefinition().getKey());
                    Toast.makeText(getContext(), Localization.translate(getContext(), "success.addOrUpdate"),
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle(Localization.translate(getContext(), "error.addOrUpdate"));
                    alertDialog.setMessage(Localization.translate(getContext(), "error.reason") + e.getMessage());
                    alertDialog.show();
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public View initialize(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.absence_management_fragment, container, false);
        LinearLayout layout = (LinearLayout) root.findViewById(R.id.absenceManagementLayout);

        //build security constraints
        HashMap<String, String> securityConstrains = ApplicationContext.getInstance().getSecurityContext().getUserCredentials();

        try {
            AFList list = getScreenDefinition()
                    .getListBuilderByKey(ShowcaseConstants.ABSENCE_INSTANCE_EDIT_LIST)
                    .setConnectionParameters(securityConstrains)
                    .setSkin(new AbsenceManagementListSkin(getActivity()))
                    .createComponent();
            layout.addView(list.getView());
        } catch (Exception e) {
            ShowCaseUtils.showBuildingFailedDialog(getContext(), e);
            e.printStackTrace();
        }

        try {
            AFForm form = getScreenDefinition()
                    .getFormBuilderByKey(ShowcaseConstants.ABSENCE_INSTANCE_EDIT_FORM)
                    .setConnectionParameters(securityConstrains)
                    .setSkin(new AbsenceManagementFormSkin(getActivity()))
                    .createComponent();

            Button button = new Button(getActivity());
            button.setText(Localization.translate(getContext(),"button.perform"));
            button.setOnClickListener(onPerformButtonClick);
            layout.addView(form.getView());
            layout.addView(button);
        } catch (Exception e) {
            ShowCaseUtils.showBuildingFailedDialog(getContext(), e);
            e.printStackTrace();
        }

        //connect list and form
        final AFList absenceList = (AFList) AFAndroid.getInstance().getCreatedComponents()
                .get(ShowcaseConstants.ABSENCE_INSTANCE_EDIT_LIST);
        final AFForm absenceForm = (AFForm) AFAndroid.getInstance().getCreatedComponents()
                .get(ShowcaseConstants.ABSENCE_INSTANCE_EDIT_FORM);

        if (absenceList != null) {
            absenceList.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    absenceForm.insertData(absenceList.getDataFromItemOnPosition(position));
                }
            });
        }


        return root;
    }

}


