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
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.R;
import cz.cvut.fel.matyapav.afandroid.builders.FormBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.ListBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.TableBuilder;
import cz.cvut.fel.matyapav.afandroid.components.AFForm;
import cz.cvut.fel.matyapav.afandroid.components.AFList;
import cz.cvut.fel.matyapav.afandroid.components.AFTable;
import cz.cvut.fel.matyapav.afandroid.showcase.ShowCaseUtils;
import cz.cvut.fel.matyapav.afandroid.showcase.skins.CountryListSkin;
import cz.cvut.fel.matyapav.afandroid.showcase.skins.CountrySkin;

/**
 * Created by Pavel on 23.02.2016.
 */
public class CountriesFragment extends Fragment {

    public static final String COUNTRY_LIST = "countryList";
    public static final String COUNTRY_LIST_CONNECTION_KEY = "tableCountryPublic";
    public static final String COUNTRY_FORM = "countryForm";
    public static final String COUNTRY_FORM_CONNECTION_KEY = "countryAdd";

    private View.OnClickListener onCountryPerformListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(COUNTRY_FORM);
            if (form != null && form.validateData()) {
                try {
                    form.sendData();
                    Toast.makeText(getActivity(), "Add or update complete", Toast.LENGTH_SHORT).show();
                    ShowCaseUtils.refreshCurrentFragment(getActivity());
                } catch (Exception e) {
                    //error while sending
                    e.printStackTrace();
                }
            }
        }
    };

    private View.OnClickListener onCountryResetListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(COUNTRY_FORM);
            if(form != null) {
                form.resetData();
            }
        }
    };

    private View.OnClickListener onCountryClearListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(COUNTRY_FORM);
            if(form != null) {
                form.clearData();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.countries_fragment_layout, container, false);
        //get layouts where we want to put components
        LinearLayout countriesTableLayout = (LinearLayout) root.findViewById(R.id.countriesTable);
        LinearLayout countriesFormLayout = (LinearLayout) root.findViewById(R.id.countriesForm);

        //initialize builders
        HashMap<String, String> securityConstrains = ShowCaseUtils.getUserCredentials(getActivity());

        ListBuilder listBuilder = AFAndroid.getInstance().getListBuilder().initBuilder(getActivity(),
                COUNTRY_LIST, getResources().openRawResource(R.raw.connection),
                COUNTRY_LIST_CONNECTION_KEY, securityConstrains).setSkin(new CountryListSkin(getContext()));

        FormBuilder formBuilder = AFAndroid.getInstance().getFormBuilder().initBuilder(getActivity(),
                COUNTRY_FORM, getResources().openRawResource(R.raw.connection),
                COUNTRY_FORM_CONNECTION_KEY, securityConstrains).setSkin(new CountrySkin(getContext()));

        //create and insert list
        try {
            final AFList list = listBuilder.createComponent();
            countriesTableLayout.addView(list.getView());
        } catch (Exception e) {
            //TODO list build failed
            e.printStackTrace();
        }

        //create and insert form
        try {
            AFForm form = formBuilder.createComponent();
            Button perform =(Button) root.findViewById(R.id.countriesBtnAdd);
            perform.setOnClickListener(onCountryPerformListener);
            Button reset = (Button) root.findViewById(R.id.countriesBtnReset);
            reset.setOnClickListener(onCountryResetListener);
            countriesFormLayout.addView(form.getView());
            Button clear = (Button) root.findViewById(R.id.countriesBtnClear);
            clear.setOnClickListener(onCountryClearListener);
        } catch (Exception e) {
            //TODO form build failed
            e.printStackTrace();
        }

        //connect list and form
        final AFList countryList = (AFList) AFAndroid.getInstance().getCreatedComponents().get(COUNTRY_LIST);
        final AFForm countryForm = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(COUNTRY_FORM);

        countryList.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                countryForm.insertData(countryList.getData(position));
            }
        });


        return root;
    }

}
