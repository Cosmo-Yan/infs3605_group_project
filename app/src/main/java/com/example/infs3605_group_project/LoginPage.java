package com.example.infs3605_group_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class LoginPage extends AppCompatActivity {
    EditText username;
    EditText password;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UserData.getInstance().Login(username.getText().toString(),password.getText().toString())){
                    Toast.makeText(LoginPage.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),FeedActivity.class));
                } else {
                    Toast.makeText(LoginPage.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
//                if (username.getText().toString().equals("user") && password.getText().toString().equals("1234")) {
//                    Toast.makeText(LoginPage.this, "Login Successful!", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getApplicationContext(),FeedActivity.class));
//
//                } else {
//                    Toast.makeText(LoginPage.this, "Login Failed!", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
}