package cz.cvut.fel.matyapav.afandroid.showcase;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.R;
import cz.cvut.fel.matyapav.afandroid.builders.ListBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.TableBuilder;
import cz.cvut.fel.matyapav.afandroid.components.AFList;
import cz.cvut.fel.matyapav.afandroid.components.AFTable;
import cz.cvut.fel.matyapav.afandroid.showcase.skins.CountryListSkin;

/**
 * Created by Pavel on 16.02.2016.
 */
public class WelcomeFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.welcome_fragment_layout, container, false);
        LinearLayout welcomeLayout = (LinearLayout) root.findViewById(R.id.welcomeLayout);
        TextView welcomeUserText = (TextView) welcomeLayout.findViewById(R.id.welcomeUserText);
        welcomeUserText.setText("Welcome user: " + ShowCaseUtils.getUserCredentials(getActivity()).get("username"));

        //initialize builders
        HashMap<String, String> securityConstrains = ShowCaseUtils.getUserCredentials(getActivity());
        return root;
    }
}
