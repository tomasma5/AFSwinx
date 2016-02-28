package cz.cvut.fel.matyapav.afandroid.showcase.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.R;
import cz.cvut.fel.matyapav.afandroid.components.AFForm;
import cz.cvut.fel.matyapav.afandroid.components.AFList;
import cz.cvut.fel.matyapav.afandroid.showcase.skins.AbsenceManagementListSkin;
import cz.cvut.fel.matyapav.afandroid.showcase.skins.CountryFormSkin;
import cz.cvut.fel.matyapav.afandroid.showcase.utils.ShowCaseUtils;
import cz.cvut.fel.matyapav.afandroid.showcase.skins.ListSkin;
import cz.cvut.fel.matyapav.afandroid.showcase.utils.ShowcaseConstants;
import cz.cvut.fel.matyapav.afandroid.utils.Constants;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;

/**
 * Created by Pavel on 28.02.2016.
 */
public class AbsenceTypeManagementFragment extends Fragment {

    private int countryId = -1;
    private String selectedCountryName;

    private View.OnClickListener onCountryChooseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AFForm chooseCountryForm = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.CHOOSE_COUNTRY_FORM);
            String country = chooseCountryForm.reserialize().getPropertiesAndValues().get(ShowcaseConstants.COUNTRY_KEY);
            setCountryId(Integer.parseInt(country));
            setSelectedCountryName(chooseCountryForm.getDataFromFieldWithId(ShowcaseConstants.COUNTRY_KEY).toString());
            ShowCaseUtils.refreshCurrentFragment(getActivity());
        }
    };

    private View.OnClickListener onPerformBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.ABSENCE_TYPE_FORM);
            if(form != null && form.validateData()) {
                try {
                    form.sendData();
                    Toast.makeText(getActivity(), "Add or update successful", Toast.LENGTH_SHORT).show(); //TODO translate
                    ShowCaseUtils.refreshCurrentFragment(getActivity());
                } catch (Exception e) {
                    //TODO sending failed
                    e.printStackTrace();
                }
            }
        }
    };


    private View.OnClickListener onResetBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.ABSENCE_TYPE_FORM);
            if(form != null) {
                form.resetData();
            }
        }
    };
    private View.OnClickListener onClearBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.ABSENCE_TYPE_FORM);
            if(form != null) {
                form.clearData();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.absence_type_management_fragment, container, false);
        LinearLayout absenceTypeManagementLayout = (LinearLayout) root.findViewById(R.id.absenceTypeManagementLayout);

        LinearLayout formWithBtn = new LinearLayout(getActivity());
        formWithBtn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        formWithBtn.setOrientation(LinearLayout.HORIZONTAL);

        try {
            AFForm chooseCountryForm = AFAndroid.getInstance().getFormBuilder().initBuilder(getActivity(),
                    ShowcaseConstants.CHOOSE_COUNTRY_FORM, getResources().openRawResource(R.raw.connection),
                    ShowcaseConstants.CHOOSE_COUNTRY_FORM_CONNECTION_KEY).createComponent();
            formWithBtn.addView(chooseCountryForm.getView());

            //set selected country
            if (getCountryId() != -1) {
                chooseCountryForm.setDataToFieldWithId(ShowcaseConstants.COUNTRY_KEY, getSelectedCountryName());
            }
        } catch (Exception e) {
            //TODO form building failed
            e.printStackTrace();
        }
        //create button
        Button chooseCountryButton = new Button(getActivity());
        chooseCountryButton.setText("Choose"); //TODO translate
        chooseCountryButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        chooseCountryButton.setOnClickListener(onCountryChooseListener);
        formWithBtn.addView(chooseCountryButton);
        absenceTypeManagementLayout.addView(formWithBtn);

        //show after country selection
        if (getCountryId() != -1) {
            HashMap<String, String> parameters = new HashMap<>();
            parameters.put(ShowcaseConstants.ID_KEY, String.valueOf(getCountryId()));
            try {
                AFList absenceTypeList = AFAndroid.getInstance().getListBuilder().initBuilder(getActivity(),
                        ShowcaseConstants.ABSENCE_TYPE_LIST, getResources().openRawResource(R.raw.connection),
                        ShowcaseConstants.ABSENCE_TYPE_LIST_CONNECTION_KEY, parameters)
                        .setSkin(new AbsenceManagementListSkin(getContext())).createComponent();
                absenceTypeManagementLayout.addView(absenceTypeList.getView());
            } catch (Exception e) {
                //TODO list building failed
                e.printStackTrace();
            }

            HashMap<String, String> securityConstrains = ShowCaseUtils.getUserCredentials(getActivity());
            securityConstrains.put(ShowcaseConstants.ID_KEY, String.valueOf(getCountryId()));
            try {
                AFForm absenceTypeForm = AFAndroid.getInstance().getFormBuilder().initBuilder(getActivity(),
                        ShowcaseConstants.ABSENCE_TYPE_FORM, getResources().openRawResource(R.raw.connection),
                        ShowcaseConstants.ABSENCE_TYPE_FORM_CONNECTION_KEY, securityConstrains)
                        .setSkin(new CountryFormSkin(getContext())).createComponent();
                absenceTypeManagementLayout.addView(absenceTypeForm.getView());
            } catch (Exception e) {
                //TODO form building failed
                e.printStackTrace();
            }

            //buttons
            Button perform = new Button(getActivity());
            perform.setText(Localization.translate("button.perform", getActivity()));
            perform.setOnClickListener(onPerformBtnClick);

            Button reset = new Button(getActivity());
            reset.setText("RESET"); //TODO translate
            reset.setOnClickListener(onResetBtnClick);

            Button clear = new Button(getActivity());
            clear.setText("Clear"); //TODO translate
            clear.setOnClickListener(onClearBtnClick);

            LinearLayout btns = new LinearLayout(getActivity());
            btns.setOrientation(LinearLayout.HORIZONTAL);
            btns.setGravity(Gravity.CENTER);
            btns.addView(perform);
            btns.addView(reset);
            btns.addView(clear);
            absenceTypeManagementLayout.addView(btns);

            //connect list and form
            final AFList absenceTypeList = (AFList) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.ABSENCE_TYPE_LIST);
            final AFForm absenceTypeForm = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.ABSENCE_TYPE_FORM);

            absenceTypeList.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    absenceTypeForm.insertData(absenceTypeList.getData(position));
                }
            });

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
