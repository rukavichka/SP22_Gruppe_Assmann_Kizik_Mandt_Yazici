package com.example.View;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Model.Lecture;
import com.example.Service.CheckMember;
import com.example.readdatabase.R;

public class LectureContentsFragment extends Fragment {
    private View root;
    private final Lecture lecture;
    private CheckMember checkMember;
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
        checkMember = new CheckMember();
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
        ImageView icon = layout.findViewById(R.id.contentIcon);
        icon.setImageResource(R.drawable.ic_forum);
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
        icon = layout.findViewById(R.id.contentButtonIcon);
        icon.setImageResource(R.drawable.ic_participants);
        title.setText("Mitglieder");
        setParticipantButton();

        layout = root.findViewById(R.id.leaveCourseButton);
        title = layout.findViewById(R.id.contentButton);
        icon = layout.findViewById(R.id.contentButtonIcon);
        icon.setImageResource(R.drawable.ic_baseline_logout_24);
        title.setText("Kursmitgliedschaft beenden");
        setLeaveButton();
        return root;
    }

    public void setParticipantButton() {
        ConstraintLayout layout = root.findViewById(R.id.participants);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)root.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container,
                        new ParticipantPageFragment(lecture.getLectureName())).addToBackStack("ContentsDetailPage").commit();
            }
        });
    }

    public void setLeaveButton() {
        checkMember = new CheckMember();
        ConstraintLayout layout = root.findViewById(R.id.leaveCourseButton);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkMember.executeDeleteMembership(lecture.getLectureName());
                checkMember.executeDeleteUserCourses(lecture.getLectureName());
                ((MainActivity)root.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container,
                        new LectureDetailsPageFragment(lecture.getLectureName())).commit();
            }
        });
    }

    private void createOnClick(String type, int id) {
        ConstraintLayout layout = root.findViewById(id);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)root.getContext()).getSupportFragmentManager().popBackStack();
                ((MainActivity)root.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container,
                        new ContentsDetailPage(type, lecture.getLectureName(), lecture.getLectureContent().get(type))).commit();
            }
        });
    }
}