package cz.cvut.fel.matyapav.showcase.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.builders.FormBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.ListBuilder;
import cz.cvut.fel.matyapav.afandroid.components.types.AFForm;
import cz.cvut.fel.matyapav.afandroid.components.types.AFList;
import cz.cvut.fel.matyapav.showcase.R;
import cz.cvut.fel.matyapav.showcase.skins.CountryFormSkin;
import cz.cvut.fel.matyapav.showcase.skins.ListSkin;
import cz.cvut.fel.matyapav.showcase.utils.ShowCaseUtils;
import cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants;

import static cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants.connectionXmlId;


/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
 */
public class CountriesFragment extends Fragment {

    private View.OnClickListener onCountryPerformListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.COUNTRY_FORM);
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
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.COUNTRY_FORM);
            if(form != null) {
                form.resetData();
            }
        }
    };

    private View.OnClickListener onCountryClearListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.COUNTRY_FORM);
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
                ShowcaseConstants.COUNTRY_LIST, getResources().openRawResource(connectionXmlId),
                ShowcaseConstants.COUNTRY_LIST_CONNECTION_KEY, securityConstrains).setSkin(new ListSkin(getContext()));

        FormBuilder formBuilder = AFAndroid.getInstance().getFormBuilder().initBuilder(getActivity(),
                ShowcaseConstants.COUNTRY_FORM, getResources().openRawResource(connectionXmlId),
                ShowcaseConstants.COUNTRY_FORM_CONNECTION_KEY, securityConstrains).setSkin(new CountryFormSkin(getContext()));


        //create and insert form
        try {
            final AFList list = listBuilder.createComponent();
            countriesTableLayout.addView(list.getView());

            AFForm form = formBuilder.createComponent();

            Button perform =(Button) root.findViewById(R.id.countriesBtnAdd);
            perform.setOnClickListener(onCountryPerformListener);
            Button reset = (Button) root.findViewById(R.id.countriesBtnReset);
            reset.setOnClickListener(onCountryResetListener);
            countriesFormLayout.addView(form.getView());
            Button clear = (Button) root.findViewById(R.id.countriesBtnClear);
            clear.setOnClickListener(onCountryClearListener);
        } catch (Exception e) {
            ShowCaseUtils.showBuildingFailedDialog(getActivity(), e);
            e.printStackTrace();
        }

        //connect list and form
        ShowCaseUtils.connectFormAndList(ShowcaseConstants.COUNTRY_LIST, ShowcaseConstants.COUNTRY_FORM);

        return root;
    }

}
