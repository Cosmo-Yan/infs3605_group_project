package com.example.infs3605_group_project;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infs3605_group_project.Activity.Activity;

import org.w3c.dom.Text;

import java.util.List;

public class HistAdapter extends RecyclerView.Adapter<HistAdapter.MyViewHolder>{
    private List<Activity> activities;
    private HistRecyclerInterface mInterface;

    public HistAdapter(List<Activity> activities, HistRecyclerInterface mInterface) {
        this.activities = activities;
        this.mInterface = mInterface;
    }

    @NonNull
    @Override
    public HistAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hist_list_item, parent, false);
        return new MyViewHolder(view,mInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull HistAdapter.MyViewHolder holder, int position) {
        Activity activity = activities.get(position);
        holder.eventName.setText(activity.getEventName());
        holder.date.setText(activity.getEventStartDate());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView eventName;
        private TextView date;
        public MyViewHolder(@NonNull View itemView, HistRecyclerInterface mInterface) {
            super(itemView);
            eventName = itemView.findViewById(R.id.ListEventName);
            date = itemView.findViewById(R.id.ListEventDate);
            ConstraintLayout tempItem = itemView.findViewById(R.id.eventItem);
            ImageView trashIcon = itemView.findViewById(R.id.trashIcon);
            ImageView editIcon = itemView.findViewById(R.id.editIcon);

            // Display when item is clicked
            tempItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mInterface != null){
                        mInterface.onClick((int) itemView.getTag());
                    }
                }
            });

            // Delete if trash icon is clicked
            trashIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mInterface != null){
                        mInterface.delClick((int) itemView.getTag());
                    }
                }
            });

            // Update if edit is clicked
            editIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mInterface != null){
                        mInterface.editClick((int) itemView.getTag());
                    }
                }
            });
        }
    }

    public void update(List<Activity> activities){
        this.activities = activities;
        notifyDataSetChanged();
    }
    public void update(){
        notifyDataSetChanged();
    }

    public List<Activity> getActivities(){
        return activities;
    }
}
