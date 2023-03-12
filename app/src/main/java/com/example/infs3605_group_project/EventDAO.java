package com.example.infs3605_group_project;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import java.util.List;

 /*This interface defines the database operations, such as insert, update, delete, and query.
It is annotated with @Dao and contains one or more methods that correspond to database queries.
 */

@Dao
public interface EventDAO {

    @Query("SELECT * FROM Event")
    List<Event> getAll();

    @Query("SELECT * FROM Event WHERE id = :id")
    Event getById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Event e);

    @Update
    void update(Event e);

    @Delete
    void delete(Event e);

    @Query("DELETE FROM Event")
    void deleteAll();
}


