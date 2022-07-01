package com.example.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.Model.Room;
import com.example.readdatabase.R;

public class RoomOccupationFragment extends Fragment {

    View root;
    Room room;
    int[] date;

    TextView timeSlot1;
    TextView timeSlot2;
    TextView timeSlot3;
    TextView timeSlot4;
    TextView timeSlot5;
    TextView timeSlot6;
    TextView timeSlot7;
    TextView roomTitle;



    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_test2, container, false);
        timeSlot1 = root.findViewById(R.id.timeSlot1);
        timeSlot2 = root.findViewById(R.id.timeSlot2);
        timeSlot3 = root.findViewById(R.id.timeSlot3);
        timeSlot4 = root.findViewById(R.id.timeSlot4);
        timeSlot5 = root.findViewById(R.id.timeSlot5);
        timeSlot6 = root.findViewById(R.id.timeSlot6);
        timeSlot7 = root.findViewById(R.id.timeSlot7);
        roomTitle = root.findViewById(R.id.roomTitle);
        roomTitle.setText(room.getRoomNumber());




        return root;
    }

    public RoomOccupationFragment(Room room, int[] date) {
        this.room = room;
        this.date = date;
    }
}
