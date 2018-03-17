package cz.cvut.fel.matyapav.showcase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.components.uiproxy.AFAndroidProxyScreenDefinition;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedLanguages;
import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.showcase.fragments.LoginFragment;
import cz.cvut.fel.matyapav.showcase.security.ApplicationContext;
import cz.cvut.fel.matyapav.showcase.utils.ShowCaseUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().hasExtra("bundle") && savedInstanceState == null) {
            savedInstanceState = getIntent().getExtras().getBundle("bundle");
        }
        super.onCreate(savedInstanceState);
        //set localization
        Localization.setPathToStrings("cz.cvut.fel.matyapav.showcase");

        if (savedInstanceState != null && Localization.getCurrentLanguage() != null) {
            Localization.changeLanguage(getApplicationContext(), Localization.getCurrentLanguage());
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        if (savedInstanceState == null) {
            //set default login fragment
            try {
                ApplicationContext.getInstance().loadUIProxyUrl(getBaseContext());
                AFAndroid.getInstance().setApplicationContextUuid(ApplicationContext.getInstance().getUiProxyApplicationUuid(getBaseContext()));
            } catch (IOException e) {
                System.err.println("Cannot get properties file - so af android could not be properly configured");
                e.printStackTrace();
            }
            try {
                AFAndroidProxyScreenDefinition loginScreenDefinition = AFAndroid.getInstance()
                        .getScreenDefinitionBuilder(
                                getApplicationContext(),
                                ApplicationContext.getInstance().getUiProxyUrl() + "/api/screens/5a9955636402eb092c3b56c7")
                        .getScreenDefinition();
                LoginFragment loginFragment = new LoginFragment();
                loginFragment.setScreenDefinition(loginScreenDefinition);
                FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
                tx.replace(R.id.mainLayout, loginFragment);
                tx.commit();
            } catch (Exception e) {
                Log.e(MainActivity.class.getName(), "Cannot build login screen, for cause please check the stack trace.");
                ShowCaseUtils.showBuildingFailedDialog(this, e);
                e.printStackTrace();
            }
        } else {
            String currentFragmentUrl = getIntent().getStringExtra("current_fragment_proxy_url");
            ShowCaseUtils.refreshCurrentFragment(getThisActivity(), currentFragmentUrl);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
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
        // LOCALIZATION
        int id = item.getItemId();
        if (id == R.id.langCZ) {
            Localization.changeLanguage(getApplicationContext(), SupportedLanguages.CZ);
            System.err.println("Current locale: " + getThisActivity().getResources().getConfiguration().locale);
            restartActivity();
        } else if (id == R.id.langEN) {
            Localization.changeLanguage(getApplicationContext(), SupportedLanguages.EN);
            System.err.println("Current locale: " + getThisActivity().getResources().getConfiguration().locale);
            restartActivity();
        }
        //do not call restart activity if new language was not set
        return super.onOptionsItemSelected(item);
    }

    public MainActivity getThisActivity() {
        return this;
    }

    private void restartActivity() {
        Intent intent = getIntent();
        Bundle temp_bundle = new Bundle();
        onSaveInstanceState(temp_bundle);
        intent.putExtra("bundle", temp_bundle);
        intent.putExtra("current_fragment_proxy_url", ApplicationContext.getInstance().getCurrentFragment().getScreenDefinition().getScreenUrl());
        finish();
        startActivity(intent);
    }


}
