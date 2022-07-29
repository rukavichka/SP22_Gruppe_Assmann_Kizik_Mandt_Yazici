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
        holder.semester.setText(lectures.get(position).getSemester());
        holder.number.setText(lectures.get(position).getNumber());
        holder.professor.setText(lectures.get(position).getProfessorName());
        holder.form.setText(lectures.get(position).getForm());
    }


    @Override
    public int getItemCount() {
        return lectures.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView courseName;
        TextView semester;
        TextView professor;
        TextView number;
        TextView form;
        Button button;
        LinearLayout mainLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.courseName2);
            semester = itemView.findViewById(R.id.semesterTextView2);
            professor = itemView.findViewById(R.id.profTextView);
            number = itemView.findViewById(R.id.numberTextView);
            form = itemView.findViewById(R.id.formTextView);
            button = itemView.findViewById(R.id.moreInfoButton);
            mainLayout = itemView.findViewById(R.id.roomLayout);
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ((MainActivity)view.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container,
                    new LectureDetailsPageFragment(courseName.getText().toString())).commit();
        }
    }
}
