//package com.example.infs3605_group_project.Data;
//
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//
//import java.util.List;
//
//@Dao
//public interface UserDao {
//
//    // Query and return all results currently in the DB
//    @Query("SELECT * FROM User")
//    List<User> getUser();
//
//    // Query and return based on the received data
//    @Query("SELECT * FROM User WHERE username == :input")
//    User getUser(String input);
//
//    // Use @Delete to delete an Array list of objects
//    // "..." is a Java operator that accepts an object or array list of objects as parameter
//    @Delete
//    void deleteUser(User... users);
//
//    // Use @Query to delete all objects from Coin entity
//    @Query("DELETE FROM User")
//    void deleteAll();
//
//    // Query to insert all received records into the DB
//    @Insert
//    void insertUser(User... users);
//}
