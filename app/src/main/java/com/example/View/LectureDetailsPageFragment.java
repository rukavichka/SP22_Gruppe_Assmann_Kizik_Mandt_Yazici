package com.example.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.Model.Lecture;
import com.example.readdatabase.R;

public class LectureDetailsPageFragment extends Fragment {
    Lecture lecture;
    public LectureDetailsPageFragment(Lecture lecture) {
        this.lecture = lecture;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lecture_details_page, container, false);
        TextView courseName = root.findViewById(R.id.courseNameTextView);
        TextView profName = root.findViewById(R.id.professorEditableTextView);
        TextView time = root.findViewById(R.id.timeEditableTextView);
        TextView semester = root.findViewById(R.id.semesterEditableTextView);
        TextView room = root.findViewById(R.id.roomEditableTextView);
        courseName.setText(lecture.getLectureName());
        room.setText(lecture.getLectureRoom());
        semester.setText(lecture.getLectureSemester());
        time.setText(lecture.getLectureTime());
        profName.setText(lecture.getProfessorName());
//        TextView content = container.findViewById(R.id.content);
//        TextView inhalt = container.findViewById(R.id.professorEditableTextView);
        return root;
    }
}