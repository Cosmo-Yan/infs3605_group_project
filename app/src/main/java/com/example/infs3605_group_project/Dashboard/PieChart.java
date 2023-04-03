package com.example.infs3605_group_project.Dashboard;

import com.github.mikephil.charting.data.PieEntry;

import java.util.List;

public class PieChart implements Chart {
    private List<PieEntry> entries;
    // other fields and methods

    @Override
    public ChartType getChartType() {
        return ChartType.PIE_CHART;
    }
}
