PRAGMA foreign_keys = ON;
PRAGMA auto_vacuum  = FULL;

CREATE TABLE SYNCPREFERENCES(LOVID VARCHAR2(50) PRIMARY KEY,LOVNAME VARCHAR2(50),LOVDESCRIPTION VARCHAR2(200),SYNCCOUNT VARCHAR2(50),LASTSYNCDATETIME VARCHAR2(100),LOVCLASSNAME VARCHAR2(50),LOVCOLLECTVAR VARCHAR2(50));
INSERT INTO SYNCPREFERENCES(LOVID,LOVNAME,LOVDESCRIPTION,SYNCCOUNT,LASTSYNCDATETIME,LOVCLASSNAME,LOVCOLLECTVAR) VALUES('1','Entity','Entity Description','0','2014-12-01 14:00:00','EntityDC','s_entity');
INSERT INTO SYNCPREFERENCES(LOVID,LOVNAME,LOVDESCRIPTION,SYNCCOUNT,LASTSYNCDATETIME,LOVCLASSNAME,LOVCOLLECTVAR) VALUES('2','Period','Period Description','0','2014-12-02 14:17:00','PeriodDC','s_period');
INSERT INTO SYNCPREFERENCES(LOVID,LOVNAME,LOVDESCRIPTION,SYNCCOUNT,LASTSYNCDATETIME,LOVCLASSNAME,LOVCOLLECTVAR) VALUES('3','Ledger','Ledger Description','0','2014-12-02 14:15:00','LedgerDC','s_ledger');
INSERT INTO SYNCPREFERENCES(LOVID,LOVNAME,LOVDESCRIPTION,SYNCCOUNT,LASTSYNCDATETIME,LOVCLASSNAME,LOVCOLLECTVAR) VALUES('4','Inventory Org','Inventory Org Description','0','2014-12-02 14:10:00','InventoryOrgDC','s_inventoryOrg');
INSERT INTO SYNCPREFERENCES(LOVID,LOVNAME,LOVDESCRIPTION,SYNCCOUNT,LASTSYNCDATETIME,LOVCLASSNAME,LOVCOLLECTVAR) VALUES('5','OU','OU Description','0','2014-12-02 14:16:00','OUDC','s_ou');
INSERT INTO SYNCPREFERENCES(LOVID,LOVNAME,LOVDESCRIPTION,SYNCCOUNT,LASTSYNCDATETIME,LOVCLASSNAME,LOVCOLLECTVAR) VALUES('6','FA Book','FA Book Description','0','2014-12-01 14:10:00','FABookDC','s_fabook');

CREATE TABLE ENTITY (COUNTRY VARCHAR2(100),ENTITYNAME VARCHAR2(240) PRIMARY KEY, LEDGER VARCHAR2(240), OWNER VARCHAR2(240), EMAIL VARCHAR2(240), PHONE VARCHAR2(240));
CREATE TABLE FABOOK (BOOKTYPE VARCHAR2(100),FABOOKNAME VARCHAR2(240) PRIMARY KEY, ENTITY VARCHAR2(240), OWNER VARCHAR2(240));
CREATE TABLE OU (OU VARCHAR2(100),OUKEY VARCHAR2(240) , ENTITY VARCHAR2(240), OWNER VARCHAR2(240),LEDGER VARCHAR2(240));
CREATE TABLE INVENTORYORG (ORGNAME VARCHAR2(100),ORGKEY VARCHAR2(240), OUKEY VARCHAR2(240), LOCATION VARCHAR2(240),ADDRESS1 VARCHAR2(100),CITY VARCHAR2(240), STATE VARCHAR2(240), COUNTY VARCHAR2(240),ZIP VARCHAR2(100),COUNTRY VARCHAR2(240), OWNER VARCHAR2(240));
CREATE TABLE LEDGER (LEDGER VARCHAR2(100),LEDGERKEY VARCHAR2(240), LEDGERCAT VARCHAR2(240), CURRENCY VARCHAR2(240),COA VARCHAR2(100),OWNER VARCHAR2(240));
CREATE TABLE PERIOD (LEDGER VARCHAR2(100),PERIODNUM VARCHAR2(240), PERIODNAME VARCHAR2(240), PERIODYEAR VARCHAR2(240),PERIODSET VARCHAR2(100),PERIODST VARCHAR2(240), PERIODET VARCHAR2(240));