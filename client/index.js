
var inputUid, inputFirst, inputLast, usersList;

var app = {

    initialize: function() {
        document.addEventListener('deviceready', this.onDeviceReady.bind(this), false);
    },

    onDeviceReady: function() {

        inputUid = document.getElementById("uid");
        inputFirst = document.getElementById("firstName");
        inputLast = document.getElementById("lastName");
        usersList = document.getElementById("usersList");

        console.log("init")
        getAll();

        document.getElementById("insert").addEventListener("click", function(){
            let usr = getCurrentUser();
            room.insert(usr);
            getAll();
        });

        document.getElementById("getAll").addEventListener("click", function(){
            getAll();
        });

        document.getElementById("find").addEventListener("click", function(){
            let usr = getCurrentUser();
            room.find(usr, function(data){
                usersList.innerHTML = JSON.stringify(data);
            });
        });

        document.getElementById("del").addEventListener("click", function(){
            let usr = getCurrentUser();
            room.delete(usr);
            getAll();
        });

    }
};

app.initialize();


     createUser = function(uid, firstName, lastName) {
         let usr = new Object();
         usr.uid = uid;
         usr.firstName = firstName;
         usr.lastName = lastName;
         return usr;
     }

     function getCurrentUser(){
         let usr = createUser(inputUid.value, inputFirst.value, inputLast.value)
         inputUid.value = ''
         inputFirst.value = ''
         inputLast.value = ''
         return usr
     }

     function getAll(){
        room.getAll(function(data){
            usersList.innerHTML = JSON.stringify(data);
        });
     }



