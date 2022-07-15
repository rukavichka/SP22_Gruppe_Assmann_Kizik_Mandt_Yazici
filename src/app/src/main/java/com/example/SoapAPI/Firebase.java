package com.example.SoapAPI;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Firebase {

    private FirebaseDatabase courseDatabase;

    public Firebase() {
        courseDatabase = FirebaseDatabase.getInstance();
    }

    public DatabaseReference getCourseDatabase(String reference) {
        return courseDatabase.getReference(reference);
    }


    /**
     * FA: Der User kann die Veranstaltungen mit den Filtereinstellungen aussortieren
     */
    public void filterCourses(MyCallback myCallback, HashMap<String, String> filterparameters) {
        List<String> listData = new ArrayList<>();
        Map<String, List<String>> hm = new HashMap<String, List<String>>();

        // calling add value event listener method for getting the values from database.
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.getChildren()){

                    FirebaseItem item = ds.getValue(FirebaseItem.class  );
                    if (!listData.contains(item.getTitleSemabh())) {
                        List<String> values = new ArrayList<String>();
                        values.add(item.getSemester());
                        values.add(item.getFrom() + " - " + item.getTill());
                        values.add(item.getRoom());
                        values.add(item.getRespLecturer());
                        hm.put(item.getTitleSemabh(), values);
                    }
                    listData.add(item.getTitleSemabh());
                }
                System.out.println(hm);
                // removing duplicates, for hashmap removed in for loop
                Set<String> listWithoutDuplicates = new LinkedHashSet<String>(listData);
                listData.clear();
                listData.addAll(listWithoutDuplicates);
                myCallback.onCallback(listData, hm);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                //Toast.makeText(MainActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        };

        Query query = null;
//        for (String key : filterparameters.keySet()) {
//            query = courseDatabase.orderByChild(key).equalTo(filterparameters.get(key));
//        }
        query.addValueEventListener(vListener);
    }

    /**
     * FA: Die Details einer Veranstaltung werden angezeigt
     */
    public void showCourseDetails(MyCallback myCallback, String course) {
        List<String> listData = new ArrayList<>();
        Map<String, List<String>> hm = new HashMap<String, List<String>>();
        Map<String, List<String>> hmFirst = new HashMap<String, List<String>>();
        //Map<String, String> courseMap = new HashMap<String, String>();

        // calling add value event listener method for getting the values from database.
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.getChildren()){

                    FirebaseItem item = ds.getValue(FirebaseItem.class);
                    List<String> values = new ArrayList<String>();
                    values.add(item.getRespLecturer());
                    values.add(item.getFrom() + " - " + item.getTill());
                    //values.add(item.getWeekDay() + "," + item.getTimeFrom()+ "-" + item.getTimeTill());
                    values.add(item.getSemester());
                    values.add(item.getRoom());
                    hm.put(item.getTitleSemabh(), values);

                }


                /**
                map2.forEach(
                        (key, value) -> map1.merge( key, value, (v1, v2) -> v1.equalsIgnoreCase(v2) ? v1 : v1 + "," + v2)
                );
                for (String key : hm.keySet()) {
                    courseMap.put("course", key);
                    resultHashMap.put(key,this.hmData.get(key));
                    resultList.add(key);
                }
                 */

                System.out.println(hm);
                // removing duplicates, for hashmap removed in for loop
                Set<String> listWithoutDuplicates = new LinkedHashSet<String>(listData);
                listData.clear();
                listData.addAll(listWithoutDuplicates);
                myCallback.onCallback(listData, hm);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                //Toast.makeText(MainActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        };

//        Query query = courseDatabase.orderByChild("titleSemunabh").equalTo("course");
//        query.addValueEventListener(vListener);
    }


}
