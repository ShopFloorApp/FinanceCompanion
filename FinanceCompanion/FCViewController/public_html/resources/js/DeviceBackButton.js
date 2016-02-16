//This is an event that fires when the user presses the device back button
document.addEventListener("deviceready", onDeviceReady, false);

function onDeviceReady() {
    document.addEventListener("backbutton", backKeyDown, true);
}

function backKeyDown() {
    //Check the device back button action happened in Employee.amx
    
    if ($('#pp1').length) {
        //Call the java method in managed bean
//        alert('back is working');
        adf.mf.api.amx.doNavigation("back");
//        adf.mf.api.invokeMethod("com.fincomp.mobile.beans.DashboardBean", "handleNavigation", onInvokeSuccess, onFail);
    }else
   {
  var cFirm = confirm("Are you sure you want to exit the application?");
    if (cFirm == true) {
        //Code to exit the application
        navigator.app.exitApp();
    }
   }
}

function onInvokeSuccess(param) {
};

function onFail() {
};
