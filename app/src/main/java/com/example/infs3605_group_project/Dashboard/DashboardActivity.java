package com.example.infs3605_group_project.Dashboard;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.infs3605_group_project.AccountActivity;
import com.example.infs3605_group_project.Activity.Activity;
import com.example.infs3605_group_project.Activity.ActivityDatabase;
import com.example.infs3605_group_project.FeedActivity;
import com.example.infs3605_group_project.NewEventActivity;
import com.example.infs3605_group_project.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

/**
 * Handles the Dashboard Activity and dashboard.xml
 */
public class DashboardActivity extends AppCompatActivity {
    private GridView StatDash;
    private RecyclerView GraphDash;
    private ArrayList<Activity> Dataset = new ArrayList<>();
    private ActivityDatabase mDb;
    private ArrayList<ChartObjects> Charts= new ArrayList<>();
    private Spinner yearSpinner;
    private Spinner countrySpinner;
    private Filter filters = new Filter("Year", "Country");
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //Loads xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        //Sets Title
        setTitle("Dashboard");

        //Connect Local Variable to xml elements
        GraphDash = findViewById(R.id.Graph_Recycler);
        StatDash = findViewById(R.id.Stat_grid);
        bottomNavigationView= findViewById( R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        //Fills Dataset variable with Data from Database
        Dataset = getDefaultDataset();
        //Sets the adapters for the RecyclerView and GridView
        setAdapters();
        //Handles Navigation Bar
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.dashboard:
                        return true;

                    case R.id.feed:
                        startActivity(new Intent(getApplicationContext(), FeedActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.newEvent:
                        startActivity(new Intent(getApplicationContext(), NewEventActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }


                return false;
            }
        });
    }

    /**
     * Returns All Data from Database
     * @return ArrayList of Activity objects from Database.
     */
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

        // Shuts down the Executor
        executor.shutdown();

        try {
            // Waits for Execution to be over
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
        }
        return defaultArray;
    }

    /**
     *Inflates Options Menu and Toolbox Spinners. Uses dashboard_menu.xml
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_menu, menu);

        //Connects MenuItem to code
        MenuItem yearSpinnerItem = menu.findItem(R.id.year_spinner);
        final Spinner yearSpinner = (Spinner) yearSpinnerItem.getActionView();
        //Defines, creates and sets the Adapter
        final ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getYears());
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        //Defines OnItemSelect Handling
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Sets local Filter Variable attribute to equal selected text.
                filters.setYear(yearSpinner.getSelectedItem().toString());
                //Filters Data
                filterData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Connects MenuItem to code
        MenuItem countrySpinnerItem = menu.findItem(R.id.country_spinner);
        countrySpinner = (Spinner) countrySpinnerItem.getActionView();

        //Defines, creates and sets the Adapter
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getCountries()
        );
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryAdapter);

        //Defines OnItemSelect Handling
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Sets local Filter Variable attribute to equal selected text.
                filters.setCountry(countrySpinner.getSelectedItem().toString());
                //Filters Data
                filterData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handles Default menuItem onClick
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.Default:
                //Resets all filters
                recreate();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Returns an ArrayList of years events were held during within the currently selected Dataset
     * @return ArrayList of Years events were held during
     */
    private ArrayList<String> getYears() {
        ArrayList<String> years = new ArrayList<>();
        ArrayList<Activity> temp = new ArrayList<>();
        temp = getDefaultDataset();

        HashSet noDuplicate = new HashSet();
        List<Integer> DateHistogramListMonth = new ArrayList<>();
        for (Activity events : temp){
            //Splits Date String into Day, Month and Year
            String str[] = events.getEventStartDate().split("/");
            //Sets variable to year
            String year = str[2];
            //Saves year to Hashset to ensure no duplicates in list
            noDuplicate.add(year);
        }
        years.addAll(noDuplicate);
        //Sorts in chronological order
        Collections.sort(years);
        //Adds default text
        years.add(0, "Year");
        return years;
    }

    /**
     * Returns an ArrayList of Countries events were held in cooperation with within the currently selected Dataset
     * @return List of all Countries events have been held in cooperation with
     */
    private ArrayList<String> getCountries() {
        ArrayList<String> countries = new ArrayList<>();

        ArrayList<Activity> temp = new ArrayList<>();
        temp = getDefaultDataset();

        //Uses Hashset to save to endure no Duplicates
        HashSet noDuplicate = new HashSet();
        List<Integer> DateHistogramListMonth = new ArrayList<>();
        for (Activity events : temp){
            String country = events.getCountry();
            noDuplicate.add(country);
        }

        countries.addAll(noDuplicate);
        //Sorts in Alphabetical order
        Collections.sort(countries);
        //Adds default text
        countries.add(0, "Country");
        return countries;
    }

    /**
     * Creates a PieChart that breaks down the types of events that were held within the dataset provided
     * @param Data Data to be used in PieChart Creation
     * @return PieChart of Selected Data, Event-Type Breakdown
     */
    public PieChart typePieChart(ArrayList<Activity> Data){
        PieChart typePieChart = new PieChart(getApplicationContext());

        //Creates Data Entry Objects, One for each event
        List<PieEntry> entries = new ArrayList<>();
        for (Activity activity : Data){
            float value = 1;
            String label = activity.getEventType();
            entries.add(new PieEntry(value, label));
        }

        //Creates a PieDataSet from the DataEntryObjects
        PieDataSet pieSet = new PieDataSet(entries, "Event Type Breakdown");

        //Combines Entries from the PieDataSet into a Hashset to Obtain a single label and the count of the label
        HashMap<String, Float> labelValues = new HashMap<>();
        for (PieEntry entry : pieSet.getValues()) {
            String label = entry.getLabel();
            float value = entry.getValue();
            if (labelValues.containsKey(label)) {
                labelValues.put(label, labelValues.get(label) + value);
            } else {
                labelValues.put(label, value);
            }
        }

        //Converts the Hashset into PieEntry objects
        List<PieEntry> combinedEntries = new ArrayList<>();
        for (String label : labelValues.keySet()) {
            float value = labelValues.get(label);
            combinedEntries.add(new PieEntry(value, label));
        }

        //Combines the PieEntryObjects into a new PieDataSet with only 3 entries and the proper
        PieDataSet combinedDataSet = new PieDataSet(combinedEntries, "Combined Data");

        //Alters the Colors and text of the Data when displayed
        combinedDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        combinedDataSet.setValueTextColor(Color.BLACK);
        combinedDataSet.setValueTextSize(12f);

        //Converts the Dataset into Data that can be displayed
        PieData data = new PieData(combinedDataSet);

        //Alters the looks of the Data when Displayed
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(typePieChart));

        //Binds the Data onto the Piechart Object
        typePieChart.setData(data);

        //Sets the Description of the PieChart
        Description description = new Description();
        description.setText("Breakdown of Events by Type");
        typePieChart.setDescription(description);

        //Updates the PieChart
        typePieChart.invalidate();

        return typePieChart;
    }

    /**
     * Creates a BarChart that breaks down when each event occured
     * @param Data Data to be used in BarChart Creation
     * @return BarChart of Selected Data, Event Breakdown by Month
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public BarAxis monthBarChart(ArrayList<Activity> Data){
        BarChart EventMonthHistogram = new HorizontalBarChart((getApplicationContext()));

        //Sets Description of the BarChart
        Description description = new Description();
        description.setText("Breakdown of Events by Month");
        EventMonthHistogram.setDescription(description);

        //Counts Month Occurrences
        Map<String, Integer> monthCounts = new HashMap<>();
        for (Activity event : Dataset) {
            String str[] = event.getEventStartDate().split("/");
            int month = Integer.valueOf(str[1]);
            String monthName = getMonthName(String.valueOf(month));
            monthCounts.put(monthName, monthCounts.getOrDefault(monthName, 0) + 1);
        }

        // Creates BarEntry objects
        List<BarEntry> barEntries = new ArrayList<>();

        // Creates ArrayList of Axis values
        ArrayList<String> monthList = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            String monthName = getMonthName(String.valueOf(i));
            int count = monthCounts.getOrDefault(monthName, 0);
            barEntries.add(new BarEntry(i - 1, count));
            monthList.add(monthName);
        }

        //Converts the BarEntryObjects into a BarDataSet
        BarDataSet dataSet = new BarDataSet(barEntries, "Events Held");

        //Changes Data Appearances when Bound
        dataSet.setBarBorderWidth(0.5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        //Converts Dataset to Data that can be set to PieChart
        BarData barData = new BarData(dataSet);

        //Changes Data Appearances when Bound
        barData.setBarWidth(1f);

        //Sets Data
        EventMonthHistogram.setData(barData);

        //Alters Look of BarChart
        EventMonthHistogram.getAxisLeft().setEnabled(true);

        //Updates BarChart
        EventMonthHistogram.invalidate();

        //Creates BarAxis object with the BarChart and Axis value ArrayList. the null is for the supertype
        return new BarAxis(null, EventMonthHistogram, (ArrayList<String>) monthList);
    }

    public BarAxis countryBar(ArrayList<Activity> data){
        BarChart chart = new HorizontalBarChart((getApplicationContext()));

        //Counts Country Occurances, uses Hashmap to prevent duplicates
        Map<String, Integer> countryCounts = new HashMap<>();
        for (Activity event : data) {
            String country = event.getCountry();
            if (countryCounts.containsKey(country)) {
                countryCounts.put(country, countryCounts.get(country) + 1);
            } else {
                countryCounts.put(country, 1);
            }
        }

        //Creates BarEntry objects
        List<BarEntry> entries = new ArrayList<>();

        //Creates Arraylist of Axis values
        List<String> countries = new ArrayList<>();
        int i = 0;
        for (Map.Entry<String, Integer> entry : countryCounts.entrySet()) {
            String country = entry.getKey();
            int count = entry.getValue();
            entries.add(new BarEntry(i++, count));
            countries.add(country); // add the country name to the list
        }

        //Creates a BarDataSet with the BarEntries
        BarDataSet dataSet = new BarDataSet(entries, "Country Counts");
        //Alters look of data when bound
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(16f);

        //Converts to BarData to be bound to BarChart Object
        BarData barData = new BarData(dataSet);

        //Sets Description
        Description description = new Description();
        description.setText("Breakdown of Events by Country");
        chart.setDescription(description);

        //Sets data to BarChart and refreshes it
        chart.setData(barData);
        chart.invalidate();

        //Creates BarAxis object with the BarChart and Axis value ArrayList. the null is for the supertype
        return new BarAxis(null, chart, (ArrayList<String>) countries);
    }

    /**
     * Creates Stat Objects to be displayed in the GridView
     * @param set The Dataset to be used
     * @return The ArrayList of Statistics
     */
    private ArrayList<Stat> getStats(ArrayList<Activity> set){
        ArrayList<Stat> statsList = new ArrayList<Stat>();

        //Counts number of Activities
        statsList.add(new Stat(set.size(), "Number of Events Overall:"));

        //Counts number of Countries Cooperated with
        HashSet noDuplicate = new HashSet();
        for (Activity active: set){
            noDuplicate.add(active.getCountry());
        }
        statsList.add(new Stat(noDuplicate.size(), "Number of Countries Participating:"));

        //Counts number of locations Events were held in
        for (Activity active: set){
            noDuplicate.add(active.getLocation());
        }
        statsList.add(new Stat(noDuplicate.size(), "Number of Active Locations:"));

        return statsList;
    }

    /**
     * Method that runs all ChartObject creation methods
     * @return ArrayList of ChartObjects
     */
    private ArrayList<ChartObjects> getGraphs(){
        ArrayList<ChartObjects> gGraph = new ArrayList<>();
        gGraph.add(new PieObject(null, typePieChart(Dataset)));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            gGraph.add(new BarAxis(null, monthBarChart(Dataset).getChart(), monthBarChart(Dataset).getAxisSuper() ));
        }
        gGraph.add(new BarAxis(null, countryBar(Dataset).getChart(), countryBar(Dataset).getAxisSuper()));
        return gGraph;
    }
    private void setAdapters(){
        //Creates and Sets adapter for RecyclerView
        //Also Defines and handles the onClick Method for RecyclerView items
        GraphDash.setLayoutManager((new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)));
        ChartAdapter cAdapter = new ChartAdapter(getGraphs(), new DashClickInterface() {
            @Override
            public void onBarItemClick(BarChart bar, ArrayList<String> Axis) {
                Intent intent = new Intent(DashboardActivity.this, DashboardGeneralDetailActivity.class);
                //For transferring data to other class
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //Defines the dimensions of the image of the graph produced to display
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenWidth = displayMetrics.widthPixels;
                int screenHeight = displayMetrics.heightPixels;
                bar.measure(View.MeasureSpec.makeMeasureSpec(screenHeight, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(screenWidth, View.MeasureSpec.EXACTLY));
                bar.layout(0, 0, bar.getMeasuredWidth(), bar.getMeasuredHeight());

                //Alters the Image to be displayed
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

                //Creates the Bitmap
                Bitmap barMap = bar.getChartBitmap();
                barMap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] chartData = stream.toByteArray();

                //Passes the Bitmap and launches the DashboardGeneralDetailActivity
                intent.putExtra("chartData", chartData);
                startActivity(intent);
            }

            @Override
            public void onPieItemClick(PieChart pieC) {
                Intent intent = new Intent(DashboardActivity.this, DashboardGeneralDetailActivity.class);
                //For transferring data to other class
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                //Defines the dimensions of the image of the graph produced to display
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenWidth = displayMetrics.widthPixels;
                int screenHeight = displayMetrics.heightPixels;
                pieC.measure(View.MeasureSpec.makeMeasureSpec(screenHeight, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(screenWidth, View.MeasureSpec.EXACTLY));
                pieC.layout(0, 0, pieC.getMeasuredWidth(), pieC.getMeasuredHeight());

                //Alters the Image to be displayed
                pieC.setUsePercentValues(true);
                pieC.setEntryLabelColor(Color.BLACK);
                pieC.getLegend().setEnabled(false);
                pieC.setDrawCenterText(false);
                pieC.getDescription().setEnabled(false);

                //Creates the Bitmap
                Bitmap pieMap = pieC.getChartBitmap();
                pieMap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] chartData = stream.toByteArray();

                //Passes the Bitmap and launches the DashboardGeneralDetailActivity
                intent.putExtra("chartData", chartData);
                startActivity(intent);
            }
        });
        //Continues setting and creating adapters
        cAdapter.notifyDataSetChanged();
        GraphDash.setAdapter(cAdapter);
        StatAdapter sAdapter = new StatAdapter( this, getStats(Dataset));
        StatDash.setAdapter(sAdapter);
    }

    /**
     * Filters the data in the Local Variable Dataset by the values of the two Spinners
     */
    private void filterData(){
        //Resets Dataset to default
        Dataset.clear();
        Dataset.addAll(getDefaultDataset());

        //For holding events to be kept
        ArrayList<Activity> filteredData = new ArrayList<>();

        //Splits Date Strings into Day, Month and Year
        //Saves the Year in an ArrayList
        ArrayList<Activity> temp = new ArrayList<>();
        ArrayList<String> yearselect = new ArrayList<>();
        for (Activity events : Dataset) {
            String str[] = events.getEventStartDate().split("/");
            String years1 = str[2];
            yearselect.add(years1);
        }

        //If statements to check which filter to run
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

        //Runs a different filter depending on the booleans above
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
            //If no filter is needed, ends method so Dataset remains in default
            return;
        }

        //Empties Dataset and fills it with entries from FilteredData
        Dataset.clear();
        Dataset.addAll(filteredData);
        //Resets Adapters using new Dataset
        setAdapters();
        }

        public String getMonthName(String num){
            int monthNum = Integer.parseInt(num);

            String monthName;
            switch (monthNum) {
                case 1:
                    monthName = "Jan";
                    break;
                case 2:
                    monthName = "Feb";
                    break;
                case 3:
                    monthName = "Mar";
                    break;
                case 4:
                    monthName = "Apr";
                    break;
                case 5:
                    monthName = "May";
                    break;
                case 6:
                    monthName = "Jun";
                    break;
                case 7:
                    monthName = "Jul";
                    break;
                case 8:
                    monthName = "Aug";
                    break;
                case 9:
                    monthName = "Sep";
                    break;
                case 10:
                    monthName = "Oct";
                    break;
                case 11:
                    monthName = "Nov";
                    break;
                case 12:
                    monthName = "Dec";
                    break;
                default:
                    monthName = "Invalid month number";
                    break;
            }

            return(monthName);
        }
    }