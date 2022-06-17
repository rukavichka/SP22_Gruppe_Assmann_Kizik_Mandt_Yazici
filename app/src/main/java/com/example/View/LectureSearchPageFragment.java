package com.example.View;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.Adapter.LectureAdapter;
import com.example.Model.AccessFirebase;
import com.example.Model.Lecture;
import com.example.Service.FetchCourses;
import com.example.SoapAPI.MyCallback;
import com.example.readdatabase.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  LectureSearchPageFragment extends Fragment {
    private ProgressBar progressBar;
    Button filterButton;
    private View root;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private FetchCourses fetchCourses;

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
        filterButton.setOnClickListener(new FilterClickListener());
        fetchCourses = new FetchCourses(this);
        fetchCourses.execute();
        return root;
    }

    public void recyclerViewLecture(HashMap<String, HashMap<String, String>> data) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewList = root.findViewById(R.id.lectureView);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        ArrayList<Lecture> courses = new ArrayList<Lecture>();
        for(String key : data.keySet()) {
            String professor = data.get(key).get("prof");
            String time = data.get(key).get("time");
            String semester = data.get(key).get("semester");
            String room = data.get(key).get("room");
            Lecture temp = new Lecture(key, professor, time, semester, room, "", "");
            courses.add(temp);
        }

        adapter = new LectureAdapter(courses);
        recyclerViewList.setAdapter(adapter);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    public class FilterClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            ((MainActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container,
                    new FilterFragment()).addToBackStack(null).commit();
        }
    }
}