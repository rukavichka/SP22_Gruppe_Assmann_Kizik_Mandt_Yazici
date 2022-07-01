package com.example.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.Model.Room;
import com.example.readdatabase.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScrollTest#} factory method to
 * create an instance of this fragment.
 */
public class ScrollTest extends Fragment {

    View root;
    Room room;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_calendar_room, container, false);
        return root;
    }

    public ScrollTest(Room room) {
        this.room = room;
    }
}