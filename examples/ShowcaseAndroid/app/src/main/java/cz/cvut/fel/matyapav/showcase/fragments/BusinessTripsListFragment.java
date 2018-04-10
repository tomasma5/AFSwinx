package cz.cvut.fel.matyapav.showcase.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.HashMap;
import cz.cvut.fel.matyapav.afandroid.builders.ListBuilder;
import cz.cvut.fel.matyapav.afandroid.components.types.AFList;
import cz.cvut.fel.matyapav.showcase.BusinessTripDetailActivity;
import cz.cvut.fel.matyapav.showcase.R;
import cz.cvut.fel.matyapav.showcase.security.ApplicationContext;
import cz.cvut.fel.matyapav.showcase.skins.BusinessTripListSkin;
import cz.cvut.fel.matyapav.showcase.utils.ShowCaseUtils;
import cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants;

import static android.app.Activity.RESULT_OK;
import static cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants.BUSINESS_TRIP_EDIT_REQUEST;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class BusinessTripsListFragment extends BaseFragment {

    public static final String SCREEN_DEFINITION_URL = "SCREEN_DEFINITION_URL";
    public static final String SCREEN_DEFINITION_KEY = "SCREEN_DEFINITION_KEY";
    public static final String LIST_ID = "LIST_ID";
    public static final String LIST_POSITITON = "LIST_POSITION";

    @Override
    public View initialize(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.business_trips_list_fragment_layout, container, false);
        //get layouts where we want to put components
        LinearLayout businessTripListLayout = root.findViewById(R.id.businessTripsListWrapper);

        //initialize builders
        HashMap<String, String> securityConstrains = ApplicationContext.getInstance().getSecurityContext().getUserCredentials();

        ListBuilder listBuilder = getScreenDefinition()
                .getListBuilderByKey(ShowcaseConstants.BUSINESS_TRIPS_LIST)
                .setConnectionParameters(securityConstrains)
                .setSkin(new BusinessTripListSkin(getContext()));

        //create and insert form
        try {
            final AFList list = listBuilder.createComponent();
            businessTripListLayout.addView(list.getView());

            list.getListView().setOnItemClickListener((parent, view, position, id) -> {
                Intent intent = new Intent(getActivity(), BusinessTripDetailActivity.class);
                intent.putExtra(SCREEN_DEFINITION_URL, getScreenDefinition().getScreenUrl());
                intent.putExtra(SCREEN_DEFINITION_KEY, getScreenDefinition().getKey());
                intent.putExtra(LIST_ID, ShowcaseConstants.BUSINESS_TRIPS_LIST);
                intent.putExtra(LIST_POSITITON, position);
                startActivityForResult(intent, BUSINESS_TRIP_EDIT_REQUEST);
            });

            FloatingActionButton addBtn = root.findViewById(R.id.add_business_trip_btn);
            if(addBtn != null){
                addBtn.setOnClickListener(v -> {
                    Intent intent = new Intent(getActivity(), BusinessTripDetailActivity.class);
                    intent.putExtra(SCREEN_DEFINITION_URL, getScreenDefinition().getScreenUrl());
                    intent.putExtra(SCREEN_DEFINITION_KEY, getScreenDefinition().getKey());
                    startActivityForResult(intent, BUSINESS_TRIP_EDIT_REQUEST);
                });
            }
        } catch (Exception e) {
            ShowCaseUtils.showBuildingFailedDialog(getActivity(), e);
            e.printStackTrace();
        }

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == BUSINESS_TRIP_EDIT_REQUEST) {
            try {
                ShowCaseUtils.refreshCurrentFragment(getActivity(), getScreenDefinition().getScreenUrl(), getScreenDefinition().getKey());
                if (resultCode == RESULT_OK) {
                    Toast.makeText(getActivity(), "Business trip was updated", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
