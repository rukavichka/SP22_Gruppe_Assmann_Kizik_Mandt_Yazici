package com.example.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.Adapter.ParticipantAdapter;
import com.example.Adapter.RoomAdapter;
import com.example.Model.Participant;
import com.example.Model.Room;
import com.example.Service.FetchParticipants;
import com.example.Service.FetchRooms;
import com.example.readdatabase.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ParticipantPageFragment extends Fragment {
    private ProgressBar progressBar;
    ImageButton filterButton;
    private View root;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    FetchParticipants fetchParticipants;
    String courseName;

    public ParticipantPageFragment(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_lecture_search_page, container, false);
        progressBar = root.findViewById(R.id.progress_loader);
        filterButton = root.findViewById(R.id.filterButton);
        TextView searchHeader = root.findViewById(R.id.resultTextView);
        searchHeader.setText(R.string.pcpt_little_header);
        FetchParticipants fetchParticipants = new FetchParticipants(courseName);
        fetchParticipants.setWeakReference(this);
        fetchParticipants.execute();
        return root;
    }

    public void recyclerViewParticipants(HashMap<String, Participant> data) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewList = root.findViewById(R.id.searchListView);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        List<Participant> participants = new ArrayList<Participant>();

            //String professor = data.get(key).get("prof");
            //String semester = data.get(key).get("semester");
            //String number = data.get(key).get("number");
        participants.addAll(data.values());


        adapter = new ParticipantAdapter(participants);
        recyclerViewList.setAdapter(adapter);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}