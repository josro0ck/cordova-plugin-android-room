
package org.apache.cordova.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

@Entity
class User {

    @Ignore
    public static final String USER_ID = "uid";
    @Ignore
    public static final String FIRST_NAME = "firstName";
    @Ignore
    public static final String LAST_NAME = "lastName";

    @PrimaryKey(autoGenerate = true)
    private Long uid;
    private String firstName;
    private String lastName;

    @Ignore
    User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    User(Long uid, String firstName, String lastName) {
        this(firstName, lastName);
        this.uid = uid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "{" + uid + ", " + firstName + ", " + lastName + " } ";
    }

    public static JSONObject userToJSON(User user) {
        JSONObject json = new JSONObject();
        try {
            json.put(USER_ID, user.getUid());
            json.put(FIRST_NAME, user.getFirstName());
            json.put(LAST_NAME, user.getLastName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}

@Dao
interface UserDAO {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE firstName LIKE :firstName AND "
            + "lastName LIKE :lastName LIMIT 1")
    User findByName(String firstName, String lastName);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}

@Database(entities = {User.class}, version = 1)
abstract class UsersDB extends RoomDatabase {
    public abstract UserDAO userDao();
}

public class RoomPlugin extends CordovaPlugin {

    private static final String ACTION_GET_ALL = "getAll";
    private static final String ACTION_FIND_BY_NAME = "findByName";
    private static final String ACTION_INSERT_ALL = "insertAll";
    private static final String ACTION_DELETE_USER = "delete";

    private static UsersDB db;

    public RoomPlugin() {}

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("insert".equals(action)) {
            insertAll(args);
            callbackContext.success();
        }
        else if ("getAll".equals(action)) {
            List<User> users = getAll();
            callbackContext.success(usersToJSON(users));
        }
        else if ("find".equals(action)) {
            findByName(args);
            User user = findByName(args);
            if (user != null) {
                callbackContext.success(User.userToJSON(user));
            }
        }
        else if ("delete".equals(action)) {
            delete(args);
            callbackContext.success();
        }
        else {
            return false;
        }
        return true;
    }

    private void insertAll(final JSONArray args) {
        User[] users = JSONToUsers(args);
        getDb().userDao().insertAll(users);
    }

    private List<User> getAll() {
        List<User> users = getDb().userDao().getAll();
        return users;
    }

    private User findByName(final JSONArray args) {
        User user = JSONToUsers(args)[0];
        if (user != null && user.getFirstName() != null && user.getLastName() != null) {
            User foundUser = getDb().userDao().findByName(user.getFirstName(), user.getLastName());
            return foundUser;
        } else {
            return null;
        }
    }

    private void delete(final JSONArray args) {
        User user = JSONToUsers(args)[0];
        if (user != null && user.getFirstName() != null && user.getLastName() != null && user.getUid() != null) {
            getDb().userDao().delete(user);
        }
    }

    private UsersDB getDb() {
        return db != null ? db : initializeDatabase();
    }

    private UsersDB initializeDatabase() {
        db = Room.databaseBuilder(cordova.getActivity(),
                UsersDB.class, "user").build();
        return db;
    }

    private User[] JSONToUsers(JSONArray array) {
        User[] users = new User[array.length()];
        for (int i = 0; i < array.length(); i++) {
            try {
                String firstName = array.getJSONObject(i).getString(User.FIRST_NAME);
                String lastName = array.getJSONObject(i).getString(User.LAST_NAME);
                users[i] = new User(firstName, lastName);
            } catch (JSONException ignored) {
                //Believe exception only occurs when adding duplicate keys, so just ignore it
            }
        }
        return users;
    }

    private JSONArray usersToJSON(List<User> users) {
        JSONArray jsonArray = new JSONArray();
        for (User u : users) {
            if (u != null) {
                jsonArray.put(User.userToJSON(u));
            }
        }
        return jsonArray;
    }
}
