package com.example.cst438_project1.DbFiles;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert
    void insert(User note);

    @Delete
    void delete(User note);

    @Query("SELECT password FROM users_table WHERE username == :username")
    String getUserPassword(String username);

    @Query("SELECT * from users_table WHERE username == :username")
    User getUserWithUsername(String username);

    @Query("SELECT id from users_table where username == :username")
    int getUserIdFromUsername(String username);
}
