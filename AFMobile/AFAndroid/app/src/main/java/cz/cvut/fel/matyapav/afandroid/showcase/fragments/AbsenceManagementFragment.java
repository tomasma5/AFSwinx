package cz.cvut.fel.matyapav.afandroid.showcase.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.InputStream;
import java.util.HashMap;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.R;
import cz.cvut.fel.matyapav.afandroid.components.AFForm;
import cz.cvut.fel.matyapav.afandroid.components.AFList;
import cz.cvut.fel.matyapav.afandroid.showcase.ShowCaseUtils;
import cz.cvut.fel.matyapav.afandroid.showcase.skins.AbsenceManagementFormSkin;
import cz.cvut.fel.matyapav.afandroid.showcase.skins.AbsenceManagementListSkin;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;

/**
 * Created by Pavel on 16.02.2016.
 */
public class AbsenceManagementFragment extends Fragment {

    public static final String ABSENCE_INSTANCE_EDIT_FORM = "absenceInstanceEditForm";
    public static final String ABSENCE_INSTANCE_EDIT_FORM_CONNECTION_KEY = "absenceInstaceEditFormConnection";
    public static final String ABSENCE_INSTANCE_EDIT_LIST = "absenceInstaceEditTable";
    public static final String ABSENCE_INSTANCE_EDIT_LIST_CONNECTION_KEY = "absenceInstaceEditTableConnection";

    private View.OnClickListener onPerformButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ABSENCE_INSTANCE_EDIT_FORM);
            if(form != null && form.validateData()){
                try {
                    form.sendData();
                    ShowCaseUtils.refreshCurrentFragment(getActivity());
                    //povedlo se neco udelej
                } catch (Exception e) {
                    // TODO: nepodarilo seodeslat formular
                    e.printStackTrace();
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.absence_management_fragment, container, false);
        LinearLayout layout = (LinearLayout) root.findViewById(R.id.absenceManagementLayout);

        //get connection.xml as stream
        InputStream connectionResource = getResources().openRawResource(R.raw.connection);
        //build security constraints
        HashMap<String, String> securityConstrains = ShowCaseUtils.getUserCredentials(getActivity());

        try {
            AFList list = AFAndroid.getInstance().getListBuilder().initBuilder(getActivity(),
                    ABSENCE_INSTANCE_EDIT_LIST, connectionResource, ABSENCE_INSTANCE_EDIT_LIST_CONNECTION_KEY,
                    securityConstrains).setSkin(new AbsenceManagementListSkin(getContext())).createComponent();
            layout.addView(list.getView());
        } catch (Exception e) {
            //todo building failed
            e.printStackTrace();
        }

        connectionResource = getResources().openRawResource(R.raw.connection); //must be called again
        try {
            AFForm form = AFAndroid.getInstance().getFormBuilder().initBuilder(getActivity(),
                    ABSENCE_INSTANCE_EDIT_FORM, connectionResource, ABSENCE_INSTANCE_EDIT_FORM_CONNECTION_KEY,
                    securityConstrains).setSkin(new AbsenceManagementFormSkin(getContext())).createComponent();

            Button button = new Button(getActivity());
            button.setText(Localization.translate("button.perform", getActivity()));
            button.setOnClickListener(onPerformButtonClick);
            layout.addView(form.getView());
            layout.addView(button);
        } catch (Exception e) {
            //TODO exception
            e.printStackTrace();
        }

        //connect list and form
        final AFList absenceList = (AFList) AFAndroid.getInstance().getCreatedComponents().get(ABSENCE_INSTANCE_EDIT_LIST);
        final AFForm absenceForm = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ABSENCE_INSTANCE_EDIT_FORM);

        absenceList.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                absenceForm.insertData(absenceList.getData(position));
            }
        });
        return root;
    }

}


