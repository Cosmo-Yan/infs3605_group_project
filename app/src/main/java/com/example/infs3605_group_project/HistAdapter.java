//package com.example.infs3605_group_project;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Filterable;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.infs3605_group_project.Activity.Activity;
//
//import java.util.List;
//
//public class HistAdapter extends RecyclerView.Adapter<HistAdapter.MyViewHolder>{
//    private List<Activity> activities;
//    private HistRecyclerInterface mInterface;
//
//    public HistAdapter(List<Activity> activities, HistRecyclerInterface mInterface) {
//        this.activities = activities;
//        this.mInterface = mInterface;
//    }
//
//    @NonNull
//    @Override
//    public HistAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hist_list_item, parent, false);
//        return new MyViewHolder(view,mInterface);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull HistAdapter.MyViewHolder holder, int position) {
//        Activity activity = activities.get(position);
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }
//}
