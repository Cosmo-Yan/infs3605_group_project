package com.example.infs3605_group_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.infs3605_group_project.Activity.Activity;
import com.example.infs3605_group_project.Activity.ActivityDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class FormV2Controller extends AppCompatActivity {
    //The following section is to make the country auto complete text view work
    private ActivityDatabase mDb;
    private List<Activity> activityList = new ArrayList<>();
    private static final String[] countries = new String[] {
            "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda",
            "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain",
            "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia",
            "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso",
            "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada",
            "Central African Republic (CAR)", "Chad", "Chile", "China", "Colombia", "Comoros",
            "Congo, Democratic Republic of the", "Congo, Republic of the", "Costa Rica",
            "Cote d'Ivoire", "Croatia", "Cuba", "Cyprus", "Czechia", "Denmark", "Djibouti",
            "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador",
            "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini", "Ethiopia",
            "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece",
            "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras",
            "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel",
            "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo",
            "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya",
            "Liechtenstein", "Lithuania", "Luxembourg", "Madagascar", "Malawi", "Malaysia",
            "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico",
            "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique",
            "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand",
            "Nicaragua", "Niger", "Nigeria", "North Korea", "North Macedonia",
            "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea",
            "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia",
            "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines",
            "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia",
            "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands",
            "Somalia", "South Africa", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname",
            "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania",
            "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey",
            "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom",
            "United States of America", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City",
            "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe"};

    private static final String[] events = new String[] {
            "Education Exchange", "Centre Opening (International)", "Centre Opening (Domestic)",
            "Relations Event", "Guest Speaker (International)", "Guest Speaker (Domestic)"};

    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> eventItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_v2);

        // Load the room database
        mDb = Room.databaseBuilder(getApplicationContext(), ActivityDatabase.class, "courses-database").fallbackToDestructiveMigration().build();
//        debugData();

        //For the country auto complete textview
        AutoCompleteTextView countryAutoComplete = findViewById(R.id.countryAC);
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, countries);
        countryAutoComplete.setAdapter(countryAdapter);

        //For the event type dropdown menu
        autoCompleteTextView = findViewById(R.id.eventAC);
        eventItems = new ArrayAdapter<String>(this,R.layout.event_type_dropdown_menu, events);

        autoCompleteTextView.setAdapter(eventItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(FormV2Controller.this, "Item " + item, Toast.LENGTH_SHORT).show();
            }
        });

        ArrayAdapter<String> eventAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, events);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.eventAC);
        textView.setAdapter(eventAdapter);

        /* This section below is the validation code for the 'Name of Organiser' edit view */

        EditText myEditText = findViewById(R.id.orgName);

        myEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            //   String regex key:
            //       ^: The start of the string
            //       [A-Za-z]: One uppercase or lowercase letter
            //       \\d{7}: Seven digits (numbers)
            //       $: The end of the string

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Define the regular expression for the specific format
                String regex = "^[z]\\d{7}$";

                // Check if the input matches the regular expression
                if (!s.toString().matches(regex)) {
                    // If the input does not match the regular expression, show an error message
                    myEditText.setError("Enter the name of the organiser");
                } else {
                    // If the input matches the regular expression, clear the error message
                    myEditText.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });

        Button submit = findViewById(R.id.saveButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    // Get data from database and log all the data for debugging and proof of concept
    private void resetData() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mDb.activityDao().deleteAll();
                Activity activity;
                activity = new Activity(1, "z1234568", "MAHE Academic exchange MoU signed", "H Vinod Bhat", "Education Exchange", "India", "Online", "29/06/2007", "\"The University of New South Wales has strengthened its teaching and research ties in India, signing a Memorandum of Understanding (MoU) with Manipal University last weekend (24 June).\"");
                mDb.activityDao().insertActivity(activity);
                activity = new Activity(2, "z1234567", "CATTS MOU signed", "Shri Sanjeev Kumar", "Centre (int/domestic)", "India", "\"New Dheli, India\"", "17/07/2017", " a memorandum of understanding (MoU) was signed between UNSW and IAHE");
                mDb.activityDao().insertActivity(activity);
                activity = new Activity(3, "z1234560", "new centre in India", "Harinder Sidhu", "Centre (int/domestic)", "India", "\"New Dheli, India\"", "19/07/2018", "UNSW Sydney's new India Centre in New Delhi is established");
                mDb.activityDao().insertActivity(activity);
                activity = new Activity(4, "z1234561", "CTET centre opened", "Zhongping Zhou", "Centre (int/domestic)", "China", "\"Shanghai, China\"", "21/11/2018", "first overseas research centre in china opened");
                mDb.activityDao().insertActivity(activity);
                activity = new Activity(5, "z12345682", "CTET agreement signed", "David Waite", "Centre (int/domestic)", "China", "\"Shanghai, China\"", "21/06/2019", "UNSW signed two agreements");
                mDb.activityDao().insertActivity(activity);
                activity = new Activity(6, "z1234567", "CATTS Indian delegations go to UNSW", "Shri Sanjeev Kumar", "Centre (int/domestic)", "India", "\"Sydney, Australia\"", "18/07/2019", "\"a high-level delegation from the Government of India to UNSW, as well as conversations as follow-up to the MoU and to further discuss the creation of the centre of excellence as indicated in the MoU.\"");
                mDb.activityDao().insertActivity(activity);
                activity = new Activity(7, "z12345684", "MAHE Academic exchange program set up", "H Vinod Bhat", "Education Exchange", "India", "Online", "5/08/2019", "partnership seeks to promote academic and educational exchange between UNSW and Manipal Academy of Higher Education and establishes a joint seed fund for research collaborations.");
                mDb.activityDao().insertActivity(activity);
                activity = new Activity(8, "z1234567", "CATTS Final draft agreement approved", "Shri Sanjeev Kumar", "Centre (int/domestic)", "India", "Online", "19/07/2020", "Final draft is agreed upon");
                mDb.activityDao().insertActivity(activity);
                activity = new Activity(9, "z1234567", "CATTS funding given", "Shri Sanjeev Kumar", "Centre (int/domestic)", "India", "Online", "16/07/2021", "New ");
                mDb.activityDao().insertActivity(activity);
                activity = new Activity(10, "z12345687", "INFS UNSW exhange program proposition", "UNSW business school", "Education Exchange", "China", "\"Shanghai, China\"", "27/01/2023", "30 students will go to china for the infs course and will gain international industry experience once the program is approved");
                mDb.activityDao().insertActivity(activity);
                activity = new Activity(11, "z12345688", "INFS UNSW exhange program start", "UNSW business school", "Education Exchange", "China", "\"Shanghai, China\"", "5/05/2023", "30 students applied and meet the criteria for the program");
                mDb.activityDao().insertActivity(activity);
                activity = new Activity(12, "z12345689", "INFS UNSW exhange program great wall visit", "UNSW business school", "Education Exchange", "China", "\"Shanghai, China\"", "12/05/2023", "Students went to the great wall and saw how the business operates and worked with process managers to understand the efficencies and bottlenecks that have naturally come with a  company of that size");
                mDb.activityDao().insertActivity(activity);
                activity = new Activity(13, "z12345690", "INFS UNSW exhange program end", "UNSW business school", "Education Exchange", "China", "\"Shanghai, China\"", "22/05/2023", "Students return to UNSW as the program comes to an end");
                mDb.activityDao().insertActivity(activity);
                activity = new Activity(14, "z12345691", "UNSW Japan exhange program", "Keiichi Tsuchiya", "Education Exchange", "Japan", "\"Tokyo, Japan\"", "20/05/2023", "30 UNSW student learning Japanese went on an exchange program and will be living in japan");
                mDb.activityDao().insertActivity(activity);
                activity = new Activity(15, "z12345692", "UNSW Japan exhange program", "Keiichi Tsuchiya", "Education Exchange", "Japan", "\"Tokyo, Japan\"", "25/05/2023", "Students visit Toyota and understand the role of translaters in meetings with individuals of high value");
                mDb.activityDao().insertActivity(activity);
                activity = new Activity(16, "z12345693", "UNSW Japan exhange program", "Keiichi Tsuchiya", "Education Exchange", "Japan", "\"Tokyo, Japan\"", "28/05/2023", "Students return to UNSW and the program ends for the term");
                mDb.activityDao().insertActivity(activity);
                activity = new Activity(17, "z12345694", "CATTS state inspected", "Shri Sanjeev Kumar", "Centre (int/domestic)", "India", "\"Sydney, Australia\"", "7/04/2024", "\"To see how what progress is being made with the CATTs centre, an inspection was organised to see how it would operate in normal circumstances\"");
                mDb.activityDao().insertActivity(activity);
                activity = new Activity(18, "z12345695", "MAHE international partners event", "H Vinod Bhat", "Relations event", "India", "\"New Dheli, India\"", "21/03/2023", "A regular social event was attended by our deligates to network with MAHE and other universities in India");
                mDb.activityDao().insertActivity(activity);
                activityList = mDb.activityDao().getActivities();
                for(Activity temp: activityList){
                    Log.i("Activity",String.valueOf(temp.getId())+" "+temp.getEventName());
                }
            }
        });
    }

    private void debugData() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                activityList = mDb.activityDao().getActivities();
                for(Activity temp: activityList){
                    Log.i("Activity",String.valueOf(temp.getId())+" "+temp.getEventName());
                }
            }
        });
    }


    // Whenever the activity is submitted, it is entered into the database
    public void submit(View v) {
        submit();
    }

    public void submit(){
        EditText temp = findViewById(R.id.eventName);
        String eventName = temp.getText().toString();
        temp = findViewById(R.id.orgName);
        String orgName = temp.getText().toString();
        AutoCompleteTextView tempAC = findViewById(R.id.countryAC);
        String country = tempAC.getText().toString();
        temp = findViewById(R.id.location);
        String location = temp.getText().toString();
        temp = findViewById(R.id.startDate);
        String startDate = temp.getText().toString();
        temp = findViewById(R.id.furtherDetails);
        String details = temp.getText().toString();
        tempAC = findViewById(R.id.eventAC);
        String eventType = tempAC.getText().toString();
        //Note ID should be autogenerated and zid should be based on login data - singleton?
        Activity activity = new Activity(eventName, UserData.getInstance().getLoggedIn().getzId(), orgName, eventType, country, location, startDate, details);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mDb.activityDao().insertActivity(activity);
                startActivity(new Intent(getApplicationContext(),FeedActivity.class));
            }
        });
    }

    public void cancel(View v){
        finish();
    }

}