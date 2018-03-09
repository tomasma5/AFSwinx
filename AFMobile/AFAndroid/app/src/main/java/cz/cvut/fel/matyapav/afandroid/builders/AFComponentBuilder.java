package cz.cvut.fel.matyapav.afandroid.builders;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;

import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionPack;
import com.tomscz.afswinx.rest.connection.JsonConnectionParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cz.cvut.fel.matyapav.afandroid.components.types.AFComponent;
import cz.cvut.fel.matyapav.afandroid.components.AFComponentFactory;
import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.parts.ClassDefinition;
import cz.cvut.fel.matyapav.afandroid.components.parts.FieldInfo;
import cz.cvut.fel.matyapav.afandroid.builders.skins.DefaultSkin;
import cz.cvut.fel.matyapav.afandroid.builders.skins.Skin;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;
import cz.cvut.fel.matyapav.afandroid.parsers.JSONDefinitionParser;
import cz.cvut.fel.matyapav.afandroid.parsers.JSONParser;
import cz.cvut.fel.matyapav.afandroid.rest.RequestTask;
import cz.cvut.fel.matyapav.afandroid.utils.Constants;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
 */
public abstract class AFComponentBuilder<T> {

    private Context context;
    private AFSwinxConnectionPack connectionPack;
    private Skin skin;
    private String componentKeyName;
    private String connectionConfiguration;
    private HashMap<String, String> connectionParameters;


    public T initBuilder(Context context, String componentKeyName, String connectionConfiguration) {
        this.context = context;
        this.componentKeyName = componentKeyName;
        this.connectionConfiguration = connectionConfiguration;
        this.skin = new DefaultSkin(this.context);
        return (T) this;
    }

    public T initBuilder(Context context, String componentKeyName,  String connectionConfiguration, HashMap<String, String> connectionParameters) {
        this.context = context;
        this.componentKeyName = componentKeyName;
        this.connectionParameters = connectionParameters;
        this.connectionConfiguration = connectionConfiguration;
        this.skin = new DefaultSkin(this.context);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public T setConnectionParameters(HashMap<String, String> connectionParameters) {
        this.connectionParameters = connectionParameters;
        return (T) this;
    }

    void initializeConnections() throws Exception {
        if (connectionPack == null && connectionConfiguration != null) {
            JsonConnectionParser connectionParser =
                    new JsonConnectionParser(connectionParameters);
            connectionPack = connectionParser.parse(new JSONObject(connectionConfiguration));
        } else {
            // Model connection is important if it could be found then throw exception
            throw new Exception(
                    "There is error during building AFForm. Connection was not specified. Did you used initBuilder method before build?");
        }
    }

    private void prepareComponent(ClassDefinition classDef, AFComponent component, int numberOfInnerClasses, boolean parsingInnerClass, StringBuilder road) {
        if (parsingInnerClass) {
            numberOfInnerClasses = 0;
        }
        if (classDef != null) {
            if (!parsingInnerClass) { //set following properties only once at the beginning
                component.setName(classDef.getClassName());
                if (classDef.getLayout() != null) {
                    component.setLayoutDefinitions(classDef.getLayout().getLayoutDefinition());
                    component.setLayoutOrientation(classDef.getLayout().getLayoutOrientation());
                }
            }
            //fieldsView = (TableLayout) buildLayout(classDef, context);
            FieldBuilder builder = new FieldBuilder();
            for (FieldInfo field : classDef.getFieldInfos()) {
                if (field.isInnerClass()) {
                    String roadBackup = road.toString();
                    road.append(classDef.getInnerClasses().get(numberOfInnerClasses).getClassName());
                    road.append(".");
                    prepareComponent(classDef.getInnerClasses().get(numberOfInnerClasses), component, numberOfInnerClasses++, true, road);
                    road = new StringBuilder(roadBackup);
                } else {
                    AFField affield = builder.prepareField(field, road, getContext(), skin);
                    if (affield != null) {
                        component.addField(affield);
                    }
                }
            }
        }
        System.err.println("NUMBER OF ELEMENTS IN COMPONENT " + component.getFields().size());
    }

    protected AFComponent buildComponent(String modelResponse, SupportedComponents type) throws JSONException {
        AFComponent component = AFComponentFactory.getInstance().getComponentByType(type);
        component.setContext(getContext());
        component.setConnectionPack(connectionPack);
        component.setSkin(skin);

        LinearLayout componentView = new LinearLayout(getContext());
        componentView.setLayoutParams(getSkin().getTopLayoutParams());
        JSONParser parser = new JSONDefinitionParser();
        JSONObject jsonObj = new JSONObject(modelResponse).getJSONObject(Constants.CLASS_INFO);
        ClassDefinition classDef = parser.parse(jsonObj);
        prepareComponent(classDef, component, 0, false, new StringBuilder());
        View view = buildComponentView(component);
        componentView.addView(view);
        component.setView(componentView);
        return component;
    }

    protected String getModelResponse() throws Exception {
        AFSwinxConnection modelConnection = connectionPack.getMetamodelConnection();
        if (modelConnection != null) {
            RequestTask task = new RequestTask(getContext(), Utils.getConnectionEndPoint(modelConnection))
                    .setHttpMethod(modelConnection.getHttpMethod())
                    .setConnectionSecurity(modelConnection.getSecurity())
                    .addHeaderParameter("CONTENT-TYPE", modelConnection.getContentType().toString());

            Object modelResponse = task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get(); //make it synchronous to wait for response
            if (modelResponse instanceof Exception) {
                throw (Exception) modelResponse;
            }
            return (String) modelResponse;
        } else {
            throw new Exception("No model connection available. Did you call initializeConnections() before?");
        }
    }

    protected String getDataResponse() throws Exception {
        AFSwinxConnection dataConnection = connectionPack.getDataConnection();
        if (dataConnection != null) {
            RequestTask getData = new RequestTask(getContext(), Utils.getConnectionEndPoint(dataConnection))
                    .setHttpMethod(dataConnection.getHttpMethod())
                    .setConnectionSecurity(dataConnection.getSecurity())
                    .addHeaderParameter("CONTENT_TYPE", dataConnection.getContentType().toString());
            Object response = getData.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
            if (response instanceof Exception) {
                throw (Exception) response;
            }
            return (String) response;
        }
        return null;
    }

    public abstract AFComponent createComponent() throws Exception;

    protected abstract View buildComponentView(AFComponent component);

    public Context getContext() {
        return context;
    }

    public String getComponentKeyName() {
        return componentKeyName;
    }

    public T setSkin(Skin skin) {
        this.skin = skin;
        return (T) this;
    }

    public Skin getSkin() {
        return skin;
    }


}
