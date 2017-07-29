package com.plugin.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by josimarvazquez on 7/28/17.
 */

@Entity
public class User {

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

