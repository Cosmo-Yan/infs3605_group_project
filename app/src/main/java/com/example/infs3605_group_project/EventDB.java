package com.example.infs3605_group_project;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

    /*
    This abstract class is the main entry point to the database and holds the connection to the
     database. It is annotated with @Database and contains an abstract method that returns the
      DAO interface.
     */

    @Database(entities = {Event.class}, version = 1)
    @TypeConverters({typeConverter.class})
    public abstract class EventDB extends RoomDatabase {
        public abstract EventDAO eventDAO();

    }

