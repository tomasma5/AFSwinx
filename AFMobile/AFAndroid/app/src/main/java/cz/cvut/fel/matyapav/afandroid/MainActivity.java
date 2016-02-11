package cz.cvut.fel.matyapav.afandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import cz.cvut.fel.matyapav.afandroid.utils.UIMap;

public class MainActivity extends AppCompatActivity {

    JSONObject loginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RequestQueue queue = Volley.newRequestQueue(this);

        final String lang = "CZ"; //DUMMY TRANSLATIONS

       final String url = "http://10.0.2.2:8080/AFServer/rest/users/loginForm";
      //  final String url = "http://10.0.2.2:8080/AFServer/rest/country/definition";
        String loginURL = "http://10.0.2.2:8080/AFServer/rest/users/login";

        //Login Request //TODO convert to Async Task in AFForm
        final StringRequest loginRequest = new StringRequest(Request.Method.POST, loginURL, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
                Toast.makeText(getApplicationContext(),"Login successful", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
                Toast.makeText(getApplicationContext(),"Login failed", Toast.LENGTH_SHORT).show();
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
        Button button = builder.buildSubmitButton(getThisActivity(), "Zaloguj se");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AFForm form = UIMap.createdForms.get("loginFormDefinitions");
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
        builder.createAndInsertForm(url, lang, button, layout);
        //------------------------------------------------------------------------------------------

    }

    public MainActivity getThisActivity(){
        return this;
    }


}
