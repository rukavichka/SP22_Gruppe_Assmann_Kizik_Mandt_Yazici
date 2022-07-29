package com.example.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.Adapter.LectureAdapter;
import com.example.Adapter.LectureTimeTableAdapter;
import com.example.Model.Data;
import com.example.Model.Lecture;
import com.example.Model.VerificationProcess;
import com.example.Service.CheckMember;
import com.example.Service.FetchCourses;
import com.example.readdatabase.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * view class which shows details of courses/lectures and enables join and leave interactions
 */
public class LectureDetailsPageFragment extends Fragment {
    private Button joinButton;
    private final String courseName;
    private String currentPeriod;
    private FetchCourses fetchCourses;
    private Lecture lecture;
    private View root;
    private ArrayList<Lecture> courseInfo;
    private boolean isCourseMember;
    private CheckMember checkMember;
    private DatabaseReference databaseReference;


    public LectureDetailsPageFragment(String courseName) {
        this.courseName = courseName;
        checkMember = new CheckMember();
        checkMember.setCourseName(courseName);
        checkMember.setWeakReference(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference("user_courses/" + VerificationProcess.getInstance().userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_lecture_details_page, container, false);
        fetchCourses = new FetchCourses();
        fetchCourses.setCourseName(courseName);
        fetchCourses.setWeakReference(this);
        fetchCourses.execute(1);
        //checkMember.executeCheckUserCourses();
        return root;
    }

    public void setCourseInfo(ArrayList<Lecture> data) {
        this.courseInfo = data;
        this.lecture = courseInfo.get(0);
        this.currentPeriod = lecture.getLecturePeriod();
        setLayout(lecture);
        createMenu();
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
            if(!lecture.getSchedule().equals(currentPeriod)) {
                popupMenu.getMenu().add(lecture.getLecturePeriod());
            }
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        changeLayout(menuItem.getTitle().toString());
                        popupMenu.dismiss();
                        return true;
                    }
                });
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

    /**
     * @param info lecture object to display
     */
    public void setLayout(Lecture info) {

        TextView courseName = root.findViewById(R.id.courseNameTextView);
        TextView profName = root.findViewById(R.id.professorEditableTextView);
        TextView period = root.findViewById(R.id.periodMenuButton);
        TextView semester = root.findViewById(R.id.semesterEditableTextView);
        TextView room = root.findViewById(R.id.roomEditableTextView);
        ConstraintLayout layout = root.findViewById(R.id.detailsConstraint);

        courseName.setText(info.getLectureName());
        room.setText(info.getLectureRoom());
        semester.setText(info.getSemester());
        period.setText(info.getLecturePeriod());
        profName.setText(info.getProfessorName());
        layout.setVisibility(View.VISIBLE);
        recyclerViewTime();

        joinButton = root.findViewById(R.id.joinButton);
        checkMember.executeCheckUserCourses();
        joinButton.setOnClickListener(new JoinButtonListener());
    }

    public void hideProgressBar() {
        ConstraintLayout layout = root.findViewById(R.id.detailsConstraint);
        ProgressBar progressBar = root.findViewById(R.id.progress_loader2);
        progressBar.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.VISIBLE);
    }

    public void setIsCourseMember(boolean checkList) {
        this.isCourseMember = checkList;
    }

    public void recyclerViewTime() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerViewList = root.findViewById(R.id.courseTimeTable);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        RecyclerView.Adapter adapter = new LectureTimeTableAdapter(lecture.getLectureTime());
        recyclerViewList.setAdapter(adapter);
    }

    public class JoinButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(isCourseMember){
                ((MainActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container,
                        new LectureContentsFragment(lecture)).addToBackStack(null).commit();
            }
            else{
                //joinButton.setText("Inhalt");
                checkMember.executeAddMembership(courseName);
                checkMember.executeAddUserCourses(courseName);
                checkMember.executeCheckUserCourses();
            }
        }
    }

    public void setJoinText(boolean isJoined) {
//        if(joinButton == null){
//            joinButton = root.findViewById(R.id.joinButton);
//        }
        if(isJoined){
            joinButton.setText("Inhalt");
        }
        else{
            joinButton.setText("Join Course");
        }
    }
}