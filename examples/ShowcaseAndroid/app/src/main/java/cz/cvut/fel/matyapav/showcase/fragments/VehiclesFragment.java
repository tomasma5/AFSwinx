package cz.cvut.fel.matyapav.showcase.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import cz.cvut.fel.matyapav.showcase.skins.VehiclesFormSkin;
import cz.cvut.fel.matyapav.showcase.skins.VehiclesListSkin;
import cz.cvut.fel.matyapav.showcase.utils.ShowCaseUtils;
import cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants;

import static cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants.connectionXmlId;


/**
 * Created by Pavel on 23.02.2016.
 */
public class VehiclesFragment extends Fragment {

    private View.OnClickListener onFormPerformListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.VEHICLES_FORM);
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

    private View.OnClickListener onFormResetListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.VEHICLES_FORM);
            if(form != null) {
                form.resetData();
            }
        }
    };

    private View.OnClickListener onFormClearListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.VEHICLES_FORM);
            if(form != null) {
                form.clearData();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.vehicles_fragment_layout, container, false);
        //get layouts where we want to put components
        LinearLayout vehiclesTableLayout = (LinearLayout) root.findViewById(R.id.vehiclesTableWrapper);
        LinearLayout vehiclesFromLayout = (LinearLayout) root.findViewById(R.id.vehiclesFormWrapper);

        //initialize builders
        HashMap<String, String> securityConstrains = ShowCaseUtils.getUserCredentials(getActivity());

        ListBuilder listBuilder = AFAndroid.getInstance().getListBuilder().initBuilder(getActivity(),
                ShowcaseConstants.VEHICLES_LIST, getResources().openRawResource(connectionXmlId),
                ShowcaseConstants.VEHICLES_LIST_CONNECTION_KEY, securityConstrains).setSkin(new VehiclesListSkin(getContext()));

        FormBuilder formBuilder = AFAndroid.getInstance().getFormBuilder().initBuilder(getActivity(),
                ShowcaseConstants.VEHICLES_FORM, getResources().openRawResource(connectionXmlId),
                ShowcaseConstants.VEHICLES_FORM_CONNECTION_KEY, securityConstrains).setSkin(new VehiclesFormSkin(getContext()));


        //create and insert form
        try {
            final AFList list = listBuilder.createComponent();
            vehiclesTableLayout.addView(list.getView());

            AFForm form = formBuilder.createComponent();

            Button perform =(Button) root.findViewById(R.id.vehiclesBtnAdd);
            perform.setOnClickListener(onFormPerformListener);
            Button reset = (Button) root.findViewById(R.id.vehiclesBtnReset);
            reset.setOnClickListener(onFormResetListener);
            vehiclesFromLayout.addView(form.getView());
            Button clear = (Button) root.findViewById(R.id.vehiclesBtnClear);
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
