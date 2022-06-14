//package com.example.soapproject;
//
//import android.os.Bundle;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ReadActivity extends AppCompatActivity {
//
//    private ListView listView;
//    private ArrayAdapter<String> adapter;
//    private List<String> listData;
//    private DatabaseReference courseDatabase;
//    private String USER_KEY = "veranstaltungen";             // group in Database
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.read_layout);
//        init();
//        showCourses();
//    }
//
//    private void init(){
//        listView = findViewById(R.id.listView);
//        listData = new ArrayList<>();
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
//        listView.setAdapter(adapter);
//        courseDatabase = FirebaseDatabase.getInstance().getReference(USER_KEY);
//    }
//
//    public List<String> showCourses() {
//
//        if (listData.size() > 0) listData.clear();
//
//        // calling add value event listener method
//        // for getting the values from database.
//        ValueEventListener vListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                List<String> lData = new ArrayList<>();
//
//                // this method is called to get the realtime updates in the data.
//                // this method is called when the data is changed in our Firebase console.
//                // below line is for getting the data from snapshot of our database.
//                //String value = snapshot.getValue(String.class);
//
//
//                for(DataSnapshot ds:snapshot.getChildren()){
//                    FirebaseItem item = ds.getValue(FirebaseItem.class); //FirebaseItem.class);
//                    assert item != null;
//                    listData.add(item.getTitleSemabh());
//                }
//                adapter.notifyDataSetChanged();
//
//                System.out.println(lData);
//                // after getting the value we are setting
//                // our value to our text view in below line.
//                // retrieveTV.setText(value);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // calling on cancelled method when we receive
//                // any error or we are not able to get the data.
//                //Toast.makeText(MainActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
//            }
//        };
//        courseDatabase.addValueEventListener(vListener);
//        return listData;
//    }
//
//
//}
