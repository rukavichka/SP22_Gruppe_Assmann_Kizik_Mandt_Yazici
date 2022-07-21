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
import com.example.View.ChatFragment;
import com.example.View.LectureDetailsPageFragment;
import com.example.View.MainActivity;
import com.example.readdatabase.R;

import java.util.List;

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.ViewHolder> {
    List<Participant> participants;

    public ParticipantAdapter(List<Participant> participants) {
        this.participants = participants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_participant, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.participantName.setText(participants.get(position).getIliasUsername());
        holder.setListener();
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
            participantProfilePic = itemView.findViewById(R.id.participantPic);
        }

        public void setListener(){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)view.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.constraint_container,
                            new ChatFragment()).addToBackStack(null).commit();
                }
            });
        }
    }
}