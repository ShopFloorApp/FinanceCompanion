package com.fincomp.mobile.pojo.dashboard;

import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class IssueDetailsBO {
    
    private String IssueCateg;
    private String IssueText;
    private String Module;
    private String IssueCount;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public IssueDetailsBO() {
        super();
    }

    public void setIssueCateg(String IssueCateg) {
        String oldIssueCateg = this.IssueCateg;
        this.IssueCateg = IssueCateg;
        propertyChangeSupport.firePropertyChange("IssueCateg", oldIssueCateg, IssueCateg);
    }

    public String getIssueCateg() {
        return IssueCateg;
    }

    public void setIssueText(String IssueText) {
        String oldIssueText = this.IssueText;
        this.IssueText = IssueText;
        propertyChangeSupport.firePropertyChange("IssueText", oldIssueText, IssueText);
    }

    public String getIssueText() {
        return IssueText;
    }

    public void setModule(String Module) {
        String oldModule = this.Module;
        this.Module = Module;
        propertyChangeSupport.firePropertyChange("Module", oldModule, Module);
    }

    public String getModule() {
        return Module;
    }

    public void setIssueCount(String IssueCount) {
        String oldIssueCount = this.IssueCount;
        this.IssueCount = IssueCount;
        propertyChangeSupport.firePropertyChange("IssueCount", oldIssueCount, IssueCount);
    }

    public String getIssueCount() {
        return IssueCount;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }
}
