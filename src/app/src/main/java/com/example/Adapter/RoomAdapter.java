package com.example.Adapter;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Model.Date;
import com.example.Model.Lecture;
import com.example.Model.Room;
import com.example.View.LectureDetailsPageFragment;
import com.example.View.MainActivity;
import com.example.View.RoomDetailFragment;
import com.example.readdatabase.R;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder>{

    private final List<Room> roomList;
    private Date date;

    public RoomAdapter(List<Room> roomList, Date date) {
        this.roomList = roomList;
        this.date = date;
    }

    public RoomAdapter(List<Room> roomList) {
        this.roomList = roomList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_room, parent, false);
        return new RoomAdapter.ViewHolder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.room.setText(roomList.get(position).getRoomNumber());
        holder.building.setText(roomList.get(position).getBuilding());
        holder.setBackgroundColor(roomList.get(position).isFree(date));
        Lecture course = roomList.get(position).getLecture(date);
        if(course != null) {
            holder.courseName.setText(course.getLectureName());
            holder.courseName.setVisibility(View.VISIBLE);
            holder.setListener(date, roomList.get(position).getLectureList());
        }
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView room;
        TextView building;
        TextView courseName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            room = itemView.findViewById(R.id.roomName);
            building = itemView.findViewById(R.id.roomBuilding);
            courseName = itemView.findViewById(R.id.busyCourseName);
        }

        public void setListener(Date date, ArrayList<Lecture> lectures) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // ((MainActivity)view.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container,
                //            new LectureDetailsPageFragment(courseName)).addToBackStack(null).commit();
                    ((MainActivity)view.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container,
                            new RoomDetailFragment(lectures, date)).addToBackStack(null).commit();
                }
            });
        }

        public void setBackgroundColor(boolean isFree) {
            GradientDrawable temp = (GradientDrawable) itemView.getBackground().mutate();
            if (isFree) {
                temp.setColor(ContextCompat.getColor(itemView.getContext(), R.color.light_green));
            }
            else {
                temp.setColor(ContextCompat.getColor(itemView.getContext(), R.color.red));
            }
        }
    }
}
