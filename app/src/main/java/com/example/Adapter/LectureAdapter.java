package com.example.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readdatabase.R;
import java.util.ArrayList;

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.ViewHolder> {
    ArrayList<Lecture> courses;

    public LectureAdapter(ArrayList<Lecture> courses) {
        this.courses = courses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_activity, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.semester.setText(courses.get(position).getLectureSemester());
        holder.professor.setText(courses.get(position).getProfessorName());
        holder.date.setText(courses.get(position).getLectureDate());
        holder.time.setText(courses.get(position).getLectureTime());
    }


    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView semester;
        TextView professor;
        TextView date;
        TextView time;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            semester = itemView.findViewById(R.id.semesterTextView2);
            professor = itemView.findViewById(R.id.profTextView);
            date = itemView.findViewById(R.id.dateTextView2);
            time = itemView.findViewById(R.id.timeTextView2);
            mainLayout = itemView.findViewById(R.id.roomLayout);
        }
    }
}
