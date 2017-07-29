package com.plugin.room;


import android.arch.persistence.room.Room;

import com.google.common.base.Strings;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class RoomPlugin extends CordovaPlugin {

    private static UsersDB db;

    public RoomPlugin() {}

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("insert".equals(action)) {
            insertAll(args, callbackContext);
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

    private void insertAll(final JSONArray args, CallbackContext callbackContext) {
        User[] users = JSONToUsers(args);
        UserDAO dao = getDb().userDao();
        try{
            dao.insertAll(users);
            callbackContext.success("Insert is successful");
        } catch (Exception e) {
            // TODO: add & fix crashes
            // 1. An Existing id has been passed
            e.printStackTrace();
            dao.update(users[0]);
        }
    }

    private List<User> getAll() {
        List<User> users = getDb().userDao().getAll();
        return users;
    }

    private User findByName(final JSONArray args) {
        User user = JSONToUsers(args)[0];
        return findByName(user);
    }

    private User findByName(User user) {
        if (user != null && user.getFirstName() != null && user.getLastName() != null) {
            User foundUser = getDb().userDao().findByName(user.getFirstName(), user.getLastName());
            return foundUser;
        } else {
            return null;
        }
    }

    private void delete(final JSONArray args) {
        User user = JSONToUsers(args)[0];
        if (user != null && !Strings.isNullOrEmpty(user.getFirstName()) && !Strings.isNullOrEmpty(user.getLastName())) {
            if(user.getUid() != null){
                getDb().userDao().delete(user);
                return;
            }
            User deleteUser = findByName(user);
            if (deleteUser!=null){
                getDb().userDao().delete(deleteUser);
                return;
            }
        }
        getDb().userDao().delete(user);
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
                String id = array.getJSONObject(i).getString(User.USER_ID);

                if(Strings.isNullOrEmpty(id)){
                    users[i] = new User(firstName, lastName);
                } else {
                    users[i] = new User(Long.valueOf(id),firstName, lastName);
                }

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
