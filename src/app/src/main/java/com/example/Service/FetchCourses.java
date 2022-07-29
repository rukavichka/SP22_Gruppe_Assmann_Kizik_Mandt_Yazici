package com.example.Service;

import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.Model.Lecture;
import com.example.Model.VerificationProcess;
import com.example.SoapAPI.Firebase;
import com.example.SoapAPI.FirebaseItem;
import com.example.View.LectureDetailsPageFragment;
import com.example.View.LectureSearchPageFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class  FetchCourses extends AsyncTask<Integer, Void, Void> {
    private static final Firebase firebase = new Firebase();
    private String courseName;
    private ArrayList<Lecture> info;
    private WeakReference<Fragment> weakReference;
    private List<String> joinedCourses;
    private boolean isJoined;
    private boolean isFiltered;

    public FetchCourses() {
        super();
    }

    public void setWeakReference(Fragment fragment) {
        this.weakReference = new WeakReference<>(fragment);
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    protected Void doInBackground(Integer... mode) {
        ValueEventListener vListener = new ValueEventListener() {
            /** gets invoked when attached or when data changes within Firebase
             * @param snapshot current state of Firebase
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //is being invoked to load course data from Firebase
                if (mode[0] == 0) {
                    setCourseSmallData(snapshot);
                    LectureSearchPageFragment temp = (LectureSearchPageFragment) weakReference.get();
                    temp.recyclerViewLecture(info);
                    temp.hideProgressBar();
                }
                //is being invoked to load detailed course data from Firebase
                else if (mode[0] == 1) {
                    LectureDetailsPageFragment temp = (LectureDetailsPageFragment) weakReference.get();
                    setCourseLargeData(snapshot);
                    temp.hideProgressBar();
                    temp.setCourseInfo(info);
                }
                // gets the names of joined courses from the user_courses entry in Firebase
                else if (mode[0] == 2 || mode[0] == 3) {
                    joinedCourses = new ArrayList<>();
                    isJoined = true;
                    for (DataSnapshot sn : snapshot.getChildren()) {
                        joinedCourses.add(sn.getValue().toString());
                    }
                    if(mode[0] == 2) doInBackground(0);
                    else doInBackground(1);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };
        if(mode[0] == 0) {
            firebase.getCourseDatabase("course-short-info").addValueEventListener(vListener);   //.limitToFirst(10).addValueEventListener(vListener);
        }
        else if(mode[0] == 1) {
            firebase.getCourseDatabase("veranstaltungen").orderByChild("titleSemabh").equalTo(courseName).addValueEventListener(vListener);
        }
        else if(mode[0] == 2 || mode[0] == 3) {
            firebase.getCourseDatabase("user_courses/" + VerificationProcess.getInstance().userId).addListenerForSingleValueEvent(vListener);
        }
        return null;
    }


    /** gets the relevant course data from the snapshot
     * @param snapshot current state of Firebase
     */
    public void setCourseSmallData(DataSnapshot snapshot) {
        info = new ArrayList<>();
        for(DataSnapshot ds:snapshot.getChildren()){
            FirebaseItem item = ds.getValue(FirebaseItem.class);
            Lecture temp = new Lecture(item.getTitleSemabh(), item.getRespLecturer(), item.getSemester(), item.getLectureNum(), item.getForm(), item.getRoom());
            if(isJoined) {
                if(joinedCourses.contains(temp.getLectureName())) {
                    info.add(temp);
                }
            }
            else {
                info.add(temp);
            }
        }
        if(isFiltered) {
            filterResult();
        }
    }

    /** gets more detailed information about te courses from the snapshot
     * @param snapshot current state of Firebase
     */
    public void setCourseLargeData(DataSnapshot snapshot) {
        info = new ArrayList<>();
        for(DataSnapshot ds:snapshot.getChildren()){
            FirebaseItem item = ds.getValue(FirebaseItem.class);
            ArrayList<ArrayList<String>> deneme = item.getExams();
            HashMap<String, ArrayList<ArrayList<String>>> content = new HashMap<>();
            content.put("klausur", item.getExams());
            content.put("forum", item.getForum());
            content.put("vorlesungen", item.getLectures());
            content.put("übungen", item.getExercises());
            Lecture lecture = new Lecture(courseName, item.getRespLecturer(), item.getWeekDay() + " " + item.getTimeFrom() + "-" + item.getTimeTill(), item.getLectureNum(), item.getForm(), item.getSemester(), item.getRoom(), content, "", item.getFrom() + "-" + item.getTill());
            if(isJoined) {
                if(joinedCourses.contains(lecture.getLectureName())){
                    info.add(lecture);
                }
            }
            else {
                info.add(lecture);
            }
        }
    }

    /**
     * the function for creating weakReference and filtering the courses
     */
    public void filterResult() {

        LectureSearchPageFragment temp = (LectureSearchPageFragment) weakReference.get();
        HashMap<String, String> filterparameters = temp.getFilterParameters();

        filterFunction(filterparameters);
    }

    /**
     * the function for filtering the courses according to the filterparameters
     * @param filterparameters current state of Firebase
     */
    public void filterFunction(HashMap<String, String> filterparameters) {

        ArrayList<Lecture> filteredResult = new ArrayList<>();

        for(Lecture lecture : info) {
            if (lecture.getProfessorName().equals("")){
                lecture.setProfessorName();     // will be "unknown"
            }
            if ((lecture.getLectureName().equals(filterparameters.get("titleSemabh")) ||
                    lecture.getProfessorName().equals(filterparameters.get("respLecturer")) ||
                    lecture.getSemester().equals(filterparameters.get("semester")) ||
                    lecture.getForm().equals(filterparameters.get("form")))) {
                if(isJoined) {
                    if(joinedCourses.contains(lecture.getLectureName())) {
                        filteredResult.add(lecture);
                    }
                }
                else {
                    filteredResult.add(lecture);
                }
            }
        }
        info = filteredResult;
    }

    public void setIsFiltered(boolean isFiltered) {
        this.isFiltered = isFiltered;
    }

    public void setIsJoined(boolean isJoined){
        this.isJoined = isJoined;
    }

    public ArrayList<Lecture> getInfo(){
        return this.info;
    }

    public void setJoinedCourses(List<String> joinedCourses) {
        this.joinedCourses = joinedCourses;
    }

}
