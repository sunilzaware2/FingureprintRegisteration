package com.Sunilmantra.deo;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.Sunilmantra.model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    Long insert(User u);

    @Query("SELECT * FROM User")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM User WHERE user_name =:name")
    User getUser(String name);


}
