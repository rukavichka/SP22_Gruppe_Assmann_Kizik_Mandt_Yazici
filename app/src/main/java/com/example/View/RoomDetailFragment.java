package com.example.View;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.Model.Date;
import com.example.Model.Lecture;
import com.example.readdatabase.R;

import java.util.ArrayList;
import java.util.HashMap;

public class RoomDetailFragment extends Fragment {
    View root;
    ArrayList<Lecture> lectures;
    Date date;

    public RoomDetailFragment(ArrayList<Lecture> lectures, Date date) {
        this.lectures = lectures;
        this.date = date;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_room_detail, container, false);
        setHours();
        showSchedule(inflater, container);
        return root;
    }

    private void showSchedule(LayoutInflater inflater, ViewGroup container) {
        int heightMultiplier = 1;
        double topMarginMultiplier = intToDP(49)/60.0;
        int leftMargin = intToDP(105);
        int height = intToDP(98);
        int width = intToDP(300);
        int startHour;
        int finishHour;
        int startMinute;
        int finishMinute;
        int[] ids = {R.id.clock8, R.id.clock10, R.id.clock12, R.id.clock14, R.id.clock16, R.id.clock18, R.id.clock20};
        int id;
        RelativeLayout relativeLayout = root.findViewById(R.id.roomDetailLayout);
        for(Lecture lecture: lectures) {
            if(lecture.getSchedule().isInPeriod(date)) {
                HashMap<String, String[]> times = lecture.getSchedule().getTimes();
                String[] hoursOfDay = times.get(date.getDayOfWeek());
                System.out.println(hoursOfDay[0]);
                System.out.println(hoursOfDay[1]);
                if(hoursOfDay != null) {
                    startHour = Integer.parseInt(hoursOfDay[0].split(":")[0]);
                    finishHour = Integer.parseInt(hoursOfDay[1].split(":")[0]);
                    startMinute = Integer.parseInt(hoursOfDay[0].split(":")[1]);

                    if(startHour >= 8 && startHour < 10) {
                        id = R.id.clock8;
                    }
                    else if(startHour >= 10 && startHour < 12) {
                        id = R.id.clock10;
                    }
                    else if(startHour >= 12 && startHour < 14) {
                        id = R.id.clock12;
                    }
                    else if(startHour >= 14 && startHour < 16) {
                        id = R.id.clock14;
                    }
                    else if(startHour >= 16 && startHour < 18) {
                        id = R.id.clock16;
                    }
                    else if(startHour >= 18 && startHour < 20) {
                        id = R.id.clock18;
                    }
                    else {
                        id = R.id.clock20;
                    }

                    heightMultiplier = (finishHour - startHour) / 2;
                    View inflate = inflater.inflate(R.layout.view_card_event, container, false);
                    inflate.setId(View.generateViewId());
                    TextView event = inflate.findViewById(R.id.eventName);
                    event.setText(lecture.getLectureName());
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.BELOW, id);
                    params.leftMargin = leftMargin;
                    params.topMargin = (int) (topMarginMultiplier * startMinute) - intToDP(19);
                    params.width = width;
                    params.height = height * heightMultiplier;

                    relativeLayout.addView(inflate, params);
                }
            }
        }
    }

    public int intToDP(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }

    private void setHours() {
        int[] ids = {R.id.clock8, R.id.clock10, R.id.clock12, R.id.clock14, R.id.clock16, R.id.clock18, R.id.clock20};
        String[] hours = {"08:00", "10:00", "12:00", "14:00", "16:00", "18:00", "20:00"};
        ConstraintLayout clock;
        for(int index = 0; index < ids.length; index++) {
            clock = root.findViewById(ids[index]);
            ((TextView) clock.findViewById(R.id.roomTime)).setText(hours[index]);
        }
    }
}