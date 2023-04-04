package com.example.infs3605_group_project.Dashboard;



import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import com.example.infs3605_group_project.Activity.Activity;
import com.example.infs3605_group_project.Activity.ActivityDatabase;
import com.example.infs3605_group_project.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

public class DashboardActivity extends AppCompatActivity {
    private GridView StatDash;
    private RecyclerView GraphDash;



    private ArrayList<Activity> Dataset = new ArrayList<>();
    private ActivityDatabase mDb;
    private ArrayList<Graph> Graphs = new ArrayList<>();
    private ArrayList<Stat> Stats = new ArrayList<>();
    private ArrayList<ChartObjects> Charts= new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //Load xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        //Connect Local Variable to xml elements
        GraphDash = findViewById(R.id.Graph_Recycler);
        StatDash = findViewById(R.id.Stat_grid);


        Dataset = getDefaultDataset();



        //Create Stats
        //Number of Events
        Stats.add(new Stat(Dataset.size(), "Number of Events Overall:"));

        //
        HashSet noDuplicate = new HashSet();
        for (Activity active: Dataset){
            noDuplicate.add(active.getCountry());
        }
        Stats.add(new Stat(noDuplicate.size(), "Number of Countries Participating:"));
        for (Activity active: Dataset){
            noDuplicate.add(active.getLocation());
        }
        Stats.add(new Stat(noDuplicate.size(), "Number of Active Locations:"));




        //Create Graph Objects
        //Piechart of Event Types Example
        Charts.add(new PieObject(null, typePieChart(Dataset)));
        Charts.add(new BarAxis(null, monthBarChart(Dataset).getChart(), monthBarChart(Dataset).getAxisSuper() ));
        Charts.add(new BarAxis(null, countryBar(Dataset).getChart(), countryBar(Dataset).getAxisSuper()));


        //Generate the adapter for the Recycler
        //GridDashAdapter = new DashboardViewAdapter();
        GraphDash.setLayoutManager((new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)));
        ChartAdapter cAdapter = new ChartAdapter(Charts);
        cAdapter.notifyDataSetChanged();
        GraphDash.setAdapter(cAdapter);
        StatAdapter sAdapter = new StatAdapter( this, (ArrayList<Stat>) Stats);
        StatDash.setAdapter(sAdapter);
    }

    private ArrayList<Activity> getDefaultDataset(){
        ArrayList<Activity> defaultArray = new ArrayList<>();
        //Instantiate Database Object & Retrieve Event List
        mDb = Room.databaseBuilder(getApplicationContext(), ActivityDatabase.class,
                "courses-database").fallbackToDestructiveMigration().build();

        // Creates an executor
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Inserts the task for the Executor to execute
        executor.submit(() -> {
            try {
                defaultArray.addAll(mDb.activityDao().getActivities());
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
        return defaultArray;

    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.filter_Country:
//                GraphDash.sort(CoinViewAdapter.SORT_METHOD_NAME);
                break;
            case R.id.filter_year:
//                GraphDash.sort(2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public PieChart typePieChart(ArrayList<Activity> Data){
        PieChart typePieChart = new PieChart(getApplicationContext());

        List<PieEntry> entries = new ArrayList<>();
        for (Activity activity : Data){
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
        combinedDataSet.setValueTextSize(12f);









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
    public BarAxis monthBarChart(ArrayList<Activity> Data){
        //Event Histogram
        BarChart EventMonthHistogram = new HorizontalBarChart((getApplicationContext()));
        Description description = new Description();
        description.setText("Breakdown of Events by Month");
        EventMonthHistogram.setDescription(description);

        List<Integer> DateHistogramListMonth = new ArrayList<>();
        for (Activity events : Data){
            String str[] = events.getEventStartDate().split("/");
            int Month = Integer.valueOf(str[1]);
            DateHistogramListMonth.add(Month);
        }

        int[] counts = new int[11];
        for (int event : DateHistogramListMonth){
            int monthIndex = event - 1;
            counts[monthIndex]++;


        }

        List<Integer> eventslist = new ArrayList<>();
        for (int i : counts)
        {
            if(i == 0){
                System.out.println("error");
            }
            eventslist.add(i);
        }

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        for (int eventnum : eventslist) {
            barEntries.add((new BarEntry(eventslist.indexOf(eventnum), eventnum)));
        }

        BarDataSet dataSet = new BarDataSet(barEntries, "Events Held");

        dataSet.setBarBorderWidth(0.5f);


        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        BarData barData = new BarData(dataSet);



        barData.setBarWidth(1f);


        EventMonthHistogram.setData(barData);

        // Set the X-axis labels
        List<String> monthsList = new ArrayList<String>();
        monthsList.add("Jan");
        monthsList.add("Feb");
        monthsList.add("Mar");
        monthsList.add("Apr");
        monthsList.add("May");
        monthsList.add("Jun");
        monthsList.add("Jul");
        monthsList.add("Aug");
        monthsList.add("Sep");
        monthsList.add("Oct");
        monthsList.add("Nov");
        monthsList.add("Dec");

        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(EventMonthHistogram);

        EventMonthHistogram.getAxisLeft().setEnabled(true);







        EventMonthHistogram.invalidate();

        return new BarAxis(null, EventMonthHistogram, (ArrayList<String>) monthsList);
    }

    public BarAxis countryBar(ArrayList<Activity> data){
        BarChart chart = new HorizontalBarChart((getApplicationContext()));

        Map<String, Integer> countryCounts = new HashMap<>();
        for (Activity event : data) {
            String country = event.getCountry();
            if (countryCounts.containsKey(country)) {
                countryCounts.put(country, countryCounts.get(country) + 1);
            } else {
                countryCounts.put(country, 1);
            }
        }

        // create a list of BarEntry objects
        List<BarEntry> entries = new ArrayList<>();
        List<String> countries = new ArrayList<>(); // create a list to store the country names
        int i = 0;
        for (Map.Entry<String, Integer> entry : countryCounts.entrySet()) {
            String country = entry.getKey();
            int count = entry.getValue();
            entries.add(new BarEntry(i++, count));
            countries.add(country); // add the country name to the list
        }

        // create a BarDataSet with the entries and style it
        BarDataSet dataSet = new BarDataSet(entries, "Country Counts");
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(16f);

        // create a BarData object with the data set
        BarData barData = new BarData(dataSet);



        // get the BarChart view from the layout
//        BarChart chart = findViewById(R.id.chart);

        // customize the chart's appearance
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.setMaxVisibleValueCount(50);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);
        chart.setDrawGridBackground(false);
        chart.setFitBars(true);
        // set the X-axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f); // set the granularity to 1 unit
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // set the position to bottom
        xAxis.setValueFormatter(new IndexAxisValueFormatter(countries)); // set the country names as labels

        // set the Y-axis
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setGranularity(1f); // set the granularity to 1 unit
        leftAxis.setAxisMinimum(0f); // set the minimum value to 0
        leftAxis.setValueFormatter(new IndexAxisValueFormatter()); // set the value formatter to null

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false); // disable the right Y-axis

        Description description = new Description();
        description.setText("Breakdown of Events by Country");
        chart.setDescription(description);

        // set the chart data and refresh it
        chart.setData(barData);
        chart.invalidate();

        // set the chart legend
        Legend legend = chart.getLegend();
        legend.setEnabled(false); // disable the legend
        return new BarAxis(null, chart, (ArrayList<String>) countries);
    }
}



