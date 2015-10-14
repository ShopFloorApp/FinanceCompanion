package com.fincomp.mobile.pojo.dashboard;

import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class DashboardBO {
    private String Entity;
    private String Ledger;
    private String OU;
    private String InvOrg;
    private String faBook;
    private String Owner;
    private String Status;
    private String Module;
    private int IssueCount;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public DashboardBO() {
        super();
    }

    public void setFaBook(String faBook) {
        String oldFaBook = this.faBook;
        this.faBook = faBook;
        propertyChangeSupport.firePropertyChange("faBook", oldFaBook, faBook);
    }

    public String getFaBook() {
        return faBook;
    }

    public void setIssueCount(int IssueCount) {
        int oldIssueCount = this.IssueCount;
        this.IssueCount = IssueCount;
        propertyChangeSupport.firePropertyChange("IssueCount", oldIssueCount, IssueCount);
    }

    public int getIssueCount() {
        return IssueCount;
    }

    public void setEntity(String Entity) {
        String oldEntity = this.Entity;
        this.Entity = Entity;
        propertyChangeSupport.firePropertyChange("Entity", oldEntity, Entity);
    }

    public String getEntity() {
        return Entity;
    }

    public void setLedger(String Ledger) {
        String oldLedger = this.Ledger;
        this.Ledger = Ledger;
        propertyChangeSupport.firePropertyChange("Ledger", oldLedger, Ledger);
    }

    public String getLedger() {
        return Ledger;
    }

    public void setOU(String OU) {
        String oldOU = this.OU;
        this.OU = OU;
        propertyChangeSupport.firePropertyChange("OU", oldOU, OU);
    }

    public String getOU() {
        return OU;
    }

    public void setInvOrg(String InvOrg) {
        String oldInvOrg = this.InvOrg;
        this.InvOrg = InvOrg;
        propertyChangeSupport.firePropertyChange("InvOrg", oldInvOrg, InvOrg);
    }

    public String getInvOrg() {
        return InvOrg;
    }

    public void setOwner(String Owner) {
        String oldOwner = this.Owner;
        this.Owner = Owner;
        propertyChangeSupport.firePropertyChange("Owner", oldOwner, Owner);
    }

    public String getOwner() {
        return Owner;
    }

    public void setStatus(String Status) {
        String oldStatus = this.Status;
        this.Status = Status;
        propertyChangeSupport.firePropertyChange("Status", oldStatus, Status);
    }

    public String getStatus() {
        return Status;
    }

    public void setModule(String Module) {
        String oldModule = this.Module;
        this.Module = Module;
        propertyChangeSupport.firePropertyChange("Module", oldModule, Module);
    }

    public String getModule() {
        return Module;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }
}
