package com.fincomp.mobile.pojo.lov;

import java.util.HashMap;

import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class PeriodBO {
    private String Ledger;
    private String PeriodNum;
    private String PeriodName;
    private String PeriodYear;
    private String PeriodSet;
    private String PeriodST;
    private String PeriodET;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public PeriodBO() {
        super();
    }

    public void setLedger(String Ledger) {
        String oldLedger = this.Ledger;
        this.Ledger = Ledger;
        propertyChangeSupport.firePropertyChange("Ledger", oldLedger, Ledger);
    }

    public String getLedger() {
        return Ledger;
    }

    public void setPeriodNum(String PeriodNum) {
        String oldPeriodNum = this.PeriodNum;
        this.PeriodNum = PeriodNum;
        propertyChangeSupport.firePropertyChange("PeriodNum", oldPeriodNum, PeriodNum);
    }

    public String getPeriodNum() {
        return PeriodNum;
    }

    public void setPeriodName(String PeriodName) {
        String oldPeriodName = this.PeriodName;
        this.PeriodName = PeriodName;
        propertyChangeSupport.firePropertyChange("PeriodName", oldPeriodName, PeriodName);
    }

    public String getPeriodName() {
        return PeriodName;
    }

    public void setPeriodYear(String PeriodYear) {
        String oldPeriodYear = this.PeriodYear;
        this.PeriodYear = PeriodYear;
        propertyChangeSupport.firePropertyChange("PeriodYear", oldPeriodYear, PeriodYear);
    }

    public String getPeriodYear() {
        return PeriodYear;
    }

    public void setPeriodSet(String PeriodSet) {
        String oldPeriodSet = this.PeriodSet;
        this.PeriodSet = PeriodSet;
        propertyChangeSupport.firePropertyChange("PeriodSet", oldPeriodSet, PeriodSet);
    }

    public String getPeriodSet() {
        return PeriodSet;
    }

    public void setPeriodST(String PeriodST) {
        String oldPeriodST = this.PeriodST;
        this.PeriodST = PeriodST;
        propertyChangeSupport.firePropertyChange("PeriodST", oldPeriodST, PeriodST);
    }

    public String getPeriodST() {
        return PeriodST;
    }

    public void setPeriodET(String PeriodET) {
        String oldPeriodET = this.PeriodET;
        this.PeriodET = PeriodET;
        propertyChangeSupport.firePropertyChange("PeriodET", oldPeriodET, PeriodET);
    }

    public String getPeriodET() {
        return PeriodET;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }
    
    public void setBOClassRow(HashMap hashMap) {
        this.setLedger((String) hashMap.get("ledger"));
        this.setPeriodNum((String) hashMap.get("periodnum"));
        this.setPeriodName((String) hashMap.get("periodname"));
        this.setPeriodYear((String) hashMap.get("periodyear"));
        this.setPeriodSet((String) hashMap.get("periodset"));
        this.setPeriodST((String) hashMap.get("periodst"));
        this.setPeriodET((String) hashMap.get("periodet"));
    }

    public HashMap getBOClassRow(PeriodBO period) {
        HashMap map = new HashMap();
        map.put("ledger", period.getLedger());
        map.put("periodnum", period.getPeriodNum());
        map.put("periodname", period.getPeriodName());
        map.put("periodyear", period.getPeriodYear());
        map.put("periodset", period.getPeriodSet());
        map.put("periodst", period.getPeriodST());
        map.put("periodet", period.getPeriodET());
        return map;
    }
}
