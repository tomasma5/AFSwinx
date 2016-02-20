package cz.cvut.fel.matyapav.afandroid.components;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;

import java.net.ConnectException;

import cz.cvut.fel.matyapav.afandroid.enums.LayoutDefinitions;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;
import cz.cvut.fel.matyapav.afandroid.rest.AFSwinxConnection;
import cz.cvut.fel.matyapav.afandroid.rest.BaseRestBuilder;
import cz.cvut.fel.matyapav.afandroid.rest.RequestTask;
import cz.cvut.fel.matyapav.afandroid.rest.RestBuilderFactory;
import cz.cvut.fel.matyapav.afandroid.rest.holder.AFDataHolder;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * Created by Pavel on 13.02.2016.
 */
public abstract class AFComponent {

    private Activity activity;
    private String name;
    private View view;
    private LayoutDefinitions layoutDefinitions;
    private LayoutOrientation layoutOrientation;

    private AFSwinxConnection modelConnection;
    private AFSwinxConnection dataConnection;
    private AFSwinxConnection sendConnection;

    public AFComponent(Activity activity, AFSwinxConnection modelConnection, AFSwinxConnection dataConnection, AFSwinxConnection sendConnection) {
        this.activity = activity;
        this.modelConnection = modelConnection;
        this.dataConnection = dataConnection;
        this.sendConnection = sendConnection;
    }

    public AFComponent(String name, View view, LayoutDefinitions layoutDefinitions, LayoutOrientation layoutOrientation) {
        this.name = name;
        this.view = view;
        this.layoutDefinitions = layoutDefinitions;
        this.layoutOrientation = layoutOrientation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public LayoutDefinitions getLayoutDefinitions() {
        return layoutDefinitions;
    }

    public void setLayoutDefinitions(LayoutDefinitions layoutDefinitions) {
        this.layoutDefinitions = layoutDefinitions;
    }

    public LayoutOrientation getLayoutOrientation() {
        return layoutOrientation;
    }

    public void setLayoutOrientation(LayoutOrientation layoutOrientation) {
        this.layoutOrientation = layoutOrientation;
    }

    abstract SupportedComponents getComponentType();

    abstract boolean validateData();

    abstract AFDataHolder reserialize();

    public AFSwinxConnection getDataConnection() {
        return dataConnection;
    }

    public AFSwinxConnection getModelConnection() {
        return modelConnection;
    }

    public AFSwinxConnection getSendConnection() {
        return sendConnection;
    }

    /*TROCHU POUPRAVENA MARTINOVA METODA*/
    public void sendData() throws Exception{
        if (getSendConnection() == null) {
            throw new IllegalStateException(
                    "The post connection was not specify. Check your XML configuration or Connection which was used to build this form");
        }
        Object data = generateSendData();
        if (data == null) {
            return;
        }
        System.err.println("SEND CONNECTION "+Utils.getConnectionEndPoint(getSendConnection()));
        RequestTask sendTask = new RequestTask(activity,getSendConnection().getHttpMethod(), getSendConnection().getContentType(),
                getSendConnection().getSecurity(), data, Utils.getConnectionEndPoint(getSendConnection()));
        Object response = sendTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        if(response instanceof Exception){
            throw (Exception) response;
        }
    }


    /*MARTINOVA METODA*/
    public Object generateSendData() {
        // before building data and sending, validate actual data
        boolean isValid = validateData();
        if (!isValid) {
            return null;
        }
        AFSwinxConnection sendConnection = getSendConnection();
        // Generate send connection based on which will be retrieve data. The send connection is
        // used to generate data in this case it will be generated JSON
        if (sendConnection == null) {
            sendConnection = new AFSwinxConnection("", 0, "");
        }
        BaseRestBuilder dataBuilder = RestBuilderFactory.getInstance().getBuilder(sendConnection);
        Object data = dataBuilder.reselialize(reserialize());
        return data;
    }
}
