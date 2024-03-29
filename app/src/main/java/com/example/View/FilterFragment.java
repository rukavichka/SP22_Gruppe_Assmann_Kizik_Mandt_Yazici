package com.example.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.Model.Lecture;
import com.example.Service.FetchFilters;
import com.example.readdatabase.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * view class for the filter
 */
public class FilterFragment extends Fragment {

    View root;
    Spinner spinnerDozent;
    Spinner spinnerSemester;
    Spinner spinnerTitle;
    Spinner spinnerCourseType;
    Button filterApplyButton;
    Button closeButton;
    FetchFilters fetchFilters;
    ArrayList<Lecture> courses;
    int mode;

    public FilterFragment(int mode, ArrayList<Lecture> courses){
        super();
        this.courses = courses;
        this.mode = mode;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_filter, container, false);
        setFilters();
        // Listener for the Close Button
        closeButton = root.findViewById(R.id.CloseButton);
        closeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((MainActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container,
                        new LectureSearchPageFragment(mode)).addToBackStack(null).commit();
            }
        });

        // Listener for the Filter Apply Button
        filterApplyButton = root.findViewById(R.id.filterApplyButton);
        filterApplyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String dozent = spinnerDozent.getSelectedItem().toString();
                String semester = spinnerSemester.getSelectedItem().toString();
                String title = spinnerTitle.getSelectedItem().toString();
                String courseType = spinnerCourseType.getSelectedItem().toString();
                HashMap<String, String> options = new HashMap<String,String>();
                options.put("respLecturer", dozent);
                options.put("semester", semester);
                options.put("titleSemabh", title);
                options.put("form", courseType);
                ((MainActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container,
                        new LectureSearchPageFragment(options,mode)).addToBackStack(null).commit();
            }
        });

        return root;
    }

    public void setFilters() {
        fetchFilters = new FetchFilters();
        if(mode == 2) {
            fetchFilters.setLectures(courses);
        }
        fetchFilters.setWeakReference(this);
        fetchFilters.execute();
    }

    /**
     * The method takes a list of dozents and sets it as a DropDown Menu
     * for dozents in the filter fragment
     * @param dozents
     */
    public void setSpinnerDozent(ArrayList<String> dozents){
        spinnerDozent = root.findViewById(R.id.spinnerDozent);
        ArrayAdapter<CharSequence> dozentAdapter =  new ArrayAdapter(root.getContext(), android.R.layout.simple_list_item_1, dozents);
        dozentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDozent.setAdapter(dozentAdapter);
    }

    /**
     * The method sets options for the semester as a DropDown Menu in the filter fragment
     */
    public void setSpinnerSemester(){
        spinnerSemester = root.findViewById(R.id.spinnerSemester);
        ArrayList<String> semesterList = new ArrayList<>(Arrays.asList("", "SoSe2022", "WiSe2022"));
        ArrayAdapter<CharSequence> semesterAdapter =  new ArrayAdapter(root.getContext(), android.R.layout.simple_list_item_1, semesterList);
        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemester.setAdapter(semesterAdapter);
    }

    /**
     * The method takes a list of lectures and sets it as a DropDown Menu
     * for lectures in the filter fragment
     * @param lectures
     */
    public void setSpinnerCourse(ArrayList<String> lectures){
        spinnerTitle = root.findViewById(R.id.spinnerTitle);
        ArrayAdapter<CharSequence> lecturesAdapter =  new ArrayAdapter(root.getContext(), android.R.layout.simple_list_item_1, lectures);
        lecturesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTitle.setAdapter(lecturesAdapter);
    }

    /**
     * The method takes a list of coursesForms and sets it as a DropDown Menu
     * for courses forms in the filter fragment
     * @param coursesForms
     */
    public void setSpinnerCourseForm(ArrayList<String> coursesForms){
        spinnerCourseType = root.findViewById(R.id.spinnerCourseType);
        ArrayAdapter<CharSequence> formsAdapter =  new ArrayAdapter(root.getContext(), android.R.layout.simple_list_item_1, coursesForms);
        formsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourseType.setAdapter(formsAdapter);
    }
}
