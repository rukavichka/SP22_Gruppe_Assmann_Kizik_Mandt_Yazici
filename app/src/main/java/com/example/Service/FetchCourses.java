package com.example.Service;

import android.os.AsyncTask;
import androidx.annotation.NonNull;
import com.example.SoapAPI.Firebase;
import com.example.SoapAPI.FirebaseItem;
import com.example.View.LectureSearchPageFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class  FetchCourses extends AsyncTask<Void, Void, Void> {
    private final Firebase firebase;
    private static final HashMap<String, HashMap<String, String>> result = new HashMap<>();
    private WeakReference<LectureSearchPageFragment> weakReference;

    public FetchCourses(LectureSearchPageFragment fragment) {
        super();
        this.firebase = new Firebase();
        this.weakReference = new WeakReference<>(fragment);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setCourse(snapshot);
                weakReference.get().hideProgressBar();
                weakReference.get().recyclerViewLecture(result);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };
        firebase.getCourseDatabase().limitToFirst(10).addValueEventListener(vListener);
        return null;
    }

    private void setCourse(DataSnapshot snapshot) {
        for(DataSnapshot ds:snapshot.getChildren()){
            HashMap<String, String> info = new HashMap<>();
            FirebaseItem item = ds.getValue(FirebaseItem.class);
            info.put("semester", item.getSemester());
            info.put("time", item.getFrom() + " - " + item.getTill());
            info.put("room", item.getRoom());
            info.put("prof", item.getRespLecturer());
            result.put(item.getTitleSemabh(), info);
        }
    }

    private void filterCourse() {

    }

    static public HashMap<String, HashMap<String, String>> getResult() {
        return result;
    }
}
