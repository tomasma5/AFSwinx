package cz.cvut.fel.matyapav.showcase.fragments;

import android.os.Bundle;
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
import cz.cvut.fel.matyapav.showcase.security.ApplicationContext;
import cz.cvut.fel.matyapav.showcase.skins.VehiclesFormSkin;
import cz.cvut.fel.matyapav.showcase.skins.VehiclesListSkin;
import cz.cvut.fel.matyapav.showcase.utils.ShowCaseUtils;
import cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class VehiclesFragment extends BaseFragment {

    private View.OnClickListener onFormPerformListener = v -> {
        AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.VEHICLES_FORM);
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

    private View.OnClickListener onFormResetListener = v -> {
        AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.VEHICLES_FORM);
        if (form != null) {
            form.resetData();
        }
    };

    private View.OnClickListener onFormClearListener = v -> {
        AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.VEHICLES_FORM);
        if (form != null) {
            form.clearData();
        }
    };

    @Override
    public View initialize(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.vehicles_fragment_layout, container, false);
        //get layouts where we want to put components
        LinearLayout vehiclesTableLayout = root.findViewById(R.id.vehiclesTableWrapper);
        LinearLayout vehiclesFromLayout = root.findViewById(R.id.vehiclesFormWrapper);

        //initialize builders
        HashMap<String, String> securityConstrains = ApplicationContext.getInstance().getSecurityContext().getUserCredentials();

        ListBuilder listBuilder = getScreenDefinition()
                .getListBuilderByKey(ShowcaseConstants.VEHICLES_LIST)
                .setConnectionParameters(securityConstrains)
                .setSkin(new VehiclesListSkin(getContext()));

        FormBuilder formBuilder = getScreenDefinition()
                .getFormBuilderByKey(ShowcaseConstants.VEHICLES_FORM)
                .setConnectionParameters(securityConstrains)
                .setSkin(new VehiclesFormSkin(getContext()));


        //create and insert form
        try {
            final AFList list = listBuilder.createComponent();
            vehiclesTableLayout.addView(list.getView());

            AFForm form = formBuilder.createComponent();

            Button perform = root.findViewById(R.id.vehiclesBtnAdd);
            perform.setOnClickListener(onFormPerformListener);
            Button reset = root.findViewById(R.id.vehiclesBtnReset);
            reset.setOnClickListener(onFormResetListener);
            vehiclesFromLayout.addView(form.getView());
            Button clear = root.findViewById(R.id.vehiclesBtnClear);
            clear.setOnClickListener(onFormClearListener);
        } catch (Exception e) {
            ShowCaseUtils.showBuildingFailedDialog(getActivity(), e);
            e.printStackTrace();
        }

        //connect list and form
        ShowCaseUtils.connectFormAndList(ShowcaseConstants.VEHICLES_LIST, ShowcaseConstants.VEHICLES_FORM);

        return root;
    }

}
