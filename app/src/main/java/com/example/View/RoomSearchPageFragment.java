package com.example.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Adapter.LectureAdapter;
import com.example.Adapter.RoomAdapter;
import com.example.Model.Lecture;
import com.example.Model.Room;
import com.example.Service.FetchRooms;
import com.example.readdatabase.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoomSearchPageFragment extends Fragment {
    private ProgressBar progressBar;
    ImageButton filterButton;
    private View root;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    FetchRooms fetchRooms;
    private TextView headerTitle;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_lecture_search_page, container, false);
        progressBar = root.findViewById(R.id.progress_loader);
        filterButton = root.findViewById(R.id.filterButton);
        headerTitle = ((MainActivity)root.getContext()).findViewById(R.id.main_title);
        headerTitle.setText(R.string.main_title_search_rooms);
        filterButton.setOnClickListener(new RoomSearchPageFragment.FilterClickListener());
        TextView searchHeader = root.findViewById(R.id.resultTextView);
        searchHeader.setText(R.string.rooms_little_header);
        fetchRooms = new FetchRooms();
        fetchRooms.setWeakReference(this);
        fetchRooms.execute(0);
        return root;
    }

    public void recyclerViewLecture(HashMap<String, HashMap<String, String>> data) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewList = root.findViewById(R.id.searchListView);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        List<Room> rooms = new ArrayList<Room>();
        for(String key : data.keySet()) {
            //String professor = data.get(key).get("prof");
            //String semester = data.get(key).get("semester");
            //String number = data.get(key).get("number");
            String roomName = data.get(key).get("roomName");
            rooms.add(new Room(roomName)); //add each room given from the class "FetchRooms" to List "rooms"
        }

        adapter = new RoomAdapter(rooms);
        recyclerViewList.setAdapter(adapter);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    public class FilterClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            ((MainActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container,
                    new FilterFragment(1)).addToBackStack("RoomSearchPageFragment").commit();
        }
    }
}
