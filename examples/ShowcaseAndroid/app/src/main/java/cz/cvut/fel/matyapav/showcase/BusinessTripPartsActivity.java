package cz.cvut.fel.matyapav.showcase;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.builders.FormBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.ListBuilder;
import cz.cvut.fel.matyapav.afandroid.components.types.AFForm;
import cz.cvut.fel.matyapav.afandroid.components.types.AFList;
import cz.cvut.fel.matyapav.afandroid.components.uiproxy.AFAndroidProxyScreenDefinition;
import cz.cvut.fel.matyapav.showcase.security.ApplicationContext;
import cz.cvut.fel.matyapav.showcase.skins.BusinessTripFormSkin;
import cz.cvut.fel.matyapav.showcase.skins.BusinessTripListSkin;
import cz.cvut.fel.matyapav.showcase.utils.ShowCaseUtils;
import cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants;

import static cz.cvut.fel.matyapav.showcase.BusinessTripDetailActivity.BUSINESS_TRIP_ID;
import static cz.cvut.fel.matyapav.showcase.fragments.BusinessTripsListFragment.LIST_ID;
import static cz.cvut.fel.matyapav.showcase.fragments.BusinessTripsListFragment.LIST_POSITITON;
import static cz.cvut.fel.matyapav.showcase.fragments.BusinessTripsListFragment.SCREEN_DEFINITION_KEY;
import static cz.cvut.fel.matyapav.showcase.fragments.BusinessTripsListFragment.SCREEN_DEFINITION_URL;
import static cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants.BUSINESS_TRIP_EDIT_REQUEST;
import static cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants.BUSINESS_TRIP_PARTS_EDIT_REQUEST;
import static cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants.BUSINESS_TRIP_PARTS_REQUEST;

public class BusinessTripPartsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_trip_parts);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        String screenDefinitionUrl = extras.getString(SCREEN_DEFINITION_URL);
        String screenKey = extras.getString(SCREEN_DEFINITION_KEY);
        int businessTripId = extras.getInt(BUSINESS_TRIP_ID);

        LinearLayout businessTripPartsListLayout = findViewById(R.id.businessTripPartsListWrapper);

        //initialize builders
        HashMap<String, String> securityConstrains = ApplicationContext.getInstance().getSecurityContext().getUserCredentials();
        securityConstrains.put(BUSINESS_TRIP_ID, String.valueOf(businessTripId));

        try {
            AFAndroidProxyScreenDefinition screenDefinition =
                    AFAndroid.getInstance()
                            .getScreenDefinitionBuilder(this, screenDefinitionUrl, screenKey).getScreenDefinition();
            ListBuilder listBuilder = screenDefinition
                    .getListBuilderByKey(ShowcaseConstants.BUSINESS_TRIPS_PARTS_LIST)
                    .setConnectionParameters(securityConstrains)
                    .setSkin(new BusinessTripListSkin(this));
            final AFList list = listBuilder.createComponent();
            businessTripPartsListLayout.addView(list.getView());

            list.getListView().setOnItemClickListener((parent, view, position, id) -> {
                Intent intent = new Intent(this, BusinessTripPartDetailActivity.class);
                intent.putExtra(SCREEN_DEFINITION_URL, screenDefinition.getScreenUrl());
                intent.putExtra(SCREEN_DEFINITION_KEY, screenDefinition.getKey());
                intent.putExtra(BUSINESS_TRIP_ID, businessTripId);
                intent.putExtra(LIST_ID, ShowcaseConstants.BUSINESS_TRIPS_PARTS_LIST);
                intent.putExtra(LIST_POSITITON, position);
                startActivityForResult(intent, BUSINESS_TRIP_PARTS_EDIT_REQUEST);
            });

            FloatingActionButton addButton = findViewById(R.id.add_business_trip_part_btn);
            if(addButton != null) {
                addButton.setOnClickListener(v -> {
                    Intent intent = new Intent(this, BusinessTripPartDetailActivity.class);
                    intent.putExtra(SCREEN_DEFINITION_URL, screenDefinition.getScreenUrl());
                    intent.putExtra(SCREEN_DEFINITION_KEY, screenDefinition.getKey());
                    intent.putExtra(BUSINESS_TRIP_ID, businessTripId);
                    startActivityForResult(intent, BUSINESS_TRIP_PARTS_EDIT_REQUEST);
                });
            }
        } catch (Exception e) {
            ShowCaseUtils.showBuildingFailedDialog(this, e);
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == BUSINESS_TRIP_PARTS_EDIT_REQUEST) {
            try {
                if (resultCode == RESULT_OK) {

                    Toast.makeText(this, "Business trip part was created or updated", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(getIntent());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
