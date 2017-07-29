
# Room Plugin


- This is a simple Cordova Room Plugin



### INSTALL:

	In order to be able to compile you need to add:

	```
		allprojects {
		    repositories {
		        ..
		        maven { url 'https://maven.google.com' }
		    }
		}
	```

### API:
	In Order to perform database operations

```

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


```
	
### MODEL:
	The JSON objects that feed the database must have the following format:

```

		{"uid":"10291", "firstName":"john", "lastName":"doe"}
	
```





