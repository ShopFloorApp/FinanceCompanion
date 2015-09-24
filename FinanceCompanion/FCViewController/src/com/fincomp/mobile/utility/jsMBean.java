package com.fincomp.mobile.utility;

import java.util.ArrayList;

import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.javax.faces.model.SelectItem;

public class jsMBean {
    private static int lov_count=0;
    public jsMBean() {
    }

    public void rightNav(ActionEvent actionEvent) {
        String featureID = AdfmfJavaUtilities.getFeatureId();
        AdfmfContainerUtilities.invokeContainerJavaScriptFunction(featureID, "rightNavigationToggleBtn", new Object[] {});
    }

    public String hideRightMenu() {
        // Add event code here...
        String featureID = AdfmfJavaUtilities.getFeatureId();
        AdfmfContainerUtilities.invokeContainerJavaScriptFunction(featureID, "rightNavigationToggleBtn", new Object[] {});
        return null;
    }
    
    public void flipCardBack(ActionEvent actionEvent) {
        System.out.println("code reached in flip card");
        String val = (String) AdfmfJavaUtilities.evaluateELExpression("#{pageFlowScope.deckName}");
        Integer i=new Integer(Integer.parseInt(val)-1);
        String featureID = AdfmfJavaUtilities.getFeatureId();
        System.out.println("code is reaching before javascript "+val);
        AdfmfContainerUtilities.invokeContainerJavaScriptFunction(featureID, "flipCardLayoutAnimActionBack", new Object[] {i});
    }
    
    public void flipCardFront(ActionEvent actionEvent) {
        System.out.println("code reached in flip card");
        String val = (String) AdfmfJavaUtilities.evaluateELExpression("#{pageFlowScope.deckName}");
        Integer i=new Integer(Integer.parseInt(val)-1);
        String featureID = AdfmfJavaUtilities.getFeatureId();
        System.out.println("code is reaching before javascript "+val);
        AdfmfContainerUtilities.invokeContainerJavaScriptFunction(featureID, "flipCardLayoutAnimActionFront", new Object[] {i});
    }
    public void changeListItemValues(ActionEvent actionEvent) {
        // Add event code here...
        String featureID = AdfmfJavaUtilities.getFeatureId();
        AdfmfContainerUtilities.invokeContainerJavaScriptFunction(featureID, "changeCardLytValues", new Object[] {});
    }
    
    public ArrayList<SelectItem> getParamLov(){
        ArrayList<SelectItem> lov=new ArrayList<SelectItem>();
        if(lov_count==0){
            SelectItem s=new SelectItem();
            s.setValue("one");
            s.setLabel("one");
            lov.add(s);
            lov_count++;
        }else{
            SelectItem s=new SelectItem();
            s.setValue("two");
            s.setLabel("two");
            lov.add(s);
        }
        return lov;
    }
    
    public void openPopup(ActionEvent actionEvent) {
        String featureID = AdfmfJavaUtilities.getFeatureId();
        AdfmfContainerUtilities.invokeContainerJavaScriptFunction(featureID, "showPopup", new Object[] {});
    }
}
