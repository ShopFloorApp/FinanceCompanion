package com.fincomp.mobile.pojo.sync;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;

import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class SyncPreferencesBO implements Comparable {
    private String lovId;
    private String lovName;
    private String lovDescription;
    private String syncCount;
    private String lastSyncDateTime;
    private String lovClassName;
    private String lovCollectVar;
    private String rowIdx;
    private static Integer idx=new Integer(0);
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public SyncPreferencesBO() {
        super();
    }
    public void setBOClassRow(HashMap hashMap) {
        this.setLovId((String) hashMap.get("lovid"));
        this.setLovName((String) hashMap.get("lovname"));
        this.setLovDescription((String) hashMap.get("lovdescription"));
        this.setSyncCount((String) hashMap.get("synccount"));
        this.setLastSyncDateTime((String) hashMap.get("lastsyncdatetime"));
        this.setLovClassName((String) hashMap.get("lovclassname"));
        this.setLovCollectVar((String) hashMap.get("lovcollectvar"));
    }

    public HashMap getBOClassRow(SyncPreferencesBO synpreference) {
        HashMap map = new HashMap();
        map.put("lovid", synpreference.getLovId());
        map.put("lovname", synpreference.getLovName());
        map.put("lovdescription", synpreference.getLovDescription());
        map.put("synccount", synpreference.getSyncCount());
        map.put("lastsyncdatetime", synpreference.getLastSyncDateTime());
        map.put("lovclassname", synpreference.getLovClassName());
        map.put("lovcollectvar", synpreference.getLovCollectVar());
        return map;
    }
    public void setRowIdx(String rowIdx) {
        String oldRowIdx = this.rowIdx;
        this.rowIdx = rowIdx;
        propertyChangeSupport.firePropertyChange("rowIdx", oldRowIdx, rowIdx);
    }

    public void setLovClassName(String lovClassName) {
        String oldLovClassName = this.lovClassName;
        this.lovClassName = lovClassName;
        propertyChangeSupport.firePropertyChange("lovClassName", oldLovClassName, lovClassName);
    }

    public String getLovClassName() {
        return lovClassName;
    }

    public void setLovCollectVar(String lovCollectVar) {
        String oldLovCollectVar = this.lovCollectVar;
        this.lovCollectVar = lovCollectVar;
        propertyChangeSupport.firePropertyChange("lovCollectVar", oldLovCollectVar, lovCollectVar);
    }

    public String getLovCollectVar() {
        return lovCollectVar;
    }

    public void setLovId(String lovId) {
        String oldLovId = this.lovId;
        this.lovId = lovId;
        propertyChangeSupport.firePropertyChange("lovId", oldLovId, lovId);
    }

    public void setLovDescription(String lovDescription) {
        String oldLovDescription = this.lovDescription;
        this.lovDescription = lovDescription;
        propertyChangeSupport.firePropertyChange("lovDescription", oldLovDescription, lovDescription);
    }

    public String getLovDescription() {
        return lovDescription;
    }

    public String getLovId() {
        return lovId;
    }

    public void setLovName(String lovName) {
        String oldLovName = this.lovName;
        this.lovName = lovName;
        propertyChangeSupport.firePropertyChange("lovName", oldLovName, lovName);
    }

    public String getLovName() {
        return lovName;
    }

    public void setSyncCount(String syncCount) {
        String oldSyncCount = this.syncCount;
        this.syncCount = syncCount;
        propertyChangeSupport.firePropertyChange("syncCount", oldSyncCount, syncCount);
    }

    public String getSyncCount() {
        return syncCount;
    }

    public void setLastSyncDateTime(String lastSyncDateTime) {
        String oldLastSyncDateTime = this.lastSyncDateTime;
        this.lastSyncDateTime = lastSyncDateTime;
        propertyChangeSupport.firePropertyChange("lastSyncDateTime", oldLastSyncDateTime, lastSyncDateTime);
    }

    public String getLastSyncDateTime() {
        try{
        SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String lastSyncDBDate=this.lastSyncDateTime;
        Date lastSyncDate=dbFormat.parse(lastSyncDBDate);
        SimpleDateFormat newFormat = new SimpleDateFormat("dd-MMM-yyyy");
        lastSyncDateTime=newFormat.format(lastSyncDate);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return lastSyncDateTime;
    }

    public String getRowIdx() {
        idx=new Integer(idx.intValue()+1);
        rowIdx=idx.toString();
        return rowIdx;
    }
    
    public int compareTo(Object o) {
        // TODO Implement this method
        return 0;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }
    

}
