package com.example.Service;

import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.Model.Lecture;
import com.example.Model.VerificationProcess;
import com.example.SoapAPI.Firebase;
import com.example.SoapAPI.FirebaseItem;
import com.example.View.FilterFragment;
import com.example.View.LectureDetailsPageFragment;
import com.example.View.LectureSearchPageFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class  FetchCourses extends AsyncTask<Integer, Void, Void> {
    private static final Firebase firebase = new Firebase();
    private String courseName;
    private static final HashMap<String, HashMap<String, String>> result = new HashMap<>();
    private static final ArrayList<String> resultCoursesTitles = new ArrayList<>(Arrays.asList(""));
    private static final ArrayList<String> resultCoursesForms = new ArrayList<>(Arrays.asList(""));
    private ArrayList<Lecture> info;
    private WeakReference<Fragment> weakReference;
    private List<String> joinedCourses = new ArrayList<>();

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
                    temp.recyclerViewLecture(result);
                    temp.hideProgressBar();
                }
                //is being invoked to load detailed course data from Firebase
                else if (mode[0] == 1) {
                    info = new ArrayList<>();
                    LectureDetailsPageFragment temp = (LectureDetailsPageFragment) weakReference.get();
                    setCourseLargeData(snapshot);
                    temp.hideProgressBar();
                    temp.setCourseInfo(info);
                }
                else if (mode[0] == 2) {
                    //setCourseList(snapshot);
                    FilterFragment temp = (FilterFragment) weakReference.get();
                    temp.setSpinnerCourse(resultCoursesTitles);
                } else if (mode[0] == 3) {
                    setCourseFormsList(snapshot);
                    FilterFragment temp = (FilterFragment) weakReference.get();
                    temp.setSpinnerCourseForm(resultCoursesForms);
                } else if (mode[0] == 4) {
                    LectureSearchPageFragment temp = (LectureSearchPageFragment) weakReference.get();
                    HashMap<String, String> filterparameters = temp.getFilterParameters();
                    System.out.println("Filterparams" + filterparameters);
                    setFilteredCourseSmallData(snapshot, filterparameters);
                    temp.hideProgressBar();
                    temp.recyclerViewLecture(result);
                }
                // gets the names of joined courses from the user_courses entry in Firebase
                else if (mode[0] == 5) {
                    joinedCourses.clear();
                    for (DataSnapshot sn : snapshot.getChildren()) {
                        joinedCourses.add(sn.getValue().toString());
                        doInBackground(6);
                    }
                }
                // is being invoked to display joined user courses cards
                else if (mode[0] == 6) {
                    setJoinedCourseData(snapshot);
                    LectureSearchPageFragment temp = (LectureSearchPageFragment) weakReference.get();
                    temp.hideProgressBar();
                    temp.recyclerViewLecture(result);

                }
                // gets the names of joined courses from the user_courses entry in Firebase
                else if (mode[0] == 7) {
                    joinedCourses.clear();
                    for (DataSnapshot sn : snapshot.getChildren()) {
                        joinedCourses.add(sn.getValue().toString());
                        doInBackground(8);
                    }
                }
                // is being invoked to realize the filter functionality.
                else if (mode[0] == 8) {
                    LectureSearchPageFragment temp = (LectureSearchPageFragment) weakReference.get();
                    HashMap<String, String> filterParameters = temp.getFilterParameters();
                    setJoinedCourseDataFilter(snapshot, filterParameters);
                    temp.hideProgressBar();
                    temp.recyclerViewLecture(result);
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
            firebase.getCourseDatabase("veranstaltungen").addValueEventListener(vListener);
        }
        else if(mode[0] == 4 || mode[0] == 8) {
            firebase.getCourseDatabase("veranstaltungen").addValueEventListener(vListener);
        }
        else if(mode[0] == 5 || mode[0] == 7) {
            firebase.getCourseDatabase("user_courses/" + VerificationProcess.getInstance().userId).addListenerForSingleValueEvent(vListener);
        }
        else if(mode[0] == 6) {
            firebase.getCourseDatabase("veranstaltungen").addListenerForSingleValueEvent(vListener);
        }
        return null;
    }


    /** implements the filter function on joined courses
     * @param snapshot current state of Firebase
     * @param filterparameters
     */
    public void setJoinedCourseDataFilter(DataSnapshot snapshot, HashMap<String, String> filterparameters) {
        result.clear();
        for(DataSnapshot ds:snapshot.getChildren()){
            HashMap<String, String> info = new HashMap<>();
            FirebaseItem item = ds.getValue(FirebaseItem.class);


            if ((item.getTitleSemabh().equals(filterparameters.get("titleSemabh")) ||
                    item.getRespLecturer().equals(filterparameters.get("respLecturer")) ||
                    item.getSemester().equals(filterparameters.get("semester")) ||
                    item.getRoom().equals(filterparameters.get("room")) ||
                    item.getForm().equals(filterparameters.get("form"))) && joinedCourses.contains(item.getTitleSemabh())) {

                info.put("semester", item.getSemester());
                info.put("form", item.getForm());
                info.put("number", item.getLectureNum());
                info.put("prof", item.getRespLecturer());
                result.put(item.getTitleSemabh(), info);
            }
        }
    }

    /**
     * to get the courses forms as a list from Firebase without duplicates
     * @param snapshot
     */
    private void setCourseFormsList(DataSnapshot snapshot) {
        for(DataSnapshot ds:snapshot.getChildren()){
            String form = ds.child("form").getValue(String.class);
            if (!resultCoursesForms.contains(form)) {
                resultCoursesForms.add(form);
            }
        }
    }

    /** gets course data from firebase from those courses which are contained in joinedCourses list
     * @param snapshot current stae of Firebase
     */
    public void setJoinedCourseData(DataSnapshot snapshot){
        result.clear();
        for(DataSnapshot ds:snapshot.getChildren()){
            HashMap<String, String> info = new HashMap<>();
            FirebaseItem item = ds.getValue(FirebaseItem.class);
            if(joinedCourses.contains(item.getTitleSemabh())){
                info.put("semester", item.getSemester());
                info.put("form", item.getForm());
                info.put("number", item.getLectureNum());
                info.put("prof", item.getRespLecturer());
                result.put(item.getTitleSemabh(), info);
            }
        }
    }

    /** gets the relevant course data from the snapshot
     * @param snapshot current state of Firebase
     */
    public void setCourseSmallData(DataSnapshot snapshot) {
        result.clear();
        for(DataSnapshot ds:snapshot.getChildren()){
            HashMap<String, String> info = new HashMap<>();
            FirebaseItem item = ds.getValue(FirebaseItem.class);
            //System.out.println(item.getTitleSemabh());
            info.put("semester", item.getSemester());
            info.put("form", item.getForm());
            info.put("number", item.getLectureNum());
            info.put("prof", item.getRespLecturer());
            result.put(item.getTitleSemabh(), info);
        }
    }

    public void setFilteredCourseSmallData(DataSnapshot snapshot, HashMap<String, String> filterparameters) {
        result.clear();
        for(DataSnapshot ds:snapshot.getChildren()){
            HashMap<String, String> info = new HashMap<>();
            FirebaseItem item = ds.getValue(FirebaseItem.class);


            if (item.getTitleSemabh().equals(filterparameters.get("titleSemabh")) ||
                    item.getRespLecturer().equals(filterparameters.get("respLecturer")) ||
                    item.getSemester().equals(filterparameters.get("semester")) ||
                    item.getRoom().equals(filterparameters.get("room")) ||
                    item.getForm().equals(filterparameters.get("form"))) {

                info.put("semester", item.getSemester());
                info.put("form", item.getForm());
                info.put("number", item.getLectureNum());
                info.put("prof", item.getRespLecturer());
                result.put(item.getTitleSemabh(), info);
            }
            System.out.println(result);
        }
    }

    /** gets more detailed information about te courses from the snapshot
     * @param snapshot current state of Firebase
     */
    public void setCourseLargeData(DataSnapshot snapshot) {
        info = new ArrayList<>();
        for(DataSnapshot ds:snapshot.getChildren()){
            FirebaseItem item = ds.getValue(FirebaseItem.class);
            Lecture lecture = new Lecture(courseName, item.getRespLecturer(), item.getWeekDay() + " " + item.getTimeFrom() + "-" + item.getTimeTill(), item.getLectureNum(), item.getForm(), item.getSemester(), item.getRoom(), "", "", item.getFrom() + "-" + item.getTill());
            info.add(lecture);
        }
    }

    public HashMap<String, HashMap<String, String>> getResult() {
        return result;
    }

    public void setJoinedCourses(List<String> joinedCourses){
        this.joinedCourses = joinedCourses;
    }

    public ArrayList<Lecture> getInfo() {
        return info;
    }
}
