package com.example.infs3605_group_project.Dashboard;

import android.graphics.Bitmap;

public class Graph {
    private Bitmap imageGraph;
    private String name;

    public Graph(Bitmap imageGraph, String name) {
        this.imageGraph = imageGraph;
        this.name = name;
    }

    public Bitmap getImageGraph() {
        return imageGraph;
    }

    public void setImageGraph(Bitmap imageGraph) {
        this.imageGraph = imageGraph;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
