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

public class ContentsDetailPage extends Fragment {
    private View root;
    private String type;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ArrayList<String[]> content;

    public ContentsDetailPage(String type, ArrayList<String[]> content) {
        this.type = type;
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
        recyclerViewList = root.findViewById(R.id.searchListView);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new ContentItemAdapter(type, content);
        recyclerViewList.setAdapter(adapter);
    }
}