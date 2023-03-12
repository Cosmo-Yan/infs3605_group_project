package com.example.infs3605_group_project;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.RoomDatabase;

/* This class represents a table in the database and defines its schema.
   It is annotated with @Entity and contains a primary key and one or more columns
*/
    @Entity
    public class Event {
    @PrimaryKey
    @NonNull
        private int id;
        private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

