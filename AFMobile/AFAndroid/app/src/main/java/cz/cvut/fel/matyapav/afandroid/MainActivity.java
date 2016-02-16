package cz.cvut.fel.matyapav.afandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cz.cvut.fel.matyapav.afandroid.builders.FormBuilder;
import cz.cvut.fel.matyapav.afandroid.components.AFForm;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.afandroid.utils.SupportedLanguages;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    JSONObject loginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Login Request //TODO convert to Async Task in AFForm --------------------
        final RequestQueue queue = Volley.newRequestQueue(this);
        //String host = "10.0.2.2:8080";
        String host = "192.168.1.39:8080";
        //final String url = "http://"+host+"/AFServer/rest/users/loginForm";
        //  final String url = "http://10.0.2.2:8080/AFServer/rest/country/definition";
        final String url = "http://"+host+"/AFServer/rest/users/profile";
        String loginURL = "http://"+host+"/AFServer/rest/users/login";

        final StringRequest loginRequest = new StringRequest(Request.Method.POST, loginURL, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
                Toast.makeText(getApplicationContext(), Localization.translate("login.success", getThisActivity()), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
                Toast.makeText(getApplicationContext(),Localization.translate("login.failed",getThisActivity()), Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                return loginInfo.toString().getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };

        //-----------------------------------------------------------------------------------------
        //LoginForm
        LinearLayout layout = (LinearLayout) findViewById(R.id.mainLayout);
        FormBuilder builder = new FormBuilder(getThisActivity());
        final AFForm form = builder.createForm(url);
        if(form != null) {
            Button button = builder.buildSubmitButton(Localization.translate("login.buttonText", getThisActivity()), form);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (form.validateForm()) {
                        try {
                            loginInfo = new JSONObject();
                            for (AFField field : form.getFields()) {
                                loginInfo.put(field.getId(), ((EditText) field.getFieldView()).getText());
                            }
                            queue.add(loginRequest);
                            System.err.println(loginInfo.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            //test accessing field in form
            //form.getFieldById("endDate").getLabel().setTextColor(Color.BLUE);

            layout.addView(form.getView());
            layout.addView(button);
        }
        //------------------------------------------------------------------------------------------
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.langCZ){
            Localization.changeLanguage(SupportedLanguages.CZ, getThisActivity());
            refreshActivity();
            }
        else if(id == R.id.langEN) {
            Localization.changeLanguage(SupportedLanguages.EN, getThisActivity());
            refreshActivity();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public MainActivity getThisActivity() {
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
