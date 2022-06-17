package com.example.soapproject;

import android.widget.ArrayAdapter;

import java.util.List;
import java.util.Map;

public interface MyCallback {
    void onCallback(List<String> eventList, Map<String, List<String>> hm);
}
