package com.example.infs3605_group_project.Dashboard;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;

/**
 * Assists in handling the onClickMethods in ChartAdapter and DashboardActivity
 */
public interface DashClickInterface {
    void onBarItemClick(BarChart bar, ArrayList<String> axis);
    void onPieItemClick(PieChart pieC);
}
