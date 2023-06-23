package com.example.wtclone.adapters;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wtclone.models.Message;
import com.example.wtclone.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_sent_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_received_item, parent, false);
        }
        return new MessageViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message currentMessage = messageList.get(position);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (currentMessage.isSent()) {
            params.gravity = Gravity.END;
            holder.messageView.setBackgroundResource(R.color.colorAccent);
        } else {
            params.gravity = Gravity.START;
            holder.messageView.setBackgroundResource(R.color.offwhite);
        }
        holder.messageView.setLayoutParams(params);
        holder.messageView.setText(currentMessage.getText());
    }
    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        if (message.isSent()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageView;

        MessageViewHolder(View itemView) {
            super(itemView);
            this.messageView = itemView.findViewById(R.id.messageText);
        }
    }
}

