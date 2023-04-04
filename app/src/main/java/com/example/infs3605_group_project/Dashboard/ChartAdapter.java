package com.example.infs3605_group_project.Dashboard;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605_group_project.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class ChartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int PIE_CHART = 0;
    private static final int BAR_CHART = 1;
    private List<ChartObjects> charts = new ArrayList<>();

    private List<ChartObjects> mCharts;

    public ChartAdapter(List<ChartObjects> charts) {
        mCharts = charts;
    }

    @Override
    public int getItemViewType(int position) {
        mCharts.get(position).getChartType();
        if (mCharts.get(position).getChartType() ==ChartType.PIE_CHART) {
            return PIE_CHART;
        } else if (mCharts.get(position).getChartType() ==ChartType.BAR_CHART) {
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
        ChartObjects chart = mCharts.get(position);
        if (holder instanceof PieChartViewHolder) {
            PieChartViewHolder pieChartViewHolder = (PieChartViewHolder) holder;
//            pieChartViewHolder.bind(chart.getChart());
            pieChartViewHolder.bind(mCharts.get(position).getChart());
        } else if (holder instanceof BarChartViewHolder) {
            BarAxis baxis = new BarAxis(null, (BarChart) chart.getChart(), chart.getAxisSuper());
            BarChartViewHolder barChartViewHolder = (BarChartViewHolder) holder;
            barChartViewHolder.bind(chart.getChart(), baxis.getAxis());
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
            mPieChart = itemView.findViewById(R.id.pie_chart_detail);
            mPieText = itemView.findViewById(R.id.label_text);
            mPieChart.setEntryLabelColor(Color.BLACK);
            mPieChart.getDescription().setEnabled(false);
            mPieChart.getLegend().setEnabled(true);
            mPieChart.setEntryLabelTextSize(12f);

        }

        public void bind(Chart chart) {
            // Bind the chart data to the view
            mPieChart.setData(((PieChart) chart).getData());
            mPieText.setText(((PieChart) chart).getDescription().getText());
            mPieChart.setUsePercentValues(true);
            mPieChart.getLegend().setEnabled(false);


        }

    }

    private static class BarChartViewHolder extends RecyclerView.ViewHolder {
        private BarChart mBarChart;
        private TextView mBarText;

        public BarChartViewHolder(View itemView) {
            super(itemView);
            mBarChart = itemView.findViewById(R.id.barChart_detail);
            mBarText = itemView.findViewById(R.id.label_text);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(dvi != null){
//                        dvi.onItemClick((String) itemView.getTag());
//                    }
//                }
//            });
        }

        public void bind(Chart chart, ArrayList<String> axis) {
            // Bind the chart data to the view
            mBarChart.setData(((BarChart) chart).getData());
            mBarText.setText((CharSequence) ((BarChart) chart).getDescription().getText());
            mBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(axis));
            mBarChart.getLegend().setEnabled(false);
            mBarChart.getXAxis().setLabelCount(axis.size(), false);
            mBarChart.getDescription().setEnabled(false);
            mBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

            mBarChart.getXAxis().setDrawGridLines(false);
            mBarChart.getXAxis().setGranularity(0.5f); // only intervals of 1 day
            System.out.println(chart.getDescription().getText() + "" + axis.size());

            mBarChart.setVisibleXRange(1f, axis.size());
            mBarChart.getAxisLeft().setAxisMinimum(0);

            mBarChart.getAxisLeft().setDrawGridLines(false);
            mBarChart.getXAxis().setDrawGridLines(false);




            // ... other configuration code for the bar chart
        }
    }
}


