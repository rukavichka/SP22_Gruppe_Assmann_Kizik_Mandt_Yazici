package com.example.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Model.Participant;
import com.example.readdatabase.R;

import java.util.ArrayList;

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.ViewHolder> {
    ArrayList<Participant> participants;

    public ParticipantAdapter(ArrayList<Participant> participants) {
        this.participants = participants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_activity, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.participantName.setText(participants.get(position).getName());
//        holder.participantUsername.setText(participants.get(position).getDate());
//        holder.participantProfilePic.setText(participants.get(position).getContent());
    }


    @Override
    public int getItemCount() {
        return participants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView participantName;
        TextView participantUsername;
        ImageView participantProfilePic;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            participantName = itemView.findViewById(R.id.participantNameTitleTextView);
            mainLayout = itemView.findViewById(R.id.activityLayout);
        }
    }
}