package com.fincomp.mobile.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.el.MethodExpression;

import oracle.adf.model.datacontrols.device.DeviceManagerFactory;
import oracle.adf.model.datacontrols.device.Location;

import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class GPSLocatorBean extends SyncUtils {
    public GPSLocatorBean() {
        super();
    }
    private boolean highAccuracy = true;
    private int updateInterval = 1000;
    private double latitude = 0;
    private double longitude = 0;
    private String watchId = "";
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void setHighAccuracy(boolean highAccuracy) {
        boolean oldHighAccuracy = this.highAccuracy;
        this.highAccuracy = highAccuracy;
        propertyChangeSupport.firePropertyChange("highAccuracy", oldHighAccuracy, highAccuracy);
    }

    public boolean isHighAccuracy() {
        return highAccuracy;
    }

    public void setUpdateInterval(int updateInterval) {
        int oldUpdateInterval = this.updateInterval;
        this.updateInterval = updateInterval;
        propertyChangeSupport.firePropertyChange("updateInterval", oldUpdateInterval, updateInterval);
    }

    public int getUpdateInterval() {
        return updateInterval;
    }

    public void setLatitude(double latitude) {
        double oldLatitude = this.latitude;
        this.latitude = latitude;
        propertyChangeSupport.firePropertyChange("latitude", oldLatitude, latitude);
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        double oldLongitude = this.longitude;
        this.longitude = longitude;
        propertyChangeSupport.firePropertyChange("longitude", oldLongitude, longitude);
    }

    public double getLongitude() {
        return longitude;
    }

    public void setWatchId(String watchId) {
        String oldWatchId = this.watchId;
        this.watchId = watchId;
        propertyChangeSupport.firePropertyChange("watchId", oldWatchId, watchId);
    }

    public String getWatchId() {
        return watchId;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }
    
    public void locationUpdated(Location currentLocation) {
        this.setLatitude(currentLocation.getLatitude());
        this.setLongitude(currentLocation.getLongitude());
        this.setWatchId(currentLocation.getWatchId());
    }
    
    public void stopLocationMonitor() {
        if (getWatchId().length() > 0) {
            DeviceManagerFactory.getDeviceManager().clearWatchPosition(getWatchId());
            setWatchId("");
        }
    }
    
    public final static double AVERAGE_RADIUS_OF_EARTH = 6371;
    public double calculateDistance(double userLat, double userLng,
      double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
          + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
          * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (double) (AVERAGE_RADIUS_OF_EARTH * c);
    }
    
    public HashMap getDefaultLocation(){
        MethodExpression me =
            AdfmfJavaUtilities.getMethodExpression("#{bindings.startLocationMonitor.execute}", Object.class, new Class[] {
                                                   });
        me.invoke(AdfmfJavaUtilities.getAdfELContext(), new Object[] { });
        double lat=this.getLatitude();
        double lng=this.getLongitude();
        if(lat==0.0){
            lat=Double.parseDouble("17.448019");
            lng=Double.parseDouble("78.376473");
        }
//        stopLocationMonitor();
        HashMap result=new HashMap();
//        ArrayList locs = (ArrayList) super.getFilteredCollectionFromDB(LocatorBO.class, "WHERE XDIM IS NOT NULL AND XDIM!=''");
//        for(int i=0;i<locs.size();i++){
//            LocatorBO loc = (LocatorBO) locs.get(i);
//            double dist=calculateDistance(lat,lng,Double.parseDouble(loc.getXDIM()),Double.parseDouble(loc.getYDIM()));
//            loc.setZDIM(Double.toString(dist));
//        }
//        Collections.sort(locs, new Comparator() {
//            public int compare(Object o1, Object o2) {
//                LocatorBO p1 = (LocatorBO) o1;
//                LocatorBO p2 = (LocatorBO) o2;
//                double a = Double.parseDouble(p1.getZDIM());
//                double b = Double.parseDouble(p2.getZDIM());
//                return Double.compare(a, b);                    
//            }
//        });
//        stopLocationMonitor();
//        result.put("subinv", ((LocatorBO)locs.get(0)).getSubinv());
//        result.put("locator", ((LocatorBO)locs.get(0)).getLocator());
        return result;
    }
}
