package com.example.wtclone.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wtclone.R;
import com.example.wtclone.models.Chat;

import java.util.List;

public class ToSelectAdapter extends RecyclerView.Adapter<ToSelectAdapter.ChatViewHolder> {

    private List<Chat> chatList;
    private OnItemClickListener onItemClickListener;

    public ToSelectAdapter(List<Chat> chatList, OnItemClickListener onItemClickListener) {
        this.chatList = chatList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        holder.bind(chat, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView chatName;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            chatName = itemView.findViewById(R.id.chatName);
        }

        public void bind(final Chat chat, final OnItemClickListener onItemClickListener) {
            chatName.setText(chat.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(chat);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Chat chat);
    }
}
