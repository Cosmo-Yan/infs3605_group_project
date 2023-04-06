package com.example.infs3605_group_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.infs3605_group_project.Activity.Activity;
import com.example.infs3605_group_project.Activity.ActivityDatabase;
import com.example.infs3605_group_project.Data.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.concurrent.Executors;

public class AccountActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    User user;
    private ActivityDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mDb = Room.databaseBuilder(getApplicationContext(), ActivityDatabase.class, "courses-database").fallbackToDestructiveMigration().build();
        debugData();

        bottomNavigationView= findViewById( R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.account);

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
                        startActivity(new Intent(getApplicationContext(),FeedActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.account:
                        return true;

                    case R.id.newEvent:
                        startActivity(new Intent(getApplicationContext(),NewEventActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }


                return false;
            }
        });
        user = UserData.getInstance().getLoggedIn();
        TextView usersName = findViewById(R.id.usersName);
        usersName.setText(user.getName());
    }

    public void debugData(){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<Activity> activities = mDb.activityDao().getActivity("z1234567");
                Log.i("activity found","<Start>");
                for(Activity tempAct: activities){
                    Log.i("activity found",tempAct.getEventName());
                }
                Log.i("activity found","<End>");
                Log.i("All acts","<Start>");
                activities = mDb.activityDao().getActivities();
                for(Activity tempAct: activities){
                    Log.i("Activity exists",tempAct.getEventName());
                    if(tempAct.getZid().equals("z1234567")) {
                        Log.i("Activity Is Ours", tempAct.getEventName());
                    }
                }
            }
        });
    }
}