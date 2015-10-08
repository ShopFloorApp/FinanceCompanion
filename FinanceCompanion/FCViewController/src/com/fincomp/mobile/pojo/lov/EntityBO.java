package com.fincomp.mobile.pojo.lov;

import java.util.HashMap;

import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class EntityBO {
    private String Name;
    private String Country;
    private String Ledger;
    private String Owner;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public EntityBO() {
        super();
    }

    public void setName(String Name) {
        String oldName = this.Name;
        this.Name = Name;
        propertyChangeSupport.firePropertyChange("Name", oldName, Name);
    }

    public String getName() {
        return Name;
    }

    public void setCountry(String Country) {
        String oldCountry = this.Country;
        this.Country = Country;
        propertyChangeSupport.firePropertyChange("Country", oldCountry, Country);
    }

    public String getCountry() {
        return Country;
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
        this.setCountry((String) hashMap.get("country"));
        this.setName((String) hashMap.get("name"));
        this.setLedger((String) hashMap.get("ledger"));
        this.setOwner((String) hashMap.get("owner"));
    }

    public HashMap getBOClassRow(EntityBO entity) {
        HashMap map = new HashMap();
        map.put("country", entity.getCountry());
        map.put("name", entity.getName());
        map.put("ledger", entity.getLedger());
        map.put("owner", entity.getOwner());
        return map;
    }
}
