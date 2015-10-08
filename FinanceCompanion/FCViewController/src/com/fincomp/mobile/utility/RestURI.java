package com.fincomp.mobile.utility;


public class RestURI {
    public RestURI() {
        super();
    }
    

    private static final String PAYLOAD_HEADER =
        "\"RESTHeader\":{\"Responsibility\":\"ORDER_MGMT_SUPER_USER\",\"RespApplication\":\"ONT\",\"SecurityGroup\":\"STANDARD\",\"NLSLanguage\":\"AMERICAN\",\"Org_Id\":\"82\"}";
    
    public static String getPayloadHeader(){
        return PAYLOAD_HEADER;
    }
    private static final String GET_ENTITY_URI = "/webservices/rest/DCOMFINLOV/getentity/";
    public static String GetEntityURI() {
        return GET_ENTITY_URI;
    }
    
    private static final String GET_FABOOK_URI = "/webservices/rest/DCOMFINLOV/getfabook/";
    public static String GetFABookURI() {
        return GET_FABOOK_URI;
    }
    private static final String GET_INVORG_URI = "/webservices/rest/DCOMFINLOV/getinvorg/";
    public static String GetInvOrgURI() {
        return GET_INVORG_URI;
    }
    private static final String GET_PERIOD_URI = "/webservices/rest/DCOMSF/getperiod/";
    public static String GetPeriodURI() {
        return GET_PERIOD_URI;
    }

}
