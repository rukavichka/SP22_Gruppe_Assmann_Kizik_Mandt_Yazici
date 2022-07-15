package com.example.Service;

import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.Model.Data;
import com.example.Model.Lecture;
import com.example.Model.Room;
import com.example.SoapAPI.Firebase;
import com.example.View.RoomSearchPageFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

public class FetchRooms extends AsyncTask<Void, Void, Void>{
        private final Firebase firebase = new Firebase();
        private final ArrayList<Room> result = new ArrayList<>();
        private WeakReference<Fragment> weakReference;

        public FetchRooms() {
            super();
        }

        public void setWeakReference(Fragment fragment) {
                this.weakReference = new WeakReference<>(fragment);
        }

        @Override
        protected Void doInBackground(Void... voids) {
                ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                setRoomsList(snapshot);
                                RoomSearchPageFragment temp = (RoomSearchPageFragment) weakReference.get();
                                temp.setRooms(result);
                                temp.recyclerViewLecture(result);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {}
                };

                firebase.getCourseDatabase("rooms").addValueEventListener(valueEventListener);
                return null;
        }


        private void setRoomsList(DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                        HashMap<String, Object> info = (HashMap<String, Object>) ds.getValue();

                        // Setting up the room
                        String roomName = (String) info.get("room");
                        Room room = new Room(roomName);

                        // Gathering the lecture data in that given room
                        ArrayList<HashMap<String, Object>> classInfo = ((ArrayList<HashMap<String, Object>>) info.get("classes"));
                        for(HashMap<String, Object> map: classInfo) {
                                String className = (String) map.get("className");
                                String period = "";
                                ArrayList<String> times = new ArrayList<>();
                                HashMap<String, String> weeklyInfo = (HashMap<String, String>) map.get("weekly");
                                for(String key: (weeklyInfo.keySet())) {
                                        if(key.equals("period")) {
                                                period = weeklyInfo.get(key);
                                                continue;
                                        }
                                        times.add(key+ " " + weeklyInfo.get(key));
                                }
                                Lecture lecture = new Lecture(className, period, room.getRoomNumber(), times);
                                room.addLecture(lecture);
                        }
                        result.add(room);
                }
        }
}
