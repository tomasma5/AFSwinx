package cz.cvut.fel.matyapav.showcase.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.builders.ListBuilder;
import cz.cvut.fel.matyapav.afandroid.components.types.AFList;
import cz.cvut.fel.matyapav.showcase.BusinessTripDetailActivity;
import cz.cvut.fel.matyapav.showcase.R;
import cz.cvut.fel.matyapav.showcase.skins.BusinessTripListSkin;
import cz.cvut.fel.matyapav.showcase.skins.ListSkin;
import cz.cvut.fel.matyapav.showcase.utils.ShowCaseUtils;
import cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants;

import static android.app.Activity.RESULT_OK;
import static cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants.BUSINESS_TRIP_EDIT_REQUEST;
import static cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants.connectionXmlId;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class BusinessTripsListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.business_trips_list_fragment_layout, container, false);
        //get layouts where we want to put components
        LinearLayout businessTripListLayout = (LinearLayout) root.findViewById(R.id.businessTripsListWrapper);

        //initialize builders
        HashMap<String, String> securityConstrains = ShowCaseUtils.getUserCredentials(getActivity());

        ListBuilder listBuilder = AFAndroid.getInstance().getListBuilder().initBuilder(getActivity(),
                ShowcaseConstants.BUSINESS_TRIPS_LIST, getResources().openRawResource(connectionXmlId),
                ShowcaseConstants.BUSINESS_TRIPS_LIST_CONNECTION_KEY, securityConstrains).setSkin(new BusinessTripListSkin(getContext()));

        //create and insert form
        try {
            final AFList list = listBuilder.createComponent();
            businessTripListLayout.addView(list.getView());

            list.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), BusinessTripDetailActivity.class);
                    intent.putExtra("LIST_ID", ShowcaseConstants.BUSINESS_TRIPS_LIST);
                    intent.putExtra("LIST_POSITION", position);
                    startActivityForResult(intent, BUSINESS_TRIP_EDIT_REQUEST);
                }
            });
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
                ShowCaseUtils.refreshCurrentFragment(getActivity());
                if (resultCode == RESULT_OK) {
                    Toast.makeText(getActivity(), "Business trip was updated", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
