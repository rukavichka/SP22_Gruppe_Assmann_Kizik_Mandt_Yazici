package com.example.Service;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.Model.Room;
import com.example.SoapAPI.Firebase;
import com.example.SoapAPI.FirebaseItem;
import com.example.View.FilterFragment;
import com.example.View.RoomSearchPageFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FetchRooms extends AsyncTask<Integer, Void, Void>{
        private static final Firebase firebase = new Firebase();
        private static final HashMap<String, HashMap<String, String>> result = new HashMap<>();
        private static final ArrayList<String> resultList = new ArrayList<>(Arrays.asList(""));
        private ArrayList<Room> info;
        private WeakReference<Fragment> weakReference;

        public FetchRooms() {
            super();
        }

        public void setWeakReference(Fragment fragment) {
                this.weakReference = new WeakReference<>(fragment);
        }

        @Override
        protected Void doInBackground(Integer... mode) {
                ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(mode[0] == 0) {
                                        setRoomSmallData(snapshot);
                                        RoomSearchPageFragment temp = (RoomSearchPageFragment) weakReference.get();
                                        temp.hideProgressBar();
                                        temp.recyclerViewLecture(result);
                                }
                                else if(mode[0] == 1) {
                                        setRoomsList(snapshot);
                                        FilterFragment temp = (FilterFragment)weakReference.get();
                                        temp.setSpinnerRoom(resultList);
                                }

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                };

                firebase.getCourseDatabase("veranstaltungen").addValueEventListener(valueEventListener);
                return null;
        }

        /**
         * to get the rooms as a list from Firebase without duplicates
         * @param snapshot
         */
        private void setRoomsList(DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                        String room = ds.child("room").getValue(String.class);
                        if (!resultList.contains(room) && !(room == null)) {
                                resultList.add(room);
                        }
                }
        }

        private void setRoomSmallData(DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                        HashMap<String, String> info = new HashMap<>();
                        FirebaseItem item = ds.getValue(FirebaseItem.class);
                        info.put("roomName", item.getRoom());
                        result.put(item.getRoom(), info);
                }
        }

}
