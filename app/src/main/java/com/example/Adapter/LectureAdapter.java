package com.example.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Model.Activity;
import com.example.Model.Lecture;
import com.example.View.LectureDetailsPageFragment;
import com.example.View.MainActivity;
import com.example.View.ScrollFragment;
import com.example.readdatabase.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.ViewHolder> {
    ArrayList<Lecture> lectures;

    public LectureAdapter(ArrayList<Lecture> lectures) {
        this.lectures = lectures;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_course, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.courseName.setText(lectures.get(position).getLectureName());
        holder.semester.setText(lectures.get(position).getLectureSemester());
        holder.time.setText(lectures.get(position).getLectureTime());
        holder.professor.setText(lectures.get(position).getProfessorName());
        holder.room.setText(lectures.get(position).getLectureRoom());
    }


    @Override
    public int getItemCount() {
        return lectures.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView courseName;
        TextView semester;
        TextView professor;
        TextView room;
        TextView time;
        Button button;
        LinearLayout mainLayout;
        WeakReference<MainActivity> weakReference;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.courseName2);
            semester = itemView.findViewById(R.id.semesterTextView2);
            professor = itemView.findViewById(R.id.profTextView);
            room = itemView.findViewById(R.id.dateTextView2);
            time = itemView.findViewById(R.id.timeTextView2);
            button = itemView.findViewById(R.id.moreInfoButton);
            mainLayout = itemView.findViewById(R.id.roomLayout);
            weakReference = new WeakReference<>((MainActivity) itemView.getContext());
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            weakReference.get().getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container,
                    new LectureDetailsPageFragment()).addToBackStack(null).commit();
        }
    }
}
