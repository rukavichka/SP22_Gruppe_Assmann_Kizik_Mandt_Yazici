package com.example.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Model.Room;
import com.example.View.CalendarFragment;
import com.example.View.FilterFragment;
import com.example.View.MainActivity;
import com.example.View.ScrollTest;
import com.example.readdatabase.R;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder>{

    List<Room> roomList;

    public RoomAdapter(List<Room> roomList) {
        this.roomList = roomList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_roomlistelement, parent, false);
        return new RoomAdapter.ViewHolder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.roomlistelementTitle.setText(roomList.get(position).getRoomNumber());
        holder.roomlistelementSubtitle.setText(roomList.get(position).getBuilding());
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView roomlistelementTitle;
        TextView roomlistelementSubtitle;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomlistelementTitle = itemView.findViewById(R.id.roomlistelementTitle);
            roomlistelementSubtitle = itemView.findViewById(R.id.roomlistelementSubtitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Room room = new Room("testraum");
                    for (Room r : roomList){
                        if(r.getRoomNumber().equals(roomlistelementTitle.getText())){
                            room = r;
                        }
                    }
                    ((MainActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container,
                            new CalendarFragment(room)).addToBackStack("RoomSearchPageFragment").commit();
                }
            });
        }
    }
}
