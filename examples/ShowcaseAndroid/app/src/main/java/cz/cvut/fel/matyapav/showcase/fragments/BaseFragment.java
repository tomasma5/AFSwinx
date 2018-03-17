package cz.cvut.fel.matyapav.showcase.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.tomscz.afswinx.component.AFSwinxBuildException;

import java.util.Map;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.components.types.AFMenu;
import cz.cvut.fel.matyapav.afandroid.components.types.AFScreenButton;
import cz.cvut.fel.matyapav.afandroid.components.uiproxy.AFAndroidProxyScreenDefinition;
import cz.cvut.fel.matyapav.afandroid.components.uiproxy.AFAndroidScreenPreparedListener;
import cz.cvut.fel.matyapav.showcase.MainActivity;
import cz.cvut.fel.matyapav.showcase.R;
import cz.cvut.fel.matyapav.showcase.security.ApplicationContext;
import cz.cvut.fel.matyapav.showcase.utils.ProxyConstants;
import cz.cvut.fel.matyapav.showcase.utils.ShowCaseUtils;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public abstract class BaseFragment extends Fragment {

    private AFAndroidProxyScreenDefinition screenDefinition;

    private AFAndroidScreenPreparedListener loginListener;
    private AFAndroidScreenPreparedListener avaiableCountryPublicListener;
    private AFAndroidScreenPreparedListener vehiclesButtonListener;
    private AFAndroidScreenPreparedListener myProfileListener;
    private AFAndroidScreenPreparedListener absenceTypeListener;
    private AFAndroidScreenPreparedListener absenceInstanceCreateListener;
    private AFAndroidScreenPreparedListener myAbsenceInstanceListener;
    private AFAndroidScreenPreparedListener absenceInstanceEditListener;
    private AFAndroidScreenPreparedListener businessTripsListener;

    public void setScreenDefinition(AFAndroidProxyScreenDefinition screenDefinition) {
        this.screenDefinition = screenDefinition;
    }

    public AFAndroidProxyScreenDefinition getScreenDefinition() {
        return screenDefinition;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ApplicationContext.getInstance().setCurrentFragment(this);
        createMenu();
        if (getScreenDefinition() != null) {
            getScreenDefinition().reload(getContext());
        }
        return initialize(inflater, container, savedInstanceState);
    }

    public abstract View initialize(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    private void createMenu() {
        LinearLayout menuLayout = getActivity().findViewById(R.id.menuLayout);
        menuLayout.removeAllViews();
        try {
            AFMenu afmenu = AFAndroid.getInstance().getMenuBuilder(getContext())
                    .setUrl("http://10.0.2.2:8081/UIxy/api/screens")
                    .buildComponent();
            initializeScreenPreparedListeners();
            Map<String, AFScreenButton> menuButtons = afmenu.getMenuButtons();
            try {
                menuButtons.get(ProxyConstants.BTN_KEY_LOGIN).setScreenPreparedListener(loginListener);
                menuButtons.get(ProxyConstants.BTN_KEY_COUNTRIES).setScreenPreparedListener(avaiableCountryPublicListener);
                menuButtons.get(ProxyConstants.BTN_KEY_VEHICLES).setScreenPreparedListener(vehiclesButtonListener);
                menuButtons.get(ProxyConstants.BTN_KEY_PROFILE).setScreenPreparedListener(myProfileListener);
                menuButtons.get(ProxyConstants.BTN_KEY_ABSENCE_TYPE).setScreenPreparedListener(absenceTypeListener);
                menuButtons.get(ProxyConstants.BTN_KEY_CREATE_ABSENCE).setScreenPreparedListener(absenceInstanceCreateListener);
                menuButtons.get(ProxyConstants.BTN_KEY_MY_ABSENCES).setScreenPreparedListener(myAbsenceInstanceListener);
                menuButtons.get(ProxyConstants.BTN_KEY_ABSENCE_MANAGEMENT).setScreenPreparedListener(absenceInstanceEditListener);
                menuButtons.get(ProxyConstants.BTN_KEY_BUSINESS_TRIPS).setScreenPreparedListener(businessTripsListener);
            } catch (NullPointerException ex) {
                System.err.println("One of the menu buttons were not found. Please check the exception\n");
                ex.printStackTrace();
            }
            //FIXME opravit menu podle toho jestli je user lognuty nebo nea

            if (afmenu.getMenuButtons() != null) {
                if (ApplicationContext.getInstance().getSecurityContext() != null && ApplicationContext.getInstance().getSecurityContext().isUserLogged()) {
                    //home button
                    Button home = createHomeButton();
                    menuLayout.addView(home);
                    //other buttons
                    AFScreenButton logoutButton = null;
                    for (AFScreenButton button : afmenu.getMenuButtons().values()) {
                        if (button.getKey().equals(ProxyConstants.BTN_KEY_LOGIN)) {
                            //skip login button
                            logoutButton = AFAndroid.getInstance()
                                    .getScreenButtonBuilder(getContext())
                                    .buildComponent(ProxyConstants.BTN_CUSTOM_LOGOUT, "Logout", button.getUrl());
                            logoutButton.setScreenPreparedListener(button.getScreenPreparedListener());
                            logoutButton.setCustomOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ApplicationContext.getInstance().setSecurityContext(null);
                                }
                            });
                            continue;
                        }
                        menuLayout.addView(button, button.getLayoutParams());
                    }
                    //logout button
                    menuLayout.addView(logoutButton);
                } else {
                    AFScreenButton loginButton = afmenu.getMenuButtons().get(ProxyConstants.BTN_KEY_LOGIN);
                    loginButton.setText("Login");
                    menuLayout.addView(loginButton);
                }
            }

        } catch (AFSwinxBuildException e) {
            ShowCaseUtils.showBuildingFailedDialog(getContext(), e);
            e.printStackTrace();
        }
    }

    private Button createHomeButton() {
        Button button = new Button(getContext());
        button.setText("Home");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change fragment
                FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
                tx.replace(R.id.mainLayout, new WelcomeFragment());
                tx.commit();
                DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
                if (drawer != null) {
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });
        return button;
    }

    private void initializeScreenPreparedListeners() {
        loginListener = new AFAndroidScreenPreparedListener() {
            @Override
            public void onScreenPrepared(AFAndroidProxyScreenDefinition afAndroidProxyScreenDefinition) {
                changeFragment(LoginFragment.class, afAndroidProxyScreenDefinition);
            }
        };
        avaiableCountryPublicListener = new AFAndroidScreenPreparedListener() {
            @Override
            public void onScreenPrepared(AFAndroidProxyScreenDefinition afAndroidProxyScreenDefinition) {
                changeFragment(CountriesFragment.class, afAndroidProxyScreenDefinition);
            }
        };
        vehiclesButtonListener = new AFAndroidScreenPreparedListener() {
            @Override
            public void onScreenPrepared(AFAndroidProxyScreenDefinition afAndroidProxyScreenDefinition) {
                changeFragment(VehiclesFragment.class, afAndroidProxyScreenDefinition);
            }
        };
        myProfileListener = new AFAndroidScreenPreparedListener() {
            @Override
            public void onScreenPrepared(AFAndroidProxyScreenDefinition afAndroidProxyScreenDefinition) {
                changeFragment(ProfileFragment.class, afAndroidProxyScreenDefinition);
            }
        };
        absenceTypeListener = new AFAndroidScreenPreparedListener() {
            @Override
            public void onScreenPrepared(AFAndroidProxyScreenDefinition afAndroidProxyScreenDefinition) {
                changeFragment(AbsenceTypeManagementFragment.class, afAndroidProxyScreenDefinition);
            }
        };
        absenceInstanceCreateListener = new AFAndroidScreenPreparedListener() {
            @Override
            public void onScreenPrepared(AFAndroidProxyScreenDefinition afAndroidProxyScreenDefinition) {
                changeFragment(CreateAbsenceFragment.class, afAndroidProxyScreenDefinition);
            }
        };
        myAbsenceInstanceListener = new AFAndroidScreenPreparedListener() {
            @Override
            public void onScreenPrepared(AFAndroidProxyScreenDefinition afAndroidProxyScreenDefinition) {
                changeFragment(MyAbsencesFragment.class, afAndroidProxyScreenDefinition);
            }
        };
        absenceInstanceEditListener = new AFAndroidScreenPreparedListener() {
            @Override
            public void onScreenPrepared(AFAndroidProxyScreenDefinition afAndroidProxyScreenDefinition) {
                changeFragment(AbsenceManagementFragment.class, afAndroidProxyScreenDefinition);
            }
        };
        businessTripsListener = new AFAndroidScreenPreparedListener() {
            @Override
            public void onScreenPrepared(AFAndroidProxyScreenDefinition afAndroidProxyScreenDefinition) {
                changeFragment(BusinessTripsListFragment.class, afAndroidProxyScreenDefinition);
            }
        };
    }

    private void changeFragment(Class fragmentClass, AFAndroidProxyScreenDefinition screenDefinition) {
        try {
            BaseFragment fragment = (BaseFragment) fragmentClass.newInstance();
            fragment.setScreenDefinition(screenDefinition);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.mainLayout, fragment).commit();
            DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
            if (drawer != null) {
                drawer.closeDrawer(GravityCompat.START);
            }
        } catch (Exception e) {
            Log.e(MainActivity.class.toString(), "Cannot instantiate fragment");
            e.printStackTrace();
        }
    }


}
