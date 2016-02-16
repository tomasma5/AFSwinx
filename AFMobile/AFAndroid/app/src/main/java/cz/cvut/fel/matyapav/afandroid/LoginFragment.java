package cz.cvut.fel.matyapav.afandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

/**
 * Created by Pavel on 16.02.2016.
 */
public class LoginFragment extends Fragment {

    JSONObject loginInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String host = "192.168.1.39:8080";
        final String url = "http://"+host+"/AFServer/rest/users/loginForm";
        String loginURL = "http://"+host+"/AFServer/rest/users/login";

        final View root = inflater.inflate(R.layout.login_fragment_layout, container, false);

        //TODO convert to ASYNC TASK for form data
        final RequestQueue queue = Volley.newRequestQueue(getContext());
        final StringRequest loginRequest = new StringRequest(Request.Method.POST, loginURL, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                LinearLayout mainLayout = (LinearLayout) root.findViewById(R.id.loginLayout);
                Toast.makeText(getContext(), Localization.translate("login.success", getActivity()), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                LinearLayout mainLayout = (LinearLayout) root.findViewById(R.id.mainLayout);
                Toast.makeText(getContext(),Localization.translate("login.failed",getActivity()), Toast.LENGTH_SHORT).show();
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

        LinearLayout layout = (LinearLayout) root.findViewById(R.id.loginLayout);
        FormBuilder builder = new FormBuilder(getActivity());
        final AFForm form = builder.createForm(url);
        if(form != null) {
            Button button = builder.buildSubmitButton(Localization.translate("login.buttonText", getActivity()), form);
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
            layout.addView(form.getView());
            layout.addView(button);
        }

        return root;
    }
}
