package com.fincomp.mobile.beans;

import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;

public class ApplicationBean {
    public String currentFeature;
    public ApplicationBean() {
        super();
    }

    public void logOut(ActionEvent actionEvent) {
        // Add event code here...
        AdfmfJavaUtilities.setELValue("#{applicationScope.isSpringboardVisible}", "N");
        AdfmfJavaUtilities.logout();
    }
    
    public void gotoSpringboard(ActionEvent actionEvent){
    currentFeature = AdfmfJavaUtilities.getFeatureId();
    AdfmfJavaUtilities.setELValue("#{applicationScope.isSpringboardVisible}", "Y");
    AdfmfContainerUtilities.gotoSpringboard();
    }
    
    public void closeSpringBoard(ActionEvent actionEvent) {
        AdfmfJavaUtilities.setELValue("#{applicationScope.isSpringboardVisible}", "N");
        AdfmfContainerUtilities.gotoFeature(currentFeature);
    }
}
