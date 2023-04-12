package com.example.infs3605_group_project.Dashboard;

import com.github.mikephil.charting.components.XAxis;

import java.util.ArrayList;

/**
 * The Supertype for PieObject and BarAxis
 * For overriding the methods below
 */
public  class ChartObjects {

    /**
     * Constructor
     * @param chart Not Used
     */
    public ChartObjects(com.github.mikephil.charting.charts.Chart chart) {}

    /**
     * Overridden
     * @return
     */
    public com.github.mikephil.charting.charts.Chart getChart() {
        return null;
    }

    /**
     * Unused
     * @param chart
     */
    public void setChart(com.github.mikephil.charting.charts.Chart chart) {}

    /**
     * Overridden
     * @return
     */
    public ChartType getChartType(){
        return null;
    };

    /**
     * Overridden
     * @return
     */
    public ArrayList<String> getAxisSuper(){
        return null;
    };
}
