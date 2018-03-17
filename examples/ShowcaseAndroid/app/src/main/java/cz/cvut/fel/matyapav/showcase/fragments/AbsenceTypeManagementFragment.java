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
import cz.cvut.fel.matyapav.afandroid.components.types.AFList;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.showcase.R;
import cz.cvut.fel.matyapav.showcase.security.ApplicationContext;
import cz.cvut.fel.matyapav.showcase.skins.AbsenceManagementListSkin;
import cz.cvut.fel.matyapav.showcase.skins.CountryFormSkin;
import cz.cvut.fel.matyapav.showcase.utils.ShowCaseUtils;
import cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class AbsenceTypeManagementFragment extends BaseFragment {

    private int countryId = -1;
    private String selectedCountryName;

    private View.OnClickListener onCountryChooseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AFForm chooseCountryForm = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.CHOOSE_COUNTRY_FORM);
            String country = chooseCountryForm.reserialize().getPropertiesAndValues().get(ShowcaseConstants.COUNTRY_KEY);
            setCountryId(Integer.parseInt(country));
            setSelectedCountryName(chooseCountryForm.getDataFromFieldWithId(ShowcaseConstants.COUNTRY_KEY).toString());
            ShowCaseUtils.refreshCurrentFragment(getActivity(), getScreenDefinition().getScreenUrl());
        }
    };

    private View.OnClickListener onPerformBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.ABSENCE_TYPE_FORM);
            if (form != null && form.validateData()) {
                try {
                    form.sendData();
                    Toast.makeText(getActivity(), Localization.translate(getContext(), "success.addOrUpdate"),
                            Toast.LENGTH_SHORT).show();
                    ShowCaseUtils.refreshCurrentFragment(getActivity(), getScreenDefinition().getScreenUrl());
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


    private View.OnClickListener onResetBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.ABSENCE_TYPE_FORM);
            if (form != null) {
                form.resetData();
            }
        }
    };
    private View.OnClickListener onClearBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.ABSENCE_TYPE_FORM);
            if (form != null) {
                form.clearData();
            }
        }
    };

    @Override
    public View initialize(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.absence_type_management_fragment, container, false);
        LinearLayout absenceTypeManagementLayout = (LinearLayout) root.findViewById(R.id.absenceTypeManagementLayout);

        LinearLayout formWithBtn = new LinearLayout(getActivity());
        formWithBtn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        formWithBtn.setOrientation(LinearLayout.HORIZONTAL);

        try {
            AFForm chooseCountryForm = getScreenDefinition()
                    .getFormBuilderByKey(ShowcaseConstants.CHOOSE_COUNTRY_FORM)
                    .createComponent();
            formWithBtn.addView(chooseCountryForm.getView());

            //set selected country
            if (getCountryId() != -1) {
                chooseCountryForm.setDataToFieldWithId(ShowcaseConstants.COUNTRY_KEY, getSelectedCountryName());
            }
        } catch (Exception e) {
            ShowCaseUtils.showBuildingFailedDialog(getActivity(), e);
            e.printStackTrace();
        }
        //create button
        Button chooseCountryButton = new Button(getActivity());
        chooseCountryButton.setText(Localization.translate(getContext(), "button.choose"));
        chooseCountryButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        chooseCountryButton.setOnClickListener(onCountryChooseListener);
        formWithBtn.addView(chooseCountryButton);
        absenceTypeManagementLayout.addView(formWithBtn);

        //show after country selection
        if (getCountryId() != -1) {
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put(ShowcaseConstants.ID_KEY, String.valueOf(getCountryId()));
            try {
                AFList absenceTypeList = getScreenDefinition()
                        .getListBuilderByKey(ShowcaseConstants.ABSENCE_TYPE_LIST)
                        .setConnectionParameters(parameters)
                        .setSkin(new AbsenceManagementListSkin(getContext()))
                        .createComponent();
                absenceTypeManagementLayout.addView(absenceTypeList.getView());

                HashMap<String, String> securityConstrains = ApplicationContext.getInstance().getSecurityContext().getUserCredentials();
                securityConstrains.put(ShowcaseConstants.ID_KEY, String.valueOf(getCountryId()));

                AFForm absenceTypeForm = getScreenDefinition()
                        .getFormBuilderByKey(ShowcaseConstants.ABSENCE_TYPE_FORM)
                        .setConnectionParameters(securityConstrains)
                        .setSkin(new CountryFormSkin(getContext()))
                        .createComponent();
                absenceTypeManagementLayout.addView(absenceTypeForm.getView());
            } catch (Exception e) {
                ShowCaseUtils.showBuildingFailedDialog(getActivity(), e);
                e.printStackTrace();
            }

            //buttons
            Button perform = new Button(getActivity());
            perform.setText(Localization.translate(getContext(),"button.perform"));
            perform.setOnClickListener(onPerformBtnClick);

            Button reset = new Button(getActivity());
            reset.setText(Localization.translate(getContext(),"button.reset"));
            reset.setOnClickListener(onResetBtnClick);

            Button clear = new Button(getActivity());
            clear.setText(Localization.translate(getContext(),"button.clear"));
            clear.setOnClickListener(onClearBtnClick);

            LinearLayout btns = new LinearLayout(getActivity());
            btns.setOrientation(LinearLayout.HORIZONTAL);
            btns.setGravity(Gravity.CENTER);
            btns.addView(perform);
            btns.addView(reset);
            btns.addView(clear);
            absenceTypeManagementLayout.addView(btns);

            //connect list and form
            ShowCaseUtils.connectFormAndList(ShowcaseConstants.ABSENCE_TYPE_LIST, ShowcaseConstants.ABSENCE_TYPE_FORM);

        }

        return root;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getSelectedCountryName() {
        return selectedCountryName;
    }

    public void setSelectedCountryName(String selectedCountryName) {
        this.selectedCountryName = selectedCountryName;
    }


}
