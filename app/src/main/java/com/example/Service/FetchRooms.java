package com.example.Service;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.Model.Lecture;
import com.example.Model.Room;
import com.example.SoapAPI.Firebase;
import com.example.SoapAPI.FirebaseItem;
import com.example.View.LectureSearchPageFragment;
import com.example.View.RoomSearchPageFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

public class FetchRooms extends AsyncTask<Integer, Void, Void>{
        private static final Firebase firebase = new Firebase();
        private static final HashMap<String, HashMap<String, String>> result = new HashMap<>();
        private ArrayList<Room> info;
        private WeakReference<Fragment> weakReference;

        public FetchRooms() {
            super();
        }

        public void setWeakReference(Fragment fragment) {
                this.weakReference = new WeakReference<>(fragment);
        }

        @Override
        protected Void doInBackground(Integer... integers) {
                ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                setCourseSmallData(snapshot);
                                RoomSearchPageFragment temp = (RoomSearchPageFragment)weakReference.get();
                                temp.hideProgressBar();
                                temp.recyclerViewLecture(result);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                };
                firebase.getCourseDatabase("veranstaltungen").addValueEventListener(valueEventListener);
                return null;
        }

        private void setCourseSmallData(DataSnapshot snapshot) {
                int counter = 0;
                for(DataSnapshot ds:snapshot.getChildren()){
                        System.out.println(counter++);
                        HashMap<String, String> info = new HashMap<>();
                        FirebaseItem item = ds.getValue(FirebaseItem.class);
                        info.put("roomName", item.getRoom());
                        result.put(item.getRoom(), info);
                }
        }

}
