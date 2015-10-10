package com.fincomp.mobile.beans;

import com.fincomp.mobile.dc.dashboard.DashboardDC;
import com.fincomp.mobile.pojo.dashboard.DashboardBO;
import com.fincomp.mobile.utility.BackgroundProcess;
import com.fincomp.mobile.utility.RestCallerUtil;
import com.fincomp.mobile.utility.RestURI;
import com.fincomp.mobile.utility.SyncUtils;

import java.util.ArrayList;

import oracle.adfmf.amx.event.ValueChangeEvent;
import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DashboardBean extends SyncUtils {
    DashboardDC dashDC=new DashboardDC();
    public DashboardBean() {
        super();
    }


    public void initializaDashboardAction() {
        // Code for running background process
        int noCloseIssue=0;
        int closeWarning=0;
        int closeIssues=0;
        BackgroundProcess bp=new BackgroundProcess();
        Thread t=new Thread(bp);
        t.start();
        getCurrentPeriod();
        dashDC.getDashboardDetails((String)AdfmfJavaUtilities.evaluateELExpression("#{pageFlowScope.currentPeriod}"), "DASHBOARD", "");
        ArrayList dash = (ArrayList) dashDC.s_dashboardEntity;
        for(int i=0;i<dash.size();i++){
            DashboardBO dashBO = (DashboardBO) dash.get(i);
            if(dashBO.getStatus().equals("0")){
                noCloseIssue++;
            }else if(dashBO.getStatus().equals("1")){
                closeWarning++;
            }else{
                closeIssues++;
            }
        }
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.noCloseIssues}", noCloseIssue);
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.closeWarnings}", closeWarning);
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.closeIssues}", closeIssues);
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
