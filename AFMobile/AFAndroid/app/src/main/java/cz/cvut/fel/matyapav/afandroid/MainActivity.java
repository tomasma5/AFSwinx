package cz.cvut.fel.matyapav.afandroid;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
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
import java.util.Locale;
import java.util.Map;

import cz.cvut.fel.matyapav.afandroid.builders.FormBuilder;
import cz.cvut.fel.matyapav.afandroid.components.AFForm;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.utils.SupportedLanguages;

public class MainActivity extends AppCompatActivity {

    JSONObject loginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //LOCALIZATION
        final ImageView changeLang = (ImageView) findViewById(R.id.changeLang);
        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getThisActivity());
                dialog.setContentView(R.layout.lang_diag);
                dialog.setCancelable(true);
                dialog.setTitle(Localization.translate("lang.title", getThisActivity()));
                Button cz = (Button) dialog.findViewById(R.id.langCZ);
                cz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Localization.changeLanguage(SupportedLanguages.CZ, getThisActivity());
                        dialog.dismiss();
                        refreshActivity();
                    }
                });
                Button en = (Button) dialog.findViewById(R.id.langEN);
                en.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Localization.changeLanguage(SupportedLanguages.EN, getThisActivity());
                        dialog.dismiss();
                        refreshActivity();
                    }
                });

                dialog.show();
            }
        });



        //Login Request //TODO convert to Async Task in AFForm --------------------
        final RequestQueue queue = Volley.newRequestQueue(this);
        //String host = "10.0.2.2:8080";
        String host = "192.168.1.39:8080";
        final String url = "http://"+host+"/AFServer/rest/users/loginForm";
        //  final String url = "http://10.0.2.2:8080/AFServer/rest/country/definition";
        String loginURL = "http://"+host+"/AFServer/rest/users/login";

        final StringRequest loginRequest = new StringRequest(Request.Method.POST, loginURL, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
                Toast.makeText(getApplicationContext(), Localization.translate("login.success",getThisActivity()), Toast.LENGTH_SHORT).show();
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
            layout.addView(form.getView());
            layout.addView(button);
        }
        //------------------------------------------------------------------------------------------
    }

    public MainActivity getThisActivity() {
        return this;
    }

    public void refreshActivity(){
        Intent i = new Intent(getThisActivity(),getThisActivity().getClass());

        finish();
        overridePendingTransition(0, 0);
        getThisActivity().startActivity(i);
        overridePendingTransition( 0, 0);
    }
}
