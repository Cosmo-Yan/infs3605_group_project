package com.example.infs3605_group_project;

import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.RoomDatabase;

/* This class represents a table in the database and defines its schema.
   It is annotated with @Entity and contains a primary key and one or more columns
*/

public class Event {
    @Entity(tableName = "users")

    public class User {
        @PrimaryKey
        private int id;

        @ColumnInfo(name = "name")
        private String name;

        @ColumnInfo(name = "email")
        private String email;

        // Constructor, getters, and setters

    }
}
