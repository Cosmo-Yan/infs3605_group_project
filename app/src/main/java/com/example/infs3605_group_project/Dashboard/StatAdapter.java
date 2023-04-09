package com.example.infs3605_group_project.Dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.infs3605_group_project.R;

import java.util.ArrayList;

/**
 * Custom Adapter for the DashboardActivity GridView
 * Connected to dashboard.xml and dashboard_stat_item.xml
 */
public class StatAdapter extends BaseAdapter {
    //Local Attributes
    private Context context;
    private ArrayList<Stat> stats;

    /**
     * Generic Constructor
     * @param context
     * @param stats Generally an Arraylist of Stat objects to be displayed
     */
    public StatAdapter(Context context, ArrayList<Stat> stats) {
        this.context = context;
        this.stats = stats;
    }

    /**
     *
     * @return Size of the ArrayList of Data
     */
    @Override
    public int getCount() {
        return stats.size();
    }

    /**
     * Returns an item defined by the position given
     * @param position The position of an item in the ArrayList
     * @return The object in the position defined
     */
    @Override
    public Stat getItem(int position) {
        return stats.get(position);
    }

    /**
     * Unused
     * @param position The position of an item in the ArrayList
     * @return The Parameter in Long form
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Inflates the View and binds the data to the View Elements
     * @param position Position of the object depicted within the ArrayList
     * @param convertView The view being inflated
     * @param parent The ViewGroup the View inflated is a part of
     * @return The Inflated View
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            // Inflates the layout file
            convertView = LayoutInflater.from(context).inflate(R.layout.dashboard_stat_item, parent, false);

            // Create a new ViewHolder to store references to the views in the layout
            holder = new ViewHolder();
            holder.numberTextView = convertView.findViewById(R.id.number_text_view);
            holder.nameTextView = convertView.findViewById(R.id.name_text_view);

            // Store the ViewHolder as a tag on the view for future use
            convertView.setTag(holder);
        } else {
            // If the view has already been inflated, retrieve the ViewHolder from the tag
            holder = (ViewHolder) convertView.getTag();
        }

        // Get the Stat object for this position
        Stat stat = getItem(position);

        //Binds the data to the View Elements
        holder.numberTextView.setText(String.valueOf(stat.getNumber()));
        holder.nameTextView.setText(stat.getName());

        return convertView;
    }

    /**
     * For use in the getView method above
     */
    private static class ViewHolder {
        TextView numberTextView;
        TextView nameTextView;
    }
}