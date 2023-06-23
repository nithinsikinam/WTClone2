package com.example.wtclone.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wtclone.R;
import com.example.wtclone.models.Chat;

import java.util.List;

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.ParticipantViewHolder> {

    private List<Chat> participants;

    public ParticipantAdapter(List<Chat> participants) {
        this.participants = participants;
    }

    @NonNull
    @Override
    public ParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_participant, parent, false);
        return new ParticipantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantViewHolder holder, int position) {
        Chat participant = participants.get(position);

        holder.participantName.setText(participant.getName());

    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    public static class ParticipantViewHolder extends RecyclerView.ViewHolder {
        ImageView participantImage;
        TextView participantName;

        public ParticipantViewHolder(@NonNull View itemView) {
            super(itemView);
            participantImage = itemView.findViewById(R.id.participantImage);
            participantName = itemView.findViewById(R.id.participantName);
        }
    }
}
