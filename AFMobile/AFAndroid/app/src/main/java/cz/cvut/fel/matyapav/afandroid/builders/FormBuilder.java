package cz.cvut.fel.matyapav.afandroid.builders;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.Localization;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.AFForm;
import cz.cvut.fel.matyapav.afandroid.components.parts.ClassDefinition;
import cz.cvut.fel.matyapav.afandroid.components.parts.FieldInfo;
import cz.cvut.fel.matyapav.afandroid.parsers.JSONDefinitionParser;
import cz.cvut.fel.matyapav.afandroid.parsers.JSONParser;
import cz.cvut.fel.matyapav.afandroid.components.parts.LayoutProperties;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * Builds for from class definition
 * Created by Pavel on 26.12.2015.
 */
public class FormBuilder {

    private Activity activity;

    public FormBuilder(Activity activity) {
        this.activity = activity;
    }

    public AFForm createForm(final String url){
        CreateFormTask task = new CreateFormTask(activity);
        try {
            String response = task.execute(url).get(); //make it synchronous to return form
            if(response != null) {
                AFForm form = buildForm(response);
                AFAndroid.getInstance().addCreatedComponent(form.getName(), form);
                return form;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AFForm buildForm(String response){
        AFForm form = new AFForm();
        LinearLayout formView = null;
        try {
            JSONParser parser = new JSONDefinitionParser();
            JSONObject jsonObj = new JSONObject(response);
            ClassDefinition classDef = parser.parse(jsonObj);
            if(classDef != null) {
                form.setName(classDef.getClassName());
                formView = (LinearLayout) buildLayout(classDef.getLayout(), activity);
                InputFieldBuilder builder = new InputFieldBuilder();
                for (FieldInfo field : classDef.getFields()) {
                    AFField affield = builder.buildField(field, activity);
                    form.addField(affield);
                    formView.addView(affield.getView());
                    formView.addView(makeSeparator(activity));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            formView = (LinearLayout) buildError("Cannot build form "+e.getMessage());
        }
        form.setView(formView);
        return form;
    }

    private View buildError(String errorMsg) {
        LinearLayout err = new LinearLayout(activity);
        err.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        TextView msg = new TextView(activity);
        msg.setText(errorMsg);
        err.addView(msg);

        return err;
    }

    private View buildLayout(LayoutProperties properties, Context context){
        LinearLayout form = new LinearLayout(context);
        /*
        if(properties.getLayoutOrientation().getName().equals(LayoutOrientation.AXISY.getName())) {
            form.setOrientation(LinearLayout.VERTICAL);
        }else if(properties.getLayoutOrientation().getName().equals(LayoutOrientation.AXISX.getName())){
            form.setOrientation(LinearLayout.HORIZONTAL);
        }
        */
        //TODO onecolumn twocolumn properties
        //TODO axisX axisY properties
        form.setOrientation(LinearLayout.VERTICAL);
        form.setGravity(Gravity.CENTER);
        form.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return form;

    }

    public Button buildSubmitButton(String text, AFForm form){
        Button btn = new Button(activity);
        btn.setText(text);
        btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        form.setSubmitBtn(btn);
        return btn;
    }

    private View makeSeparator(Activity activity){
        View v = new View(activity);
        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
        v.setBackgroundColor(Color.rgb(51, 51, 51));
        return v;
    }

    /*ASYNC TASK FOR CREATING FORM*/
    private class CreateFormTask extends AsyncTask<String,Integer,String>{

        ProgressDialog progressDialog;
        AlertDialog.Builder dlgAlert;
        Activity activity;


        public CreateFormTask(Activity activity) {
            this.activity = activity;
            progressDialog = new ProgressDialog(activity);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(Localization.translate("form.building.msg", activity));
            //error dialog
            dlgAlert  = new AlertDialog.Builder(activity);
            dlgAlert.setTitle(Localization.translate("form.building.failed.title", activity));
            dlgAlert.setMessage(Localization.translate("form.building.failed.msg", activity));
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
                //urlConnection.setReadTimeout(5000);
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
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


}