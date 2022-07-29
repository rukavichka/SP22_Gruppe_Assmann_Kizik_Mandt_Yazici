package com.example.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.Adapter.ActivityAdapter;
import com.example.Adapter.NewsAdapter;
import com.example.Model.Activity;
import com.example.Model.News;
import com.example.readdatabase.R;

import java.util.ArrayList;
import java.util.Objects;

/**
 * view class to be the initial page after login. Has no functionality. Can be used for future implementations of features.
 */
public class HomePageFragment extends Fragment {
    private View root;
    private RecyclerView.Adapter adapterNews, adapterActivity;
    private RecyclerView recyclerViewNewsList, recyclerViewActivityList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_home_page, container, false);
        recyclerViewNews();
        recyclerViewActivities();
        return root;
    }

    private void recyclerViewNews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewNewsList = root.findViewById(R.id.newsView);
        recyclerViewNewsList.setLayoutManager(linearLayoutManager);

        ArrayList<News> newsList = new ArrayList<News>();
        newsList.add(new News("News 1", "06/13/2022", "News 1 content"));
        newsList.add(new News("News 2", "06/13/2022", "News 2 content"));
        newsList.add(new News("News 3", "06/13/2022", "News 3 content"));
        newsList.add(new News("News 4", "06/13/2022", "News 3 content"));
        newsList.add(new News("News 5", "06/13/2022", "News 3 content"));
        newsList.add(new News("News 6", "06/13/2022", "News 3 content"));
        newsList.add(new News("News 7", "06/13/2022", "News 3 content"));

        adapterNews = new NewsAdapter(newsList);
        recyclerViewNewsList.setAdapter(adapterNews);
    }

    private void recyclerViewActivities() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewActivityList = root.findViewById(R.id.activityView);
        recyclerViewActivityList.setLayoutManager(linearLayoutManager);

        ArrayList<Activity> activityList = new ArrayList<Activity>();
        activityList.add(new Activity("Activity 1", "06/13/2022", "Activity 1 content"));
        activityList.add(new Activity("Activity 2", "06/13/2022", "Activity 2 content"));
        activityList.add(new Activity("Activity 3", "06/13/2022", "Activity 3 content"));
        activityList.add(new Activity("Activity 4", "06/13/2022", "Activity 3 content"));
        activityList.add(new Activity("Activity 5", "06/13/2022", "Activity 3 content"));
        activityList.add(new Activity("Activity 6", "06/13/2022", "Activity 3 content"));
        activityList.add(new Activity("Activity 7", "06/13/2022", "Activity 3 content"));

        adapterActivity = new ActivityAdapter(activityList);
        recyclerViewActivityList.setAdapter(adapterActivity);
    }
}