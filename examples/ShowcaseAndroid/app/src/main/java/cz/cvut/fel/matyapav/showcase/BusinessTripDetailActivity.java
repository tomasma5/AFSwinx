package cz.cvut.fel.matyapav.showcase;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import cz.cvut.fel.matyapav.showcase.skins.BusinessTripFormSkin;
import cz.cvut.fel.matyapav.showcase.skins.CountryFormSkin;
import cz.cvut.fel.matyapav.showcase.utils.ShowCaseUtils;
import cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants;

import static cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants.BUSINESS_TRIP_EDIT_REQUEST;
import static cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants.connectionXmlId;

public class BusinessTripDetailActivity extends AppCompatActivity {

    private View.OnClickListener onFormPerformListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.BUSINESS_TRIPS_EDIT_FORM);
            if (form != null && form.validateData()) {
                try {
                    form.sendData();
                    Intent returnIntent = new Intent();
                    setResult(RESULT_OK, returnIntent);
                    finish();
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
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.BUSINESS_TRIPS_EDIT_FORM);
            if (form != null) {
                form.resetData();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_trip_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();

        LinearLayout businessTripsFormLayout = (LinearLayout) findViewById(R.id.businessTripsFormWrapper);

        //initialize builders
        HashMap<String, String> securityConstrains = ShowCaseUtils.getUserCredentials(this);

        FormBuilder formBuilder = AFAndroid.getInstance().getFormBuilder().initBuilder(this,
                ShowcaseConstants.BUSINESS_TRIPS_EDIT_FORM, getResources().openRawResource(connectionXmlId),
                ShowcaseConstants.BUSINESS_TRIPS_EDIT_FORM_CONNECTION_KEY, securityConstrains).setSkin(new BusinessTripFormSkin(this));

        //create and insert form
        try {

            AFForm form = formBuilder.createComponent();

            if (extras != null) {
                String listId = extras.getString("LIST_ID");
                int listPosition = extras.getInt("LIST_POSITION");
                final AFList list = (AFList) AFAndroid.getInstance().getCreatedComponents().get(listId);
                form.insertData(list.getDataFromItemOnPosition(listPosition));
            }

            Button perform = (Button) findViewById(R.id.businessTripsBtnAdd);
            perform.setOnClickListener(onFormPerformListener);
            Button reset = (Button) findViewById(R.id.businessTripsBtnReset);
            reset.setOnClickListener(onFormResetListener);
            businessTripsFormLayout.addView(form.getView());
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


}
