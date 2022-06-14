package com.example.SoapAPI;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

// singleton pattern
public class Firebase {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> listData;
    private DatabaseReference courseDatabase;
    private String USER_KEY = "veranstaltungen";             // group in Database

    //constructor
    public Firebase() {
        listData = new ArrayList<>();
        courseDatabase = FirebaseDatabase.getInstance().getReference(USER_KEY);
        System.out.println("courses: " + showCourses().toString());
    }



    /**
     * FA: Dem User wird eine Liste der von allen Veranstaltungen des FB12 angezeigt
     */
    public List<String> showCourses() {

        // calling add value event listener method
        // for getting the values from database.
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<String> lData = new ArrayList<>();

                // this method is called to get the realtime updates in the data.
                // this method is called when the data is changed in our Firebase console.
                // below line is for getting the data from snapshot of our database.
                //String value = snapshot.getValue(String.class);


                for(DataSnapshot ds:snapshot.getChildren()){
                    FirebaseItem item = ds.getValue(FirebaseItem.class); //FirebaseItem.class);
                    //System.out.println(item.getStg());
                    lData.add(item.getTitleSemabh());
                }

                System.out.println(lData);
                // after getting the value we are setting
                // our value to our text view in below line.
                // retrieveTV.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                //Toast.makeText(MainActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        };
        courseDatabase.addValueEventListener(vListener);
        return listData;
    }

    /**
     * FA: Der User kann die Veranstaltungen mit einem Suchterm aussortieren
     */
    public void sortCourses() {

    }

    /**
     * FA: Der User kann die Veranstaltungen mit den Filtereinstellungen aussortieren
     */
    public void filterCourses() {

    }

    /**
     * FA: Der User kann den Zeitplan bzw. Belegung eines Raumes sehen
     */
    public void roomSchedule() {

    }

    /**
     * FA: Mit Eingabe einer definierten Zeit kann der User leere Räume suchen
     */
    public void searchFreeRooms() {

    }

    /**
     * FA: Mit Eingabe einer definierten Zeit kann der User leere Räume suchen
     */
    public ArrayList<String> showCourseDescription() {
        ArrayList<String> courseDescription = new ArrayList<>();
        return courseDescription;
    }

}
