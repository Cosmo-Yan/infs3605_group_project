package com.example.infs3605_group_project.Dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605_group_project.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;
import java.util.List;

public class ChartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int PIE_CHART = 0;
    private static final int BAR_CHART = 1;
    private List<Chart> charts = new ArrayList<>();

    private List<Chart> mCharts;

    public ChartAdapter(List<Chart> charts) {
        mCharts = charts;
    }

    @Override
    public int getItemViewType(int position) {
        Chart chart = mCharts.get(position);
        if (chart instanceof PieChart) {
            return PIE_CHART;
        } else if (chart instanceof BarChart) {
            return BAR_CHART;
        } else {
            throw new IllegalArgumentException("Unknown chart type");
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == PIE_CHART) {
            View view = inflater.inflate(R.layout.dashboard_piechart, parent, false);
            return new PieChartViewHolder(view);
        } else if (viewType == BAR_CHART) {
            View view = inflater.inflate(R.layout.dashboard_barchart, parent, false);
            return new BarChartViewHolder(view);
        } else {
            throw new IllegalArgumentException("Unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Chart chart = mCharts.get(position);
        if (holder instanceof PieChartViewHolder) {
            PieChartViewHolder pieChartViewHolder = (PieChartViewHolder) holder;
            pieChartViewHolder.bind(chart);
        } else if (holder instanceof BarChartViewHolder) {
            BarChartViewHolder barChartViewHolder = (BarChartViewHolder) holder;
            barChartViewHolder.bind(chart);
        } else {
            throw new IllegalArgumentException("Unknown view holder type");
        }
    }

    @Override
    public int getItemCount() {
        return mCharts.size();
    }

    private static class PieChartViewHolder extends RecyclerView.ViewHolder {
        private PieChart mPieChart;
        private TextView mPieText;

        public PieChartViewHolder(View itemView) {
            super(itemView);
            mPieChart = itemView.findViewById(R.id.pie_chart);
            mPieText = itemView.findViewById(R.id.label_text);
        }

        public void bind(Chart chart) {
            // Bind the chart data to the view
            mPieChart.setData(((PieChart) chart).getData());
            mPieText.setText(((PieChart) chart).getDescription().getText());
        }
    }

    private static class BarChartViewHolder extends RecyclerView.ViewHolder {
        private BarChart mBarChart;
        private TextView mBarText;

        public BarChartViewHolder(View itemView) {
            super(itemView);
            mBarChart = itemView.findViewById(R.id.barChart);
            mBarText = itemView.findViewById(R.id.label_text);
        }

        public void bind(Chart chart) {
            // Bind the chart data to the view
            mBarChart.setData(((BarChart) chart).getData());
            mBarText.setText((CharSequence) ((BarChart) chart).getDescription().getText());
            // ... other configuration code for the bar chart
        }
    }
}


