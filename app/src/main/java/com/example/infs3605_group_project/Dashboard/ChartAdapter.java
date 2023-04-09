package com.example.infs3605_group_project.Dashboard;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

/**
 * Custom Adapter for the DashboardActivity RecyclerView
 * Connected to dashboard.xml, dashboard_barchart.xml and dashboard_piechart.xml
 */
public class ChartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //Local Attributes
    private static final int PIE_CHART = 0;
    private static final int BAR_CHART = 1;
    private List<ChartObjects> charts = new ArrayList<>();
    private List<ChartObjects> mCharts;
    private DashClickInterface listener;

    /**
     * Generic Constructor
     * @param charts Generally an Arraylist of ChartObjects to be displayed
     * @param listener An interface that assists with handling the onclick of Recyclerview items
     */
    public ChartAdapter(List<ChartObjects> charts, DashClickInterface listener) {
        mCharts = charts;
        this.listener = listener;
    }

    /**
     * Returns the type of View object necessary for the Chart
     * @param position Position of the ChartObject in the Arraylist
     * @return int The integer representing the chart type in question as defined in the ChartType Enum
     */
    @Override
    public int getItemViewType(int position) {
        //Retrieves ChartType of the ChartObject in the Position defined
        mCharts.get(position).getChartType();
        // If statement to return the correct integer
        if (mCharts.get(position).getChartType() ==ChartType.PIE_CHART) {
            return PIE_CHART;
        } else if (mCharts.get(position).getChartType() ==ChartType.BAR_CHART) {
            return BAR_CHART;
        } else {
            //Exception Handling
            throw new IllegalArgumentException("Chart Type Unknown");
        }
    }

    /**
     * Inflates the correct view and layout file depending on the Chart Type
     * @param parent The Parent view the created view will become a part of
     * @param viewType The Chart Type of the chart to be depicted
     * @return The ViewHolder to be displayed
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //If statement to return correct layout file to be inflated and filled
        if (viewType == PIE_CHART) {
            View view = inflater.inflate(R.layout.dashboard_piechart, parent, false);
            return new PieChartViewHolder(view);
        } else if (viewType == BAR_CHART) {
            View view = inflater.inflate(R.layout.dashboard_barchart, parent, false);
            return new BarChartViewHolder(view);
        } else {
            //Exception Handling
            throw new IllegalArgumentException("Unknown view type");
        }
    }

    /**
     * Binds the data to the views
     * @param holder The ViewHolder of Views to be filled
     * @param position Position of the ChartObject in the ArrayList to be Depicted
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ChartObjects chart = mCharts.get(position);
        //Binds Data to views and set onClickListeners
        if (holder instanceof PieChartViewHolder) {
            PieChartViewHolder pieChartViewHolder = (PieChartViewHolder) holder;
            pieChartViewHolder.bind(mCharts.get(position).getChart());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPieItemClick((PieChart) mCharts.get(position).getChart());
                    }
                });
        }
        else if (holder instanceof BarChartViewHolder) {
            BarAxis baxis = new BarAxis(null, (BarChart) chart.getChart(), chart.getAxisSuper());
            BarChartViewHolder barChartViewHolder = (BarChartViewHolder) holder;
            barChartViewHolder.bind(chart.getChart(), baxis.getAxis());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onBarItemClick((BarChart)mCharts.get(position).getChart(), baxis.getAxis());
                }
            });
        } else {
            //Exception Handling
            throw new IllegalArgumentException("Unknown view holder type");
        }
    }

    /**
     *
     * @return Size of the ArrayList of Data
     */
    @Override
    public int getItemCount() {
        return mCharts.size();
    }

    /**
     * Class that handles the PieChart Chart Type View Holders
     */
    private static class PieChartViewHolder extends RecyclerView.ViewHolder {
        private PieChart mPieChart;
        private TextView mPieText;

        /**
         * Constructor for PieChart View Holder
         * @param itemView The ItemView Supertype
         */
        public PieChartViewHolder(View itemView) {
            super(itemView);
            //Connects elements to local attributes
            mPieChart = itemView.findViewById(R.id.pie_chart_detail);
            mPieText = itemView.findViewById(R.id.label_text);
            //PieChart View Alterations
            mPieChart.setEntryLabelColor(Color.BLACK);
            mPieChart.getDescription().setEnabled(false);
            mPieChart.getLegend().setEnabled(true);
            mPieChart.setEntryLabelTextSize(12f);
        }

        /**
         * Binds Data to the View
         * @param chart The Chart object to be depicted
         */
        public void bind(Chart chart) {
            mPieChart.setData(((PieChart) chart).getData());
            mPieText.setText(((PieChart) chart).getDescription().getText());
            //PieChart View Alterations
            mPieChart.setUsePercentValues(true);
            mPieChart.getLegend().setEnabled(false);
        }
    }

    /**
     * Class that handles the BarChart Chart Type View Holders
     */
    private static class BarChartViewHolder extends RecyclerView.ViewHolder {
        private BarChart mBarChart;
        private TextView mBarText;

        /**
         * Constructor for BarChart View Holder
         * @param itemView The View Supertype that this viewholder inherits attributes from
         */
        public BarChartViewHolder(View itemView) {
            super(itemView);
            mBarChart = itemView.findViewById(R.id.barChart_detail);
            mBarText = itemView.findViewById(R.id.label_text);
        }

        /**
         * Binds Data to the View
         * @param chart The Chart object to be depicted
         */
        public void bind(Chart chart, ArrayList<String> axis) {
            // Bind the chart data to the view
            mBarChart.setData(((BarChart) chart).getData());
            mBarText.setText((CharSequence) ((BarChart) chart).getDescription().getText());
            //BarChart View Alterations
            mBarChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(axis));
            mBarChart.getLegend().setEnabled(false);
            mBarChart.getXAxis().setLabelCount(axis.size(), false);
            mBarChart.getDescription().setEnabled(false);
            mBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            mBarChart.getXAxis().setDrawGridLines(false);
            mBarChart.getAxisLeft().setDrawGridLines(false);
            mBarChart.getAxisRight().setDrawGridLines(false);
            mBarChart.getXAxis().setGranularity(0.5f); // only intervals of 1 day
            mBarChart.setVisibleXRange(1f, axis.size());
            mBarChart.getAxisLeft().setAxisMinimum(0);
            mBarChart.getAxisLeft().setDrawGridLines(false);
            mBarChart.getXAxis().setDrawGridLines(false);
            mBarChart.setPinchZoom(true);
        }
    }
}


