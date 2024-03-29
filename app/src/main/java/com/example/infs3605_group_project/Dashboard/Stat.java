package com.example.infs3605_group_project.Dashboard;

/**
 * Holds a number and the name of the statistic
 * Needed to attach a label to the stat to be displayed
 */
public class Stat {
    private int number;
    private String name;

    public Stat(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
