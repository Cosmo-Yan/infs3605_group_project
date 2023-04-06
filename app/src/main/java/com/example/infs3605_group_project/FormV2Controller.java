package com.example.infs3605_group_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.Executors;

public class FormV2Controller extends AppCompatActivity {

    private final int GALLERY_REQ_CODE = 1000; //For image upload feature
    ImageView imgGallery;

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
        Log.d("Database debug","Step 1 - Load Room");
        mDb = Room.databaseBuilder(getApplicationContext(), ActivityDatabase.class, "courses-database").build();
        Log.d("Database debug","Step 2 Room loaded, Load Data");
        getData();
        Log.d("Database debug","Step 3");

//        Singleton.getInstance(Room.databaseBuilder(getApplicationContext(), ActivityDatabase.class, "activities").build());
//        Singleton.getInstance().resetData();
//        activityList = Singleton.getInstance().getData();
//        Singleton.getInstance().viewData();

        //For the country auto complete textview
        AutoCompleteTextView countryAutoComplete = findViewById(R.id.countryAC);
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, countries);
        countryAutoComplete.setAdapter(countryAdapter);

        //For the upload image button
        imgGallery = findViewById(R.id.imageView2);
        Button btnGallery = findViewById(R.id.uploadImage);

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });

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

    }
    private void getData() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mDb.activityDao().deleteAll();
                for(int i = 0; i<10; i++){
                    Activity activity = new Activity(i, "Event Named "+String.valueOf(i),"z100000"+String.valueOf(i),"UNSW or someone","Relations Building","not AU","Australia or somewhere","01/01/2001","Yes, there's more","Image URL here");
                    mDb.activityDao().insertActivity(activity);
                }
                activityList = mDb.activityDao().getActivities();
                for(Activity temp: activityList){
                    Log.i("Activity",String.valueOf(temp.getId())+" "+temp.getEventName());
                }
            }
        });
    }

    //This method is for the upload image feature
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("imgTest", String.valueOf(data.getData()));
        imgGallery.setImageURI(Uri.parse("https://images.unsplash.com/photo-1542370285-b8eb8317691c?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxleHBsb3JlLWZlZWR8Mnx8fGVufDB8fHx8&w=1000&q=80"));

        if (resultCode==RESULT_OK){

            if(requestCode==GALLERY_REQ_CODE){
            Log.i("imgTest2", "success");
//                imgGallery.setImageURI(data.getData());
            }
        }
    }

}