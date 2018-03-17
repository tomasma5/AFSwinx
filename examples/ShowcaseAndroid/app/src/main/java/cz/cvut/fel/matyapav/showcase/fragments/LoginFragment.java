package cz.cvut.fel.matyapav.showcase.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.types.AFForm;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.showcase.R;
import cz.cvut.fel.matyapav.showcase.security.ApplicationContext;
import cz.cvut.fel.matyapav.showcase.security.ShowcaseSecurity;
import cz.cvut.fel.matyapav.showcase.skins.LoginSkin;
import cz.cvut.fel.matyapav.showcase.utils.ShowCaseUtils;
import cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
 */
public class LoginFragment extends BaseFragment {

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
                    alertDialog.setTitle(Localization.translate(getContext(), "login.failed"));
                    alertDialog.setMessage(Localization.translate(getContext(), "error.reason") + e.getMessage());
                    alertDialog.show();
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public View initialize(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.login_fragment_layout, container, false);

        final LinearLayout layout = root.findViewById(R.id.loginLayout);
        try {
            //init builder
            AFForm form = getScreenDefinition()
                    .getFormBuilderByKey(ShowcaseConstants.LOGIN_FORM)
                    .setSkin(new LoginSkin(getContext()))
                    .createComponent();
            layout.addView(form.getView());
        } catch (Exception e) {
            ShowCaseUtils.showBuildingFailedDialog(getActivity(), e);
            System.err.println("FORM BUILDING FAILED");
            e.printStackTrace();
        }

        Button loginButton = new Button(getActivity());
        loginButton.setLayoutParams(new ViewGroup.LayoutParams(
                ShowCaseUtils.convertDpToPixels(200, getContext()), ViewGroup.LayoutParams.WRAP_CONTENT));
        loginButton.setText(Localization.translate(getContext(), "login.buttonText"));
        loginButton.setOnClickListener(onLoginButtonClick);
        layout.addView(loginButton);

        return root;
    }

    private void doLogin(AFForm form) {
        AFField usernameField = form.getFieldById("username");
        AFField passwordField = form.getFieldById("password");
        if (usernameField != null && passwordField != null) {
            //save user to application context
            String username = (String) form.getDataFromFieldWithId("username");
            String password = (String) form.getDataFromFieldWithId("password");
            ApplicationContext.getInstance().setSecurityContext(new ShowcaseSecurity(username, password, true));
            //change fragment
            FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.mainLayout, new WelcomeFragment());
            tx.commit();
        }
        //success
    }


}
