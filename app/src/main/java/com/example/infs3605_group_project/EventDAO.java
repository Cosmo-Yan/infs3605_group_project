package com.example.infs3605_group_project;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import java.util.List;

 /*This interface defines the database operations, such as insert, update, delete, and query.
It is annotated with @Dao and contains one or more methods that correspond to database queries.
 */

public interface EventDAO {

    @Dao
    public interface UserDao {
        @Query("SELECT * FROM users")
        List<Event.User> getAll();

        @Insert
        void insert(Event.User user);

        @Update
        void update(Event.User user);

        @Delete
        void delete(Event.User user);
    }
}
