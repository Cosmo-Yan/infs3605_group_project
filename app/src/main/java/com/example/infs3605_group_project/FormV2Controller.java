package com.example.infs3605_group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

public class FormV2Controller extends AppCompatActivity {
    //The following section is to make the country auto complete text view work
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
            "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini (formerly Swaziland)", "Ethiopia",
            "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece",
            "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras",
            "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel",
            "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo",
            "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya",
            "Liechtenstein", "Lithuania", "Luxembourg", "Madagascar", "Malawi", "Malaysia",
            "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico",
            "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique",
            "Myanmar (formerly Burma)", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand",
            "Nicaragua", "Niger", "Nigeria", "North Korea", "North Macedonia (formerly Macedonia)",
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
            "Belgium", "France", "Italy", "Germany", "Spain"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_v2);

        //For the country auto complete textview
        AutoCompleteTextView countryAutoComplete = findViewById(R.id.countryAC);
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, countries);
        countryAutoComplete.setAdapter(countryAdapter);

        //For the event type auto complete textview
        ArrayAdapter<String> eventAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, events);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.eventAdapter);
        textView.setAdapter(eventAdapter);


        /* The code below is for the drop downs for Event type*/
        //This is the drop down for the events type/activity type
        Spinner spinner = (Spinner) findViewById(R.id.events_spinner);
        // Creates an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.events_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setPrompt("Select an event*");

        spinner.setAdapter(
                new FormV2Adapter(
                        adapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));

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
                    myEditText.setError("Invalid input format. Please enter one letter followed by 7 numbers (e.g., z123456).");
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
    }
}