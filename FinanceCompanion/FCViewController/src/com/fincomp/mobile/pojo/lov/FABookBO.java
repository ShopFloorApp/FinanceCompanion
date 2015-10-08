package com.fincomp.mobile.pojo.lov;

import java.util.HashMap;

import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class FABookBO {
    private String BookType;
    private String Name;
    private String Entity;
    private String Owner;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public FABookBO() {
        super();
    }

    public void setBookType(String BookType) {
        String oldBookType = this.BookType;
        this.BookType = BookType;
        propertyChangeSupport.firePropertyChange("BookType", oldBookType, BookType);
    }

    public String getBookType() {
        return BookType;
    }

    public void setName(String Name) {
        String oldName = this.Name;
        this.Name = Name;
        propertyChangeSupport.firePropertyChange("Name", oldName, Name);
    }

    public String getName() {
        return Name;
    }

    public void setEntity(String Entity) {
        String oldEntity = this.Entity;
        this.Entity = Entity;
        propertyChangeSupport.firePropertyChange("Entity", oldEntity, Entity);
    }

    public String getEntity() {
        return Entity;
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
        this.setBookType((String) hashMap.get("booktype"));
        this.setName((String) hashMap.get("name"));
        this.setEntity((String) hashMap.get("entity"));
        this.setOwner((String) hashMap.get("owner"));
    }

    public HashMap getBOClassRow(FABookBO faBook) {
        HashMap map = new HashMap();
        map.put("booktype", faBook.getBookType());
        map.put("name", faBook.getName());
        map.put("entity", faBook.getEntity());
        map.put("owner", faBook.getOwner());
        return map;
    }
}
