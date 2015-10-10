package com.fincomp.mobile.dc.lov;

import com.fincomp.mobile.pojo.lov.OUBO;
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

public class OUDC extends SyncUtils {
    protected static List s_ou = new ArrayList();
    private static final String NOT_REACHABLE = "NotReachable"; // Indiates no network connectivity
    public OUDC() {
        super();
        syncLocalDB();
        }
        
        public void syncLocalDB(){
        s_ou.clear();
        String networkStatus =
            (String) AdfmfJavaUtilities.evaluateELExpression("#{deviceScope.hardware.networkStatus}");
        //List collections;
        if (networkStatus.equals(NOT_REACHABLE)) {
            s_ou = getCollectionFromDB(OUBO.class);
        } else {
            System.out.println("Inside orgItem");
            Utility.ApplicationLogger.info("Inside script dcomShopFloor.db");
            String restURI = RestURI.GetOUURI();
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
                "       \"POU\": \"\"\n }\n" + "}\n" + "}\n";
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
                    jsObject1 = (JSONObject) jsObject.get("XOU");
                    JSONArray array = (JSONArray) jsObject1.get("XOU_ITEM");
                    if (array != null) {
                        int size = array.size();
                        
                        for (int i = 0; i < size; i++) {


                            OUBO ouItems = new OUBO();
                            JSONObject jsObject2 = (JSONObject) array.get(i);

                            ouItems.setOU((jsObject2.get("OU").toString()));
                            ouItems.setOUKey((jsObject2.get("OUKEY").toString()));
                            ouItems.setLedger((jsObject2.get("LEDGER").toString()));
                            ouItems.setEntity((jsObject2.get("ENTITY").toString()));
                            ouItems.setOwner((jsObject2.get("OWNER").toString()));

                            s_ou.add(ouItems);

                        }

                        super.updateSqlLiteTable(OUBO.class, s_ou);
                    }
                } 
                catch (ClassCastException e2) {
                    JSONObject jsObject2 = (JSONObject) jsObject1.get("XOU_ITEM");
                    if (jsObject2 != null) {
                        OUBO ouItems = new OUBO();
                        ouItems.setOU((jsObject2.get("OU").toString()));
                        ouItems.setOUKey((jsObject2.get("OUKEY").toString()));
                        ouItems.setLedger((jsObject2.get("LEDGER").toString()));
                        ouItems.setEntity((jsObject2.get("ENTITY").toString()));
                        ouItems.setOwner((jsObject2.get("OWNER").toString()));
                        s_ou.add(ouItems);
                        super.updateSqlLiteTable(OUBO.class, s_ou);
                    }
                    }                
                catch (ParseException e) {
                    e.getMessage();
                }
            }
        }
        }
        
        public OUBO[] getOU() {
        s_ou = super.getOfflineCollection(OUBO.class);
        OUBO[] ouArray = (OUBO[]) s_ou.toArray(new OUBO[s_ou.size()]);
        return ouArray;
        }
}
