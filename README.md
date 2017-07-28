-----------------------------------------------------------
    Room Plugin
-----------------------------------------------------------

- This is a simple Cordova Room Plugin



INSTALL:
	in order to be able to compile you need to add:

	[groovy]
		allprojects {
		    repositories {
		        ..
		        maven { url 'https://maven.google.com' }
		    }
		}
	[\groovy]

API:
	In Order to perform database operations

	[js]

		room.insert(usr);

		room.getAll(function(data){
            console.log(data.uuid);
        });

		room.find(usr, function(data){
            console.log(data.uuid)
        });

		room.delete(usr, function(data){
           console.log(data.uuid)
       });


	[\js]]
	
MODEL:
	The JSON objects that feed the database must have the following format:
	
	[json]

		{"uid":"10291", "firstName":"john", "lastName":"doe"}
	
	[\json]



