package com.example.infs3605_group_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.Executors;

// Database imports
//import com.example.infs3605_group_project.Data.UserDatabase;
//import com.example.infs3605_group_project.Data.User;
//import com.example.infs3605_group_project.Data.UserDao;

public class LoginPage extends AppCompatActivity {

    // The database - need to work out how multiple
//    private UserDatabase mDb;

    // Fields from the view (UI elements) being classwide variables
    EditText username;
    EditText password;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        // load/define database
//        mDb = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "courses-database").build();

        // Create an event listener to get actions from events
//        generateLogins();
//        showUsers();

        // Connect UI elements with respective variables
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Context context = LoginPage.this;
//                tryLogin(username.getText().toString(),password.getText().toString(), context);
//                return;

                if (username.getText().toString().equals("user") && password.getText().toString().equals("1234")) {
                    Toast.makeText(LoginPage.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    gotoFormActivity();
                } else {
                    Toast.makeText(LoginPage.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Change scene to form page
    public void gotoFormActivity(){
        Intent intent = new Intent(this, FormV2Controller.class);
        startActivity(intent);
    }

    // Check the login with the database - currently not working due to context/toast
//    public void tryLogin(String username,String password, Context context){
//        Executors.newSingleThreadExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//                User user = mDb.userDao().getUser(username);
//                if(user.getPassword().equals(password)) {
//                    Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show();
//                    gotoFormActivity();
//                    return;
//                } else{
//                    Toast.makeText(context, "Login Failed!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//            }
//        });
//    }
//
//    // Generate dummy data for room if its your first time
//    public void generateLogins(){
//        Executors.newSingleThreadExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//                User tempUser = new User("user","1234",1);
//                mDb.userDao().insertUser(tempUser);
//                tempUser = new User("OtherUser","1234",2);
//                mDb.userDao().insertUser(tempUser);
//                tempUser = new User("third","",3);
//                mDb.userDao().insertUser(tempUser);
//                tempUser = new User("was","",1);
//                mDb.userDao().insertUser(tempUser);
//
//            }
//        });
//    }
//
//    public void showUsers(){
//        Executors.newSingleThreadExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//                List<User> users = mDb.userDao().getUser();
//                for(User user: users){
//                    Log.i("User recorded",user.getUsername());
//                }
//
//            }
//        });
//    }

}