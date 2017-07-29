package com.plugin.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by josimarvazquez on 7/28/17.
 */

@Database(entities = {User.class}, version = 1)
public abstract class UsersDB extends RoomDatabase {
    public abstract UserDAO userDao();
}
