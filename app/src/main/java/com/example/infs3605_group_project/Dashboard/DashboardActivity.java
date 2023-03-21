package com.example.infs3605_group_project.Dashboard;


import android.os.Bundle;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.infs3605_group_project.Event;
import com.example.infs3605_group_project.R;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    private GridView GridDash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //Load xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        //Connect Local Variable to xml elements
        GridDash = findViewById(R.id.dashboard_gridview);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Test Data Creation
        List<Event> TestEvents = new ArrayList<>();
        TestEvents.add(new Event());

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }
}
