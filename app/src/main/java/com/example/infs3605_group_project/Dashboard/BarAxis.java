package com.example.infs3605_group_project.Dashboard;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;

import java.util.ArrayList;

public class BarAxis extends ChartObjects {
    private BarChart Chart;
    private ArrayList<String> axis;



    public BarAxis(com.github.mikephil.charting.charts.Chart chart, BarChart chart1, ArrayList<String> axis) {
        super(chart);
        Chart = chart1;
        this.axis = axis;
    }



    @Override
    public BarChart getChart() {
        return Chart;
    }

    public void setChart(BarChart chart) {
        Chart = chart;
    }

    public ArrayList<String> getAxis() {
        return axis;
    }

    public void setAxis(ArrayList<String> axis) {
        this.axis = axis;
    }

    @Override
    public ChartType getChartType(){
        return ChartType.BAR_CHART;
    }

    @Override
    public ArrayList<String> getAxisSuper() {
        return axis;
    }
}
