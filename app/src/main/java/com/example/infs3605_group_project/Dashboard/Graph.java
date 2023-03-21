package com.example.infs3605_group_project.Dashboard;

public class Graph {
    private int imageResource;
    private String name;

    public Graph(int imageResource, String name) {
        this.imageResource = imageResource;
        this.name = name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getName() {
        return name;
    }
}
