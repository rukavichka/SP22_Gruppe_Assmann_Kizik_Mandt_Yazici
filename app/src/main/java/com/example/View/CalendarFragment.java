package com.example.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.Model.Room;
import com.example.readdatabase.R;

public class CalendarFragment extends Fragment {

    View root;
    Room room;
    int[] date = new int[]{12,2,2002};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_calendar_room, container, false);
        //TextView text = root.findViewById(R.id.textView2);
        //text.setText(room.getRoomNumber());
        Button button = root.findViewById(R.id.buttonCalendar);
        button.setOnClickListener(new CalendarButtonClickListener());
        CalendarView calendar = root.findViewById(R.id.calendarView);
        return root;
    }

    public CalendarFragment(Room room) {
        this.room = room;
    }

    public class CalendarButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            ((MainActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container,
                    new RoomOccupationFragment(room, date)).addToBackStack("CalendarFragment").commit();
        }
    }

    public class CalendarDaxChangeListener implements CalendarView.OnDateChangeListener{

        @Override
        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            int[] temp = new int[3];
            temp[0] = dayOfMonth;
            temp[1] = month;            //maybe there is a better way to commit the date?
            temp[2] = year;
            date = temp;
        }
    }
}
