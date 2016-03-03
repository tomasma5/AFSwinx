package cz.cvut.fel.matyapav.afandroid.showcase.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.R;
import cz.cvut.fel.matyapav.afandroid.components.types.AFForm;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.showcase.utils.ShowCaseUtils;
import cz.cvut.fel.matyapav.afandroid.showcase.skins.LoginSkin;
import cz.cvut.fel.matyapav.afandroid.showcase.utils.ShowcaseConstants;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;

/**
 * Created by Pavel on 16.02.2016.
 */
public class LoginFragment extends Fragment {

    private View.OnClickListener onLoginButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AFForm form = (AFForm) AFAndroid.getInstance().getCreatedComponents().get(ShowcaseConstants.LOGIN_FORM);
            if (form != null && form.validateData()) {
                try {
                    form.sendData();
                    doLogin(form);
                } catch (Exception e) {
                    //login failed
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle(Localization.translate("login.failed", getActivity()));
                    alertDialog.setMessage(Localization.translate("error.reason", getActivity()) + e.getMessage());
                    alertDialog.show();
                    e.printStackTrace();
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.login_fragment_layout, container, false);

        final LinearLayout layout = (LinearLayout) root.findViewById(R.id.loginLayout);
        try {
            //init builder
            AFForm form = AFAndroid.getInstance().getFormBuilder().initBuilder(getActivity(),
                    ShowcaseConstants.LOGIN_FORM, getResources().openRawResource(R.raw.connection),
                    ShowcaseConstants.LOGIN_FORM_CONNECTION_KEY).
                    setSkin(new LoginSkin(getContext())).createComponent();
            layout.addView(form.getView());
        } catch (Exception e) {
            ShowCaseUtils.showBuildingFailedDialog(getActivity(), e);
            System.err.println("FORM BUILDING FAILED");
            e.printStackTrace();
        }

        Button loginButton = new Button(getActivity());
        loginButton.setLayoutParams(new ViewGroup.LayoutParams(
                ShowCaseUtils.convertDpToPixels(200, getContext()), ViewGroup.LayoutParams.WRAP_CONTENT));
        loginButton.setText(Localization.translate("login.buttonText", getActivity()));
        loginButton.setOnClickListener(onLoginButtonClick);
        layout.addView(loginButton);

        if(ShowCaseUtils.getUserLogin(getActivity())!= null){
            TextView lastLoggedAs = new TextView(getContext());
            lastLoggedAs.setText("Last logged as "+ShowCaseUtils.getUserLogin(getActivity()));
            lastLoggedAs.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            Button continueAsThisUser = new Button(getContext());
            continueAsThisUser.setText("Continue as this user");
            continueAsThisUser.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            continueAsThisUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //set visibility in menu
                    Menu menu = ((NavigationView) getActivity().findViewById(R.id.nav_view)).getMenu();
                    menu.setGroupVisible(R.id.beforeLoginGroup, false);
                    menu.setGroupVisible(R.id.afterLoginGroup, true);

                    //change fragment
                    FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
                    tx.replace(R.id.mainLayout, new WelcomeFragment());
                    tx.commit();
                }
            });
            layout.addView(lastLoggedAs);
            layout.addView(continueAsThisUser);
        }
        return root;
    }

    private void doLogin(AFForm form) {
        AFField usernameField = form.getFieldById("username");
        AFField passwordField = form.getFieldById("password");
        if (usernameField != null && passwordField != null) {
            //save user to shared preferences
            String username = (String) form.getDataFromFieldWithId("username");
            String password = (String) form.getDataFromFieldWithId("password");
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
