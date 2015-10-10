package com.fincomp.mobile.dc.lov;

import com.fincomp.mobile.pojo.lov.PeriodBO;
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

public class PeriodDC extends SyncUtils {
    protected static List s_period = new ArrayList();
    private static final String NOT_REACHABLE = "NotReachable"; // Indiates no network connectivity
    public PeriodDC() {
        super();
        syncLocalDB();
        }
        
        public void syncLocalDB(){
        s_period.clear();
        String networkStatus =
            (String) AdfmfJavaUtilities.evaluateELExpression("#{deviceScope.hardware.networkStatus}");
        //List collections;
        if (networkStatus.equals(NOT_REACHABLE)) {
            s_period = getCollectionFromDB(PeriodBO.class);
        } else {
            System.out.println("Inside orgItem");
            Utility.ApplicationLogger.info("Inside script dcomShopFloor.db");
            String restURI = RestURI.GetPeriodLOVURI();
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
                    jsObject1 = (JSONObject) jsObject.get("XPERIOD");
                    JSONArray array = (JSONArray) jsObject1.get("XPERIOD_ITEM");
                    if (array != null) {
                        int size = array.size();
                        
                        for (int i = 0; i < size; i++) {


                            PeriodBO periodItems = new PeriodBO();
                            JSONObject jsObject2 = (JSONObject) array.get(i);

                            periodItems.setLedger((jsObject2.get("LEDGER").toString()));
                            periodItems.setPeriodNum((jsObject2.get("PERIODNUM").toString()));
                            periodItems.setPeriodName((jsObject2.get("PERIODNAME").toString()));
                            periodItems.setPeriodYear((jsObject2.get("PERIODYEAR").toString()));
                            periodItems.setPeriodSet((jsObject2.get("PERIODSET").toString()));
                            periodItems.setPeriodST((jsObject2.get("PERIODST").toString()));
                            periodItems.setPeriodET((jsObject2.get("PERIODET").toString()));

                            s_period.add(periodItems);

                        }

                        super.updateSqlLiteTable(PeriodBO.class, s_period);
                    }
                } 
                catch (ClassCastException e2) {
                    JSONObject jsObject2 = (JSONObject) jsObject1.get("XPERIOD_ITEM");
                    if (jsObject2 != null) {
                        PeriodBO periodItems = new PeriodBO();
                        periodItems.setLedger((jsObject2.get("LEDGER").toString()));
                        periodItems.setPeriodNum((jsObject2.get("PERIODNUM").toString()));
                        periodItems.setPeriodName((jsObject2.get("PERIODNAME").toString()));
                        periodItems.setPeriodYear((jsObject2.get("PERIODYEAR").toString()));
                        periodItems.setPeriodSet((jsObject2.get("PERIODSET").toString()));
                        periodItems.setPeriodST((jsObject2.get("PERIODST").toString()));
                        periodItems.setPeriodET((jsObject2.get("PERIODET").toString()));
                        s_period.add(periodItems);
                        super.updateSqlLiteTable(PeriodBO.class, s_period);
                    }
                    }                
                catch (ParseException e) {
                    e.getMessage();
                }
            }
        }
        }
        
        public PeriodBO[] getPeriod() {
        s_period = super.getOfflineCollection(PeriodBO.class);
        PeriodBO[] periodArray = (PeriodBO[]) s_period.toArray(new PeriodBO[s_period.size()]);
        return periodArray;
        }
}
