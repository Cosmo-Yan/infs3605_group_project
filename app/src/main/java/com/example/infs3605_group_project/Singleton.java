package com.example.infs3605_group_project;

import android.util.Log;

import java.util.List;
import java.util.concurrent.Executors;

public class Singleton {
    private static Singleton instance = null;
    private List<Activity> activityList = null;
    private ActivityDatabase database = null;

    private Singleton(ActivityDatabase roomDatabase){
        this.database = roomDatabase;
        refresh();
    }

    public static synchronized Singleton getInstance(ActivityDatabase roomDatabase){
        if (instance == null)
            instance = new Singleton(roomDatabase);

        return instance;
    }

    public static synchronized Singleton getInstance(){
        if (instance == null)
            throw new RuntimeException("Error - entering data into database before data exists");

        return instance;
    }

    public void refresh(){
        getData(true);
    }

    public void update(Activity activity){
        database.activityDao().insertActivity(activity);
        activityList.add(activity);
    }

    public void resetData(){
        Log.d("Database debug","Step R1 - resetting database");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                database.activityDao().deleteAll();
                Log.d("Database debug","Step R2 - reset database");
                for(int i = 0; i<10; i++){
                    Activity activity = new Activity(i, "Event Named "+String.valueOf(i),"UNSW or someone","Relations Building","not AU","Australia or somewhere","01/01/2001","Yes, there's more","Image URL here");
                    database.activityDao().insertActivity(activity);
                }
                Log.d("Database debug","Step R3 - added data to database");
                getData(false);
                viewData();
            }
        });
    }

    public List<Activity> getData() {
        return activityList;
    }

    public void viewData() {
        for(Activity temp: activityList){
            Log.i("Activity_Log_event",String.valueOf(temp.getId())+" "+temp.getEventName());
        }
    }

    private void getData(boolean newThread) {
        if (newThread){
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    getData(false);
                }
            });
            return;
        }
        activityList = database.activityDao().getActivities();
    }

}
