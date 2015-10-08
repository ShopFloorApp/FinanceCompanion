package com.fincomp.mobile.utility;


import java.util.logging.Level;

import oracle.adfmf.dc.ws.rest.RestServiceAdapter;
import oracle.adfmf.framework.api.Model;
import oracle.adfmf.util.logging.Trace;


public class RestCallerUtil {
    
    public RestCallerUtil() {
        super();
    }
    
    //GET
    public String invokeREAD(String requestURI){
        return this.invokeRestRequest(RestServiceAdapter.REQUEST_TYPE_GET, requestURI, "");
    }
    
    //POST
    public String invokeUPDATE(String requestURI, String payload){
        
        return this.invokeRestRequest(RestServiceAdapter.REQUEST_TYPE_POST, requestURI, payload);
    }
    
    //PUT
    public String invokeCREATE(String requestURI, String payload){
        System.out.println("Inside invokeCreate");
        return this.invokeRestRequest(RestServiceAdapter.REQUEST_TYPE_PUT, requestURI, payload);
    }
    
    
    //DELETE
    public String invokeDELETE(String requestURI){
        return this.invokeRestRequest(RestServiceAdapter.REQUEST_TYPE_POST, requestURI, "");
    }
    
    /**
     * Method that handles the boilerplate code for obtaining and configuring a service 
     * adapter instance. 
     * 
     * @param httpMethod GET, POST, PUT, DELETE
     * @param requestURI the URI to appends to the base REST URL. URIs are expected to start with "/"
     * @return
     */
    private String invokeRestRequest(String httpMethod, String requestURI, String payload){
    System.out.println("Inside rest");
        String restPayload = "";
        
        RestServiceAdapter restServiceAdapter = Model.createRestServiceAdapter();
        restServiceAdapter.clearRequestProperties();
        restServiceAdapter.setConnectionName("DCOMFINAPP");
        
        //set GET, POST, DELETE, PUT
        restServiceAdapter.setRequestType(httpMethod);
        String theUsername = "SYSADMIN";
        String thePassword = "tstadm1n"; 
        String userPassword = theUsername + ":" + thePassword;
        String encoding = new sun.misc.BASE64Encoder().encode(userPassword.getBytes());

        restServiceAdapter.addRequestProperty("Authorization", "Basic " + encoding);
        restServiceAdapter.addRequestProperty("Content-Language", "en-US");
        restServiceAdapter.addRequestProperty("Content-Type", "application/json");
        restServiceAdapter.setRequestURI(requestURI);        
        restServiceAdapter.setRetryLimit(0);    
        String response = "";
        if(payload != null){   
             //send with empty payload
             restPayload  = payload;
        }

        try {
            response = (restServiceAdapter.send(restPayload)).toString();
        } catch (Exception e) {
            //log error
            Trace.log("REST_JSON",Level.SEVERE, this.getClass(),"invokeRestRequest", "Invoke of REST Resource failed for "+httpMethod+" to "+requestURI);
            Trace.log("REST_JSON",Level.SEVERE, this.getClass(),"invokeRestRequest", e.getLocalizedMessage());
        }
        return response;
    };
}

