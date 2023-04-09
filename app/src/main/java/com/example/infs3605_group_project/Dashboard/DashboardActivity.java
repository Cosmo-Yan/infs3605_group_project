package com.example.infs3605_group_project.Dashboard;



import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import com.example.infs3605_group_project.Activity.Activity;
import com.example.infs3605_group_project.Activity.ActivityDatabase;
import com.example.infs3605_group_project.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.github.mikephil.charting.charts.BarChart;
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
    private ArrayList<ChartObjects> Charts= new ArrayList<>();

    private Spinner yearSpinner;
    private Spinner countrySpinner;

    private Filter filters = new Filter("Year", "Country");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //Load xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        setTitle("Dashboard");

        //Connect Local Variable to xml elements
        GraphDash = findViewById(R.id.Graph_Recycler);
        StatDash = findViewById(R.id.Stat_grid);


        Dataset = getDefaultDataset();



        setAdapters();







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
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_menu, menu);

        MenuItem yearSpinnerItem = menu.findItem(R.id.year_spinner);
        final Spinner yearSpinner = (Spinner) yearSpinnerItem.getActionView();

        final ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getYears());

        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(yearSpinner.getSelectedItem().toString());
                filters.setYear(yearSpinner.getSelectedItem().toString());
                filterData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        MenuItem countrySpinnerItem = menu.findItem(R.id.country_spinner);
        countrySpinner = (Spinner) countrySpinnerItem.getActionView();

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getCountries()
        );

        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryAdapter);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filters.setCountry(countrySpinner.getSelectedItem().toString());
                filterData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Default:
                recreate();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ArrayList<String> getYears() {
        ArrayList<String> years = new ArrayList<>();
        ArrayList<Activity> temp = new ArrayList<>();
        temp = getDefaultDataset();

        HashSet noDuplicate = new HashSet();
        List<Integer> DateHistogramListMonth = new ArrayList<>();
        for (Activity events : temp){
            String str[] = events.getEventStartDate().split("/");
            String year = str[2];
            noDuplicate.add(year);
        }
        years.addAll(noDuplicate);
        Collections.sort(years);
        years.add(0, "Year");


        System.out.println(years);


        return years;
    }

    private ArrayList<String> getCountries() {
        ArrayList<String> countries = new ArrayList<>();

        ArrayList<Activity> temp = new ArrayList<>();
        temp = getDefaultDataset();

        HashSet noDuplicate = new HashSet();
        List<Integer> DateHistogramListMonth = new ArrayList<>();
        for (Activity events : temp){
            String country = events.getCountry();
            noDuplicate.add(country);
        }

        countries.addAll(noDuplicate);
        Collections.sort(countries);
        countries.add(0, "Country");

        return countries;
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

    private ArrayList<Stat> getStats(ArrayList<Activity> set){
        //Create Stats
        //Number of Events
        ArrayList<Stat> statsList = new ArrayList<Stat>();
        statsList.add(new Stat(set.size(), "Number of Events Overall:"));

        //
        HashSet noDuplicate = new HashSet();
        for (Activity active: set){
            noDuplicate.add(active.getCountry());
        }
        statsList.add(new Stat(noDuplicate.size(), "Number of Countries Participating:"));
        for (Activity active: set){
            noDuplicate.add(active.getLocation());
        }
        statsList.add(new Stat(noDuplicate.size(), "Number of Active Locations:"));

        return statsList;
    }

    private ArrayList<ChartObjects> getGraphs(ArrayList<Activity> set){
        ArrayList<ChartObjects> gGraph = new ArrayList<>();
        gGraph.add(new PieObject(null, typePieChart(Dataset)));
        gGraph.add(new BarAxis(null, monthBarChart(Dataset).getChart(), monthBarChart(Dataset).getAxisSuper() ));
        gGraph.add(new BarAxis(null, countryBar(Dataset).getChart(), countryBar(Dataset).getAxisSuper()));

        return gGraph;
    }
    private void setAdapters(){
        //Create Graph Objects
        //Piechart of Event Types Example


        //Generate the adapter for the Recycler
        //GridDashAdapter = new DashboardViewAdapter();
        GraphDash.setLayoutManager((new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)));
        ChartAdapter cAdapter = new ChartAdapter(getGraphs(Dataset), new DashClickInterface() {
            @Override
            public void onBarItemClick(BarChart bar, ArrayList<String> Axis) {
                Intent intent = new Intent(DashboardActivity.this, DashboardGeneralDetailActivity.class);
                // Serialize the chart data to a byte array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenWidth = displayMetrics.widthPixels;
                int screenHeight = displayMetrics.heightPixels;

                bar.measure(View.MeasureSpec.makeMeasureSpec(screenHeight, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(screenWidth, View.MeasureSpec.EXACTLY));

                bar.layout(0, 0, bar.getMeasuredWidth(), bar.getMeasuredHeight());

                bar.getXAxis().setValueFormatter(new IndexAxisValueFormatter(Axis));
                bar.getLegend().setEnabled(false);
                bar.getXAxis().setLabelCount(Axis.size(), false);
                bar.getDescription().setEnabled(false);
                bar.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);



                bar.getXAxis().setDrawGridLines(false);
                bar.getXAxis().setGranularity(0.5f); // only intervals of 1 day



                bar.setVisibleXRange(1f, Axis.size());
                bar.getAxisLeft().setAxisMinimum(0);

                bar.getBarData().setValueTextSize(12f);

                bar.getAxisLeft().setDrawGridLines(false);
                bar.getXAxis().setDrawGridLines(false);
                bar.getAxisRight().setDrawGridLines(false);


                bar.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                bar.getXAxis().setLabelRotationAngle(-90);

                Bitmap barMap = bar.getChartBitmap();

                barMap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                byte[] chartData = stream.toByteArray();

                // Pass the chart data as an extra to the new activity
                intent.putExtra("chartData", chartData);
                startActivity(intent);
            }

            @Override
            public void onPieItemClick(PieChart pieC) {
                Intent intent = new Intent(DashboardActivity.this, DashboardGeneralDetailActivity.class);
                // Serialize the chart data to a byte array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenWidth = displayMetrics.widthPixels;
                int screenHeight = displayMetrics.heightPixels;

                pieC.measure(View.MeasureSpec.makeMeasureSpec(screenHeight, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(screenWidth, View.MeasureSpec.EXACTLY));

                pieC.layout(0, 0, pieC.getMeasuredWidth(), pieC.getMeasuredHeight());

                pieC.setUsePercentValues(true);
                pieC.setEntryLabelColor(Color.BLACK);
                pieC.getLegend().setEnabled(false);
                pieC.setDrawCenterText(false);
                pieC.getDescription().setEnabled(false);

                Bitmap pieMap = pieC.getChartBitmap();

                pieMap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                byte[] chartData = stream.toByteArray();

                // Pass the chart data as an extra to the new activity
                intent.putExtra("chartData", chartData);
                startActivity(intent);
            }
        });
        cAdapter.notifyDataSetChanged();
        GraphDash.setAdapter(cAdapter);
        StatAdapter sAdapter = new StatAdapter( this, getStats(Dataset));
        StatDash.setAdapter(sAdapter);
    }
    private void filterData(){
        Dataset.clear();
        Dataset.addAll(getDefaultDataset());
        ArrayList<Activity> filteredData = new ArrayList<>();

//        String year = yearSpinner.getSelectedItem().toString();
        ArrayList<Activity> filter = new ArrayList<>();


        ArrayList<Activity> temp = new ArrayList<>();
        ArrayList<String> yearselect = new ArrayList<>();
        for (Activity events : Dataset) {
            String str[] = events.getEventStartDate().split("/");
            String years1 = str[2];
            yearselect.add(years1);
        }

        Boolean checkCountry = new Boolean(true);
        if (filters.getCountry() == null || filters.getCountry() == "Country"){
            checkCountry = false;
        }

        Boolean checkYear = new Boolean(true);
        if (filters.getYear() == null || filters.getYear() == "Year"){
            checkYear = false;
        }

        Boolean checkBoth = new Boolean(false);
        if (checkCountry == true && checkYear == true){
            checkBoth = true;
            checkCountry = false;
            checkYear = false;
        }



        System.out.println("year" + checkYear);
        System.out.println("country" + checkCountry);
        System.out.println("both" + checkBoth);


        if (checkBoth == true){
            for (Activity act: Dataset){
                if (act.getCountry().contains(filters.getCountry())
                        && yearselect.get(Dataset.indexOf(act)).contains(filters.getYear())){
                    filteredData.add(act);
                }
            }
        }
        else if (checkYear == true){
            for (Activity act: Dataset){
                if (yearselect.get(Dataset.indexOf(act)).contains(filters.getYear())){
                    filteredData.add(act);
                }
            }
        }
        else if (checkCountry == true){
            for (Activity act: Dataset){
                if (act.getCountry().contains(filters.getCountry())){
                    filteredData.add(act);
                }
            }
        }
        else{
            return;
        }

            Dataset.clear();
            Dataset.addAll(filteredData);
            setAdapters();
        }
    }




