package cz.cvut.fel.matyapav.showcase.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.showcase.R;
import cz.cvut.fel.matyapav.showcase.security.ApplicationContext;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
 */
public class WelcomeFragment extends BaseFragment {

    @SuppressLint("SetTextI18n")
    @Override
    public View initialize(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.welcome_fragment_layout, container, false);
        LinearLayout welcomeLayout = root.findViewById(R.id.welcomeLayout);
        TextView welcomeUserText = welcomeLayout.findViewById(R.id.welcomeUserText);
        welcomeUserText.setText(Localization.translate(getContext(), "welcome.msg") + ": " +
                ApplicationContext.getInstance().getSecurityContext().getUsername());
        return root;
    }
}
