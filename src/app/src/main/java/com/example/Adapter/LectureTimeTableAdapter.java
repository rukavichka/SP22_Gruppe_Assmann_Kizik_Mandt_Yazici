package com.example.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Model.Lecture;
import com.example.View.LectureDetailsPageFragment;
import com.example.View.MainActivity;
import com.example.readdatabase.R;

import java.util.ArrayList;

public class LectureTimeTableAdapter extends RecyclerView.Adapter<LectureTimeTableAdapter.ViewHolder>{
    ArrayList<String> times;

    public LectureTimeTableAdapter(ArrayList<String> times) {
        this.times = times;
    }

    @NonNull
    @Override
    public LectureTimeTableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_day_time, parent, false);
        return new LectureTimeTableAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull LectureTimeTableAdapter.ViewHolder holder, int position) {
        String[] temp = times.get(position).split(" ");
        holder.day.setText(temp[0]);
        holder.hour.setText(temp[1]);
    }


    @Override
    public int getItemCount() {
        return times.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView day;
        TextView hour;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.day = itemView.findViewById(R.id.timeTableDay);
            this.hour = itemView.findViewById(R.id.timeTableHour);
        }
    }
}
