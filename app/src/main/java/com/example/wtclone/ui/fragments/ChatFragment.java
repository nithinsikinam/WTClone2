package com.example.wtclone.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wtclone.ui.activities.ChatActivity;
import com.example.wtclone.R;
import com.example.wtclone.adapters.ChatAdapter;
import com.example.wtclone.database.DatabaseHelper;
import com.example.wtclone.models.Chat;

import java.util.List;

public class ChatFragment extends Fragment {
    private RecyclerView chatRecyclerView;
    private List<Chat> chatList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        DatabaseHelper db = new DatabaseHelper(getContext());
        chatList = db.getAllChats();
        chatRecyclerView = view.findViewById(R.id.chatRecyclerView);
        chatRecyclerView.setHasFixedSize(true);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ChatAdapter adapter = new ChatAdapter(chatList);
        chatRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ChatAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Chat clickedChat = chatList.get(position);
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("chatId", clickedChat.getId());
                intent.putExtra("username",clickedChat.getName());
                startActivity(intent);
            }
        });


        return view;
    }
}
