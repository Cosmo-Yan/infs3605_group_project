package com.example.infs3605_group_project;

import androidx.room.Database;
import androidx.room.RoomDatabase;

public class EventDB {

    /*
    This abstract class is the main entry point to the database and holds the connection to the
     database. It is annotated with @Database and contains an abstract method that returns the
      DAO interface.
     */

    @Database(entities = {Event.User.class}, version = 1)
    public abstract class MyDatabase extends RoomDatabase {
        public abstract EventDAO.UserDao userDao();
    }
}
