
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

        document.getElementById("insert").addEventListener("click", function(){
            let usr = getCurrentUser();
            room.insert(usr);
        });

        document.getElementById("getAll").addEventListener("click", function(){
            room.getAll(function(data){
                console.log(data.uuid);
                usersList.innerHTML = data.uuid;
            });
        });

        document.getElementById("find").addEventListener("click", function(){
            let usr = getCurrentUser();
            room.find(usr, function(data){
                console.log(data.uuid)
            });
        });

        document.getElementById("del").addEventListener("click", function(){
            let usr = getCurrentUser();
            room.delete(usr, function(data){
               console.log(data.uuid)
           });
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

