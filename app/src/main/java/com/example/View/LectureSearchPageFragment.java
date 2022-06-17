package com.example.View;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.Adapter.LectureAdapter;
import com.example.Model.Lecture;
import com.example.readdatabase.R;

import java.util.ArrayList;

public class  LectureSearchPageFragment extends Fragment {
    private View root;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_lecture_search_page, container, false);
        recyclerViewLecture();
        return root;
    }

    private void recyclerViewLecture() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewList = root.findViewById(R.id.activityView);
        recyclerViewList.setLayoutManager(linearLayoutManager);

        ArrayList<Lecture> courses = new ArrayList<Lecture>();
        courses.add(new Lecture("Lecture 1", "Professor X", "120min", "WS 2021/2022", "03CXV", "Trial Lecture", "Null"));
        courses.add(new Lecture("Lecture 2", "Professor X", "120min", "WS 2021/2022", "03CXV", "Trial Lecture", "Null"));
        courses.add(new Lecture("Lecture 3", "Professor X", "120min", "WS 2021/2022", "03CXV", "Trial Lecture", "Null"));
        courses.add(new Lecture("Lecture 4", "Professor X", "120min", "WS 2021/2022", "03CXV", "Trial Lecture", "Null"));
        courses.add(new Lecture("Lecture 5", "Professor X", "120min", "WS 2021/2022", "03CXV", "Trial Lecture", "Null"));
        courses.add(new Lecture("Lecture 6", "Professor X", "120min", "WS 2021/2022", "03CXV", "Trial Lecture", "Null"));
        courses.add(new Lecture("Lecture 7", "Professor X", "120min", "WS 2021/2022", "03CXV", "Trial Lecture", "Null"));

        adapter = new LectureAdapter(courses);
        recyclerViewList.setAdapter(adapter);
    }
}