package com.example.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.Model.News;
import com.example.Service.FetchCourses;
import com.example.Service.FetchDozents;
import com.example.Service.FetchRooms;
import com.example.readdatabase.R;

import java.util.ArrayList;
import java.util.Arrays;

public class FilterFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    View root;
    Spinner spinnerDozent;
    Spinner spinnerSemester;
    Spinner spinnerRoom;
    Spinner spinnerTitle;
    Spinner spinnerCourseType;
    FetchDozents fetchDozents;
    FetchCourses fetchCourses;
    FetchRooms fetchRooms;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_filter, container, false);

        // Spinner for dozents
        fetchDozents = new FetchDozents();
        fetchDozents.setWeakReference(this);
        fetchDozents.execute(0);

        // Spinner for semesters
        setSpinnerSemester();

        // Spinner for rooms
        fetchRooms = new FetchRooms();
        fetchRooms.setWeakReference(this);
        fetchRooms.execute(1);

        // Spinner for courses titles
        fetchCourses = new FetchCourses();
        fetchCourses.setWeakReference(this);
        fetchCourses.execute(2);

        // Spinner for course type
        fetchCourses = new FetchCourses();
        fetchCourses.setWeakReference(this);
        fetchCourses.execute(3);

        return root;
    }


    public void setSpinnerDozent(ArrayList<String> dozents){
        spinnerDozent = root.findViewById(R.id.spinnerDozent);
        ArrayAdapter<CharSequence> dozentAdapter =  new ArrayAdapter(root.getContext(), android.R.layout.simple_list_item_1, dozents);
        dozentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDozent.setAdapter(dozentAdapter);
    }

    public void setSpinnerSemester(){
        spinnerSemester = root.findViewById(R.id.spinnerSemester);
        ArrayList<String> semesterList = new ArrayList<>(Arrays.asList("", "SoSe2022", "WiSe2022"));
        ArrayAdapter<CharSequence> semesterAdapter =  new ArrayAdapter(root.getContext(), android.R.layout.simple_list_item_1, semesterList);
        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemester.setAdapter(semesterAdapter);
    }

    public void setSpinnerRoom(ArrayList<String> rooms){
        spinnerRoom = root.findViewById(R.id.spinnerRoom);
        ArrayAdapter<CharSequence> roomAdapter =  new ArrayAdapter(root.getContext(), android.R.layout.simple_list_item_1, rooms);
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoom.setAdapter(roomAdapter);
    }

    public void setSpinnerCourse(ArrayList<String> lectures){
        spinnerTitle = root.findViewById(R.id.spinnerTitle);
        ArrayAdapter<CharSequence> lecturesAdapter =  new ArrayAdapter(root.getContext(), android.R.layout.simple_list_item_1, lectures);
        lecturesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTitle.setAdapter(lecturesAdapter);
    }

    public void setSpinnerCourseForm(ArrayList<String> coursesForms){
        spinnerCourseType = root.findViewById(R.id.spinnerCourseType);
        ArrayAdapter<CharSequence> formsAdapter =  new ArrayAdapter(root.getContext(), android.R.layout.simple_list_item_1, coursesForms);
        formsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourseType.setAdapter(formsAdapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
