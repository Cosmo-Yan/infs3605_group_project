package com.example.infs3605_group_project.Dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.infs3605_group_project.R;

import java.util.ArrayList;

public class StatAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Stat> stats;

    public StatAdapter(Context context, ArrayList<Stat> stats) {
        this.context = context;
        this.stats = stats;
    }

    @Override
    public int getCount() {
        return stats.size();
    }

    @Override
    public Stat getItem(int position) {
        return stats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            // Inflate the layout for each list item
            convertView = LayoutInflater.from(context).inflate(R.layout.dashboard_stat_item, parent, false);

            // Create a new ViewHolder to store references to the views in the layout
            holder = new ViewHolder();
            holder.numberTextView = convertView.findViewById(R.id.number_text_view);
            holder.nameTextView = convertView.findViewById(R.id.name_text_view);

            // Store the ViewHolder as a tag on the view
            convertView.setTag(holder);
        } else {
            // If the view has already been inflated, retrieve the ViewHolder from the tag
            holder = (ViewHolder) convertView.getTag();
        }

        // Get the Stat object for this position
        Stat stat = getItem(position);

        // Set the number and name text views to display the corresponding values from the Stat object
        holder.numberTextView.setText(String.valueOf(stat.getNumber()));
        holder.nameTextView.setText(stat.getName());

        return convertView;
    }

    private static class ViewHolder {
        TextView numberTextView;
        TextView nameTextView;
    }
}
