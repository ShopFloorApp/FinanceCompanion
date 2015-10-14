package com.fincomp.mobile.dc.dashboard;

import com.fincomp.mobile.pojo.dashboard.DashboardBO;
import com.fincomp.mobile.pojo.dashboard.IssueDetailsBO;
import com.fincomp.mobile.pojo.lov.EntityBO;
import com.fincomp.mobile.pojo.lov.LedgerBO;
import com.fincomp.mobile.utility.RestCallerUtil;
import com.fincomp.mobile.utility.RestURI;

import java.util.ArrayList;
import java.util.List;

import oracle.adfmf.framework.api.AdfmfJavaUtilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DashboardDC {
    public static List s_dashboardEntity = new ArrayList();
    public static List s_dashboardIssueDtls = new ArrayList();
    public static List s_dashboardIssueSumm = new ArrayList();

    public DashboardDC() {
        super();
    }

    public void getDashboardDetails(String pPeriod, String pModule, String pIdentifier) {
        s_dashboardEntity.clear();
        s_dashboardIssueDtls.clear();
        s_dashboardIssueSumm.clear();
        String restURI = RestURI.GetDashboardURI();
        RestCallerUtil rcu = new RestCallerUtil();
        String payload =
            "{\n" + "\"GET_SO_PER_ORG_Input\":\n" + "{\n" +
            "\"@xmlns\": \"http://xmlns.oracle.com/apps/fnd/rest/GetSoPerOrgSvc/get_so_per_org/\",\n" +
            "   \"RESTHeader\": {\"@xmlns\": \"http://xmlns.oracle.com/apps/fnd/rest/GetSoPerOrgSvc/header\",\n" +
            "                  \"Responsibility\": \"ORDER_MGMT_SUPER_USER\",\n" +
            "                  \"RespApplication\": \"ONT\",\n" +
            "                  \"SecurityGroup\": \"STANDARD\",\n" +
            "                  \"NLSLanguage\": \"AMERICAN\",\n" + "                  \"Org_Id\": \"82\"\n" +
            "                 },\n" + "   \"InputParameters\": \n" + "      {\"PPERIOD\": \"" + pPeriod + "\",\n" +
            "                   \"PMODULE\": \"" + pModule + "\",\n" + "       \"PIDENTIFIER\": \"" + pIdentifier +
            "\"\n }\n" + "}\n" + "}\n";
        System.out.println("Calling create method");
        String jsonArrayAsString = rcu.invokeUPDATE(restURI, payload);
        System.out.println("Received response");
        String rowModuleName="";
        if (jsonArrayAsString != null) {
            JSONObject jsObject1 = null;
            try {
                JSONParser parser = new JSONParser();
                Object object;

                object = parser.parse(jsonArrayAsString);

                JSONObject jsonObject = (JSONObject) object;
                JSONObject jsObject = (JSONObject) jsonObject.get("OutputParameters");
                jsObject1 = (JSONObject) jsObject.get("XDASHBOARD");
                JSONArray array = (JSONArray) jsObject1.get("XDASHBOARD_ITEM");
                if (array != null) {
                    int size = array.size();

                    for (int i = 0; i < size; i++) {


                        DashboardBO dashboardItems = new DashboardBO();
                        JSONObject jsObject2 = (JSONObject) array.get(i);

                        dashboardItems.setEntity((jsObject2.get("ENTITY").toString()));
                        dashboardItems.setLedger((jsObject2.get("LEDGER").toString()));
                        dashboardItems.setOU((jsObject2.get("OU").toString()));
                        dashboardItems.setInvOrg((jsObject2.get("INVORG").toString()));
                        dashboardItems.setFaBook((jsObject2.get("FABOOK").toString()));
                        dashboardItems.setOwner((jsObject2.get("OWNER").toString()));
                        dashboardItems.setStatus((jsObject2.get("STATUS").toString()));
                        dashboardItems.setIssueCount(Integer.parseInt((jsObject2.get("ISSUECOUNT").toString())));
//                        dashboardItems.setModule((jsObject2.get("MODULE").toString()));                        
                        
                        if(pModule.equalsIgnoreCase("DASHBOARD") || pModule.equalsIgnoreCase("GL")){
                            rowModuleName=jsObject2.get("MODULE").toString();
                        }else{
                            String moduleName=jsObject2.get("MODULE").toString();
                            if(moduleName.equalsIgnoreCase("Inventory")){
                                rowModuleName=jsObject2.get("INVORG").toString();
                            }else if(moduleName.equalsIgnoreCase("Asset")){
                                rowModuleName=jsObject2.get("FABOOK").toString();
                            }else if(moduleName.equalsIgnoreCase("General Ledger")){
                                rowModuleName=jsObject2.get("LEDGER").toString();
                            }else{
                                rowModuleName=jsObject2.get("OU").toString();
                            }
                        }
                        
                        dashboardItems.setModule(rowModuleName);
                        s_dashboardEntity.add(dashboardItems);
                        
                        s_dashboardIssueDtls.addAll(getIssueDetails((JSONObject) jsObject2.get("DTLS"),rowModuleName));
                        s_dashboardIssueSumm.addAll(getIssueDetails((JSONObject) jsObject2.get("SUMMARY"),rowModuleName));
                    }

                }
            } catch (ClassCastException e2) {
                JSONObject jsObject2 = (JSONObject) jsObject1.get("XDASHBOARD_ITEM");
                if (jsObject2 != null) {
                    DashboardBO dashboardItems = new DashboardBO();
                    dashboardItems.setEntity((jsObject2.get("ENTITY").toString()));
                    dashboardItems.setLedger((jsObject2.get("LEDGER").toString()));
                    dashboardItems.setOU((jsObject2.get("OU").toString()));
                    dashboardItems.setInvOrg((jsObject2.get("INVORG").toString()));
                    dashboardItems.setFaBook((jsObject2.get("FABOOK").toString()));
                    dashboardItems.setOwner((jsObject2.get("OWNER").toString()));
                    dashboardItems.setStatus((jsObject2.get("STATUS").toString()));
                    dashboardItems.setIssueCount(Integer.parseInt((jsObject2.get("ISSUECOUNT").toString())));
                    dashboardItems.setModule((jsObject2.get("MODULE").toString()));

                    
                    
                    if(pModule.equalsIgnoreCase("DASHBOARD") || pModule.equalsIgnoreCase("GL")){
                        rowModuleName=jsObject2.get("MODULE").toString();
                    }else{
                        String moduleName=jsObject2.get("MODULE").toString();
                        if(moduleName.equalsIgnoreCase("Inventory")){
                            rowModuleName=jsObject2.get("INVORG").toString();
                        }else if(moduleName.equalsIgnoreCase("Asset")){
                            rowModuleName=jsObject2.get("FABOOK").toString();
                        }else if(moduleName.equalsIgnoreCase("General Ledger")){
                            rowModuleName=jsObject2.get("LEDGER").toString();
                        }else{
                            rowModuleName=jsObject2.get("OU").toString();
                        }
                    }
                    
                    dashboardItems.setModule(rowModuleName);
                    s_dashboardEntity.add(dashboardItems);
                    
                    s_dashboardIssueDtls.addAll(getIssueDetails((JSONObject) jsObject2.get("DTLS"),rowModuleName));
                    s_dashboardIssueSumm.addAll(getIssueDetails((JSONObject) jsObject2.get("SUMMARY"),rowModuleName));

                }
            } catch (ParseException e) {
                e.getMessage();
            }
        }
    }

    public List getIssueDetails(JSONObject issueDtlsObject, String pModule) {
        List issueDtlsList = new ArrayList();
        try {
            JSONArray array = (JSONArray) issueDtlsObject.get("DTLS_ITEM");
            if (array != null) {
                int size = array.size();

                for (int i = 0; i < size; i++) {


                    IssueDetailsBO dashboardIssueItems = new IssueDetailsBO();
                    JSONObject jsObject2 = (JSONObject) array.get(i);

                    dashboardIssueItems.setIssueCateg((jsObject2.get("ISSUECATEG").toString()));
                    dashboardIssueItems.setIssueText((jsObject2.get("ISSUETEXT").toString()));
                    dashboardIssueItems.setModule(pModule);
                    dashboardIssueItems.setIssueCount((jsObject2.get("ISSUECOUNT").toString()));

                    issueDtlsList.add(dashboardIssueItems);

                }
            }
        } catch (ClassCastException e2) {
            JSONObject jsObject2 = (JSONObject) issueDtlsObject.get("DTLS_ITEM");
            if (jsObject2 != null) {
                IssueDetailsBO dashboardIssueItems = new IssueDetailsBO();

                dashboardIssueItems.setIssueCateg((jsObject2.get("ISSUECATEG").toString()));
                dashboardIssueItems.setIssueText((jsObject2.get("ISSUETEXT").toString()));
                dashboardIssueItems.setModule((jsObject2.get("MODULE").toString().contains("@xsi:nil") ? pModule :
                                               jsObject2.get("MODULE").toString()));
                dashboardIssueItems.setIssueCount((jsObject2.get("ISSUECOUNT").toString()));

                issueDtlsList.add(dashboardIssueItems);

            }
        } catch (Exception e) {
            e.getMessage();
        }
        return issueDtlsList;
    }
    
    public DashboardBO[] getDashboardDetails() {
        List dashboardList=new ArrayList();
        String subModule = (String) AdfmfJavaUtilities.evaluateELExpression("#{pageFlowScope.currentSubModule}");
        if (subModule == null) {
            subModule = "";
        }
        if(subModule.trim().equals("")){
            dashboardList.addAll(s_dashboardEntity);
        }else{
            for(int i=0;i<s_dashboardEntity.size();i++){
                DashboardBO dashBO = (DashboardBO) s_dashboardEntity.get(i);
                if(dashBO.getModule().equalsIgnoreCase(subModule)){
                    dashboardList.add(dashBO);
                }
            }
        }
        DashboardBO[] dashboardArray = (DashboardBO[]) dashboardList.toArray(new DashboardBO[dashboardList.size()]);
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.moduleCount}", dashboardList.size());
        return dashboardArray;
    }
    
    public IssueDetailsBO[] getIssueDetails() {
        List issueList=new ArrayList();
        String subModule = (String) AdfmfJavaUtilities.evaluateELExpression("#{pageFlowScope.currentSubModule}");
        if (subModule == null) {
            subModule = "";
        }
        if(subModule.trim().equals("")){
            issueList.addAll(s_dashboardIssueDtls);
        }else{
            for(int i=0;i<s_dashboardIssueDtls.size();i++){
                IssueDetailsBO dashBO = (IssueDetailsBO) s_dashboardIssueDtls.get(i);
                if(dashBO.getModule().equalsIgnoreCase(subModule)){
                    issueList.add(dashBO);
                }
            }
        }
        IssueDetailsBO[] dashboardIssueDtlsArray = (IssueDetailsBO[]) issueList.toArray(new IssueDetailsBO[issueList.size()]);
        return dashboardIssueDtlsArray;
    }
    
    public IssueDetailsBO[] getIssueSummDetails() {
        IssueDetailsBO[] dashboardIssueSummArray = (IssueDetailsBO[]) s_dashboardIssueSumm.toArray(new IssueDetailsBO[s_dashboardIssueSumm.size()]);
        return dashboardIssueSummArray;
    }
}
