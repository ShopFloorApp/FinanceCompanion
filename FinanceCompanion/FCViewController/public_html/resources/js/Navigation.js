(function () {
    // set orientation class name to root element for sidebar
    function orientationChange(){
        if(window.orientation === 0 || window.orientation === 180){
            $('html').removeClass('landscape').addClass('portrait');
          }else{
            $('html').removeClass('portrait').addClass('landscape');
          }
    };
    
    // add class on page load
    orientationChange();
    
    // add class on orientaiton change
    $(window).on('orientationchange', function(event) {
        orientationChange();
    });

    var leftNavigationVisible = false,
        rightNavigationVisible = false;

    leftNavigationToggleBtn = function(){
        if (leftNavigationVisible) {
            adf.mf.api.hideSpringboard();
            leftNavigationVisible = false;
            jQuery('.spring-board-link').removeClass('hide');
        }
        else {
            adf.mf.api.showSpringboard();
            leftNavigationVisible = true;
            jQuery('.spring-board-link').addClass('hide');
        }
    }

    rightNavigationToggleBtn = function(){
        if (rightNavigationVisible) {
            rightNavigationVisible = false;
            jQuery('#right-navigation').removeClass('show');
            jQuery('.rightnav-link').removeClass('hide');
        }
        else {
            rightNavigationVisible = true;
            jQuery('#right-navigation').addClass('show');
            jQuery('.rightnav-link').addClass('hide');
        }
    }
    
    flipCardLayoutAnimActionBack = function () {
    var args = arguments;
        var str = ""+ args[0];
//        alert("code reached to JS "+str);
        
        $('.front').eq(str).addClass('flip');
        $('.back').eq(str).addClass('flip');
    };
    flipCardLayoutAnimActionFront = function () {
    var args = arguments;
        var str = ""+ args[0];
//        alert("code reached to JS "+str);
        
        $('.front').eq(str).removeClass('flip');
        $('.back').eq(str).removeClass('flip');
    };
    changeCardLytValues=function(){
//    alert('inside JS again');
        var args = arguments;
        var str = ""+ args[0];
        var count=""+args[1];
        var sdate=""+args[2];
        
        $('.synccount').eq(str).text(count);
        $('.syncdate').eq(str).text(sdate);
    }
    
    deactivateCardLayout = function () {
    var args = arguments;
        var str = ""+ args[0];
        $('.front').eq(str).addClass('dimLyt');
    };
    
    activateCardLayout = function () {
    var args = arguments;
        var str = ""+ args[0];
        $('.front').eq(str).removeClass('dimLyt');
    };
})();