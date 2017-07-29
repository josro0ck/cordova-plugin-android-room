package com.plugin.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by josimarvazquez on 7/28/17.
 */

@Dao
interface UserDAO {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE firstName LIKE :firstName AND "
            + "lastName LIKE :lastName LIMIT 1")
    User findByName(String firstName, String lastName);

    @Insert
    void insertAll(User... users);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}
