showAlert = function(title,message,buttonLabel){
           Alert.alert(title,
                       message,
                       buttonLabel,
                       function(button){
                        console.log("User Tapped "+button);
        });
        };