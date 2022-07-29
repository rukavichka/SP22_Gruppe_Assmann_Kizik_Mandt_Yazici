package com.example.View;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.Adapter.LectureAdapter;
import com.example.Model.Lecture;
import com.example.Service.FetchCourses;
import com.example.readdatabase.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;

public class  LectureSearchPageFragment extends Fragment {
    private ProgressBar progressBar;
    ImageButton filterButton;
    private SearchView searchView;
    private View root;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private FetchCourses fetchCourses;
    private ArrayList<Lecture> courses;
    private int mode;
    //private String[] options;                    // dozent, semester, room, title, courseType
    private HashMap<String, String> options;

    public LectureSearchPageFragment(HashMap<String, String> options, int mode) {
        this.options = options;
        this.mode = mode;
    }

    public LectureSearchPageFragment(int mode) {
        this.mode = mode;
        this.options = new HashMap<String, String>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_lecture_search_page, container, false);
        TextView mainTitle = ((MainActivity)root.getContext()).findViewById(R.id.main_title);
        progressBar = root.findViewById(R.id.progress_loader);
        filterButton = root.findViewById(R.id.filterButton);
        filterButton.setOnClickListener(new FilterClickListener());
        fetchCourses = new FetchCourses();
        fetchCourses.setWeakReference(this);
        if(mode == 1) {
            mainTitle.setText(R.string.main_title_all_courses);
            if (!options.isEmpty()) {
                fetchCourses.setIsFiltered(true);
            }
            fetchCourses.execute(0);
            searchWidget();
        }
        if(mode == 2){
            mainTitle.setText(R.string.main_title_my_courses);
            if (!options.isEmpty()) {
                fetchCourses.setIsFiltered(true);
            }
            fetchCourses.execute(2);
            searchWidget();
        }
        return root;
    }

    public void recyclerViewLecture(ArrayList<Lecture> courses) {
        this.courses = courses;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewList = root.findViewById(R.id.searchListView);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new LectureAdapter(courses);
        recyclerViewList.setAdapter(adapter);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }


    /**
     * implementation for the Search field
     */
    public void searchWidget() {
        searchView = root.findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                ArrayList<Lecture> sortedCourses = new ArrayList<>();
                for (Lecture lecture: courses) {
                    if (lecture.getLectureName().toLowerCase().contains(s.toLowerCase())) {
                        sortedCourses.add(lecture);
                    }
                }
                adapter = new LectureAdapter(sortedCourses);
                recyclerViewList.setAdapter(adapter);
                return false;
            }
        });
    }

    public class FilterClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            ((MainActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container,
                    new FilterFragment(mode, courses)).addToBackStack("LectureSearchPageFragment").commit();
        }
    }

    public HashMap<String, String> getFilterParameters(){
        return this.options;
    }


}