package com.example.infs3605_group_project.Activity;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Activity.class}, version = 1)
public abstract class ActivityDatabase extends RoomDatabase {
    public abstract ActivityDao activityDao();
}