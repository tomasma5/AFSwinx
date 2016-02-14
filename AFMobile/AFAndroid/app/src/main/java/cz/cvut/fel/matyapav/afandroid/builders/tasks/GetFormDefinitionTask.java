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
