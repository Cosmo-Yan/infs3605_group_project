package com.example.infs3605_group_project;


import android.util.Log;

import com.example.infs3605_group_project.Data.User;

import java.util.ArrayList;


public class UserData {
    // Static variable reference of single_instance
    // of type Singleton
    private static UserData single_instance = null;
    public ArrayList<User> users = new ArrayList<User>();
    public User loggedIn = null;


    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private UserData(){
        User tempUser = new User("user","1234",1,"Basic");
        users.add(tempUser);
        tempUser = new User("z1234567","i5",3,"Bob");
        users.add(tempUser);
    }

    // Static method
    // Static method to create instance of Singleton class
    public static synchronized UserData getInstance() {
        if (single_instance == null)
            single_instance = new UserData();

        return single_instance;
    }

    public ArrayList<User> getUsers(){
        return users;
    }

    public boolean Login(String username, String password){
        for(User tempUser: users){
            if(tempUser.getzId().equals(username)){
                Log.i("Username correct",username);
                if(tempUser.getPassword().equals(password)){
                    Log.i("Password Correct",password);
                    loggedIn = tempUser;
                    return true;
                }
            }
            Log.i("not User:",username+" - "+password);
        }
        return false;
    }

    public User getLoggedIn(){
        return loggedIn;
    }
}
