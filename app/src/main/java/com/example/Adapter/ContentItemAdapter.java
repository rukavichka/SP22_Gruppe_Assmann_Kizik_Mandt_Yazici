package com.example.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Service.DownloadContent;
import com.example.readdatabase.R;
import java.util.ArrayList;

public class ContentItemAdapter extends RecyclerView.Adapter<ContentItemAdapter.ViewHolder> {
    ArrayList<String[]> contents;

    public ContentItemAdapter(ArrayList<String[]> contents) {
        this.contents = contents;
    }

    @NonNull
    @Override
    public ContentItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_content_item, parent, false);
        return new ContentItemAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentItemAdapter.ViewHolder holder, int position) {
        holder.title.setText(contents.get(position)[0]);
        holder.details.setText(contents.get(position)[1]);
        // Change the icon depending on homework, lecture pdf
        // holder.icon.setImageDrawable();
    }


    @Override
    public int getItemCount() {
        return contents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView details;
        ImageView icon;
        ConstraintLayout mainLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.contentLayout);
            title = itemView.findViewById(R.id.contentTitle);
            details = itemView.findViewById(R.id.contentDetail);
            icon = itemView.findViewById(R.id.contentIcon);
            mainLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Download the corresponding data
            DownloadContent contentDownloader = new DownloadContent();
            // Add Params
            // Param1: Lecture name
            // Param2: Data type -> Vorlesung, Ubung, Klasur
            // Param3: file name
            contentDownloader.execute();
        }
    }
}

