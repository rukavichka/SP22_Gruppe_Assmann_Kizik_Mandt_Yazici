package com.example.View;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.Model.Lecture;
import com.example.readdatabase.R;

public class LectureContentsFragment extends Fragment {
    View root;
    Lecture lecture;
    public LectureContentsFragment(Lecture lecture) {
        // Required empty public constructor
        this.lecture = lecture;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_lecture_contents, container, false);

        ConstraintLayout layout = root.findViewById(R.id.courseContent);
        TextView title = layout.findViewById(R.id.contentTitle);
        TextView details = layout.findViewById(R.id.contentDetail);
        title.setText("Vorlesungen");
        details.setText("Vorlesungsmaterial (Folien etc.)");
        createOnClick("vorlesungen", R.id.courseContent);

        layout = root.findViewById(R.id.homeworkContent);
        title = layout.findViewById(R.id.contentTitle);
        details = layout.findViewById(R.id.contentDetail);
        title.setText("Übungen");
        details.setText("Übungsmaterialien (Übungszettel etc.)");
        createOnClick("übungen", R.id.homeworkContent);

        layout = root.findViewById(R.id.forum);
        title = layout.findViewById(R.id.contentTitle);
        details = layout.findViewById(R.id.contentDetail);
        title.setText("Kollaborations-Forum");
        details.setText("Forum zur Organisation der Zusammenarbeit in den Übungen etc.");
        createOnClick("forum", R.id.forum);

        layout = root.findViewById(R.id.examContent);
        title = layout.findViewById(R.id.contentTitle);
        details = layout.findViewById(R.id.contentDetail);
        title.setText("Klausur ");
        details.setText("");
        createOnClick("klausur", R.id.examContent);

        layout = root.findViewById(R.id.participants);
        title = layout.findViewById(R.id.contentButton);
        title.setText("Mitglieder");

        layout = root.findViewById(R.id.leaveCourseButton);
        title = layout.findViewById(R.id.contentButton);
        title.setText("Kursmitgliedschaft beenden");
        return root;
    }

    private void createOnClick(String type, int id) {
        ConstraintLayout layout = root.findViewById(id);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)root.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container,
                        new ContentsDetailPage(type, lecture.getLectureContent())).addToBackStack("ContentsDetailPage").commit();
            }
        });
    }
}