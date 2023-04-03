package com.example.infs3605_group_project.Dashboard;



import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import com.example.infs3605_group_project.Activity.Activity;
import com.example.infs3605_group_project.Activity.ActivityDatabase;
import com.example.infs3605_group_project.FormV2Controller;
import com.example.infs3605_group_project.R;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class DashboardActivity extends AppCompatActivity {
    private GridView StatDash;
    private RecyclerView GraphDash;

    private GraphAdapter GridDashAdapter;

    private ArrayList<Activity> Dataset = new ArrayList<>();
    private ActivityDatabase mDb;
    private ArrayList<Graph> Graphs = new ArrayList<>();
    private ArrayList<Stat> Stats = new ArrayList<>();
    private ArrayList<Chart> Charts= new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //Load xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        //Connect Local Variable to xml elements
        GraphDash = findViewById(R.id.Graph_Recycler);
        StatDash = findViewById(R.id.Stat_grid);





        //Instantiate Database Object & Retrieve Event List
        mDb = Room.databaseBuilder(getApplicationContext(), ActivityDatabase.class,
                "courses-database").fallbackToDestructiveMigration().build();

        // Creates an executor
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Inserts the task for the Executor to execute
        executor.submit(() -> {
            try {
                Dataset.addAll(mDb.activityDao().getActivities());
            }
            catch (NullPointerException npe){
                System.out.println("Database Empty");
            }
        });

        // Shuts down the executor
        executor.shutdown();

        try {
            // Waits for Execution to be over
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
        }


        //Create Stats
        //Number of Events
        Stats.add(new Stat(Dataset.size(), "Number of Events Overall:"));



        //Create Graph Objects
        //Piechart of Event Types Example
        Charts.add(typePieChart());
        Charts.add(monthBarChart());

        //Generate the adapter for the Recycler
        //GridDashAdapter = new DashboardViewAdapter();
        GraphDash.setLayoutManager((new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)));
        ChartAdapter cAdapter = new ChartAdapter((List<com.github.mikephil.charting.charts.Chart>) Charts);
        GraphDash.setAdapter(cAdapter);
        StatAdapter sAdapter = new StatAdapter( this, (ArrayList<Stat>) Stats);
        StatDash.setAdapter(sAdapter);
    }

    public PieChart typePieChart(){
        PieChart typePieChart = new PieChart(getApplicationContext());

        List<PieEntry> entries = new ArrayList<>();
        for (Activity activity : Dataset){
            float value = 1;
            String label = activity.getEventType();
            entries.add(new PieEntry(value, label));
        }

        PieDataSet pieSet = new PieDataSet(entries, "Event Type Breakdown");

        HashMap<String, Float> labelValues = new HashMap<>();

// Iterate through the entries and add the values for entries with the same label
        for (PieEntry entry : pieSet.getValues()) {
            String label = entry.getLabel();
            float value = entry.getValue();
            if (labelValues.containsKey(label)) {
                labelValues.put(label, labelValues.get(label) + value);
            } else {
                labelValues.put(label, value);
            }
        }

// Create a new list of entries with the combined values
        List<PieEntry> combinedEntries = new ArrayList<>();
        for (String label : labelValues.keySet()) {
            float value = labelValues.get(label);
            combinedEntries.add(new PieEntry(value, label));
        }

// Create a new PieDataSet with the combined entries
        PieDataSet combinedDataSet = new PieDataSet(combinedEntries, "Combined Data");

        combinedDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        combinedDataSet.setValueTextColor(Color.BLACK);
        combinedDataSet.setValueTextSize(10f);

        typePieChart.getDescription().setEnabled(false);
        typePieChart.getLegend().setEnabled(true);
        typePieChart.setEntryLabelColor(Color.BLACK);
        typePieChart.setEntryLabelTextSize(10f);



        PieData data = new PieData(combinedDataSet);


        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(typePieChart));
        typePieChart.setData(data);

        Description description = new Description();
        description.setText("Breakdown of Events by Type");
        typePieChart.setDescription(description);

        typePieChart.invalidate();

        return typePieChart;
    }
    public BarChart monthBarChart(){
        //Event Histogram
        BarChart EventMonthHistogram = new HorizontalBarChart((getApplicationContext()));
        Description description = new Description();
        description.setText("Breakdown of Events by Month");
        EventMonthHistogram.setDescription(description);
        EventMonthHistogram.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        EventMonthHistogram.getDescription().setEnabled(false);

        EventMonthHistogram.getLegend().setEnabled(false);
        List<Integer> DateHistogramListMonth = new ArrayList<>();

        for (Activity events : Dataset){
            Calendar cal = Calendar.getInstance();
            String str[] = events.getEventStartDate().split("/");
           int Month = Integer.valueOf(str[1]);

            DateHistogramListMonth.add(Month);
        }

        int[] counts = new int[12];
        for (int event : DateHistogramListMonth){
            int monthIndex = event - 1;
            counts[monthIndex]++;
        }

        List<Integer> eventslist = new ArrayList<>(counts.length);
        for (int i : counts)
        {
            eventslist.add(i);
        }

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        for (int eventnum : eventslist) {
            barEntries.add((new BarEntry(eventslist.indexOf(eventnum), eventnum)));
        }

        BarDataSet dataSet = new BarDataSet(barEntries, "Events Held");
        BarData barData = new BarData(dataSet);

        EventMonthHistogram.setData(barData);

        // Set the X-axis labels
//        String[] monthsList = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
//        XAxis xAxis = EventMonthHistogram.getXAxis();
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(monthsList));

        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(EventMonthHistogram);
        XAxis xAxis = EventMonthHistogram.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(12);
        xAxis.setValueFormatter(xAxisFormatter);

        EventMonthHistogram.getAxisLeft().setEnabled(true);







        EventMonthHistogram.invalidate();

        return EventMonthHistogram;
    }
}



