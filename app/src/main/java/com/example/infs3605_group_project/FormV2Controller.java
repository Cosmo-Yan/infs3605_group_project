package com.example.infs3605_group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

public class FormV2Controller extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_v2);

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

        //This is the drop down for the different countries
        //NOTE: Currently, we are unable to show all countries. Currently stopped at India
        Spinner Countryspinner = (Spinner) findViewById(R.id.country_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this,
                R.array.countries_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        Countryspinner.setAdapter(adapter);
        Countryspinner.setPrompt("Select country*");

        Countryspinner.setAdapter(
                new FormV2Adapter(
                        adapter,
                        R.layout.contact_spinner_row_nothing_selected2,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));

        //---------------------------------------------------------------------------------//



        EditText myEditText = findViewById(R.id.orgName);

        myEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Define the regular expression for the specific format
                String regex = "^[z]\\d{7}$";

                /* String regex key:
                    ^: The start of the string
                    [A-Za-z]: One uppercase or lowercase letter
                    \\d{7}: Seven digits (numbers)
                    $: The end of the string
                */

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