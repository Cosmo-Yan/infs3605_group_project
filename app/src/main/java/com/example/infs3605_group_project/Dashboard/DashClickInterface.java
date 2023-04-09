package com.example.infs3605_group_project.Dashboard;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;

public interface DashClickInterface {
    void onBarItemClick(BarChart bar, ArrayList<String> axis);
    void onPieItemClick(PieChart pieC);
}
