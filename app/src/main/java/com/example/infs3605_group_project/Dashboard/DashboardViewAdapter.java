package com.example.infs3605_group_project.Dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.infs3605_group_project.R;

import java.util.List;

public class DashboardViewAdapter extends ArrayAdapter<Graph> {
    private Context context;
    private List<Graph> graphs;

    public DashboardViewAdapter(Context context, int id, List<Graph> graphs) {
        super(context, id, graphs);
        this.graphs = graphs;
    }

    @Override
    public int getCount() {
        return graphs.size();
    }

    @Override
    public Graph getItem(int position) {
        return graphs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.dashboard_item, parent, false);
        Graph graph = graphs.get(position);
        ImageView Graph = convertView.findViewById(R.id.dashboard_item_image);
        TextView GraphName = convertView.findViewById(R.id.dashboard_item_text);
        //Graph.setImageResource(graph.getImageResource());
        Graph.setImageBitmap(graph.getImageGraph());
        GraphName.setText(graph.getName());
        return convertView;
    }
}
