package com.tomscz.afswinx.rest.rebuild;

import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.connection.BaseConnector.HeaderType;

public class RestBuilderFactory {

    private static RestBuilderFactory instance;
    
    private RestBuilderFactory(){
        
    }
    
    public static synchronized RestBuilderFactory getInstance(){
        if(instance == null){
            instance = new RestBuilderFactory();
        }
        return instance;
    }
    
    public BaseRestBuilder getBuilder(AFSwinxConnection connection){
        if(connection.getAcceptedType().equals(HeaderType.JSON)){
            return new JSONBuilder();
        }
        else if(connection.getAcceptedType().equals(HeaderType.XML)){
            return new XMLBuilder();
        }
        else{
            return null;
        }     
    }
    
}
