package cz.cvut.fel.matyapav.afandroid.builders.tasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.cvut.fel.matyapav.afandroid.utils.Localization;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * Created by Pavel on 14.02.2016.
 */
public class GetFormDefinitionTask extends AsyncTask<String,Integer,String> {

    ProgressDialog progressDialog;
    AlertDialog.Builder dlgAlert;
    Activity activity;


    public GetFormDefinitionTask(final Activity activity) {
        this.activity = activity;
        progressDialog = new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(Localization.translate("form.building.msg", activity));
        //error dialog
        dlgAlert  = new AlertDialog.Builder(activity);
        dlgAlert.setTitle(Localization.translate("form.building.failed.title", activity))
                .setMessage(Localization.translate("form.building.failed.msg", activity)).setCancelable(false)
                .setPositiveButton(Localization.translate("exit.application", activity), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //exit app
                        activity.finish();
                        System.exit(0);
                    }
                });
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.show();
            }
        });

    }

    @Override
    protected String doInBackground(String... params) {
        InputStream inputStream;
        String response = null;
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("CONTENT-TYPE", "application/json");
            int responseCode = urlConnection.getResponseCode();

            if(responseCode >= 400){
                inputStream = urlConnection.getErrorStream();
            }else{
                inputStream = urlConnection.getInputStream();
            }
            if (inputStream != null){
                response = Utils.convertInputStreamToString(inputStream);
                //test date required and more options and label position
                //response = "{\"classInfo\":{\"name\":\"person\",\"layout\":{\"layoutDefinition\":\"ONECOLUMNLAYOUT\",\"layoutOrientation\":\"AXISX\"},\"fieldInfo\":[{\"widgetType\":\"TEXTFIELD\",\"id\":\"login\",\"label\":\"Login\",\"classType\":false,\"readOnly\":false,\"visible\":true,\"layout\":{\"layoutDefinition\":\"ONECOLUMNLAYOUT\",\"layoutOrientation\":\"AXISY\",\"labelPosstion\":\"BEFORE\"},\"rules\":[{\"validationType\":\"REQUIRED\",\"value\":\"true\"},{\"validationType\":\"MAXLENGTH\",\"value\":\"255\"}],\"options\":null},{\"widgetType\":\"PASSWORD\",\"id\":\"password\",\"label\":\"Password\",\"classType\":false,\"readOnly\":false,\"visible\":true,\"layout\":{\"layoutDefinition\":\"ONECOLUMNLAYOUT\",\"layoutOrientation\":\"AXISY\",\"labelPosstion\":\"BEFORE\"},\"rules\":[{\"validationType\":\"REQUIRED\",\"value\":\"true\"},{\"validationType\":\"MAXLENGTH\",\"value\":\"255\"}],\"options\":null},{\"widgetType\":\"TEXTFIELD\",\"id\":\"firstName\",\"label\":\"person.firstName\",\"classType\":false,\"readOnly\":false,\"visible\":true,\"layout\":{\"layoutDefinition\":\"ONECOLUMNLAYOUT\",\"layoutOrientation\":\"AXISY\",\"labelPosstion\":\"BEFORE\"},\"rules\":[{\"validationType\":\"REQUIRED\",\"value\":\"true\"},{\"validationType\":\"MAXLENGTH\",\"value\":\"255\"}],\"options\":null},{\"widgetType\":\"TEXTFIELD\",\"id\":\"lastName\",\"label\":\"person.lastName\",\"classType\":false,\"readOnly\":false,\"visible\":true,\"layout\":{\"layoutDefinition\":\"ONECOLUMNLAYOUT\",\"layoutOrientation\":\"AXISY\",\"labelPosstion\":\"BEFORE\"},\"rules\":[{\"validationType\":\"MAXLENGTH\",\"value\":\"255\"}],\"options\":null},{\"widgetType\":\"NUMBERDOUBLEFIELD\",\"id\":\"age\",\"label\":\"person.age\",\"classType\":false,\"readOnly\":false,\"visible\":true,\"layout\":{\"layoutDefinition\":\"ONECOLUMNLAYOUT\",\"layoutOrientation\":\"AXISY\",\"labelPosstion\":\"AFTER\"},\"rules\":[{\"validationType\":\"REQUIRED\",\"value\":\"true\"},{\"validationType\":\"MIN\",\"value\":\"15.4\"},{\"validationType\":\"MAX\",\"value\":\"60.2\"}],\"options\":null},{\"widgetType\":null,\"id\":\"myAddress\",\"label\":null,\"classType\":true,\"readOnly\":false,\"visible\":true,\"layout\":{\"layoutDefinition\":null,\"layoutOrientation\":null,\"labelPosstion\":null},\"rules\":null,\"options\":null},{\"widgetType\":\"OPTION\",\"id\":\"active\",\"label\":\"Active\",\"classType\":false,\"readOnly\":false,\"visible\":true,\"layout\":{\"layoutDefinition\":\"ONECOLUMNLAYOUT\",\"layoutOrientation\":\"AXISY\",\"labelPosstion\":\"BEFORE\"},\"rules\":[{\"validationType\":\"REQUIRED\",\"value\":\"true\"}],\"options\":null},{\"widgetType\":\"CHECKBOX\",\"id\":\"confidentialAgreement\",\"label\":\"Confidential Agreement\",\"classType\":false,\"readOnly\":false,\"visible\":true,\"layout\":{\"layoutDefinition\":\"ONECOLUMNLAYOUT\",\"layoutOrientation\":\"AXISY\",\"labelPosstion\":\"BEFORE\"},\"rules\":null,\"options\":null},{\"widgetType\":\"TEXTFIELD\",\"id\":\"email\",\"label\":\"Email\",\"classType\":false,\"readOnly\":false,\"visible\":true,\"layout\":{\"layoutDefinition\":\"ONECOLUMNLAYOUT\",\"layoutOrientation\":\"AXISY\",\"labelPosstion\":\"BEFORE\"},\"rules\":[{\"validationType\":\"MAXLENGTH\",\"value\":\"255\"}],\"options\":null},{\"widgetType\":\"OPTION\",\"id\":\"gender\",\"label\":\"Gender\",\"classType\":false,\"readOnly\":false,\"visible\":true,\"layout\":{\"layoutDefinition\":\"ONECOLUMNLAYOUT\",\"layoutOrientation\":\"AXISY\",\"labelPosstion\":\"BEFORE\"},\"rules\":[{\"validationType\":\"REQUIRED\",\"value\":\"true\"}],\"options\":[{\"key\":\"MALE\",\"value\":\"MALE\"},{\"key\":\"FEMALE\",\"value\":\"FEMALE\"},{\"key\":\"UNDEFINED\",\"value\":\"UNDEFINED\"}]},{\"widgetType\":\"CALENDAR\",\"id\":\"hireDate\",\"label\":\"Hire Date\",\"classType\":false,\"readOnly\":false,\"visible\":true,\"layout\":{\"layoutDefinition\":\"ONECOLUMNLAYOUT\",\"layoutOrientation\":\"AXISY\",\"labelPosstion\":\"BEFORE\"},\"rules\":[{\"validationType\":\"REQUIRED\",\"value\":\"true\"}],\"options\":null},{\"widgetType\":\"NUMBERFIELD\",\"id\":\"id\",\"label\":\"Id\",\"classType\":false,\"readOnly\":true,\"visible\":false,\"layout\":{\"layoutDefinition\":null,\"layoutOrientation\":null,\"labelPosstion\":null},\"rules\":null,\"options\":null}],\"innerClasses\":[{\"name\":\"myAddress\",\"layout\":{\"layoutDefinition\":\"ONECOLUMNLAYOUT\",\"layoutOrientation\":\"AXISX\"},\"fieldInfo\":[{\"widgetType\":\"TEXTFIELD\",\"id\":\"street\",\"label\":\"Street\",\"classType\":false,\"readOnly\":false,\"visible\":true,\"layout\":{\"layoutDefinition\":\"ONECOLUMNLAYOUT\",\"layoutOrientation\":\"AXISY\",\"labelPosstion\":\"BEFORE\"},\"rules\":[{\"validationType\":\"REQUIRED\",\"value\":\"true\"},{\"validationType\":\"MAXLENGTH\",\"value\":\"255\"}],\"options\":null},{\"widgetType\":\"TEXTFIELD\",\"id\":\"city\",\"label\":\"City\",\"classType\":false,\"readOnly\":false,\"visible\":true,\"layout\":{\"layoutDefinition\":\"ONECOLUMNLAYOUT\",\"layoutOrientation\":\"AXISY\",\"labelPosstion\":\"BEFORE\"},\"rules\":[{\"validationType\":\"REQUIRED\",\"value\":\"true\"},{\"validationType\":\"MAXLENGTH\",\"value\":\"255\"}],\"options\":null},{\"widgetType\":\"NUMBERFIELD\",\"id\":\"postCode\",\"label\":\"Post Code\",\"classType\":false,\"readOnly\":false,\"visible\":true,\"layout\":{\"layoutDefinition\":\"ONECOLUMNLAYOUT\",\"layoutOrientation\":\"AXISY\",\"labelPosstion\":\"BEFORE\"},\"rules\":[{\"validationType\":\"REQUIRED\",\"value\":\"true\"}],\"options\":null},{\"widgetType\":\"DROPDOWNMENU\",\"id\":\"country\",\"label\":\"Country\",\"classType\":false,\"readOnly\":false,\"visible\":true,\"layout\":{\"layoutDefinition\":\"ONECOLUMNLAYOUT\",\"layoutOrientation\":\"AXISY\",\"labelPosstion\":\"BEFORE\"},\"rules\":[{\"validationType\":\"REQUIRED\",\"value\":\"true\"}],\"options\":[{\"key\":\"Czech republic\",\"value\":\"Czech republic\"},{\"key\":\"Denmark\",\"value\":\"Denmark\"},{\"key\":\"Slovakia\",\"value\":\"Slovakia\"},{\"key\":\"Switzerland\",\"value\":\"Switzerland\"}]},{\"widgetType\":\"NUMBERFIELD\",\"id\":\"id\",\"label\":\"Id\",\"classType\":false,\"readOnly\":true,\"visible\":false,\"layout\":{\"layoutDefinition\":null,\"layoutOrientation\":null,\"labelPosstion\":null},\"rules\":null,\"options\":null}],\"innerClasses\":null}]}}";
                //response="{\"classInfo\":{\"name\":\"absenceInstance\",\"layout\":{\"layoutDefinition\":\"TWOCOLUMNSLAYOUT\",\"layoutOrientation\":\"AXISX\"},\"fieldInfo\":[{\"widgetType\":null,\"id\":\"affectedPerson\",\"label\":null,\"classType\":true,\"readOnly\":false,\"visible\":true,\"layout\":{\"layoutDefinition\":null,\"layoutOrientation\":null,\"labelPosstion\":null},\"rules\":null,\"options\":null},{\"widgetType\":\"CALENDAR\",\"id\":\"startDate\",\"label\":\"absenceInstance.startDate\",\"classType\":false,\"readOnly\":false,\"visible\":true,\"layout\":{\"layoutDefinition\":\"ONECOLUMNLAYOUT\",\"layoutOrientation\":\"AXISY\",\"labelPosstion\":\"BEFORE\"},\"rules\":[{\"validationType\":\"REQUIRED\",\"value\":\"true\"}],\"options\":null},{\"widgetType\":\"CALENDAR\",\"id\":\"endDate\",\"label\":\"absenceInstance.endDate\",\"classType\":false,\"readOnly\":false,\"visible\":true,\"layout\":{\"layoutDefinition\":\"ONECOLUMNLAYOUT\",\"layoutOrientation\":\"AXISY\",\"labelPosstion\":\"BEFORE\"},\"rules\":[{\"validationType\":\"REQUIRED\",\"value\":\"true\"}],\"options\":null},{\"widgetType\":null,\"id\":\"absenceType\",\"label\":null,\"classType\":true,\"readOnly\":false,\"visible\":true,\"layout\":{\"layoutDefinition\":null,\"layoutOrientation\":null,\"labelPosstion\":null},\"rules\":null,\"options\":null},{\"widgetType\":\"NUMBERFIELD\",\"id\":\"id\",\"label\":\"Id\",\"classType\":false,\"readOnly\":true,\"visible\":false,\"layout\":{\"layoutDefinition\":null,\"layoutOrientation\":null,\"labelPosstion\":null},\"rules\":null,\"options\":null},{\"widgetType\":\"DROPDOWNMENU\",\"id\":\"status\",\"label\":\"absenceInstance.state\",\"classType\":false,\"readOnly\":false,\"visible\":true,\"layout\":{\"layoutDefinition\":\"ONECOLUMNLAYOUT\",\"layoutOrientation\":\"AXISY\",\"labelPosstion\":\"BEFORE\"},\"rules\":[{\"validationType\":\"REQUIRED\",\"value\":\"true\"}],\"options\":[{\"key\":\"CANCELLED\",\"value\":\"absenceInstances.cancelled\"},{\"key\":\"DENIED\",\"value\":\"absenceInstances.denied\"},{\"key\":\"ACCEPTED\",\"value\":\"absenceInstances.accepted\"},{\"key\":\"REQUESTED\",\"value\":\"absenceInstances.requested\"}]}],\"innerClasses\":[{\"name\":\"affectedPerson\",\"layout\":{\"layoutDefinition\":null,\"layoutOrientation\":null},\"fieldInfo\":[{\"widgetType\":\"TEXTFIELD\",\"id\":\"login\",\"label\":\"Login\",\"classType\":false,\"readOnly\":true,\"visible\":true,\"layout\":{\"layoutDefinition\":\"ONECOLUMNLAYOUT\",\"layoutOrientation\":\"AXISY\",\"labelPosstion\":\"BEFORE\"},\"rules\":[{\"validationType\":\"REQUIRED\",\"value\":\"true\"}],\"options\":null},{\"widgetType\":\"TEXTFIELD\",\"id\":\"lastName\",\"label\":\"person.lastName\",\"classType\":false,\"readOnly\":true,\"visible\":true,\"layout\":{\"layoutDefinition\":\"ONECOLUMNLAYOUT\",\"layoutOrientation\":\"AXISY\",\"labelPosstion\":\"BEFORE\"},\"rules\":null,\"options\":null}],\"innerClasses\":null},{\"name\":\"absenceType\",\"layout\":{\"layoutDefinition\":null,\"layoutOrientation\":null},\"fieldInfo\":[{\"widgetType\":\"TEXTFIELD\",\"id\":\"name\",\"label\":\"absenceType.name\",\"classType\":false,\"readOnly\":true,\"visible\":true,\"layout\":{\"layoutDefinition\":\"ONECOLUMNLAYOUT\",\"layoutOrientation\":\"AXISY\",\"labelPosstion\":\"BEFORE\"},\"rules\":[{\"validationType\":\"REQUIRED\",\"value\":\"true\"}],\"options\":null},{\"widgetType\":null,\"id\":\"country\",\"label\":null,\"classType\":true,\"readOnly\":false,\"visible\":true,\"layout\":{\"layoutDefinition\":null,\"layoutOrientation\":null,\"labelPosstion\":null},\"rules\":null,\"options\":null}],\"innerClasses\":[{\"name\":\"country\",\"layout\":{\"layoutDefinition\":null,\"layoutOrientation\":null},\"fieldInfo\":[{\"widgetType\":\"TEXTFIELD\",\"id\":\"name\",\"label\":\"country.name\",\"classType\":false,\"readOnly\":true,\"visible\":true,\"layout\":{\"layoutDefinition\":\"ONECOLUMNLAYOUT\",\"layoutOrientation\":\"AXISY\",\"labelPosstion\":\"BEFORE\"},\"rules\":[{\"validationType\":\"REQUIRED\",\"value\":\"true\"}],\"options\":null}],\"innerClasses\":null}]}]}}";
            }

        } catch (Exception e) {
            System.err.println("InputStream " + e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return response;
    }

    @Override
    protected void onPostExecute(final String response){
        super.onPostExecute(response);
        this.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                if(response == null){
                    dlgAlert.show();
                }
            }
        });

    }
}
