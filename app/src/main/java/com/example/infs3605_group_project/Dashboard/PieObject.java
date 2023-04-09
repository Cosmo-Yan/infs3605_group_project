package com.example.infs3605_group_project.Dashboard;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.Chart;

/**
 *  Holds a PieChart
 *  Required because the PieChart needed to share a supertype with BarAxis
 *  so that some methods could be overridden
 */
public class PieObject extends  ChartObjects{
    private com.github.mikephil.charting.charts.PieChart pie;

    public PieObject(Chart chart, PieChart pie) {
        super(pie);
        this.pie = pie;
    }

    public PieChart getPie() {
        return pie;
    }

    public void setPie(PieChart pie) {
        this.pie = pie;
    }

    @Override
    public ChartType getChartType(){
        return ChartType.PIE_CHART;
    }

    @Override
    public Chart getChart() {
        return pie;
    }
}
