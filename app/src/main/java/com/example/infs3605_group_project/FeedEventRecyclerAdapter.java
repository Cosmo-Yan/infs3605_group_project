package com.example.infs3605_group_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FeedEventRecyclerAdapter extends RecyclerView.Adapter<FeedEventRecyclerAdapter.MyViewHolder> {
    Context context;
    ArrayList<FeedEventModel> feedEventModels;

    public FeedEventRecyclerAdapter(Context context, ArrayList<FeedEventModel> feedEventModels){
        this.context =context;
        this.feedEventModels = feedEventModels;
    }

    @NonNull
    @Override
    public FeedEventRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.feed_recyclerview_row,parent,false);

        return new FeedEventRecyclerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedEventRecyclerAdapter.MyViewHolder holder, int position) {
        holder.hostName.setText(feedEventModels.get(position).getHostName());
        holder.eventType.setText(feedEventModels.get(position).getEventType());
        holder.imageView.setImageResource(feedEventModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return feedEventModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView eventType, hostName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.imageView);
            eventType = itemView.findViewById(R.id.textView2);
            hostName = itemView.findViewById(R.id.textView3);
        }
    }
}
