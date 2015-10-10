package com.fincomp.mobile.dc.lov;

import com.fincomp.mobile.pojo.lov.InventoryOrgBO;
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

public class InventoryOrgDC extends SyncUtils {
    
    protected static List s_inventoryOrg = new ArrayList();
    private static final String NOT_REACHABLE = "NotReachable"; // Indiates no network connectivity
    
    public InventoryOrgDC() {
        super();
        syncLocalDB();
        }
        
        public void syncLocalDB(){
        s_inventoryOrg.clear();
        String networkStatus =
            (String) AdfmfJavaUtilities.evaluateELExpression("#{deviceScope.hardware.networkStatus}");
        //List collections;
        if (networkStatus.equals(NOT_REACHABLE)) {
            s_inventoryOrg = getCollectionFromDB(InventoryOrgBO.class);
        } else {
            System.out.println("Inside orgItem");
            Utility.ApplicationLogger.info("Inside script dcomShopFloor.db");
            String restURI = RestURI.GetInvOrgURI();
            RestCallerUtil rcu = new RestCallerUtil();
            String payload =
                "{\n" + "\"GET_SO_PER_ORG_Input\":\n" + "{\n" +
                "\"@xmlns\": \"http://xmlns.oracle.com/apps/fnd/rest/GetSoPerOrgSvc/get_so_per_org/\",\n" +
                "   \"RESTHeader\": {\"@xmlns\": \"http://xmlns.oracle.com/apps/fnd/rest/GetSoPerOrgSvc/header\",\n" +
                "                  \"Responsibility\": \"ORDER_MGMT_SUPER_USER\",\n" +
                "                  \"RespApplication\": \"ONT\",\n" +
                "                  \"SecurityGroup\": \"STANDARD\",\n" +
                "                  \"NLSLanguage\": \"AMERICAN\",\n" + "                  \"Org_Id\": \"82\"\n" +
                "                 },\n" + "   \"InputParameters\": \n" + "      {\"PENTITY\": \"\",\n" +
                "       \"PORG\": \"\"\n }\n" + "}\n" + "}\n";
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
                    jsObject1 = (JSONObject) jsObject.get("XORG");
                    JSONArray array = (JSONArray) jsObject1.get("XORG_ITEM");
                    if (array != null) {
                        int size = array.size();
                        
                        for (int i = 0; i < size; i++) {


                            InventoryOrgBO invOrgItems = new InventoryOrgBO();
                            JSONObject jsObject2 = (JSONObject) array.get(i);

                            invOrgItems.setOrgName((jsObject2.get("ORGNAME").toString()));
                            invOrgItems.setOrgKey((jsObject2.get("ORGKEY").toString()));
                            invOrgItems.setOUkey((jsObject2.get("OUKEY").toString()));
                            invOrgItems.setLocation((jsObject2.get("LOCATION").toString()));
                            invOrgItems.setAddress1((jsObject2.get("ADDRESS1").toString()));
                            invOrgItems.setCity((jsObject2.get("CITY").toString()));
                            invOrgItems.setState((jsObject2.get("STATE").toString()));
                            invOrgItems.setCounty((jsObject2.get("COUNTY").toString()));
                            invOrgItems.setZip((jsObject2.get("ZIP").toString()));
                            invOrgItems.setCountry((jsObject2.get("COUNTRY").toString()));
                            invOrgItems.setOwner((jsObject2.get("OWNER").toString()));

                            s_inventoryOrg.add(invOrgItems);

                        }

                        super.updateSqlLiteTable(InventoryOrgBO.class, s_inventoryOrg);
                    }
                } 
                catch (ClassCastException e2) {
                    JSONObject jsObject2 = (JSONObject) jsObject1.get("XORG_ITEM");
                    if (jsObject2 != null) {
                        InventoryOrgBO invOrgItems = new InventoryOrgBO();
                        invOrgItems.setOrgName((jsObject2.get("ORGNAME").toString()));
                        invOrgItems.setOrgKey((jsObject2.get("ORGKEY").toString()));
                        invOrgItems.setOUkey((jsObject2.get("OUKEY").toString()));
                        invOrgItems.setLocation((jsObject2.get("LOCATION").toString()));
                        invOrgItems.setAddress1((jsObject2.get("ADDRESS1").toString()));
                        invOrgItems.setCity((jsObject2.get("CITY").toString()));
                        invOrgItems.setState((jsObject2.get("STATE").toString()));
                        invOrgItems.setCounty((jsObject2.get("COUNTY").toString()));
                        invOrgItems.setZip((jsObject2.get("ZIP").toString()));
                        invOrgItems.setCountry((jsObject2.get("COUNTRY").toString()));
                        invOrgItems.setOwner((jsObject2.get("OWNER").toString()));
                        s_inventoryOrg.add(invOrgItems);
                        super.updateSqlLiteTable(InventoryOrgBO.class, s_inventoryOrg);
                    }
                    }                
                catch (ParseException e) {
                    e.getMessage();
                }
            }
        }
        }
        
        public InventoryOrgBO[] getInvOrg() {
        s_inventoryOrg = super.getOfflineCollection(InventoryOrgBO.class);
        InventoryOrgBO[] invOrgArray = (InventoryOrgBO[]) s_inventoryOrg.toArray(new InventoryOrgBO[s_inventoryOrg.size()]);
        return invOrgArray;
        }
}
