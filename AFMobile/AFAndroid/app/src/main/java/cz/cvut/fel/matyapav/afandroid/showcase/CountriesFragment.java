package cz.cvut.fel.matyapav.afandroid.showcase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.R;
import cz.cvut.fel.matyapav.afandroid.builders.FormBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.ListBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.TableBuilder;
import cz.cvut.fel.matyapav.afandroid.components.AFForm;
import cz.cvut.fel.matyapav.afandroid.components.AFList;
import cz.cvut.fel.matyapav.afandroid.components.AFTable;
import cz.cvut.fel.matyapav.afandroid.showcase.skins.CountryListSkin;
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

        ListBuilder listBuilder = AFAndroid.getInstance().getListBuilder().
                initBuilder(getActivity(), "countryTable", getResources().openRawResource(R.raw.connection),
                        "tableCountryPublic", securityConstrains).setSkin(new CountryListSkin(getContext()));
        FormBuilder formBuilder = AFAndroid.getInstance().getFormBuilder()
                .initBuilder(getActivity(), "countryForm", getResources().openRawResource(R.raw.connection), "countryAdd", securityConstrains)
                .setSkin(new CountrySkin(getContext()));

        //create and insert list

        try {
            final AFList list = listBuilder.createComponent();
            countriesTableLayout.addView(list.getView());

        } catch (Exception e) {
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
                Button clear = (Button) root.findViewById(R.id.countriesBtnClear);
                clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        form.clearData();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        final AFList countryList = (AFList) AFAndroid.getInstance().getCreatedComponents().get("countryTable");
        final AFForm countryForm = (AFForm) AFAndroid.getInstance().getCreatedComponents().get("countryForm");

        countryList.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                countryForm.insertData(countryList.getData(position));
            }
        });


        return root;
    }

}
