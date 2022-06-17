package com.example.View;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.Adapter.LectureAdapter;
import com.example.Model.AccessFirebase;
import com.example.Model.Lecture;
import com.example.SoapAPI.MyCallback;
import com.example.readdatabase.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  LectureSearchPageFragment extends Fragment {
    private View root;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    public List<String> listData;
    public Map<String, List<String>> hmData;
    AccessFirebase afb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hmData = new HashMap<>();
        afb = new AccessFirebase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_lecture_search_page, container, false);
        showAllCourses();
        return root;
    }

    private void recyclerViewLecture(Map<String, List<String>> data) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewList = root.findViewById(R.id.lectureView);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        ArrayList<Lecture> courses = new ArrayList<Lecture>();
        for(String key : data.keySet()) {
            String professor = data.get(key).get(3);
            String time = data.get(key).get(1);
            String semester = data.get(key).get(0);
            String room = data.get(key).get(2);
            Lecture temp = new Lecture(key, professor, time, semester, room, "", "");
            courses.add(temp);
        }

        adapter = new LectureAdapter(courses);
        recyclerViewList.setAdapter(adapter);
    }

    public void showAllCourses(){
        MyCallback myFirebaseCallback = new MyCallback() {

            @Override
            public void onCallback(List<String> eventList, Map<String, List<String>> hm) {
                if (!hmData.isEmpty()) { hmData.clear();}
                hmData = hm;
                recyclerViewLecture(hm);
            }
        };
        afb.showAllCourses(myFirebaseCallback);
    }
}