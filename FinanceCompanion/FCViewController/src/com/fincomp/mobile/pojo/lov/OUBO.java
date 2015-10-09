package com.fincomp.mobile.pojo.lov;

import java.util.HashMap;

import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class OUBO {
    private String OU;
    private String OUKey;
    private String Entity;
    private String Ledger;
    private String Owner;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public OUBO() {
        super();
    }

    public void setOU(String OU) {
        String oldOU = this.OU;
        this.OU = OU;
        propertyChangeSupport.firePropertyChange("OU", oldOU, OU);
    }

    public String getOU() {
        return OU;
    }

    public void setOUKey(String OUKey) {
        String oldOUKey = this.OUKey;
        this.OUKey = OUKey;
        propertyChangeSupport.firePropertyChange("OUKey", oldOUKey, OUKey);
    }

    public String getOUKey() {
        return OUKey;
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
        this.setOU((String) hashMap.get("ou"));
        this.setOUKey((String) hashMap.get("oukey"));
        this.setEntity((String) hashMap.get("entity"));
        this.setLedger((String) hashMap.get("ledger"));
        this.setOwner((String) hashMap.get("owner"));
    }

    public HashMap getBOClassRow(OUBO ou) {
        HashMap map = new HashMap();
        map.put("ou", ou.getOU());
        map.put("oukey", ou.getOUKey());
        map.put("entity", ou.getEntity());
        map.put("ledger", ou.getLedger());
        map.put("owner", ou.getOwner());
        return map;
    }
}
