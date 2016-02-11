package cz.cvut.fel.matyapav.afandroid.builders;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.cvut.fel.matyapav.afandroid.AFField;
import cz.cvut.fel.matyapav.afandroid.AFForm;
import cz.cvut.fel.matyapav.afandroid.ClassDefinition;
import cz.cvut.fel.matyapav.afandroid.FieldInfo;
import cz.cvut.fel.matyapav.afandroid.JSONDefinitionParser;
import cz.cvut.fel.matyapav.afandroid.JSONParser;
import cz.cvut.fel.matyapav.afandroid.LayoutProperties;
import cz.cvut.fel.matyapav.afandroid.utils.UIMap;

/**
 * Builds for from class definition
 * Created by Pavel on 26.12.2015.
 */
public class FormBuilder {

    private Context context;

    public FormBuilder(Context context) {
        this.context = context;
    }

    public void createAndInsertForm(final String url, final String lang, Button btn, ViewGroup parentLayout){
        CreateFormTask task = new CreateFormTask(btn, parentLayout, getContext());
        task.execute(url, lang);
    }

    public AFForm buildForm(String response, String lang){
        AFForm form = new AFForm();
        LinearLayout formView = null;
        try {
            JSONParser parser = new JSONDefinitionParser();
            JSONObject jsonObj = new JSONObject(response);
            ClassDefinition classDef = parser.parse(jsonObj);
            if(classDef != null) {
                form.setName(classDef.getClassName());
                formView = (LinearLayout) buildLayout(classDef.getLayout(), context);
                InputFieldBuilder builder = new InputFieldBuilder();
                for (FieldInfo field : classDef.getFields()) {
                    AFField affield = builder.buildField(field, context, lang);
                    form.addField(affield);
                    formView.addView(affield.getView());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            formView = (LinearLayout) buildError("Cannot build form "+e.getMessage(), context);
        }
        form.setView(formView);
        return form;
    }

    private View buildError(String errorMsg, Context context) {
        LinearLayout err = new LinearLayout(context);
        err.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        TextView msg = new TextView(context);
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
        form.setGravity(View.TEXT_ALIGNMENT_CENTER);
        form.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return form;

    }

    public Button buildSubmitButton(Context context, String text){
        Button btn = new Button(context);
        btn.setText(text);
        btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return btn;
    }

    public Context getContext() {
        return context;
    }

    /*ASYNC TASK FOR CREATING FORM*/
    private class CreateFormTask extends AsyncTask<String,Integer,AFForm>{

        ViewGroup parentLayout;
        ProgressDialog progressDialog;
        AlertDialog.Builder dlgAlert;
        Button btn;

        public CreateFormTask(Button btn, ViewGroup parentLayout, Context context) {
            this.parentLayout = parentLayout;
            this.btn = btn;
            //progress dialog
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Building form...");
            //error dialog
            dlgAlert  = new AlertDialog.Builder(context);
            dlgAlert.setTitle("Building failed...");
            dlgAlert.setMessage("Something went wrong during building form.");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected AFForm doInBackground(String... params) {
            AFForm form = null;
            String lang = params[1];
            InputStream inputStream = null;
            String response = "";
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("CONTENT-TYPE","application/json");
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                if (inputStream != null){
                    response = convertInputStreamToString(inputStream);
                    System.err.println("Response: "+response);
                    form = buildForm(response, lang);
                    form.setSubmitBtn(btn);
                    UIMap.createdForms.put(form.getName(), form);
                }
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            } finally {
                urlConnection.disconnect();
            }
            return form;
        }

        @Override
        protected void onPostExecute(AFForm form){
            super.onPostExecute(form);
            progressDialog.hide();
            if(form != null) {
                parentLayout.addView(form.getView());
                if(form.getSubmitBtn() != null) {
                    parentLayout.addView(form.getSubmitBtn());
                }
            }else{
                dlgAlert.show();
            }
        }

        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;
            inputStream.close();
            return result;
        }
    }

}