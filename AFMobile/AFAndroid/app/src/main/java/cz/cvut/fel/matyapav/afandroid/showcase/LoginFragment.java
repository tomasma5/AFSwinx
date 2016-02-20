package cz.cvut.fel.matyapav.afandroid.showcase;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.R;
import cz.cvut.fel.matyapav.afandroid.builders.FormBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.FieldBuilderFactory;
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
        InputStream connectionResource = getResources().openRawResource(R.raw.connection);
        final View root = inflater.inflate(R.layout.login_fragment_layout, container, false);

        LinearLayout layout = (LinearLayout) root.findViewById(R.id.loginLayout);
        final AFForm form;
        try {
            //init builder
            FormBuilder builder = AFAndroid.getInstance().getFormBuilder().
                    initBuilder(getActivity(), "loginForm", connectionResource, "loginForm");
            form = builder.createComponent();
            if(form != null) {
                Button button = builder.buildSubmitButton(Localization.translate("login.buttonText", getActivity()), form);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(form.validateData()) {
                            try {
                                form.sendData();
                                doLogin(form);
                            } catch (Exception e) {
                                //login failed
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                                alertDialog.setTitle("Login failed");
                                alertDialog.setMessage("Reason " + e.getMessage());
                                alertDialog.show();
                                e.printStackTrace();
                            }
                        }
                    }
                });
                layout.addView(form.getView());
                layout.addView(button);
            }
        } catch (Exception e) {
            //handle errors building failed
            System.err.println("FORM BUILDING FAILED");
            e.printStackTrace();
        }

        return root;
    }

    private void doLogin(AFForm form){
        AFField usernameField = form.getFieldById("username");
        AFField passwordField = form.getFieldById("password");
        if(usernameField != null && passwordField != null){
            //save user to shared preferences
            String username = (String) FieldBuilderFactory.getInstance().getFieldBuilder(usernameField.getFieldInfo()).getData(usernameField);
            String password = (String) FieldBuilderFactory.getInstance().getFieldBuilder(passwordField.getFieldInfo()).getData(passwordField);
            ShowCaseUtils.setUserInPreferences(getActivity(), username, password);
            //set visibility in menu
            Menu menu = ((NavigationView) getActivity().findViewById(R.id.nav_view)).getMenu();
            menu.setGroupVisible(R.id.beforeLoginGroup, false);
            menu.setGroupVisible(R.id.afterLoginGroup, true);

            //change fragment
            FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.mainLayout, new WelcomeFragment());
            tx.commit();
        }
        //success
    }
}
