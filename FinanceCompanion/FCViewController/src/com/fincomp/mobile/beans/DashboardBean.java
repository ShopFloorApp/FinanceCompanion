package com.fincomp.mobile.beans;

import com.fincomp.mobile.utility.BackgroundProcess;
import com.fincomp.mobile.utility.RestCallerUtil;
import com.fincomp.mobile.utility.RestURI;
import com.fincomp.mobile.utility.SyncUtils;

import oracle.adfmf.amx.event.ValueChangeEvent;
import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DashboardBean extends SyncUtils {
    
    public DashboardBean() {
        super();
    }


    public void initializaDashboardAction() {
        // Code for running background process
        BackgroundProcess bp=new BackgroundProcess();
        Thread t=new Thread(bp);
        t.start();
        getCurrentPeriod();
    }
    public void callButtonActionJS(String btn){
        String featureID = AdfmfJavaUtilities.getFeatureId();
        AdfmfContainerUtilities.invokeContainerJavaScriptFunction(featureID, "showPopup", new Object[] {btn});
    }

    public void valueChangeOnEntity(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        callButtonActionJS("cb1");
    }
    
    public void getCurrentPeriod(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        // Code for Getting Current Period
        String restURI = RestURI.GetPeriodURI();
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
        "                   \"PLEDGER\": \"\",\n" + 
            "       \"PWHAT\": \"\"\n }\n" + "}\n" + "}\n";
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
                String period = (String) jsObject.get("XPERIOD");
                AdfmfJavaUtilities.setELValue("#{pageFlowScope.currentPeriod}", period);
                
            }catch(Exception e){
                e.getMessage();
            }
        }
    }
}
