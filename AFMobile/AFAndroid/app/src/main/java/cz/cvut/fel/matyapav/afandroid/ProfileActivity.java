package cz.cvut.fel.matyapav.afandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity  {

    JSONObject loginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public ProfileActivity getThisActivity() {
        return this;
    }

    private void refreshActivity(){
        finish();
        Intent i = new Intent(getThisActivity(),getThisActivity().getClass());
        //no animation
        overridePendingTransition(0, 0);
        getThisActivity().startActivity(i);
        overridePendingTransition(0, 0);

    }
}
