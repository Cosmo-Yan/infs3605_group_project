package com.example.infs3605_group_project.Dashboard;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.infs3605_group_project.Event;
import com.example.infs3605_group_project.EventDB;
import com.example.infs3605_group_project.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

public class DashboardActivity extends AppCompatActivity {
    private GridView StatDash;
    private RecyclerView GraphDash;

    private GraphAdapter GridDashAdapter;

    private List<Event> Dataset;
    private EventDB eDb;
    private List<Graph> Graphs;
    private List<Stat> Stats;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //Load xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        //Connect Local Variable to xml elements
        GraphDash = findViewById(R.id.Graph_Recycler);
        StatDash = findViewById(R.id.Stat_grid);




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

        //Event Histogram
        BarChart EventMonthHistogram = new BarChart(getApplicationContext());
        List<Date> DateHistogramListMonth = new ArrayList<>();
//        for (Event events : Dataset){
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(events.getEventStartDate());
//            int month = cal.get(Calendar.MONTH);
//            DateHistogramListMonth.add(month);
//        }

        int[] monthCounts = new int[12];
        for (Date events : DateHistogramListMonth) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(events);
            int month = cal.get(Calendar.MONTH);
            monthCounts[month]++;
        }

        List<BarEntry> months = new ArrayList<>();
        for (int i = 0; i < monthCounts.length; i++) {
            months.add(new BarEntry(i, monthCounts[i]));
        }
        BarDataSet dataSet = new BarDataSet(months, "Events Held");
        BarData barData = new BarData(dataSet);
        EventMonthHistogram.setData(barData);

        Bitmap HistogramMap = EventMonthHistogram.getChartBitmap();

        Graph EventMonthHistogramGraph = new Graph(HistogramMap, "Event Occurrence Breakdown Monthly");

        //Generate the adapter for the Recycler
        //GridDashAdapter = new DashboardViewAdapter();
        GraphAdapter gAdapter = new GraphAdapter( Graphs, this);
        GraphDash.setAdapter(gAdapter);
        StatAdapter sAdapter = new StatAdapter( this, (ArrayList<Stat>) Stats);
    }
}
