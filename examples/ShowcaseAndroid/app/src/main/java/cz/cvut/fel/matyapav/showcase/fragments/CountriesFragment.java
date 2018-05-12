package cz.cvut.fel.matyapav.showcase.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.builders.FormBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.ListBuilder;
import cz.cvut.fel.matyapav.afandroid.components.types.AFComponent;
import cz.cvut.fel.matyapav.afandroid.components.types.AFForm;
import cz.cvut.fel.matyapav.afandroid.components.types.AFList;
import cz.cvut.fel.matyapav.showcase.R;
import cz.cvut.fel.matyapav.showcase.security.ApplicationContext;
import cz.cvut.fel.matyapav.showcase.skins.CountryFormSkin;
import cz.cvut.fel.matyapav.showcase.skins.ListSkin;
import cz.cvut.fel.matyapav.showcase.utils.ShowCaseUtils;
import cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class CountriesFragment extends BaseFragment {

    private View.OnClickListener onCountryPerformListener = v -> {
        AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.COUNTRY_FORM);
        if (form != null && form.validateData()) {
            try {
                form.sendData();
                Toast.makeText(getActivity(), "Add or update complete", Toast.LENGTH_SHORT).show();
                ShowCaseUtils.refreshCurrentFragment(getActivity(), getScreenDefinition().getScreenUrl(), getScreenDefinition().getKey());
            } catch (Exception e) {
                //error while sending
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener onCountryResetListener = v -> {
        AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.COUNTRY_FORM);
        if (form != null) {
            form.resetData();
        }
    };

    private View.OnClickListener onCountryClearListener = v -> {
        AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.COUNTRY_FORM);
        if (form != null) {
            form.clearData();
        }
    };

    @Override
    public View initialize(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.countries_fragment_layout, container, false);
        //get layouts where we want to put components
        LinearLayout countriesLayout = root.findViewById(R.id.countriesComponents);

        //initialize builders
        HashMap<String, String> securityConstrains = ApplicationContext.getInstance().getSecurityContext().getUserCredentials();
        List<AFComponent> componentList = getScreenDefinition().buildAllComponents(securityConstrains);
        for (AFComponent component : componentList) {
            countriesLayout.addView(component.getView());
        }

        Button perform = root.findViewById(R.id.countriesBtnAdd);
        perform.setOnClickListener(onCountryPerformListener);
        Button reset = root.findViewById(R.id.countriesBtnReset);
        reset.setOnClickListener(onCountryResetListener);
        Button clear = root.findViewById(R.id.countriesBtnClear);
        clear.setOnClickListener(onCountryClearListener);
        //connect list and form
        ShowCaseUtils.connectFormAndList(ShowcaseConstants.COUNTRY_LIST, ShowcaseConstants.COUNTRY_FORM);

        return root;
    }

}
