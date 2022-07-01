package com.example.Service;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.Model.Lecture;
import com.example.Model.Room;
import com.example.SoapAPI.Firebase;
import com.example.SoapAPI.FirebaseItem;
import com.example.View.RoomSearchPageFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FetchOccRooms extends AsyncTask<Integer, Void, Void> {
    private static final Firebase firebase = new Firebase();
    private static final HashMap<String, Room> occRooms = new HashMap<>();
    private WeakReference<Fragment> weakReference;
    int[] date;

    public FetchOccRooms(int[] date) {
        super();
        this.date = date;
    }
    public void setWeakReference(Fragment fragment) {
        this.weakReference = new WeakReference<>(fragment);
    }



    @Override
    protected Void doInBackground(Integer... integers) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        firebase.getCourseDatabase("veranstaltungen").orderByChild("");

        return null;
    }

    private void setRoomSmallData(DataSnapshot snapshot) {
//
        for(DataSnapshot ds:snapshot.getChildren()){
//
//
//            FirebaseItem item = ds.getValue(FirebaseItem.class);
//            List<Lecture> list = new ArrayList<>();
//            //TODO: Implement filter to get room occupation data (List of Lectures using the specific room)
//        }
//
//            Room room = new Room(item.getRoom());
//            room.setLectureList(list);
//            occRooms.put(item.getRoom(), room);
        }
    }
}
