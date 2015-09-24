package com.fincomp.mobile.utility;


import com.fincomp.application.database.ConnectionFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.framework.api.GenericTypeBeanSerializationHelper;
import oracle.adfmf.util.GenericType;
import oracle.adfmf.util.GenericVirtualType;
import oracle.adfmf.util.Utility;

public class SyncUtils {
    private static final String NOT_REACHABLE = "NotReachable"; // Indiates no network connectivity

    protected static GenericVirtualType getTransactionHeader() {
        GenericVirtualType transactionHeader = new GenericVirtualType(null, "TransactionHeader");
        transactionHeader.defineAttribute(null, "CallingInterfaceName", String.class, "1");
        transactionHeader.defineAttribute(null, "CallingSystemName", String.class, "1");
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date lastSyncDate = new Date();
        String date = DATE_FORMAT.format(lastSyncDate);
        transactionHeader.defineAttribute(null, "LastSyncDateTime", Object.class, date);
        transactionHeader.defineAttribute(null, "RoleName", String.class, "1");
        transactionHeader.defineAttribute(null, "UserIdentifier", String.class, "1");

        //Fetching Status
        GenericVirtualType status = new GenericVirtualType(null, "Status");
        status.defineAttribute(null, "Code", String.class, "1");
        status.defineAttribute(null, "Msg", String.class, "1");

        transactionHeader.defineAttribute(null, "Status", GenericType.class, status);

        return transactionHeader;
    }


    protected List getCollection(Class collectionClass, HashMap params) {
        String networkStatus =
            (String) AdfmfJavaUtilities.evaluateELExpression("#{deviceScope.hardware.networkStatus}");
        List collections;
        if (networkStatus.equals(NOT_REACHABLE)) {
            System.out.println("In not reachable");
            collections = getCollectionFromDB(collectionClass);
        } else {
            System.out.println("Getting from Warehouse web service");
            collections = getCollectionFromWS(collectionClass, params);
            updateSqlLiteTable(collectionClass, collections);
        }
        return collections;
    }

    protected List getOfflineCollection(Class collectionClass) {
        List collections;
        collections = getCollectionFromDB(collectionClass);
        return collections;
    }

    protected List getOnlineCollection(Class collectionClass, HashMap params) {
        List collections;
        collections = getCollectionFromWS(collectionClass, params);
        return collections;
    }

    public List getCollectionFromDB(Class collectionClass) {
        Connection conn = null;
        List returnValue = new ArrayList();

        try {
            conn = ConnectionFactory.getConnection();

            Statement stmt = conn.createStatement();


            StringBuffer query = new StringBuffer();
            query.append("SELECT ");

            Field[] fields = collectionClass.getDeclaredFields();

            StringBuffer q1 = new StringBuffer();
            for (int i = 0; i < fields.length; i++) {

                if ((fields[i].getName().trim().equalsIgnoreCase("attributes")) ||
                    (fields[i].getName().trim().equalsIgnoreCase("propertyChangeSupport")) ||
                    (fields[i].getName().trim().equalsIgnoreCase("rowIdx")) ||
                    (fields[i].getName().trim().equalsIgnoreCase("idx"))) {
                    continue;
                }
                q1.append(fields[i].getName().toUpperCase() + ",");
            }
            String q2 = q1.substring(0, q1.length() - 1);
            String tableName = collectionClass.getName();
            String tabName = tableName.substring(tableName.lastIndexOf(".") + 1, tableName.length() - 2);
            query.append(q2 + " FROM " + tabName.toUpperCase() + ";");
            ResultSet result = stmt.executeQuery(query.toString());
            while (result.next()) {
                System.out.println("inside first while");
                HashMap map = new HashMap();
                for (int i = 0; i < fields.length; i++) {
                    if ((fields[i].getName().trim().equalsIgnoreCase("attributes")) ||
                        (fields[i].getName().trim().equalsIgnoreCase("propertyChangeSupport")) ||
                        (fields[i].getName().trim().equalsIgnoreCase("rowIdx")) ||
                        (fields[i].getName().trim().equalsIgnoreCase("idx"))) {
                        continue;
                    }
                    System.out.println("adding rows to hashmap");
                    map.put(fields[i].getName().toLowerCase(), result.getObject(fields[i].getName()));
                }
                System.out.println("before invoking the method");
                Object obj = collectionClass.newInstance();
                Method method = collectionClass.getMethod("setBOClassRow", new Class[] { HashMap.class });
                method.invoke(obj, new Object[] { map });
                System.out.println("after invoking");
                Iterator entries = map.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry entry = (Map.Entry) entries.next();
                    String key = (String) entry.getKey();
                    String value = null;
                    if (entry.getValue() != null) {
                        if (entry.getValue().getClass().toString().contains("Integer")) {
                            value = Integer.toString((Integer) entry.getValue());
                        } else {
                            value = (String) entry.getValue();
                        }
                    }
                    System.out.println("Key = " + key + ", Value = " + value);
                }
                System.out.println("after iterating hashmap");
                returnValue.add(obj);
            }

        } catch (Exception ex) {
            Utility.ApplicationLogger.severe(ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return returnValue;
    }

    public List getCollectionFromWS(Class collectionClass, HashMap params) {

        System.out.println("Inside getWarehousesFromWS");
        GenericVirtualType payload = (GenericVirtualType) params.get("payload");
        String lovDCName = (String) params.get("lovDCName");
        String opeartionName = (String) params.get("opeartionName");
        String resAttriName = (String) params.get("resAttriName");

        //Fetching Transaction header
        GenericVirtualType transactionHeader = getTransactionHeader();
        payload.defineAttribute(null, "TransactionHeader", GenericType.class, transactionHeader);

        List pnames = new ArrayList();
        List pvals = new ArrayList();
        List ptypes = new ArrayList();
        List collection = new ArrayList();

        pnames.add("payload");
        ptypes.add(GenericType.class);
        pvals.add(payload);
        System.out.println("Before Webservice call");
        try {
            Object obj = collectionClass.newInstance();
            GenericType genericReturnValue =
                (GenericType) AdfmfJavaUtilities.invokeDataControlMethod(lovDCName, null, opeartionName, pnames, pvals,
                                                                         ptypes);
            GenericType respListType = (GenericType) genericReturnValue.getAttribute(resAttriName);

            for (int i = 0; i < respListType.getAttributeCount(); i++) {
                // Get each individual GenericType instance that holds the attribute key-value pairs of department
                GenericType entityGenericType = (GenericType) respListType.getAttribute(i);
                // Now create Department instance out of this GenericType
                // this works fine if payload attr names match department attr names

                obj = GenericTypeBeanSerializationHelper.fromGenericType(collectionClass, entityGenericType);
                collection.add(obj);
            }
            return collection;

        } catch (Exception aie) {
            throw new RuntimeException(aie);
        }
    }

    public void updateSqlLiteTable(Class collectionClass, List collections) {
        Connection conn = null;

        String tableName = collectionClass.getName();
        String tabName = tableName.substring(tableName.lastIndexOf(".") + 1, tableName.length() - 2);

        try {
            conn = ConnectionFactory.getConnection();

            Statement stmt = conn.createStatement();

            stmt.executeQuery("DELETE FROM " + tabName.toUpperCase() + ";");

            Field[] fields = collectionClass.getDeclaredFields();

            StringBuffer query = new StringBuffer();
            query.append("INSERT INTO " + tabName.toUpperCase() + " (");

            StringBuffer fieldNames = new StringBuffer();
            for (int i = 0; i < fields.length; i++) {

                if ((fields[i].getName().trim().equalsIgnoreCase("attributes")) ||
                    (fields[i].getName().trim().equalsIgnoreCase("propertyChangeSupport")) ||
                    (fields[i].getName().trim().equalsIgnoreCase("rowIdx")) ||
                    (fields[i].getName().trim().equalsIgnoreCase("idx"))) {
                    continue;
                }
                fieldNames.append(fields[i].getName().toUpperCase() + ",");
            }
            String fieldNamesStr = fieldNames.substring(0, fieldNames.length() - 1);

            query.append(fieldNamesStr + " ) VALUES ( ");

            System.out.println("after resultset logic query is " + query);
            Object obj = collectionClass.newInstance();
            for (int i = 0; i < collections.size(); i++) {
                stmt = conn.createStatement();
                StringBuffer insertQueryValues = new StringBuffer();
                System.out.println("row is " + collections.get(i));
                System.out.println("row class is " + collections.get(i).getClass());
                Method method = collectionClass.getMethod("getBOClassRow", new Class[] { collectionClass });
                System.out.println("invoking the method and getting the output");
                HashMap pojoRowMap = (HashMap) method.invoke(obj, new Object[] { collections.get(i) });

                for (int j = 0; j < fields.length; j++) {
                    if ((fields[j].getName().trim().equalsIgnoreCase("attributes")) ||
                        (fields[j].getName().trim().equalsIgnoreCase("propertyChangeSupport")) ||
                        (fields[j].getName().trim().equalsIgnoreCase("rowIdx")) ||
                        (fields[j].getName().trim().equalsIgnoreCase("idx"))) {
                        continue;
                    }
                    System.out.println("getting row from hashmap");
                    String columnValue = (String) pojoRowMap.get(fields[j].getName().toLowerCase()); //
                    System.out.println("field name is " + fields[j].getName().toLowerCase());

                    insertQueryValues.append("'" + columnValue + "'" + ",");
                }
                System.out.println("insert value is " + insertQueryValues);
                String valuesStr = insertQueryValues.substring(0, insertQueryValues.length() - 1);
                String finalQuery = query.toString() + valuesStr + ");";
                System.out.println("insert query is " + finalQuery);
                stmt.execute(finalQuery);

            }

        } catch (Exception ex) {
            Utility.ApplicationLogger.severe(ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }


    public boolean insertSqlLiteTable(Class collectionClass, List collections) {
        Connection conn = null;
        boolean update = false;
        String tableName = collectionClass.getName();
        String tabName = tableName.substring(tableName.lastIndexOf(".") + 1, tableName.length() - 2);

        try {
            conn = ConnectionFactory.getConnection();

            Statement stmt = conn.createStatement();

            //stmt.executeQuery("DELETE FROM " + tabName.toUpperCase() + ";");

            Field[] fields = collectionClass.getDeclaredFields();

            StringBuffer query = new StringBuffer();
            query.append("INSERT INTO " + tabName.toUpperCase() + " (");

            StringBuffer fieldNames = new StringBuffer();
            for (int i = 0; i < fields.length; i++) {

                if ((fields[i].getName().trim().equalsIgnoreCase("attributes")) ||
                    (fields[i].getName().trim().equalsIgnoreCase("propertyChangeSupport")) ||
                    (fields[i].getName().trim().equalsIgnoreCase("rowIdx")) ||
                    (fields[i].getName().trim().equalsIgnoreCase("idx"))) {
                    continue;
                }
                fieldNames.append(fields[i].getName().toUpperCase() + ",");
            }
            String fieldNamesStr = fieldNames.substring(0, fieldNames.length() - 1);

            query.append(fieldNamesStr + " ) VALUES ( ");

            System.out.println("after resultset logic query is " + query);
            Object obj = collectionClass.newInstance();
            for (int i = 0; i < collections.size(); i++) {
                stmt = conn.createStatement();
                StringBuffer insertQueryValues = new StringBuffer();
                System.out.println("row is " + collections.get(i));
                System.out.println("row class is " + collections.get(i).getClass());
                Method method = collectionClass.getMethod("getBOClassRow", new Class[] { collectionClass });
                System.out.println("invoking the method and getting the output");
                HashMap pojoRowMap = (HashMap) method.invoke(obj, new Object[] { collections.get(i) });

                for (int j = 0; j < fields.length; j++) {
                    if ((fields[j].getName().trim().equalsIgnoreCase("attributes")) ||
                        (fields[j].getName().trim().equalsIgnoreCase("propertyChangeSupport")) ||
                        (fields[j].getName().trim().equalsIgnoreCase("rowIdx")) ||
                        (fields[j].getName().trim().equalsIgnoreCase("idx"))) {
                        continue;
                    }
                    System.out.println("getting row from hashmap");
                    String columnValue = null;

                    if (pojoRowMap.get(fields[j].getName().toLowerCase()) != null) {


                        if (pojoRowMap.get(fields[j].getName().toLowerCase()).getClass().toString().contains("Integer")) {
                            columnValue = Integer.toString((Integer) pojoRowMap.get(fields[j].getName().toLowerCase()));
                        } else {
                            columnValue = (String) pojoRowMap.get(fields[j].getName().toLowerCase());
                        }
                    }
                    System.out.println("field name is " + fields[j].getName().toLowerCase());

                    insertQueryValues.append("'" + columnValue + "'" + ",");
                }
                System.out.println("insert value is " + insertQueryValues);
                String valuesStr = insertQueryValues.substring(0, insertQueryValues.length() - 1);
                String finalQuery = query.toString() + valuesStr + ");";
                System.out.println("insert query is " + finalQuery);
                int updateCount = stmt.executeUpdate(finalQuery);
                if (updateCount > 0) {
                    update = true;
                }


            }
            return update;

        } catch (Exception ex) {
            Utility.ApplicationLogger.severe(ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    
    public void updateSqlLiteTableWithWhere(Class collectionClass, List collections, HashMap whereClause) {
        Connection conn = null;
        
        StringBuffer where = new StringBuffer();
        Iterator filterWhereClause = whereClause.entrySet().iterator();

        while (filterWhereClause.hasNext()) {
            Map.Entry entry = (Map.Entry) filterWhereClause.next();

            String key = (String) entry.getKey();
            System.out.println(" column name is " + key);
            // String filtervalue = (String) entry.getValue();
            String filtervalue = null;
            if (entry.getValue().getClass().toString().contains("Integer")) {
                filtervalue = Integer.toString((Integer) entry.getValue());
            } else {
                filtervalue = (String) entry.getValue();
            }
            where.append(key.toUpperCase() + " = " + filtervalue + " AND ");
        }
        String queryStmt = where.substring(0, where.length() - 4)+ " ;";
        
        String tableName = collectionClass.getName();
        String tabName = tableName.substring(tableName.lastIndexOf(".") + 1, tableName.length() - 2);

        try {
            conn = ConnectionFactory.getConnection();

            Statement stmt = conn.createStatement();

            stmt.executeQuery("DELETE FROM " + tabName.toUpperCase()  + " WHERE " +queryStmt);

            Field[] fields = collectionClass.getDeclaredFields();

            StringBuffer query = new StringBuffer();
            query.append("INSERT INTO " + tabName.toUpperCase() + " (");

            StringBuffer fieldNames = new StringBuffer();
            for (int i = 0; i < fields.length; i++) {

                if ((fields[i].getName().trim().equalsIgnoreCase("attributes")) ||
                    (fields[i].getName().trim().equalsIgnoreCase("propertyChangeSupport")) ||
                    (fields[i].getName().trim().equalsIgnoreCase("rowIdx")) ||
                    (fields[i].getName().trim().equalsIgnoreCase("idx"))) {
                    continue;
                }
                fieldNames.append(fields[i].getName().toUpperCase() + ",");
            }
            String fieldNamesStr = fieldNames.substring(0, fieldNames.length() - 1);

            query.append(fieldNamesStr + " ) VALUES ( ");

            System.out.println("after resultset logic query is " + query);
            Object obj = collectionClass.newInstance();
            for (int i = 0; i < collections.size(); i++) {
                stmt = conn.createStatement();
                StringBuffer insertQueryValues = new StringBuffer();
                System.out.println("row is " + collections.get(i));
                System.out.println("row class is " + collections.get(i).getClass());
                Method method = collectionClass.getMethod("getBOClassRow", new Class[] { collectionClass });
                System.out.println("invoking the method and getting the output");
                HashMap pojoRowMap = (HashMap) method.invoke(obj, new Object[] { collections.get(i) });
                String columnValue = null;

                for (int j = 0; j < fields.length; j++) {
                    if ((fields[j].getName().trim().equalsIgnoreCase("attributes")) ||
                        (fields[j].getName().trim().equalsIgnoreCase("propertyChangeSupport")) ||
                        (fields[j].getName().trim().equalsIgnoreCase("rowIdx")) ||
                        (fields[j].getName().trim().equalsIgnoreCase("idx"))) {
                        continue;
                    }
                    System.out.println("getting row from hashmap");
                    if (pojoRowMap.get(fields[j].getName().toLowerCase()) != null) {
                    if (pojoRowMap.get(fields[j].getName().toLowerCase()).getClass().toString().contains("Integer")) {
                        columnValue = Integer.toString((Integer) pojoRowMap.get(fields[j].getName().toLowerCase()));
                    } else {
                        columnValue = (String) pojoRowMap.get(fields[j].getName().toLowerCase());
                    }
                    }else{
                        columnValue = "";
                    }
                   // String columnValue = (String) pojoRowMap.get(fields[j].getName().toLowerCase()); //
                    System.out.println("field name is " + fields[j].getName().toLowerCase());

                    insertQueryValues.append("'" + columnValue + "'" + ",");
                }
                System.out.println("insert value is " + insertQueryValues);
                String valuesStr = insertQueryValues.substring(0, insertQueryValues.length() - 1);
                String finalQuery = query.toString() + valuesStr + ");";
                System.out.println("insert query is " + finalQuery);
                stmt.execute(finalQuery);

            }

        } catch (Exception ex) {
            Utility.ApplicationLogger.severe(ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public boolean deleteSqlLiteTable(Class collectionClass, HashMap whereClause) {

        Connection conn = null;
        boolean update = false;
        try {

            conn = ConnectionFactory.getConnection();

            Statement stmt = conn.createStatement();

            System.out.println("Inside deleteSQLLiteTable");
            String tableName = collectionClass.getName();
            String tabName = tableName.substring(tableName.lastIndexOf(".") + 1, tableName.length() - 2);

            StringBuffer query = new StringBuffer();
            query.append("DELETE FROM " + tabName.toUpperCase() + " WHERE ");
            StringBuffer where = new StringBuffer();
            Iterator filterWhereClause = whereClause.entrySet().iterator();

            while (filterWhereClause.hasNext()) {
                Map.Entry entry = (Map.Entry) filterWhereClause.next();

                String key = (String) entry.getKey();
                System.out.println(" column name is " + key);
                // String filtervalue = (String) entry.getValue();
                String filtervalue = null;
                if (entry.getValue().getClass().toString().contains("Integer")) {
                    filtervalue = Integer.toString((Integer) entry.getValue());
                } else {
                    filtervalue = (String) entry.getValue();
                }
                where.append(key.toUpperCase() + " = " + filtervalue + " AND ");
            }
            String queryStmt = where.substring(0, where.length() - 4);
            String finalQuery = query.toString() + queryStmt + ";";
            stmt.executeQuery(finalQuery);
            return update;


        } catch (Exception aie) {
            throw new RuntimeException(aie);
        }
    }

    public String toCamelCase(String s) {
        String finalString = s.substring(0, 1).toUpperCase() + s.substring(1);
        return finalString;

    }

    public List getFileteredCollection(Class classPOJO, HashMap params) {
        List filteredRows = new ArrayList();
        try {
            System.out.println("code inside try");
            List allRowsCollection = (List) params.get("collection");
            HashMap filterFieldsValues = (HashMap) params.get("filterFieldsValues");
            String filter = "";
            int fieldsCount = 0;
            Iterator entriesFields = filterFieldsValues.entrySet().iterator();
            while (entriesFields.hasNext()) {
                Map.Entry entry = (Map.Entry) entriesFields.next();
                // String value = (String) entry.getValue();
                String value = null;
                if (entry.getValue().getClass().toString().contains("Integer")) {
                    value = Integer.toString((Integer) entry.getValue());
                } else {
                    value = (String) entry.getValue();
                }
                filter = filter + value;
                fieldsCount = fieldsCount + 1;
            }
            System.out.println("filter text is " + filter + " and length is " + filter.length());
            boolean nofilter = (filter.length() == 0);


            Object obj = classPOJO.newInstance();
            for (int x = 0; x < allRowsCollection.size(); x++) {
                System.out.println("inside for code");

                if (nofilter) {
                    filteredRows.add(allRowsCollection.get(x));
                } else {
                    System.out.println("inside else condition");
                    Method method = classPOJO.getMethod("getBOClassRow", new Class[] { classPOJO });
                    System.out.println("invoking the method and getting the output");
                    HashMap pojoRowMap = (HashMap) method.invoke(obj, new Object[] { allRowsCollection.get(x) });
                    int i = 0;
                    Iterator filterFields = filterFieldsValues.entrySet().iterator();
                    while (filterFields.hasNext()) {
                        Map.Entry entry = (Map.Entry) filterFields.next();
                        String key = (String) entry.getKey();
                        System.out.println("filter column name is " + key);
                        // String filtervalue = (String) entry.getValue();
                        String filtervalue = null;
                        if (entry.getValue().getClass().toString().contains("Integer")) {
                            filtervalue = Integer.toString((Integer) entry.getValue());
                        } else {
                            filtervalue = (String) entry.getValue();
                        }
                        System.out.println("filter value for column " + key + " is " + filtervalue);
                        //String attribValue = (String) pojoRowMap.get(key);
                        String attribValue = null;
                        if (pojoRowMap.get(key).getClass().toString().contains("Integer")) {
                            attribValue = Integer.toString((Integer) pojoRowMap.get(key));
                        } else {
                            attribValue = (String) pojoRowMap.get(key);
                        }
                        System.out.println("column row value is " + attribValue);
                        if (attribValue.indexOf(filtervalue) != -1 || attribValue == null || attribValue.equals("")) {
                            System.out.println("inside if of while");
                            i = i + 1;
                        }
                    }
                    if (i == fieldsCount) {
                        System.out.println("inside if..row is filtered");
                        filteredRows.add(allRowsCollection.get(x));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return filteredRows;
    }

    public List getFilteredCollectionFromDB(Class collectionClass, String whereClause) {
        Connection conn = null;
        List returnValue = new ArrayList();

        try {
            conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            StringBuffer query = new StringBuffer();
            query.append("SELECT ");

            Field[] fields = collectionClass.getDeclaredFields();

            StringBuffer q1 = new StringBuffer();
            for (int i = 0; i < fields.length; i++) {

                if ((fields[i].getName().trim().equalsIgnoreCase("attributes")) ||
                    (fields[i].getName().trim().equalsIgnoreCase("propertyChangeSupport")) ||
                    (fields[i].getName().trim().equalsIgnoreCase("rowIdx")) ||
                    (fields[i].getName().trim().equalsIgnoreCase("idx"))) {
                    continue;
                }
                q1.append(fields[i].getName().toUpperCase() + ",");
            }
            String q2 = q1.substring(0, q1.length() - 1);
            String tableName = collectionClass.getName();
            String tabName = tableName.substring(tableName.lastIndexOf(".") + 1, tableName.length() - 2);
            query.append(q2 + " FROM " + tabName.toUpperCase() + " " + whereClause + ";");
            ResultSet result = stmt.executeQuery(query.toString());
            while (result.next()) {
                System.out.println("inside first while");
                HashMap map = new HashMap();
                for (int i = 0; i < fields.length; i++) {
                    if ((fields[i].getName().trim().equalsIgnoreCase("attributes")) ||
                        (fields[i].getName().trim().equalsIgnoreCase("propertyChangeSupport")) ||
                        (fields[i].getName().trim().equalsIgnoreCase("rowIdx")) ||
                        (fields[i].getName().trim().equalsIgnoreCase("idx"))) {
                        continue;
                    }
                    System.out.println("adding rows to hashmap");
                    map.put(fields[i].getName().toLowerCase(), result.getObject(fields[i].getName()));
                }
                System.out.println("before invoking the method");
                Object obj = collectionClass.newInstance();
                Method method = collectionClass.getMethod("setBOClassRow", new Class[] { HashMap.class });
                method.invoke(obj, new Object[] { map });
                System.out.println("after invoking");
                Iterator entries = map.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry entry = (Map.Entry) entries.next();
                    String key = (String) entry.getKey();
                    String value = null;
                    if (entry.getValue() != null) {
                        if (entry.getValue().getClass().toString().contains("Integer")) {
                            value = Integer.toString((Integer) entry.getValue());
                        } else {
                            value = (String) entry.getValue();
                        }
                    }
                    System.out.println("Key = " + key + ", Value = " + value);
                }
                System.out.println("after iterating hashmap");
                returnValue.add(obj);
            }

        } catch (Exception ex) {
            Utility.ApplicationLogger.severe(ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return returnValue;
    }
}
