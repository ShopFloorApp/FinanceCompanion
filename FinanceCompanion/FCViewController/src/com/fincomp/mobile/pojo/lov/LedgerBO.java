package com.fincomp.mobile.pojo.lov;

import java.util.HashMap;

import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class LedgerBO {
    
    private String Ledger;
    private String LedgerKey;
    private String LedgerCat;
    private String Currency;
    private String Coa;
    private String Owner;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public LedgerBO() {
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

    public void setLedgerKey(String LedgerKey) {
        String oldLedgerKey = this.LedgerKey;
        this.LedgerKey = LedgerKey;
        propertyChangeSupport.firePropertyChange("LedgerKey", oldLedgerKey, LedgerKey);
    }

    public String getLedgerKey() {
        return LedgerKey;
    }

    public void setLedgerCat(String LedgerCat) {
        String oldLedgerCat = this.LedgerCat;
        this.LedgerCat = LedgerCat;
        propertyChangeSupport.firePropertyChange("LedgerCat", oldLedgerCat, LedgerCat);
    }

    public String getLedgerCat() {
        return LedgerCat;
    }

    public void setCurrency(String Currency) {
        String oldCurrency = this.Currency;
        this.Currency = Currency;
        propertyChangeSupport.firePropertyChange("Currency", oldCurrency, Currency);
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCoa(String Coa) {
        String oldCoa = this.Coa;
        this.Coa = Coa;
        propertyChangeSupport.firePropertyChange("Coa", oldCoa, Coa);
    }

    public String getCoa() {
        return Coa;
    }

    public void setOwner(String Owner) {
        String oldOwner = this.Owner;
        this.Owner = Owner;
        propertyChangeSupport.firePropertyChange("Owner", oldOwner, Owner);
    }

    public String getOwner() {
        return Owner;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }
    
    public void setBOClassRow(HashMap hashMap) {
        this.setLedger((String) hashMap.get("ledger"));
        this.setLedgerKey((String) hashMap.get("ledgerkey"));
        this.setLedgerCat((String) hashMap.get("ledgercat"));
        this.setOwner((String) hashMap.get("owner"));
        this.setCoa((String) hashMap.get("coa"));
        this.setCurrency((String) hashMap.get("currency"));
    }

    public HashMap getBOClassRow(LedgerBO ledger) {
        HashMap map = new HashMap();
        map.put("ledger", ledger.getLedger());
        map.put("ledgerkey", ledger.getLedgerKey());
        map.put("ledgercat", ledger.getLedgerCat());
        map.put("owner", ledger.getOwner());
        map.put("coa", ledger.getCoa());
        map.put("currency", ledger.getCurrency());
        return map;
    }
}
