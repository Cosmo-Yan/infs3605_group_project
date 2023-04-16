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
import android.widget.TextView;

import com.example.infs3605_group_project.Activity.Activity;
import com.example.infs3605_group_project.Activity.ActivityDatabase;
import com.example.infs3605_group_project.Data.User;
import com.example.infs3605_group_project.Dashboard.DashboardActivity;
import com.example.infs3605_group_project.Data.UserData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class AccountActivity extends AppCompatActivity implements HistRecyclerInterface{

    BottomNavigationView bottomNavigationView;
    User user;
    List<Activity> activities;

    private ActivityDatabase mDb;
    private RecyclerView histList;
    private HistAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activities = new ArrayList<Activity>();
        super.onCreate(savedInstanceState);

        user = UserData.getInstance().getLoggedIn();
        setContentView(R.layout.activity_account);
        mDb = Room.databaseBuilder(getApplicationContext(), ActivityDatabase.class, "courses-database").fallbackToDestructiveMigration().build();
        loadData();

        histList = findViewById(R.id.HistListView);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
        histList.setLayoutManager(layoutManager);
        adapter = new HistAdapter(activities, this);
        histList.setAdapter(adapter);

        bottomNavigationView= findViewById( R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.account);

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
                        startActivity(new Intent(getApplicationContext(),FeedActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.account:
                        return true;

                    case R.id.newEvent:
                        startActivity(new Intent(getApplicationContext(),FormV2Controller.class));
                        overridePendingTransition(0,0);
                        return true;
                }


                return false;
            }
        });
        TextView usersName = findViewById(R.id.usersName);
        usersName.setText(user.getName());
    }

    public void loadData(){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                activities = mDb.activityDao().getActivities(user.getzId());
                Log.i("Activities",String.valueOf(activities.size()));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.update(activities);
                    }
                });
            }
        });
    }

    @Override
    public void onClick(int id) {
        int eId = activities.get(id).getId();
        Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
        intent.putExtra("activityID",eId);
        Log.i("id",String.valueOf(eId));
        startActivity(intent);
    }

    public void delClick(int id){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mDb.activityDao().deleteActivity(activities.get(id));
                activities.remove(id);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.update(activities);
                    }
                });
            }
        });
    }

    public void editClick(int id){
        UserData.getInstance().setTempAct(activities.get(id));
        startActivity(new Intent(getApplicationContext(),FormV2Controller.class));
    }

}