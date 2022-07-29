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
    ArrayList<ArrayList<String>> contents;
    String courseName;
    String type;

    public ContentItemAdapter(String type, String courseName, ArrayList<ArrayList<String>> contents) {
        this.type = type;
        this.contents = contents;
        this.courseName = courseName;
    }

    @NonNull
    @Override
    public ContentItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_content_item, parent, false);
        return new ContentItemAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentItemAdapter.ViewHolder holder, int position) {
        holder.title.setText(contents.get(position).get(1));
        holder.details.setText(contents.get(position).get(0));
        if(type.equals("vorlesungen") || type.equals("klausur")) holder.icon.setImageResource(R.drawable.ic_presentation);
        else if(type.equals("übungen")) holder.icon.setImageResource(R.drawable.ic_homework);
        else if(type.equals("forum")) holder.icon.setImageResource(R.drawable.ic_forum);

        holder.onClick(courseName, contents.get(position).get(0));
    }


    @Override
    public int getItemCount() {
        return contents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
        }

        public void onClick(String lectureName, String fileName) {
            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DownloadContent contentDownloader = new DownloadContent(mainLayout.getContext());
                    contentDownloader.execute(lectureName, fileName);
                }
            });
        }
    }
}

