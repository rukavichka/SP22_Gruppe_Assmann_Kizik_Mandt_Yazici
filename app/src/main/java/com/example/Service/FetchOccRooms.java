package com.example.Service;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.Model.Lecture;
import com.example.Model.Room;
import com.example.SoapAPI.Firebase;
import com.example.SoapAPI.FirebaseItem;
import com.example.View.RoomOccupationFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class FetchOccRooms  {
    private static final Firebase firebase = new Firebase();
    private HashMap<String, Lecture> occRooms = new HashMap<>();
    private List<FirebaseItem> items = new ArrayList<>();
    private WeakReference<Fragment> weakReference;
    private HashMap<Integer, String> dayOfWeekMap;
    Calendar cal;
    Room room;

    public FetchOccRooms(Calendar calendar, Room room) {
        super();
        this.cal = calendar;
        this.room = room;
        this.dayOfWeekMap = new HashMap<>();
        this.dayOfWeekMap.put(1,"So");
        this.dayOfWeekMap.put(2,"Mo");
        this.dayOfWeekMap.put(3,"Di");
        this.dayOfWeekMap.put(4,"Mi");
        this.dayOfWeekMap.put(5,"Do");
        this.dayOfWeekMap.put(6,"Fr");
        this.dayOfWeekMap.put(7,"Sa");
    }
    public void setWeakReference(Fragment fragment) {
        this.weakReference = new WeakReference<>(fragment);
    }




    public void execute(Integer... integers) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getCourses(snapshot);
                filterCourse();
                RoomOccupationFragment roomOccupationFragment = (RoomOccupationFragment)weakReference.get();
                roomOccupationFragment.setOccRooms(occRooms);
                roomOccupationFragment.populateTimeSlots();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        firebase.getCourseDatabase("veranstaltungen").orderByChild("room").equalTo(room.getRoomNumber()).addListenerForSingleValueEvent(valueEventListener);

    }

    private void filterCourse() {
        String dayOfWeek = dayOfWeekMap.get(cal.get(Calendar.DAY_OF_WEEK)); //doesn't check if the day is within the time period of the semester
        for(FirebaseItem fuego : items){
            if(fuego.getWeekDay().equals(dayOfWeek)){
                String concat = fuego.getTimeFrom() + "-" + fuego.getTimeTill();
                Lecture lecture = new Lecture(fuego.getTitleSemabh(), fuego.getRespLecturer(), fuego.getTimeFrom() + " - " + fuego.getTimeTill(), fuego.getLectureNum(), fuego.getForm(), fuego.getSemester(), fuego.getRoom(), "", "", fuego.getFrom() + " - " + fuego.getTill());
                occRooms.put(lecture.getLectureTime(), lecture);
            }

        }
    }

    private void getCourses(DataSnapshot snapshot) {
        List<FirebaseItem> temp = new ArrayList<>();
        for(DataSnapshot ds:snapshot.getChildren()){
            FirebaseItem firebaseItem = ds.getValue(FirebaseItem.class);
            temp.add(firebaseItem);
        }
        items = temp;
    }

}
