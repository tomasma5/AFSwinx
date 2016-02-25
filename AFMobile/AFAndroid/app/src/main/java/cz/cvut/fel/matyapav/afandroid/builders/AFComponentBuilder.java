package cz.cvut.fel.matyapav.afandroid.builders;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;

import cz.cvut.fel.matyapav.afandroid.builders.widgets.FieldBuilder;
import cz.cvut.fel.matyapav.afandroid.components.AFComponent;
import cz.cvut.fel.matyapav.afandroid.components.AFForm;
import cz.cvut.fel.matyapav.afandroid.components.AFList;
import cz.cvut.fel.matyapav.afandroid.components.AFTable;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.ClassDefinition;
import cz.cvut.fel.matyapav.afandroid.components.parts.FieldInfo;
import cz.cvut.fel.matyapav.afandroid.components.skins.DefaultSkin;
import cz.cvut.fel.matyapav.afandroid.components.skins.Skin;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;
import cz.cvut.fel.matyapav.afandroid.parsers.ConnectionParser;
import cz.cvut.fel.matyapav.afandroid.parsers.JSONDefinitionParser;
import cz.cvut.fel.matyapav.afandroid.parsers.JSONParser;
import cz.cvut.fel.matyapav.afandroid.rest.AFSwinxConnection;
import cz.cvut.fel.matyapav.afandroid.rest.AFSwinxConnectionPack;
import cz.cvut.fel.matyapav.afandroid.rest.RequestTask;
import cz.cvut.fel.matyapav.afandroid.utils.Constants;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * Created by Pavel on 19.02.2016.
 */
public abstract class AFComponentBuilder<T> {

    private Activity activity;

    private AFSwinxConnectionPack connectionPack;

    private String connectionKey;
    private String componentKeyName;
    private InputStream connectionResource;
    private HashMap<String, String> connectionParameters;

    private Skin skin;

    public T initBuilder(Activity activity, String componentKeyName, InputStream connectionResource, String connectionKey){
        this.activity = activity;
        this.componentKeyName = componentKeyName;
        this.connectionResource = connectionResource;
        this.connectionKey = connectionKey;
        this.skin = new DefaultSkin(activity);
        return (T) this;
    }

    public T initBuilder(Activity activity, String componentKeyName, InputStream connectionResource,
                         String connectionKey, HashMap<String, String> connectionParameters) {
        this.activity = activity;
        this.componentKeyName = componentKeyName;
        this.connectionResource = connectionResource;
        this.connectionKey = connectionKey;
        this.connectionParameters = connectionParameters;
        this.skin = new DefaultSkin(activity);
        return (T) this;
    }

    public void initializeConnections() throws Exception {
        if (connectionPack == null && connectionKey != null && connectionResource != null) {
            ConnectionParser connectionParser =
                    new ConnectionParser(connectionKey, connectionParameters);
            AFSwinxConnectionPack connections =
                    connectionParser.parseDocument(Utils
                            .buildDocumentFromFile(connectionResource));
            connectionPack = connections;
        } else {
            // Model connection is important if it could be found then throw exception
            throw new Exception(
                    "There is error during building AFSwinxForm. Connection was not specified. Did you used initBuilder method before build?");
        }
    }

    protected void prepareComponent(ClassDefinition classDef, AFComponent component, int numberOfInnerClasses, boolean parsingInnerClass, StringBuilder road){
        if(parsingInnerClass){
            numberOfInnerClasses = 0;
        }
        if(classDef != null) {
            if(!parsingInnerClass) { //set following properties only once at the beginning
                component.setName(classDef.getClassName());
                component.setLayoutDefinitions(classDef.getLayout().getLayoutDefinition());
                component.setLayoutOrientation(classDef.getLayout().getLayoutOrientation());
            }
            //fieldsView = (TableLayout) buildLayout(classDef, activity);
            FieldBuilder builder = new FieldBuilder();
            for (FieldInfo field : classDef.getFieldInfos()) {
                if(field.isInnerClass()){
                    String roadBackup = road.toString();
                    road.append(classDef.getInnerClasses().get(numberOfInnerClasses).getClassName());
                    road.append(".");
                    prepareComponent(classDef.getInnerClasses().get(numberOfInnerClasses), component, numberOfInnerClasses++, true, road);
                    road = new StringBuilder(roadBackup);
                }else {
                    AFField affield = builder.prepareField(field, road, getActivity(), skin);
                    if (affield != null) {
                        component.addField(affield);
                    }
                }
            }
        }
        System.err.println("NUMBER OF ELEMENTS IN COMPONENT " + component.getFields().size());
    }

    protected AFComponent buildComponent(String modelResponse, SupportedComponents type){
        //TODO popremyslet co s timto
        AFComponent component = null;
        if(type.equals(SupportedComponents.FORM)) {
            component = new AFForm(getActivity(), connectionPack, skin);
        }else if(type.equals(SupportedComponents.TABLE)){
            component = new AFTable(getActivity(), connectionPack, skin);
        }else{
            component = new AFList(getActivity(), connectionPack, skin);
        }

        LinearLayout componentView = new LinearLayout(getActivity());
        componentView.setLayoutParams(getSkin().getTopLayoutParams());
        try {
            JSONParser parser = new JSONDefinitionParser();
            JSONObject jsonObj = new JSONObject(modelResponse).getJSONObject(Constants.CLASS_INFO);
            ClassDefinition classDef = parser.parse(jsonObj);
            prepareComponent(classDef, component, 0, false, new StringBuilder());
            View view = buildComponentView(component);
            System.err.println("VIEWWWWW" + view);
            componentView.addView(view);
        } catch (JSONException e) {
            e.printStackTrace();
            componentView = (LinearLayout) buildError("Cannot parse JSon data "+e.getMessage());
        }
        component.setView(componentView);
        return component;
    }

    protected String getModelResponse() throws Exception{
        AFSwinxConnection modelConnection = connectionPack.getMetamodelConnection();
        if(modelConnection != null) {
            RequestTask task = new RequestTask(getActivity(), modelConnection.getHttpMethod(), modelConnection.getContentType(),
                    modelConnection.getSecurity(), null, Utils.getConnectionEndPoint(modelConnection));

            Object modelResponse = task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get(); //make it synchronous to wait for response
            if (modelResponse instanceof Exception) {
                throw (Exception) modelResponse;
            }
            return (String) modelResponse;
        }else{
            throw new Exception("No model connection available. Did you call initializeConnections() before?");
        }
    }

    protected String getDataResponse() throws Exception{
        AFSwinxConnection dataConnection = connectionPack.getDataConnection();
        if(dataConnection != null) {
            RequestTask getData = new RequestTask(getActivity(), dataConnection.getHttpMethod(), dataConnection.getContentType(),
                    dataConnection.getSecurity(), null, Utils.getConnectionEndPoint(dataConnection));
            Object response = getData.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
            if (response instanceof Exception) {
                throw (Exception) response;
            }
            return (String) response;
        }
        return null;
    }

    private View buildError(String errorMsg) {
        LinearLayout err = new LinearLayout(getActivity());
        err.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        TextView msg = new TextView(getActivity());
        msg.setText(errorMsg);
        err.addView(msg);
        return err;
    }

    public abstract AFComponent createComponent() throws Exception;



    protected abstract View buildComponentView(AFComponent component);

    public Activity getActivity() {
        return activity;
    }

    public String getComponentKeyName() {
        return componentKeyName;
    }

    public T setSkin(Skin skin){
        this.skin = skin;
        return (T) this;
    }

    public Skin getSkin() {
        return skin;
    }


}
