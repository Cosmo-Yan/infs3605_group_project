package com.example.infs3605_group_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class FeedActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ArrayList<FeedEventModel> feedEventModels = new ArrayList<>();
    int [] eventImages = {R.drawable.event_pic1,R.drawable.event_pic2,R.drawable.event_pic3,R.drawable.event_pic4,R.drawable.event_pic5,R.drawable.event_pic6,R.drawable.event_pic7,R.drawable.event_pic8,R.drawable.event_pic9,R.drawable.event_pic10,
            R.drawable.event_pic11,R.drawable.event_pic12,R.drawable.event_pic13,R.drawable.event_pic14,R.drawable.event_pic15,R.drawable.event_pic16,R.drawable.event_pic17};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_activity);

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
                        startActivity(new Intent(getApplicationContext(),DashBoardActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.feed:
                        return true;

                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(),AccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.newEvent:
                        startActivity(new Intent(getApplicationContext(),NewEventActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }


                return false;
            }
        });
    }

    private void setUpFeedEventModel() {
        String [] eventType = getResources().getStringArray(R.array.events_array);
        String [] hostName = getResources().getStringArray(R.array.host_name);

        //hard code it for 17 for now
        for (int i=0; i<17; i++){
            feedEventModels.add(new FeedEventModel(eventType[i],hostName[i],eventImages[i]));
        }
    }
}