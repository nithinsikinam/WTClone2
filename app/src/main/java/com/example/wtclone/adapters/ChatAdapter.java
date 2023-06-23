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

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    private final List<Chat> chatList;
    private ItemClickListener listener;

    public ChatAdapter(List<Chat> chatList) {
        this.chatList = chatList;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat currentChat = chatList.get(position);
        holder.imageView.setImageResource(currentChat.getImage());
        holder.nameView.setText(currentChat.getName());
        if (currentChat.getReceivedMessage() != null) {
            holder.messageView.setText(currentChat.getReceivedMessage());
        } else {
            holder.messageView.setText(currentChat.getSentMessage());
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameView;
        TextView messageView;

        ChatViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.chatImage);
            this.nameView = itemView.findViewById(R.id.chatName);
            this.messageView = itemView.findViewById(R.id.chatMessage);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position);
                }
            });
        }
    }
}
