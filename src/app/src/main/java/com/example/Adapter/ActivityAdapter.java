package com.example.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Model.Activity;
import com.example.readdatabase.R;

import java.util.ArrayList;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
    ArrayList<Activity> activityList;

    public ActivityAdapter(ArrayList<Activity> activityList) {
        this.activityList = activityList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_activity, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.activityTitle.setText(activityList.get(position).getTitle());
        holder.activityDate.setText(activityList.get(position).getDate());
        holder.activityContent.setText(activityList.get(position).getContent());
    }


    @Override
    public int getItemCount() {
        return activityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView activityTitle;
        TextView activityDate;
        TextView activityContent;
        ConstraintLayout mainLayout;

        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            activityTitle = itemView.findViewById(R.id.activityTitleTextView);
            activityDate = itemView.findViewById(R.id.activityDateTextView);
            activityContent = itemView.findViewById(R.id.activityContentTextView);
            mainLayout = itemView.findViewById(R.id.activityLayout);
        }
    }
}
