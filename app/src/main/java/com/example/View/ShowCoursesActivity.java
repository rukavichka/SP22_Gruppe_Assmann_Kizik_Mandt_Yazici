package com.example.View;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Model.AccessFirebase;
import com.example.readdatabase.R;
import com.example.soapproject.Firebase;
import com.example.soapproject.MyCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ShowCoursesActivity extends AppCompatActivity {
    public ArrayAdapter<String> adapter;
    ListView listView;
    public List<String> listData;
    public Map<String, List<String>> hmData;
    AccessFirebase afb;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_courses);
        init();

        //showAllCourses();
        //sortCourses("Ma");
        HashMap<String, String> filters = new HashMap<String, String>();
        filters.put("room", "3060/+3/A14");
        filterCourses(filters);
    }

    private void init(){
        listView = findViewById(R.id.listView);
        listData = new ArrayList<>();
        hmData = new HashMap<String, List<String>>();
        afb = new AccessFirebase();
    }


    public void showAllCourses(){
        MyCallback myFirebaseCallback = new MyCallback() {

            @Override
            public void onCallback(List<String> eventList, Map<String, List<String>> hm) {
                if (!listData.isEmpty()) { listData.clear();}
                if (!hmData.isEmpty()) { hmData.clear();}
                listData =eventList;
                hmData = hm;
                adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1, eventList);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };
        afb.showAllCourses(myFirebaseCallback);
    }

    public void sortCourses(String term){

        MyCallback myFirebaseCallback = new MyCallback() {

            @Override
            public void onCallback(List<String> eventList, Map<String, List<String>> hm) {
                if (!listData.isEmpty()) { listData.clear();}
                if (!hm.isEmpty()) { hmData.clear();}
                listData =eventList;
                hmData = hm;

                Map<String, List<String>> resultHashMap = new HashMap<String, List<String>>();
                ArrayList<String> resultList = new ArrayList<>();
                System.out.println("hm list: " + hmData);
                if (!hmData.isEmpty()) {
                    for (String key : hmData.keySet()) {
                        if(key.toLowerCase().contains(term.toLowerCase())){
                            resultHashMap.put(key,hmData.get(key));
                            resultList.add(key);
                        }
                    }
                }
                adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1, resultList);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };
        afb.showAllCourses(myFirebaseCallback);

        /**
        Map<String, List<String>> resultHashMap = new HashMap<String, List<String>>();
        ArrayList<String> resultList = new ArrayList<>();

        if (!this.hmData.isEmpty()) {
            for (String key : this.hmData.keySet()) {
                if(key.contains(term)){
                    resultHashMap.put(key,this.hmData.get(key));
                    resultList.add(key);
                }
            }
        }
        System.out.println("res list: " + resultList);
        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, resultList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
         */

    }

    public void filterCourses(HashMap<String, String> filterparameters) {

        MyCallback myFirebaseCallback = new MyCallback() {

            @Override
            public void onCallback(List<String> eventList, Map<String, List<String>> hm) {
                if (!listData.isEmpty()) {
                    listData.clear();
                }
                if (!hm.isEmpty()) {
                    hmData.clear();
                }
                listData = eventList;
                hmData = hm;

                adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, listData);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };
        afb.filterCourses(myFirebaseCallback, filterparameters);
    }

    public void showCourseDetails(String courseTitle) {

        MyCallback myFirebaseCallback = new MyCallback() {

            @Override
            public void onCallback(List<String> eventList, Map<String, List<String>> hm) {
                if (!listData.isEmpty()) {
                    listData.clear();
                }
                if (!hm.isEmpty()) {
                    hmData.clear();
                }
                listData = eventList;
                hmData = hm;

                adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, listData);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };
        afb.showCourseDetails(myFirebaseCallback, courseTitle);
    }

}