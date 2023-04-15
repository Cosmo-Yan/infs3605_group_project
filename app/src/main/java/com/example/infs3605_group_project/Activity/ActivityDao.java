package com.example.infs3605_group_project.Activity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ActivityDao {

    // Query and return all results currently in the DB
    @Query("SELECT * FROM Activity")
    List<Activity> getActivities();

    // Query and return based on the received data
//    @Query("SELECT * FROM Activity WHERE symbol == :coinSymbol")
//    Activity getActivity(Activity activity);

    @Query("SELECT * FROM Activity WHERE zid == :input")
    List<Activity> getActivities(String input);

    @Query("SELECT * FROM Activity WHERE id == :input")
    Activity getActivityById(int input);


    // Use @Delete to delete an Array list of objects
    // "..." is a Java operator that accepts an object or array list of objects as parameter
    @Delete
    void deleteActivity(Activity... activities);

    // Use @Query to delete all objects from Coin entity
    @Query("DELETE FROM Activity")
    void deleteAll();

    // Query to insert all received records into the DB
    @Insert
    void insertActivity(Activity... Activities);

    // A method specifically made to insert Dummy data on login
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void safeInsertActivity(Activity... Activities);

    // Update Activity
    @Update
    void update(Activity activity);

    // Delete Activity
    @Delete
    void delete(Activity activity);

}
