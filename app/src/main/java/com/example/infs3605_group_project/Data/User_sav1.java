//package com.example.infs3605_group_project.Data;
//
//import androidx.annotation.NonNull;
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;
//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//@Entity
//public class User {
//
//    @PrimaryKey
//    @NonNull
//    @SerializedName("username")
//    @Expose
//    private String username;     // IDK why its integer but I recall there being an error with int
//
//    @SerializedName("password")
//    @Expose
//    private String password;
//
//    @SerializedName("level")
//    @Expose
//    private Integer level;
//
//
//    public User(){} // Needed or database (room) won't work
//
//    public User(@NonNull String username, String password, int level) {
//        this.username = username;
//        this.password = password;
//        this.level = level;
//    }
//
//    @NonNull
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(@NonNull String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public Integer getLevel() {
//        return level;
//    }
//
//    public void setLevel(Integer level) {
//        this.level = level;
//    }
//}