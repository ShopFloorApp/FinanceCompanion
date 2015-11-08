package com.fincomp.mobile.beans;

import com.fincomp.mobile.dc.dashboard.DashboardDC;
import com.fincomp.mobile.dc.lov.EntityDC;
import com.fincomp.mobile.pojo.dashboard.DashboardBO;
import com.fincomp.mobile.pojo.dashboard.IssueDetailsBO;
import com.fincomp.mobile.pojo.lov.EntityBO;
import com.fincomp.mobile.utility.BackgroundProcess;
import com.fincomp.mobile.utility.RestCallerUtil;
import com.fincomp.mobile.utility.RestURI;
import com.fincomp.mobile.utility.SyncUtils;

import java.util.ArrayList;

import java.util.List;

import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.amx.event.ValueChangeEvent;
import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DashboardBean extends SyncUtils {
    DashboardDC dashDC = new DashboardDC();

    public DashboardBean() {
        super();
    }


    public void initializaDashboardAction() {
        // Code for running background process
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.currentEntityOwner}", null);
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.currentEntity}", null);
        int noCloseIssue = 0;
        int closeWarning = 0;
        int closeIssues = 0;
        dashDC.getDashboardDetails((String) AdfmfJavaUtilities.evaluateELExpression("#{pageFlowScope.currentPeriod}"),
                                   "DASHBOARD", "");
        ArrayList dash = (ArrayList) dashDC.s_dashboardEntity;
        for (int i = 0; i < dash.size(); i++) {
            DashboardBO dashBO = (DashboardBO) dash.get(i);
            if (dashBO.getStatus().equals("0")) {
                noCloseIssue++;
            } else if (dashBO.getStatus().equals("1")) {
                closeWarning++;
            } else {
                closeIssues++;
            }
        }
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.noCloseIssues}", noCloseIssue);
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.closeWarnings}", closeWarning);
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.closeIssues}", closeIssues);
    }

    public void callButtonActionJS(String btn) {
        String featureID = AdfmfJavaUtilities.getFeatureId();
        AdfmfContainerUtilities.invokeContainerJavaScriptFunction(featureID, "showPopup", new Object[] { btn });
    }

    public void valueChangeOnEntity(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        getModuleInfo();
    }

    public void getModuleInfo() {
        String entityName = (String) AdfmfJavaUtilities.evaluateELExpression("#{pageFlowScope.currentEntity}");
        ArrayList dash = (ArrayList) EntityDC.s_entity;
        for (int i = 0; i < dash.size(); i++) {
            EntityBO entityBO = (EntityBO) dash.get(i);
            if (entityBO.getEntityName().equals(entityName)) {
                String owner = entityBO.getOwner();
                AdfmfJavaUtilities.setELValue("#{pageFlowScope.currentEntityOwner}", owner);
                break;
            }
        }
        dashDC.getDashboardDetails((String) AdfmfJavaUtilities.evaluateELExpression("#{pageFlowScope.currentPeriod}"),
                                   "ENTITY",
                                   (String) AdfmfJavaUtilities.evaluateELExpression("#{pageFlowScope.currentEntity}"));
        callButtonActionJS("cb1");
    }

    public void getSubModuleDetails() {
        String currentEntityName = (String) AdfmfJavaUtilities.evaluateELExpression("#{pageFlowScope.currentEntity}");
        String currentPeriod = (String) AdfmfJavaUtilities.evaluateELExpression("#{pageFlowScope.currentPeriod}");
        String currentModule = (String) AdfmfJavaUtilities.evaluateELExpression("#{pageFlowScope.currentModule}");
        dashDC.getDashboardDetails(currentPeriod, currentModule, currentEntityName);
        getErrorCounts(dashDC.s_dashboardIssueSumm);
    }

    public void getErrorCounts(List summaryList) {
        String subModule = (String) AdfmfJavaUtilities.evaluateELExpression("#{pageFlowScope.currentSubModule}");
        if (subModule == null) {
            subModule = "";
        }
        int intfError = 0;
        int unpostTxn = 0;
        int perStatus = 0;
        int miscIssue = 0;
        if (subModule.trim().equals("")) {
            for (int i = 0; i < summaryList.size(); i++) {
                IssueDetailsBO dashBO = (IssueDetailsBO) summaryList.get(i);
                if (dashBO.getIssueCateg().equalsIgnoreCase("Interface Errors")) {
                    intfError = intfError + Integer.parseInt(dashBO.getIssueCount());
                } else if (dashBO.getIssueCateg().equalsIgnoreCase("Unposted Trxn")) {
                    unpostTxn = unpostTxn + Integer.parseInt(dashBO.getIssueCount());
                } else if (dashBO.getIssueCateg().equalsIgnoreCase("Period Status")) {
                    perStatus = perStatus + Integer.parseInt(dashBO.getIssueCount());
                } else {
                    miscIssue = miscIssue + Integer.parseInt(dashBO.getIssueCount());
                }
            }
        } else {
            for (int i = 0; i < summaryList.size(); i++) {
                IssueDetailsBO dashBO = (IssueDetailsBO) summaryList.get(i);
                if (dashBO.getIssueCateg().equalsIgnoreCase("Interface Errors") &&
                    dashBO.getModule().equalsIgnoreCase(subModule)) {
                    intfError = intfError + Integer.parseInt(dashBO.getIssueCount());
                } else if (dashBO.getIssueCateg().equalsIgnoreCase("Unposted Trxn") &&
                           dashBO.getModule().equalsIgnoreCase(subModule)) {
                    unpostTxn = unpostTxn + Integer.parseInt(dashBO.getIssueCount());
                } else if (dashBO.getIssueCateg().equalsIgnoreCase("Period Status") &&
                           dashBO.getModule().equalsIgnoreCase(subModule)) {
                    perStatus = perStatus + Integer.parseInt(dashBO.getIssueCount());
                } else if (dashBO.getIssueCateg().equalsIgnoreCase("Misc issues") &&
                           dashBO.getModule().equalsIgnoreCase(subModule)) {
                    miscIssue = miscIssue + Integer.parseInt(dashBO.getIssueCount());
                }
            }
        }
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.intfError}", intfError);
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.unpostTxn}", unpostTxn);
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.perStatus}", perStatus);
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.miscIssue}", miscIssue);
    }

    public void getCurrentPeriod() {
        // Code for Getting Current Period
        BackgroundProcess bp = new BackgroundProcess();
        Thread t = new Thread(bp);
        t.start();

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
            "                   \"PLEDGER\": \"\",\n" + "       \"PWHAT\": \"\"\n }\n" + "}\n" + "}\n";
        System.out.println("Calling create method");
        String jsonArrayAsString = rcu.invokeUPDATE(restURI, payload);
        System.out.println("Received response");
        if (jsonArrayAsString != null) {
            JSONObject jsObject1 = null;
            try {
                JSONParser parser = new JSONParser();
                Object object;

                object = parser.parse(jsonArrayAsString);

                JSONObject jsonObject = (JSONObject) object;
                JSONObject jsObject = (JSONObject) jsonObject.get("OutputParameters");
                String period = (String) jsObject.get("XPERIOD");
                AdfmfJavaUtilities.setELValue("#{pageFlowScope.currentPeriod}", period);

            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public void returnFromModuleDetails(ActionEvent actionEvent) {
        // Add event code here...
        AdfmfJavaUtilities.setELValue("#{pageFlowScope.currentSubModule}",null);
    }

    public void subModuleValueChange(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        String subModule = (String) AdfmfJavaUtilities.evaluateELExpression("#{pageFlowScope.currentSubModule}");
        callButtonActionJS("cb1");
    }

    public void onSubModuleSelection(ActionEvent actionEvent) {
        // Add event code here...
        getErrorCounts(dashDC.s_dashboardIssueSumm);
    }
}
