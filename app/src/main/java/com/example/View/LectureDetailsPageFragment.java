package com.example.View;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.Model.Lecture;
import com.example.readdatabase.R;

public class LectureDetailsPageFragment extends Fragment {
    Lecture lecture;
    Button joinButton;
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
        joinButton = root.findViewById(R.id.joinButton);
        if(lecture.isJoined()){
            joinButton.setText("Leave Course");
        }
        else{
            joinButton.setText("Join Course");
        }
        joinButton.setOnClickListener(new JoinButtonListener());
        courseName.setText(lecture.getLectureName());
        room.setText(lecture.getLectureRoom());
        semester.setText(lecture.getLectureSemester());
        time.setText(lecture.getLectureTime());
        profName.setText(lecture.getProfessorName());
//        TextView content = container.findViewById(R.id.content);
//        TextView inhalt = container.findViewById(R.id.professorEditableTextView);
        return root;
    }

    public class JoinButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(lecture.isJoined()){
                joinButton.setText("Join Course");
                lecture.setJoined(false);
            }
            else{
                joinButton.setText("Leave Course");
                lecture.setJoined(true);
            }
            //Update Firebase about the Join. Save it in List.
        }
    }
}