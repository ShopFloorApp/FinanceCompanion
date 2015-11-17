package com.fincomp.mobile.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

public class AEntity {
    public AEntity() {
        super();
    }

    public String getAttributeValue(String value) {
        if (value.contains("@xsi")) {
            return "";
        }
        return value;
    }
    public String getAttributeValueInt(String value) {
        if (value.contains("@xsi")) {
            return "0";
        }
        return value;
    }

    protected String toUnixDate(String value) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat intialFormat = new SimpleDateFormat("dd, MMM yyyy HH:mm:ss");
        String newDate = null;
        try {
            Date date = intialFormat.parse(value);
            newDate = formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }
    
    protected String toDate(String value){
        SimpleDateFormat intialFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat newFormat = new SimpleDateFormat("dd, MMM yyyy HH:mm:ss");
        String newDate = null;
        try {
            Date date = intialFormat.parse(value);
            newDate = newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }
    protected String toDateTime(String value){
        SimpleDateFormat intialFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newDate = null;
        try {
            Date date = intialFormat.parse(value);
            newDate = newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }
}
