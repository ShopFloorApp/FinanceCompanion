package com.fincomp.mobile.utility;

import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.framework.api.AdfmfSlidingWindowOptions;
import oracle.adfmf.framework.api.AdfmfSlidingWindowUtilities;
import oracle.adfmf.framework.exception.AdfException;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class WindowBean {
    private String springboardWindow;
    private boolean springboardToggleFlag;
    
    private boolean warehouse;

       public void setWarehouse(boolean warehouse) {
           boolean oldWarehouse = this.warehouse;
           this.warehouse = warehouse;
           propertyChangeSupport.firePropertyChange("warehouse", oldWarehouse, warehouse);
       }
       
    public boolean isWarehouse() {
           System.out.println("isWarehouse");
           System.out.println("setwarehouse");
           String orgId =
               (String)AdfmfJavaUtilities.evaluateELExpression("#{preferenceScope.feature.dcom.shop.MyWarehouse.OrgCodePG.OrgCode}");
           System.out.println("orgId " + orgId.length());
           if(orgId.length() == 0)
               setWarehouse(true);
           else
               setWarehouse(false);
           return warehouse;
       }

    public void setSpringboardToggleFlag(boolean springboardToggleFlag) {
        boolean oldSpringboardToggleFlag = this.springboardToggleFlag;
        this.springboardToggleFlag = springboardToggleFlag;
        propertyChangeSupport.firePropertyChange("springboardToggleFlag", oldSpringboardToggleFlag,
                                                 springboardToggleFlag);
    }

    public boolean isSpringboardToggleFlag() {
        return springboardToggleFlag;
    }

    public void setPropertyChangeSupport(PropertyChangeSupport propertyChangeSupport) {
        PropertyChangeSupport oldPropertyChangeSupport = this.propertyChangeSupport;
        this.propertyChangeSupport = propertyChangeSupport;
        propertyChangeSupport.firePropertyChange("propertyChangeSupport", oldPropertyChangeSupport,
                                                 propertyChangeSupport);
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void setSpringboardWindow(String springboardWindow) {
        Object oldSpringboardWindow = this.springboardWindow;
        this.springboardWindow = springboardWindow;
        propertyChangeSupport.firePropertyChange("springboardWindow", oldSpringboardWindow, springboardWindow);
    }

    public String getSpringboardWindow() {
        return springboardWindow;
    }

    public WindowBean() {
        super();
    }

    public void showSpringboard(ActionEvent actionEvent) {
        springboardToggleFlag = !springboardToggleFlag;
        if (springboardWindow == null) {
            springboardWindow = AdfmfSlidingWindowUtilities.create("dcom.shop.Springboard"); //slidingWindowContent
            this.setSpringboardWindow(springboardWindow);
        }

        AdfmfSlidingWindowOptions windowOptions = new AdfmfSlidingWindowOptions();

        windowOptions.setDirection(AdfmfSlidingWindowOptions.DIRECTION_LEFT);
        windowOptions.setStyle(AdfmfSlidingWindowOptions.STYLE_PINNED);
        if (springboardToggleFlag) {
            windowOptions.setSize("200");
            AdfmfSlidingWindowUtilities.show(springboardWindow, windowOptions);
        } else {
            AdfmfSlidingWindowUtilities.hide(springboardWindow);
        }
    }

    public void hideSpringboard(ActionEvent actionEvent) {
        springboardToggleFlag = !springboardToggleFlag;
        String springboardWindow = this.getSpringboardWindow();
        AdfmfSlidingWindowUtilities.hide(springboardWindow);
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }

    public void logout() {
        AdfmfJavaUtilities.logout();
    }

    public String goToDispatch() {
        // Add event code here...
        System.out.println("here");
        BackgroundProcess bp=new BackgroundProcess();
        Thread t=new Thread(bp);
        t.start();
        AdfmfContainerUtilities.invokeContainerJavaScriptFunction(AdfmfJavaUtilities.getFeatureId(),
                                                                  "showAlert", new Object[] {"Warehouse","Your warehouse has been set.","Ok" });
        //AdfmfContainerUtilities.invokeContainerJavaScriptFunction("dcom.shop.MyWarehouse",
        //"function(){navigator.notification.alert('Your warehouse has been set.',  function(){}, 'Information', 'OK');}", new Object[] { });
        return "Dispatch";
    }
}
