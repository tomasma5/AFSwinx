package cz.cvut.fel.matyapav.afandroid.builders;

import android.os.AsyncTask;
import android.widget.TableLayout;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.components.AFComponent;
import cz.cvut.fel.matyapav.afandroid.components.AFForm;
import cz.cvut.fel.matyapav.afandroid.components.AFTable;
import cz.cvut.fel.matyapav.afandroid.rest.RequestTask;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * Created by Pavel on 20.02.2016.
 */
public class TableBuilder extends AFComponentBuilder<TableBuilder> {
    @Override
    public AFTable createComponent() throws Exception {
        initializeConnections();
        RequestTask task = new RequestTask(getActivity(), modelConnection.getHttpMethod(), modelConnection.getContentType(),
                modelConnection.getSecurity(), null, Utils.getConnectionEndPoint(modelConnection));

        Object modelResponse = task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get(); //make it synchronous to return form
        if(modelResponse instanceof Exception){
            throw (Exception) modelResponse;
        }

        //create form from response
        AFTable table = buildTable((String) modelResponse);
        //fill it with data (if there are some)
        if(dataConnection != null){
            RequestTask getData = new RequestTask(getActivity(), dataConnection.getHttpMethod(), dataConnection.getContentType(),
                    dataConnection.getSecurity(), null, Utils.getConnectionEndPoint(dataConnection));
            Object data = getData.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
            if(data instanceof Exception){
                throw (Exception) data;
            }
            insertData((String) data, table, new StringBuilder());
        }

        AFAndroid.getInstance().addCreatedComponent(componentKeyName, table);
        return table;
    }

    private AFTable buildTable(String modelResponse) {
        return null;
    }

    @Override
    protected void insertData(String dataResponse, AFComponent table, StringBuilder road) {

    }
}
