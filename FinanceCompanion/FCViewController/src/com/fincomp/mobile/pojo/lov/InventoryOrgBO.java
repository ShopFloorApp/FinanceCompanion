package com.fincomp.mobile.pojo.lov;

import java.util.HashMap;

import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class InventoryOrgBO {
    private String OrgName;
    private String OrgKey;
    private String OUkey;
    private String Location;
    private String Address1;
    private String City;
    private String State;
    private String County;
    private String Zip;
    private String Country;
    private String Owner;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public InventoryOrgBO() {
        super();
    }

    public void setOrgName(String OrgName) {
        String oldOrgName = this.OrgName;
        this.OrgName = OrgName;
        propertyChangeSupport.firePropertyChange("OrgName", oldOrgName, OrgName);
    }

    public String getOrgName() {
        return OrgName;
    }

    public void setOrgKey(String OrgKey) {
        String oldOrgKey = this.OrgKey;
        this.OrgKey = OrgKey;
        propertyChangeSupport.firePropertyChange("OrgKey", oldOrgKey, OrgKey);
    }

    public String getOrgKey() {
        return OrgKey;
    }

    public void setOUkey(String OUkey) {
        String oldOUkey = this.OUkey;
        this.OUkey = OUkey;
        propertyChangeSupport.firePropertyChange("OUkey", oldOUkey, OUkey);
    }

    public String getOUkey() {
        return OUkey;
    }

    public void setLocation(String Location) {
        String oldLocation = this.Location;
        this.Location = Location;
        propertyChangeSupport.firePropertyChange("Location", oldLocation, Location);
    }

    public String getLocation() {
        return Location;
    }

    public void setAddress1(String Address1) {
        String oldAddress1 = this.Address1;
        this.Address1 = Address1;
        propertyChangeSupport.firePropertyChange("Address1", oldAddress1, Address1);
    }

    public String getAddress1() {
        return Address1;
    }

    public void setCity(String City) {
        String oldCity = this.City;
        this.City = City;
        propertyChangeSupport.firePropertyChange("City", oldCity, City);
    }

    public String getCity() {
        return City;
    }

    public void setState(String State) {
        String oldState = this.State;
        this.State = State;
        propertyChangeSupport.firePropertyChange("State", oldState, State);
    }

    public String getState() {
        return State;
    }

    public void setCounty(String County) {
        String oldCounty = this.County;
        this.County = County;
        propertyChangeSupport.firePropertyChange("County", oldCounty, County);
    }

    public String getCounty() {
        return County;
    }

    public void setZip(String Zip) {
        String oldZip = this.Zip;
        this.Zip = Zip;
        propertyChangeSupport.firePropertyChange("Zip", oldZip, Zip);
    }

    public String getZip() {
        return Zip;
    }

    public void setCountry(String Country) {
        String oldCountry = this.Country;
        this.Country = Country;
        propertyChangeSupport.firePropertyChange("Country", oldCountry, Country);
    }

    public String getCountry() {
        return Country;
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
        this.setAddress1((String) hashMap.get("address1"));
        this.setCity((String) hashMap.get("city"));
        this.setCountry((String) hashMap.get("country"));
        this.setCounty((String) hashMap.get("county"));
        this.setLocation((String) hashMap.get("location"));
        this.setOUkey((String) hashMap.get("oukey"));
        this.setOrgKey((String) hashMap.get("orgkey"));
        this.setOrgName((String) hashMap.get("orgname"));
        this.setOwner((String) hashMap.get("owner"));
        this.setState((String) hashMap.get("state"));
        this.setZip((String) hashMap.get("zip"));
    }

    public HashMap getBOClassRow(InventoryOrgBO inventoryOrg) {
        HashMap map = new HashMap();
        map.put("address1", inventoryOrg.getAddress1());
        map.put("city", inventoryOrg.getCity());
        map.put("country", inventoryOrg.getCountry());
        map.put("county", inventoryOrg.getCounty());
        map.put("location", inventoryOrg.getLocation());
        map.put("oukey", inventoryOrg.getOUkey());
        map.put("orgkey", inventoryOrg.getOrgKey());
        map.put("orgname", inventoryOrg.getOrgName());
        map.put("owner", inventoryOrg.getOwner());
        map.put("state", inventoryOrg.getState());
        map.put("zip", inventoryOrg.getZip());
        return map;
    }
}
