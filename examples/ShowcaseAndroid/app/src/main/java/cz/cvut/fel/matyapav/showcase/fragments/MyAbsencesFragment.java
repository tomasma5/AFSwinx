package cz.cvut.fel.matyapav.showcase.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import java.util.HashMap;
import cz.cvut.fel.matyapav.afandroid.components.types.AFList;
import cz.cvut.fel.matyapav.showcase.R;
import cz.cvut.fel.matyapav.showcase.security.ApplicationContext;
import cz.cvut.fel.matyapav.showcase.skins.MyAbsencesListSkin;
import cz.cvut.fel.matyapav.showcase.utils.ShowCaseUtils;
import cz.cvut.fel.matyapav.showcase.utils.ShowcaseConstants;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
 */
public class MyAbsencesFragment extends BaseFragment {

    @Override
    public View initialize(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.my_absences_fragment_layout, container, false);
        LinearLayout myAbsencesLayout = root.findViewById(R.id.myAbsencesLayout);

        //initialize builders
        HashMap<String, String> securityConstrains = ApplicationContext.getInstance().getSecurityContext().getUserCredentials();

        try {
            AFList list = getScreenDefinition()
                    .getListBuilderByKey(ShowcaseConstants.MY_ABSENCES_LIST)
                    .setConnectionParameters(securityConstrains)
                    .setSkin(new MyAbsencesListSkin(getContext()))
                    .createComponent();
            myAbsencesLayout.addView(list.getView());
        } catch (Exception e) {
            ShowCaseUtils.showBuildingFailedDialog(getActivity(), e);
            e.printStackTrace();
        }
        return root;
    }
}
