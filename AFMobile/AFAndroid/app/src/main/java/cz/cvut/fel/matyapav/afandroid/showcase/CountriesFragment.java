package cz.cvut.fel.matyapav.afandroid.showcase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.R;
import cz.cvut.fel.matyapav.afandroid.builders.FormBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.TableBuilder;
import cz.cvut.fel.matyapav.afandroid.components.AFForm;
import cz.cvut.fel.matyapav.afandroid.components.AFTable;
import cz.cvut.fel.matyapav.afandroid.showcase.skins.CountrySkin;

/**
 * Created by Pavel on 23.02.2016.
 */
public class CountriesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.countries_fragment_layout, container, false);
        //get layouts where we want to put components
        LinearLayout countriesTableLayout = (LinearLayout) root.findViewById(R.id.countriesTable);
        LinearLayout countriesFormLayout = (LinearLayout) root.findViewById(R.id.countriesForm);

        //initialize builders
        HashMap<String, String> securityConstrains = ShowCaseUtils.getUserCredentials(getActivity());

        TableBuilder tableBuilder = AFAndroid.getInstance().getTableBuilder()
                .initBuilder(getActivity(), "countryTable", getResources().openRawResource(R.raw.connection), "tableCountryPublic", securityConstrains);
        FormBuilder formBuilder = AFAndroid.getInstance().getFormBuilder()
                .initBuilder(getActivity(), "countryForm", getResources().openRawResource(R.raw.connection), "countryAdd", securityConstrains)
                .setSkin(new CountrySkin(getContext()));

        //create and insert table
        try {
            final AFTable table = tableBuilder.createComponent();
            if(table != null ) { //TODO <-- can be null?
                countriesTableLayout.addView(table.getView());


                for (int i = 0; i < table.getRows().size(); i++) {
                    final int finalI = i;
                    table.getRows().get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            StringBuilder rowInfo = new StringBuilder();
                            rowInfo.append("Row "+finalI+" :");
                            for(int j=0; j < table.getNumberOfColumns(); j++){
                                TextView cell = (TextView) table.getCellAt(finalI, j);
                                rowInfo.append(j +"="+cell.getText()+" ");
                            }
                            Toast.makeText(getActivity(), rowInfo, Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        } catch (Exception e) {
            //table building failed //TODO show for instance alert diag
            e.printStackTrace();
        }

        //create and insert form
        try {
            final AFForm form = formBuilder.createComponent();
            if(form != null) {
                Button btn =(Button) root.findViewById(R.id.countriesBtnAdd);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (form.validateData()) {
                            try {
                                form.sendData();
                                Toast.makeText(getActivity(), "Add or update complete", Toast.LENGTH_SHORT).show();
                                ShowCaseUtils.refreshCurrentFragment(getActivity());
                                //success
                            } catch (Exception e) {
                                //error while sending
                                e.printStackTrace();
                            }
                        }
                    }
                });
                Button reset = (Button) root.findViewById(R.id.countriesBtnReset);
                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        form.resetData();
                    }
                });
                countriesFormLayout.addView(form.getView());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }
}
