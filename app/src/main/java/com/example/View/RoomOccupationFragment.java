package com.example.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.Model.Lecture;
import com.example.Model.Room;
import com.example.Service.FetchOccRooms;
import com.example.readdatabase.R;

import java.util.Calendar;
import java.util.HashMap;

public class RoomOccupationFragment extends Fragment {

    View root;
    Room room;
    int[] date;
    Calendar cal;

    HashMap<String, Lecture> occRooms = new HashMap<>();

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
        FetchOccRooms fetchOccRooms = new FetchOccRooms(cal, room);
        fetchOccRooms.setWeakReference(this);
        fetchOccRooms.execute();
        System.out.println("Das ist der ausgewählte Tag der Woche;" + cal.get(Calendar.DAY_OF_WEEK));




        return root;
    }

    public RoomOccupationFragment(Room room, int[] date) {
        this.room = room;
        this.date = date;
        this.cal = Calendar.getInstance();
        cal.set(date[2], date[1], date[0]);
    }

    public void setOccRooms(HashMap<String, Lecture> occRooms) {
        this.occRooms = occRooms;
    }

    public void populateTimeSlots(){
        if(occRooms.containsKey("8:00 - 10:00")){
            timeSlot1.setBackgroundColor(getResources().getColor(R.color.red));
            timeSlot1.setOnClickListener(new OnSlotClickListener(occRooms.get("8:00 - 10:00")));
        } else if(occRooms.containsKey("10:00 - 12:00")){
            timeSlot2.setBackgroundColor(getResources().getColor(R.color.red));
            timeSlot2.setOnClickListener(new OnSlotClickListener(occRooms.get("10:00 - 12:00")));
        } else if(occRooms.containsKey("12:00 - 14:00")){
            timeSlot3.setBackgroundColor(getResources().getColor(R.color.red));
            timeSlot3.setOnClickListener(new OnSlotClickListener(occRooms.get("12:00 - 14:00")));
        } else if(occRooms.containsKey("14:00 - 16:00")){
            timeSlot4.setBackgroundColor(getResources().getColor(R.color.red));
            timeSlot4.setOnClickListener(new OnSlotClickListener(occRooms.get("14:00 - 16:00")));
        }else if(occRooms.containsKey("16:00 - 18:00")){
            timeSlot5.setBackgroundColor(getResources().getColor(R.color.red));
            timeSlot5.setOnClickListener(new OnSlotClickListener(occRooms.get("16:00 - 18:00")));
        }else if(occRooms.containsKey("18:00 - 20:00")){
            timeSlot6.setBackgroundColor(getResources().getColor(R.color.red));
            timeSlot6.setOnClickListener(new OnSlotClickListener(occRooms.get("18:00 - 20:00")));
        }
    }

    public class OnSlotClickListener implements TextView.OnClickListener{
        public Lecture lucture;

        public OnSlotClickListener(Lecture lucture) {
            this.lucture = lucture;
        }

        @Override
        public void onClick(View v) {
            ((MainActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container,
                    new LectureDetailsPageFragment(lucture.getLectureName())).addToBackStack("CalendarFragment").commit();
        }
    }
}
