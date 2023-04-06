package com.example.infs3605_group_project.Data;

public class User{

    private String username;
    private String password;

    private Integer level;      // 1 = admin with analytics, 2 = basic without analytics
    private String name;

    public User(String username, String password, int level) {
        this.username = username;
        this.password = password;
        this.level = level;
    }

    public User(String username, String password, int level, String name) {
        this.username = username;
        this.password = password;
        this.level = level;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
