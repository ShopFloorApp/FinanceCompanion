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

}
