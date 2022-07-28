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
}
