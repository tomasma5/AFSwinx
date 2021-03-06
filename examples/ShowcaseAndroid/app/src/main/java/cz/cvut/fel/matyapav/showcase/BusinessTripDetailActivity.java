package cz.cvut.fel.matyapav.showcase;

import android.content.Intent;
import android.os.Bundle;
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
import cz.cvut.fel.matyapav.afandroid.components.types.AFForm;
import cz.cvut.fel.matyapav.afandroid.components.types.AFList;
import cz.cvut.fel.matyapav.afandroid.components.uiproxy.AFAndroidProxyScreenDefinition;
import cz.cvut.fel.matyapav.showcase.security.ApplicationContext;
import cz.cvut.fel.matyapav.showcase.skins.BusinessTripFormSkin;
import cz.cvut.fel.matyapav.showcase.utils.ShowCaseUtils;
import cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants;

import static cz.cvut.fel.matyapav.showcase.fragments.BusinessTripsListFragment.LIST_ID;
import static cz.cvut.fel.matyapav.showcase.fragments.BusinessTripsListFragment.LIST_POSITITON;
import static cz.cvut.fel.matyapav.showcase.fragments.BusinessTripsListFragment.SCREEN_DEFINITION_KEY;
import static cz.cvut.fel.matyapav.showcase.fragments.BusinessTripsListFragment.SCREEN_DEFINITION_URL;
import static cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants.BUSINESS_TRIP_EDIT_REQUEST;
import static cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants.BUSINESS_TRIP_PARTS_REQUEST;

public class BusinessTripDetailActivity extends AppCompatActivity {

    public static final String BUSINESS_TRIP_ID = "businessTripId";

    private View.OnClickListener onFormPerformListener = v -> {
        AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.BUSINESS_TRIPS_EDIT_FORM);
        if (form != null && form.validateData()) {
            try {
                form.sendData();
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
            } catch (Exception e) {
                //error while sending
                Toast.makeText(this, "There was an error while creating or updating business trip part", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener onFormResetListener = v -> {
        AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.BUSINESS_TRIPS_EDIT_FORM);
        if (form != null) {
            form.resetData();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_trip_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        String screenDefinitionUrl = extras.getString(SCREEN_DEFINITION_URL);
        String screenKey = extras.getString(SCREEN_DEFINITION_KEY);

        LinearLayout businessTripsFormLayout = findViewById(R.id.businessTripsFormWrapper);
        //initialize builders
        HashMap<String, String> securityConstrains = ApplicationContext.getInstance().getSecurityContext().getUserCredentials();

        try {
            AFAndroidProxyScreenDefinition screenDefinition =
                    AFAndroid.getInstance()
                            .getScreenDefinitionBuilder(this, screenDefinitionUrl, screenKey).getScreenDefinition();
            FormBuilder formBuilder = screenDefinition
                    .getFormBuilderByKey(ShowcaseConstants.BUSINESS_TRIPS_EDIT_FORM)
                    .setConnectionParameters(securityConstrains)
                    .setSkin(new BusinessTripFormSkin(this));

            AFForm form = formBuilder.createComponent();
            String listId = extras.getString(LIST_ID);
            int listPosition = extras.getInt(LIST_POSITITON);
            if (listId != null) {
                final AFList list = (AFList) AFAndroid.getInstance().getCreatedComponents().get(listId);
                form.insertData(list.getDataFromItemOnPosition(listPosition));
            }
            if (businessTripsFormLayout != null) {
                businessTripsFormLayout.addView(form.getView());
            }

            Button tripParts = findViewById(R.id.businessTripsBtnParts);
            if (tripParts != null) {
                tripParts.setOnClickListener(v -> {
                    int businessTripId = Integer.parseInt(form.getDataFromFieldWithId("id").toString());
                    Intent intent = new Intent(this, BusinessTripPartsActivity.class);
                    intent.putExtra(SCREEN_DEFINITION_URL, screenDefinition.getScreenUrl());
                    intent.putExtra(SCREEN_DEFINITION_KEY, screenDefinition.getKey());
                    intent.putExtra(BUSINESS_TRIP_ID, businessTripId);
                    startActivity(intent);
                });
            }
        } catch (Exception e) {
            ShowCaseUtils.showBuildingFailedDialog(this, e);
            e.printStackTrace();
            return;
        }

        Button perform = findViewById(R.id.businessTripsBtnAdd);
        if (perform != null) {
            perform.setOnClickListener(onFormPerformListener);
        }
        Button reset = findViewById(R.id.businessTripsBtnReset);
        if (reset != null) {
            reset.setOnClickListener(onFormResetListener);
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


}
