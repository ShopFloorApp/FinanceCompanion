package com.fincomp.mobile.dc.lov;

import com.fincomp.mobile.pojo.lov.EntityBO;
import com.fincomp.mobile.utility.RestCallerUtil;
import com.fincomp.mobile.utility.RestURI;
import com.fincomp.mobile.utility.SyncUtils;

import java.util.ArrayList;
import java.util.List;

import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.util.Utility;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class EntityDC extends SyncUtils{
    protected static List s_entity = new ArrayList();
    private static final String NOT_REACHABLE = "NotReachable"; // Indiates no network connectivity
    
    public EntityDC() {
        super();
        syncLocalDB();
    }
    
    public void syncLocalDB(){
        s_entity.clear();
        String networkStatus =
            (String) AdfmfJavaUtilities.evaluateELExpression("#{deviceScope.hardware.networkStatus}");
        //List collections;
        if (networkStatus.equals(NOT_REACHABLE)) {
            s_entity = getCollectionFromDB(EntityBO.class);
        } else {
            System.out.println("Inside orgItem");
            Utility.ApplicationLogger.info("Inside script dcomShopFloor.db");
            String restURI = RestURI.GetEntityURI();
            RestCallerUtil rcu = new RestCallerUtil();
            String payload =
                "{\n" + "\"GET_SO_PER_ORG_Input\":\n" + "{\n" +
                "\"@xmlns\": \"http://xmlns.oracle.com/apps/fnd/rest/GetSoPerOrgSvc/get_so_per_org/\",\n" +
                "   \"RESTHeader\": {\"@xmlns\": \"http://xmlns.oracle.com/apps/fnd/rest/GetSoPerOrgSvc/header\",\n" +
                "                  \"Responsibility\": \"ORDER_MGMT_SUPER_USER\",\n" +
                "                  \"RespApplication\": \"ONT\",\n" +
                "                  \"SecurityGroup\": \"STANDARD\",\n" +
                "                  \"NLSLanguage\": \"AMERICAN\",\n" + "                  \"Org_Id\": \"82\"\n" +
                "                 },\n" + "   \"InputParameters\": \n" + "      {\"PENTITY\": \"\"\n }\n" + "}\n" + "}\n";
            System.out.println("Calling create method");
            String jsonArrayAsString = rcu.invokeUPDATE(restURI, payload);
            System.out.println("Received response");
            if (jsonArrayAsString != null) {
                JSONObject jsObject1=null;
                try {
                    JSONParser parser = new JSONParser();
                    Object object;

                    object = parser.parse(jsonArrayAsString);

                    JSONObject jsonObject = (JSONObject) object;
                    JSONObject jsObject = (JSONObject) jsonObject.get("OutputParameters");
                    jsObject1 = (JSONObject) jsObject.get("XENTITY");
                    JSONArray array = (JSONArray) jsObject1.get("XENTITY_ITEM");
                    if (array != null) {
                        int size = array.size();
                        
                        for (int i = 0; i < size; i++) {


                            EntityBO entityItems = new EntityBO();
                            JSONObject jsObject2 = (JSONObject) array.get(i);

                            entityItems.setCountry((jsObject2.get("COUNTRY").toString()));
                            entityItems.setEntityName((jsObject2.get("NAME").toString()));
                            entityItems.setLedger((jsObject2.get("LEDGER").toString()));
                            entityItems.setOwner((jsObject2.get("OWNER").toString()));

                            s_entity.add(entityItems);

                        }

                        super.updateSqlLiteTable(EntityBO.class, s_entity);
                    }
                } 
                catch (ClassCastException e2) {
                    JSONObject jsObject2 = (JSONObject) jsObject1.get("XWAREHOUSE_ITEM");
                    if (jsObject2 != null) {
                        EntityBO entityItems = new EntityBO();
                        entityItems.setCountry((jsObject2.get("COUNTRY").toString()));
                        entityItems.setEntityName((jsObject2.get("NAME").toString()));
                        entityItems.setLedger((jsObject2.get("LEDGER").toString()));
                        entityItems.setOwner((jsObject2.get("OWNER").toString()));
                        s_entity.add(entityItems);
                        super.updateSqlLiteTable(EntityBO.class, s_entity);
                    }
                    }                
                catch (ParseException e) {
                    e.getMessage();
                }
            }
        }
    }
    
    public EntityBO[] getEntity() {
        s_entity = super.getOfflineCollection(EntityBO.class);
        EntityBO[] entityArray = (EntityBO[]) s_entity.toArray(new EntityBO[s_entity.size()]);
        return entityArray;
    }
}
