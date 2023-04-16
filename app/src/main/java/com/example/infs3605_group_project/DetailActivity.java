package com.example.infs3605_group_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.infs3605_group_project.Activity.Activity;
import com.example.infs3605_group_project.Activity.ActivityDatabase;

import java.util.concurrent.Executors;

public class DetailActivity extends AppCompatActivity {
    private Activity activity = new Activity();
    private TextView eventName;
    private TextView eventDate;
    private TextView creator;
    private TextView detailDate;
    private TextView detailOrganiser;
    private TextView detailType;
    private TextView detailLocation;
    private TextView detailCountry;
    private TextView detailFurtherDetails;
    private ActivityDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDb = Room.databaseBuilder(getApplicationContext(), ActivityDatabase.class, "courses-database").fallbackToDestructiveMigration().build();
        Intent intent = getIntent();
        if(intent.hasExtra("activityID")){
            int id = (int) intent.getIntExtra("activityID",1);
            Log.i("loading",String.valueOf(id));
            loadActivity(id);
        }
        eventDate = findViewById(R.id.detailDate);
        eventName = findViewById(R.id.DetailEventName);
        creator = findViewById(R.id.creator);
        detailDate = findViewById(R.id.detailDate);
        detailOrganiser = findViewById(R.id.detailOrganiser);
        detailType = findViewById(R.id.detailType);
        detailLocation = findViewById(R.id.detailLocation);
        detailCountry = findViewById(R.id.detailCountry);
        detailFurtherDetails = findViewById(R.id.detailFurtherDetails);


    }

    private void update(Activity activity){
        eventName.setText(activity.getZid());
        eventDate.setText(activity.getEventStartDate());
        creator.setText(activity.getNameOfOrganiser());
        creator.setText(activity.getZid());
        detailDate.setText(activity.getEventStartDate());
        detailOrganiser.setText(activity.getNameOfOrganiser());
        detailType.setText(activity.getEventType());
        detailLocation.setText(activity.getLocation());
        detailCountry.setText(activity.getCountry());
        detailFurtherDetails.setText(activity.getFurtherDetails());
    }


    public void loadActivity(int id){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                activity = mDb.activityDao().getActivityById(id);
                Log.i("loading",String.valueOf(activity.getEventName()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        update(activity);
                    }
                });
            }
        });
    }
}