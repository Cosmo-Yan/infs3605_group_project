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
import com.example.infs3605_group_project.Data.GenericMethods;

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

//        myEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // Do nothing
//            }
//
//            //   String regex key:
//            //       ^: The start of the string
//            //       [A-Za-z]: One uppercase or lowercase letter
//            //       \\d{7}: Seven digits (numbers)
//            //       $: The end of the string
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // Define the regular expression for the specific format
//                String regex = "^[z]\\d{7}$";
//
//                // Check if the input matches the regular expression
//                if (!s.toString().matches(regex)) {
//                    // If the input does not match the regular expression, show an error message
//                    myEditText.setError("Enter the name of the organiser");
//                } else {
//                    // If the input matches the regular expression, clear the error message
//                    myEditText.setError(null);
//                }
//            }
//
//
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // Do nothing
//            }
//        });

        Button submit = findViewById(R.id.saveButton);
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                submit();
//            }
//        });
    }

    // Whenever the activity is submitted, it is entered into the database
    public void submit(View v) {
        submit();
    }

    public void submit(){
        boolean inputError = false;


        // Extract and validate data on page
        EditText temp = findViewById(R.id.eventName);
        String eventName = temp.getText().toString();
        if(eventName.length()==0){
            temp.setError("Please Enter an Event Name");
            inputError = true;
        }

        temp = findViewById(R.id.orgName);
        String orgName = temp.getText().toString();
        if(eventName.length()==0){
            temp.setError("Please Enter an Organisation Name");
            inputError = true;
        }

        AutoCompleteTextView tempAC = findViewById(R.id.countryAC);
        String country = tempAC.getText().toString();
        if(country.length()==0){
            temp.setError("Please Enter the name of the main country involved, if there are multiple countries please write the host or main country");
            inputError = true;
        }


        temp = findViewById(R.id.location);
        String location = temp.getText().toString();
        if(location.length()==0){
            temp.setError("Please Enter a location, if the event was online write Online");
            inputError = true;
        }

        temp = findViewById(R.id.startDate);
        String startDate = temp.getText().toString();
        if(!GenericMethods.isDate(startDate)){
            temp.setError("Please Enter a valid Date as dd/mm/yyyy i.e. 01/01/2000");
            inputError = true;
        }


        temp = findViewById(R.id.furtherDetails);
        String details = temp.getText().toString();

        tempAC = findViewById(R.id.eventAC);
        String eventType = tempAC.getText().toString();
        if(eventName.length()==0){
            temp.setError("Please Enter an Event Type");
            inputError = true;
        }

        if(inputError){
            return;
        }


        //Note ID should be autogenerated and zid should be based on login data class
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

    // Highly unoptimised method to see if what's entered was an integer, to be optimised at launch
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(Exception e) {
            Log.d("Error notInt","Nothing entered");
            return false;
        }

        // only got here if we didn't return false
        return true;
    }
}