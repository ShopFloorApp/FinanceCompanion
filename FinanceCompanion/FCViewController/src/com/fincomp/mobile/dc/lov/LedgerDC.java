package com.fincomp.mobile.dc.lov;

import com.fincomp.mobile.pojo.lov.LedgerBO;
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

public class LedgerDC extends SyncUtils {
    protected static List s_ledger = new ArrayList();
    private static final String NOT_REACHABLE = "NotReachable"; // Indiates no network connectivity
    public LedgerDC() {
        super();
        syncLocalDB();
        }
        
        public void syncLocalDB(){
        s_ledger.clear();
        String networkStatus =
            (String) AdfmfJavaUtilities.evaluateELExpression("#{deviceScope.hardware.networkStatus}");
        //List collections;
        if (networkStatus.equals(NOT_REACHABLE)) {
            s_ledger = getCollectionFromDB(LedgerBO.class);
        } else {
            System.out.println("Inside orgItem");
            Utility.ApplicationLogger.info("Inside script dcomShopFloor.db");
            String restURI = RestURI.GetLedgerURI();
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
                "       \"PLEDGER\": \"\"\n }\n" + "}\n" + "}\n";
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
                    jsObject1 = (JSONObject) jsObject.get("XLEDGER");
                    JSONArray array = (JSONArray) jsObject1.get("XLEDGER_ITEM");
                    if (array != null) {
                        int size = array.size();
                        
                        for (int i = 0; i < size; i++) {


                            LedgerBO ledgerItems = new LedgerBO();
                            JSONObject jsObject2 = (JSONObject) array.get(i);

                            ledgerItems.setLedger((jsObject2.get("LEDGER").toString()));
                            ledgerItems.setLedgerKey((jsObject2.get("LEDGERKEY").toString()));
                            ledgerItems.setLedgerCat((jsObject2.get("LEDGERCAT").toString()));
                            ledgerItems.setOwner((jsObject2.get("CURRENCY").toString()));
                            ledgerItems.setCoa((jsObject2.get("COA").toString()));
                            ledgerItems.setOwner((jsObject2.get("OWNER").toString()));

                            s_ledger.add(ledgerItems);

                        }

                        super.updateSqlLiteTable(LedgerBO.class, s_ledger);
                    }
                } 
                catch (ClassCastException e2) {
                    JSONObject jsObject2 = (JSONObject) jsObject1.get("XLEDGER_ITEM");
                    if (jsObject2 != null) {
                        LedgerBO ledgerItems = new LedgerBO();
                        ledgerItems.setLedger((jsObject2.get("LEDGER").toString()));
                        ledgerItems.setLedgerKey((jsObject2.get("LEDGERKEY").toString()));
                        ledgerItems.setLedgerCat((jsObject2.get("LEDGERCAT").toString()));
                        ledgerItems.setOwner((jsObject2.get("CURRENCY").toString()));
                        ledgerItems.setCoa((jsObject2.get("COA").toString()));
                        ledgerItems.setOwner((jsObject2.get("OWNER").toString()));
                        s_ledger.add(ledgerItems);
                        super.updateSqlLiteTable(LedgerBO.class, s_ledger);
                    }
                    }                
                catch (ParseException e) {
                    e.getMessage();
                }
            }
        }
        }
        
        public LedgerBO[] getLedger() {
        s_ledger = super.getOfflineCollection(LedgerBO.class);
        LedgerBO[] ledgerArray = (LedgerBO[]) s_ledger.toArray(new LedgerBO[s_ledger.size()]);
        return ledgerArray;
        }
}
