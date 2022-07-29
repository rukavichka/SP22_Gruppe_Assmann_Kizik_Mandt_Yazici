package com.example.View;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.Adapter.ContentItemAdapter;
import com.example.readdatabase.R;
import java.util.ArrayList;
import java.util.HashMap;

public class ContentsDetailPage extends Fragment {
    private View root;
    private final String courseName;
    private final ArrayList<ArrayList<String>> content;

    public ContentsDetailPage(String courseName, ArrayList<ArrayList<String>> content) {
        this.courseName = courseName;
        this.content = content;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_contents_detail_page, container, false);
        recyclerViewContent();
        return root;
    }

    public void recyclerViewContent() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerViewList = root.findViewById(R.id.contentItemsList);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        RecyclerView.Adapter adapter = new ContentItemAdapter(courseName, content);
        recyclerViewList.setAdapter(adapter);
    }
}