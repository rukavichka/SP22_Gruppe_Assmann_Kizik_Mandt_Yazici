package com.example.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Model.News;
import com.example.readdatabase.R;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    ArrayList<News> newsList;

    public NewsAdapter(ArrayList<News> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_news, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.newsTitle.setText(newsList.get(position).getTitle());
        holder.newsDate.setText(newsList.get(position).getDate());
        holder.newsContent.setText(newsList.get(position).getContent());
    }


    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle;
        TextView newsDate;
        TextView newsContent;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.newsTitleTextView);
            newsDate = itemView.findViewById(R.id.newsDateTextView);
            newsContent = itemView.findViewById(R.id.newsContentTextView);
            mainLayout = itemView.findViewById(R.id.newsLayout);
        }
    }
}
