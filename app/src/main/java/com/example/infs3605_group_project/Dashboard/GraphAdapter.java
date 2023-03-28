package com.example.infs3605_group_project.Dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605_group_project.R;

import java.util.List;

public class GraphAdapter extends RecyclerView.Adapter<GraphAdapter.ViewHolder> {

    private List<Graph> graphList;
    private Context context;

    public GraphAdapter(List<Graph> graphList, Context context) {
        this.graphList = graphList;
        this.context = context;
    }

    @Override
    public GraphAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_graph_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GraphAdapter.ViewHolder holder, int position) {
        Graph graph = graphList.get(position);

        // Bind the graph data to the ImageView
        holder.graphImageView.setImageBitmap(graph.getImageGraph());

        // Bind the graph title to the TextView
        holder.graphTitleTextView.setText(graph.getName());
    }

    @Override
    public int getItemCount() {
        return graphList.size();
    }

    public void addGraph(Graph graph) {
        graphList.add(graph);
        notifyItemInserted(graphList.size() - 1);
    }

    public void removeGraph(int position) {
        graphList.remove(position);
        notifyItemRemoved(position);
    }

    public void updateGraph(int position, Graph graph) {
        graphList.set(position, graph);
        notifyItemChanged(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView graphImageView;
        private TextView graphTitleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            graphImageView = itemView.findViewById(R.id.graph_image_view);
            graphTitleTextView = itemView.findViewById(R.id.graph_name);
        }
    }
}
