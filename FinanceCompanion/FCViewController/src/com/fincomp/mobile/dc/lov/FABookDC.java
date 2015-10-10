package com.fincomp.mobile.dc.lov;

import com.fincomp.mobile.pojo.lov.FABookBO;
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

public class FABookDC extends SyncUtils{
    
    protected static List s_fabook = new ArrayList();
    private static final String NOT_REACHABLE = "NotReachable"; // Indiates no network connectivity
    
    public FABookDC() {
        super();
        syncLocalDB();
        }
        
        public void syncLocalDB(){
        s_fabook.clear();
        String networkStatus =
            (String) AdfmfJavaUtilities.evaluateELExpression("#{deviceScope.hardware.networkStatus}");
        //List collections;
        if (networkStatus.equals(NOT_REACHABLE)) {
            s_fabook = getCollectionFromDB(FABookBO.class);
        } else {
            System.out.println("Inside orgItem");
            Utility.ApplicationLogger.info("Inside script dcomShopFloor.db");
            String restURI = RestURI.GetFABookURI();
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
                "       \"PBOOK\": \"\"\n }\n" + "}\n" + "}\n";
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
                    jsObject1 = (JSONObject) jsObject.get("XBOOK");
                    JSONArray array = (JSONArray) jsObject1.get("XBOOK_ITEM");
                    if (array != null) {
                        int size = array.size();
                        
                        for (int i = 0; i < size; i++) {


                            FABookBO fabookItems = new FABookBO();
                            JSONObject jsObject2 = (JSONObject) array.get(i);

                            fabookItems.setBookType((jsObject2.get("BOOKTYPE").toString()));
                            fabookItems.setFABookName((jsObject2.get("NAME").toString()));
                            fabookItems.setEntity((jsObject2.get("ENTITY").toString()));
                            fabookItems.setOwner((jsObject2.get("OWNER").toString()));

                            s_fabook.add(fabookItems);

                        }

                        super.updateSqlLiteTable(FABookBO.class, s_fabook);
                    }
                } 
                catch (ClassCastException e2) {
                    JSONObject jsObject2 = (JSONObject) jsObject1.get("XBOOK_ITEM");
                    if (jsObject2 != null) {
                        FABookBO fabookItems = new FABookBO();
                        fabookItems.setBookType((jsObject2.get("BOOKTYPE").toString()));
                        fabookItems.setFABookName((jsObject2.get("NAME").toString()));
                        fabookItems.setEntity((jsObject2.get("ENTITY").toString()));
                        fabookItems.setOwner((jsObject2.get("OWNER").toString()));
                        s_fabook.add(fabookItems);
                        super.updateSqlLiteTable(FABookBO.class, s_fabook);
                    }
                    }                
                catch (ParseException e) {
                    e.getMessage();
                }
            }
        }
        }
        
        public FABookBO[] getFABook() {
        s_fabook = super.getOfflineCollection(FABookBO.class);
        FABookBO[] faBookArray = (FABookBO[]) s_fabook.toArray(new FABookBO[s_fabook.size()]);
        return faBookArray;
        }
}
