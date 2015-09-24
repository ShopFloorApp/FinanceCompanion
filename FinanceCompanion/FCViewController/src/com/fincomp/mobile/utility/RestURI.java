package com.fincomp.mobile.utility;


public class RestURI {
    public RestURI() {
        super();
    }
    private static final String ITEM_INQUIRY_URI = "/webservices/rest/DCOMInquiry/getitems/";

    private static final String PAYLOAD_HEADER =
        "\"RESTHeader\":{\"Responsibility\":\"ORDER_MGMT_SUPER_USER\",\"RespApplication\":\"ONT\",\"SecurityGroup\":\"STANDARD\",\"NLSLanguage\":\"AMERICAN\",\"Org_Id\":\"82\"}";
    
    public static String getPayloadHeader(){
        return PAYLOAD_HEADER;
    }
    public static String PostItemInquiryURI() {
        return ITEM_INQUIRY_URI;
    }


    private static final String PROCESS_INV_TRXN_URI = "/webservices/rest/DcomTrns/processinvtxn/";

    public static String PostInvTrxnURI() {
        return PROCESS_INV_TRXN_URI;
    }

    private static final String PROCESS_LPN_TRXN_URI = "/webservices/rest/DCOMLPN/processTxn/";

    public static String PostLpnTrxnURI() {
        return PROCESS_LPN_TRXN_URI;
    }

    private static final String GENERATE_LPN_URI = "/webservices/rest/DCOMLPN/generatelpn/";

    public static String GenerateLpnURI() {
        return GENERATE_LPN_URI;
    }

    private static final String ONHAND_INQUIRY_URI = "/webservices/rest/DCOMInquiry/getonhand/";

    public static String PostOnHandInquiryURI() {
        return ONHAND_INQUIRY_URI;
    }

    private static final String DELIVERY_INQUIRY_URI = "/webservices/rest/DCOMShip/getdelivery/";

    public static String PostDeliveryInquiryURI() {
        return DELIVERY_INQUIRY_URI;
    }

    private static final String QUICK_SHIP_URI = "/webservices/rest/DCOMShip/quickship/";

    public static String PostQuickShipURI() {
        return QUICK_SHIP_URI;
    }
    
    private static final String PACK_URI = "/webservices/rest/DCOMShip/pack/";
    
    public static String PostPackURI() {
        return PACK_URI;
    }
    
    private static final String DIRECT_SHIP_URI = "/webservices/rest/DCOMShip/directship/";
    
    public static String PostDirectShipURI() {
        return DIRECT_SHIP_URI;
    }

    private static final String MTLTXN_INQUIRY_URI = "/webservices/rest/DCOMInquiry/getmtltxn/";

    public static String PostMtlTxnInquiryURI() {
        return MTLTXN_INQUIRY_URI;
    }
    private static final String ITEM_CATEG_INQUIRY_URI = "/webservices/rest/DCOMInquiry/getitemcateg/";

    public static String PostItemCategInquiryURI() {
        return ITEM_CATEG_INQUIRY_URI;
    }

    private static final String ITEM_UDA_INQUIRY_URI = "/webservices/rest/DCOMInquiry/getitemuda/";

    public static String PostItemUdaInquiryURI() {
        return ITEM_UDA_INQUIRY_URI;
    }

    private static final String UNLOAD_TRUCK_URI = "/webservices/rest/DCOMShip/unloadtruck/";

    public static String PostUnloadTruckURI() {
        return UNLOAD_TRUCK_URI;
    }

    private static final String CYCLE_COUNT_URI = "/webservices/rest/DCOMCycleCntSvc/processcyclecount/";

    public static String PostCycleCountURI() {
        return CYCLE_COUNT_URI;
    }
    private static final String CYCLE_COUNT_SUBINV_URI = "/webservices/rest/DCOMCycleCntSvc/get_subinventory_in_cc/";

    public static String PostCycleCountSubinvURI() {
        return CYCLE_COUNT_SUBINV_URI;
    }
    
    private static final String CYCLE_COUNT_TASKS_URI = "/webservices/rest/DcomGetTaskDtls/get_task_details/";

    public static String PostCycleCountTasksURI() {
        return CYCLE_COUNT_TASKS_URI;
    }

    private static final String ProgramListURI = "/webservices/rest/DCOMSRS/getproglist/";
    private static final String ProgramParamsURI = "/webservices/rest/DCOMSRS/getprogparam/";
    private static final String ProgramParamLovURI = "/webservices/rest/DCOMSRS/getlov/";
    private static final String SubmitProgramURI = "/webservices/rest/DCOMSRS/submitprog/";
    private static final String RequestsURI = "/webservices/rest/DCOMSRS/getrequest/";
    private static final String fileDetailsURI = "/webservices/rest/DCOMSRS/getfile/";

    public static String PostProgramListURI() {
        return ProgramListURI;
    }
    
    public static String PostFileDetailsURI() {
        return fileDetailsURI;
    }

    public static String PostProgramParamsURI() {
        return ProgramParamsURI;
    }

    public static String PostProgramParamLovURI() {
        return ProgramParamLovURI;
    }

    public static String PostSubmitProgramURI() {
        return SubmitProgramURI;
    }

    public static String PostRequestsURI() {
        return RequestsURI;
    }
    private static final String LPN_DETAILS_URI = "/webservices/rest/DCOMInquiry/getlpn/";

    public static String PostLpnDetailsURI() {
        return LPN_DETAILS_URI;
    }

    private static final String DOCKDOOR_DETAILS_URI = "/webservices/rest/DCOMShip/getdockdoor/";

    public static String PostDockDoorDetailsURI() {
        return DOCKDOOR_DETAILS_URI;
    }

    private static final String TASK_DETAILS_URI = "/webservices/rest/DCOMTask/gettask/";

    public static String PostTaskDetailsURI() {
        return TASK_DETAILS_URI;
    }


    private static final String LPN_INQUIRY_URI = "/webservices/rest/DCOMShip/getlpn/";

    public static String PostLpnInquiryURI() {
        return LPN_INQUIRY_URI;

    }
    
    private static final String GET_PICK_URI = "/webservices/rest/DCOMShip/getpick/";

    public static String PostGetPickURI() {
        return GET_PICK_URI;

    }
    
    private static final String GET_INFO_URI = "/webservices/rest/DCOMShip/getinfo/";

    public static String PostGetInfoURI() {
        return GET_INFO_URI;

    }
    
    private static final String LOAD_PICK_URI = "/webservices/rest/DCOMShip/loadpick/";

    public static String PostLoadPickURI() {
        return LOAD_PICK_URI;

    }
    private static final String ITEM_DETAILS_URI = "/webservices/rest/DCOMInquiry/getitem/";

    public static String PostItemDetailsURI() {
        return ITEM_DETAILS_URI;
    }
    /*
     * Dispatch Module
     */
    private static final String GET_DEPT = "/webservices/rest/DCOMDispatchTxn/getdept/";

    public static String PostGetDeptURI() {
        return GET_DEPT;
    }

    private static final String GET_JOBOPS = "/webservices/rest/DCOMDispatchTxn/getjobops/";

    public static String PostGetJobOps() {
        return GET_JOBOPS;
    }

    private static final String GET_JOBOP = "/webservices/rest/DCOMDispatchTxn/getjobop/";

    public static String PostGetJobOp() {
        return GET_JOBOP;
    }
    private static final String GET_JOBPROP = "/webservices/rest/DCOMDispatchTxn/getjobdetails/";

    public static String PostGetJobProp() {
        return GET_JOBPROP;
    }

    private static final String GET_RESOURCES = "/webservices/rest/DCOMDispatchTxn/getjobresources/";

    public static String PostGetResources() {
        return GET_RESOURCES;
    }

    private static final String GET_COMPONENTS = "/webservices/rest/DCOMDispatchTxn/getjobcomponents/";

    public static String PostGetComponents() {
        return GET_COMPONENTS;
    }
    
    private static final String GET_INSTRUCTIONS = "/webservices/rest/DCOMDispatchTxn/getjobinstr/";

    public static String PostGetInstructions() {
        return GET_INSTRUCTIONS;
    }
    
    private static final String GET_BLOB = "/webservices/rest/getBlob/get_blob/";

    public static String PostGetBlobData() {
        return GET_BLOB;
    }
    private static final String GET_OPSEQ = "/webservices/rest/DCOMDispatchTxn/getopseq/";

    public static String PostGetOpSeq() {
        return GET_OPSEQ;
    }

    private static final String POST_JOB_ACTION = "/webservices/rest/DCOMDispatchTxn/jobaction/";

    public static String PostJobAction() {
        return POST_JOB_ACTION;
    }

    private static final String POST_REPORT_TIME = "/webservices/rest/DCOMDispatchTxn/report_time/";

    public static String PostReportTime() {
        return POST_REPORT_TIME;
    }
    private static final String POST_CLOCK_IN = "/webservices/rest/DCOMDispatchTxn/clockin/";

    public static String PostClockIn() {
        return POST_CLOCK_IN;
    }
    private static final String POST_CLOCK_OUT = "/webservices/rest/DCOMDispatchTxn/clockout/";

    public static String PostClockOut() {
        return POST_CLOCK_OUT;
    }

    private static final String POST_WIP_TRX = "/webservices/rest/DcomTrns/processwiptxn/";

    public static String PostWipTrx() {
        return POST_WIP_TRX;
    }

    private static final String UPLOAD_IMAGE = "/webservices/rest/XXDcomsInsertBlob/insert_blob/";

    public static String UploadImage() {
        return UPLOAD_IMAGE;
    }
    /*
     * Dispatch Lists end
     */

    private static final String GET_SALES_ORDER = "/webservices/rest/DCOMLOV/getsalesorder/";

    public static String PostGetSalesOrder() {
        return GET_SALES_ORDER;
    }

    private static final String LOT_NUMBERS_URI = "/webservices/rest/DCOMLOV/getlots/";

    public static String LotNumbers() {
        return LOT_NUMBERS_URI;
    }

    private static final String SERIAL_NUMBERS_URI = "/webservices/rest/DCOMLOV/getserials/";

    public static String SerialNumbers() {
        return SERIAL_NUMBERS_URI;
    }

    private static final String GET_REQUISITION = "/webservices/rest/DCOMLOV/getreq/";

    public static String PostGetRequisition() {
        return GET_REQUISITION;
    }

    private static final String GET_PURCHASE_ORDER = "/webservices/rest/DCOMLOV/getpo/";

    public static String PostGetPurchaseOrder() {
        return GET_PURCHASE_ORDER;
    }
    private static final String GET_SHIPMENT = "/webservices/rest/DCOMRCV/getshipment/";

    public static String PostGetShipment() {
        return GET_SHIPMENT;
    }

    private static final String POST_RECEIVE = "/webservices/rest/DCOMRCV/receive/";

    public static String PostReceiveTxn() {
        return POST_RECEIVE;
    }
    
    private static final String POST_LPN_SHIP = "/webservices/rest/DCOMShip/lpnship/";
    private static final String POST_LPN_LOAD = "/webservices/rest/DCOMShip/loadlpn/";
    private static final String POST_LPN_UNLOAD = "/webservices/rest/DCOMShip/unloadlpn/";
    private static final String POST_LPN = "/webservices/rest/DCOMShip/getlpn/";
    
    public static String PostLpnShip(){
        return POST_LPN_SHIP;
    }
    
    public static String PostLpnLoad(){
        return POST_LPN_LOAD;
    }
    
    public static String PostLpnUnLoad(){
        return POST_LPN_UNLOAD;
    }
    
    public static String PostLpn(){
        return POST_LPN;

}
    private static final String POST_LPN_PUTAWAY = "/webservices/rest/DCOMShip/lpnputway/";
    public static String PostLpnPutaway(){
    return POST_LPN_PUTAWAY;
    }
}
