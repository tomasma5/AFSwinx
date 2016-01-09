package cz.cvut.fel.matyapav.afandroid;

import android.app.ActionBar;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cz.cvut.fel.matyapav.afandroid.builders.FormBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.InputFieldBuilder;

public class MainActivity extends AppCompatActivity {

    JSONObject loginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RequestQueue queue = Volley.newRequestQueue(this);

        final String lang = "CZ"; //DUMMY TRANSLATIONS

        String url = "http://10.0.2.2:8080/AFServer/rest/users/loginForm";
       // String url = "http://10.0.2.2:8080/AFServer/rest/country/definition";
        String loginURL = "http://10.0.2.2:8080/AFServer/rest/users/login";

        final StringRequest loginRequest = new StringRequest(Request.Method.POST, loginURL, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
                TextView loggedIn = new TextView(getThisActivity());
                loggedIn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                loggedIn.setText("OK");
                mainLayout.addView(loggedIn);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
                TextView loggedIn = new TextView(getThisActivity());
                loggedIn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                loggedIn.setText("FAILED");
                mainLayout.addView(loggedIn);
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

        final StringRequest stringRequest = new StringRequest
                (Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // CREATE LOGIN FORM
                        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
                        FormBuilder formBuilder = new FormBuilder();
                        final AFForm form = formBuilder.buildForm(response, getThisActivity(), lang);
                        mainLayout.addView(form.getFormView());

                        //SET SUMBIT BUTTON //TODO move this somewhere .. maybe in formBuilder
                        Button button = formBuilder.buildSumbitButton(getThisActivity());
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(form.validateForm()){
                                    try {
                                        loginInfo = new JSONObject();
                                        for (AFField field : form.getFields()) {
                                            loginInfo.put(field.getId(), field.getField().getText());
                                        }
                                        queue.add(loginRequest);
                                        System.err.println(loginInfo.toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                        mainLayout.addView(button);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };

        //SET CREATE BUTTON
        Button btn = (Button) findViewById(R.id.retryBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queue.add(stringRequest);
            }
        });
    }

    public MainActivity getThisActivity(){
        return this;
    }


}
