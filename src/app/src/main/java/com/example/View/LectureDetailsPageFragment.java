package com.example.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.Model.Lecture;
import com.example.Service.FetchCourses;
import com.example.readdatabase.R;
import java.util.ArrayList;

public class LectureDetailsPageFragment extends Fragment {
    private Button joinButton;
    private final String courseName;
    private String currentPeriod;
    private FetchCourses fetchCourses;
    private Lecture lecture;
    private View root;
    private ArrayList<Lecture> courseInfo;

    public LectureDetailsPageFragment(String courseName) {
        this.courseName = courseName;
        fetchCourses = new FetchCourses();
        fetchCourses.setCourseName(courseName);
        fetchCourses.setWeakReference(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_lecture_details_page, container, false);
        fetchCourses.execute(1);
        return root;
    }

    public void setCourseInfo(ArrayList<Lecture> courses) {
        this.courseInfo = courses;
        this.lecture =courseInfo.get(0);
        this.currentPeriod = lecture.getLecturePeriod();
        setLayout(lecture);
        createMenu();
        hideProgressBar();
    }

    private void createMenu() {
        TextView button = root.findViewById(R.id.periodMenuButton);
        if(courseInfo.size() == 1) {
            button.setCompoundDrawables(null, null, null, null);
            return;
        }
        PopupMenu popupMenu = new PopupMenu(getContext(), button);
        popupMenu.getMenu().clear();
        for(Lecture lecture: courseInfo) {
            System.out.println(lecture.getLecturePeriod());
            if(!lecture.getLecturePeriod().equals(currentPeriod)) {
                popupMenu.getMenu().add(lecture.getLecturePeriod());
            }

        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        changeLayout(menuItem.getTitle().toString());
                        popupMenu.dismiss();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void changeLayout(String period) {
        for(Lecture lecture: courseInfo) {
            if(period.equals(lecture.getLecturePeriod())) {
                setLayout(lecture);
                this.lecture = lecture;
                break;
            }
        }
        this.currentPeriod = period;
        createMenu();
    }

    public void setLayout(Lecture info) {
        TextView courseName = root.findViewById(R.id.courseNameTextView);
        TextView profName = root.findViewById(R.id.professorEditableTextView);
        TextView period = root.findViewById(R.id.periodMenuButton);
        TextView semester = root.findViewById(R.id.semesterEditableTextView);
        TextView room = root.findViewById(R.id.roomEditableTextView);
        TextView hours = root.findViewById(R.id.courseHoursTextView);
        ConstraintLayout layout = root.findViewById(R.id.detailsConstrain);

        hours.setText(info.getLectureTime());
        courseName.setText(info.getLectureName());
        room.setText(info.getLectureRoom());
        semester.setText(info.getSemester());
        period.setText(info.getLecturePeriod());
        profName.setText(info.getProfessorName());
        layout.setVisibility(View.VISIBLE);

        joinButton = root.findViewById(R.id.joinButton);
        if(lecture.isJoined()){
            joinButton.setText("Leave Course");
        }
        else{
            joinButton.setText("Join Course");
        }
        joinButton.setOnClickListener(new JoinButtonListener());
    }

    public void hideProgressBar() {
        ProgressBar progressBar = root.findViewById(R.id.progress_loader2);
        progressBar.setVisibility(View.INVISIBLE);
    }


    public class JoinButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(lecture.isJoined()){
                joinButton.setText("Join Course");
                // Further implementation needed
                //lecture.setJoined(false);
            }
            else{
                joinButton.setText("Leave Course");
                // Further implementation needed
                // lecture.setJoined(true);
            }
            //Update Firebase about the Join. Save it in List.
        }
    }
}