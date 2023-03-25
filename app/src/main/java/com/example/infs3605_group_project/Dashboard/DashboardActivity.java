package com.example.infs3605_group_project.Dashboard;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.infs3605_group_project.Event;
import com.example.infs3605_group_project.EventDB;
import com.example.infs3605_group_project.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

public class DashboardActivity extends AppCompatActivity {
    private GridView GridDash;
    private TextView eventCount;
    private DashboardViewAdapter GridDashAdapter;

    private List<Event> Dataset;
    private EventDB eDb;
    private List<Graph> Graphs;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //Load xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        //Connect Local Variable to xml elements
        GridDash = findViewById(R.id.dashboard_gridview);
        eventCount = findViewById(R.id.eventListNum);



        //Instantiate Database Object & Retrieve Event List
        eDb = Room.databaseBuilder(getApplicationContext()
                , EventDB.class, "Event-Database").build();

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Dataset.addAll(eDb.eventDAO().getAll());
                }
                catch (NullPointerException npe){
                    System.out.println("Database Empty");
                }
            }
        });

        //Set Event List Count
        eventCount.setText(String.valueOf(Dataset.size()));


        //Create Graph Objects
        //Piechart of Event Types Example
        PieChart typePieChart = new PieChart(getApplicationContext());

        List<PieEntry> entries = new ArrayList<>();
        for (Event events : Dataset){
            float value = 1;
            String label = events.getEventType();
            entries.add(new PieEntry(value, label));
        }

        PieDataSet pieSet = new PieDataSet(entries, "Event Type Breakdown");
        pieSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieSet.setValueTextColor(Color.BLACK);
        pieSet.setValueTextSize(16f);

        PieData data = new PieData(pieSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(typePieChart));

        typePieChart.setData(data);
        typePieChart.getDescription().setEnabled(false);
        typePieChart.setCenterText("Pie Chart Title");
        typePieChart.setCenterTextSize(20f);
        typePieChart.animateY(1000, Easing.EaseInOutCubic);

        Bitmap pieMap = typePieChart.getChartBitmap();

        Graph eTypePie = new Graph(pieMap, "Event Type Breakdown");
        Graphs.add(eTypePie);

        //Generate the adapter for the GridView
        //GridDashAdapter = new DashboardViewAdapter();
        DashboardViewAdapter vAdapter = new DashboardViewAdapter(this, R.layout.dashboard_item, Graphs);
        GridDash.setAdapter(vAdapter);
    }
}
