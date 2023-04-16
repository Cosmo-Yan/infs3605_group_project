package com.example.infs3605_group_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.infs3605_group_project.Activity.Activity;
import com.example.infs3605_group_project.Activity.ActivityDatabase;
import com.example.infs3605_group_project.Dashboard.DashboardActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;

public class FeedActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ArrayList<FeedEventModel> feedEventModels = new ArrayList<>();
    int [] eventImages = {R.drawable.event_pic1,R.drawable.event_pic2,R.drawable.event_pic3,R.drawable.event_pic4,R.drawable.event_pic5,R.drawable.event_pic6,R.drawable.event_pic7,R.drawable.event_pic8,R.drawable.event_pic9,R.drawable.event_pic10,
            R.drawable.event_pic11,R.drawable.event_pic12,R.drawable.event_pic13,R.drawable.event_pic14,R.drawable.event_pic15,R.drawable.event_pic16,R.drawable.event_pic17};
    private ActivityDatabase mDb;
    HashMap<String, Integer> eventImageMap = new HashMap<String, Integer>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_activity);
        eventImageMap.put("Education Exchange (Domestic)",1);
        eventImageMap.put("Education Exchange (International)",2);
        eventImageMap.put("Education Exchange",3);
        eventImageMap.put("Centre Opening (International)",4);
        eventImageMap.put("Centre Opening (Domestic)",5);
        eventImageMap.put("Centre (int/domestic)",5);
        eventImageMap.put("Relations Event",6);
        eventImageMap.put("Guest Speaker (International)",7);
        eventImageMap.put("Guest Speaker (Domestic)",8);
        mDb = Room.databaseBuilder(getApplicationContext(), ActivityDatabase.class, "courses-database").fallbackToDestructiveMigration().build();


        RecyclerView recyclerView = findViewById(R.id.feedRecycleView);

        setUpFeedEventModel();

        FeedEventRecyclerAdapter adapter = new FeedEventRecyclerAdapter(this, feedEventModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bottomNavigationView= findViewById( R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.feed);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.feed:
                        return true;

                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(),AccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.newEvent:
                        startActivity(new Intent(getApplicationContext(),FormV2Controller.class));
                        overridePendingTransition(0,0);
                        return true;

                }


                return false;
            }
        });
    }

    private void setUpFeedEventModel() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<Activity> activityList = mDb.activityDao().getActivities();
                for(Activity temp: activityList){
                    String eventType1 =temp.getEventType();
                    int imageId = 1;
                    if(eventImageMap.containsKey(eventType1)){
                        imageId = eventImageMap.get(temp.getEventType());
                    } else{
                        Log.i("Activity Type Error",eventType1);
                    }
                    feedEventModels.add(new FeedEventModel(temp.getEventName(), temp.getEventType(),eventImages[imageId]));
                }
            }
        });
    }
}