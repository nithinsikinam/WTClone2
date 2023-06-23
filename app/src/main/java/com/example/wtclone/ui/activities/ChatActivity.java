package com.example.wtclone.ui.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wtclone.R;
import com.example.wtclone.adapters.MessageAdapter;
import com.example.wtclone.database.DatabaseHelper;
import com.example.wtclone.models.Message;

import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView chatRecyclerView;
    private List<Message> messageList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        int chatId = getIntent().getIntExtra("chatId", -1);
        String username = getIntent().getStringExtra("username");
        if (chatId == -1) {
            finish();
            return;
        }


        EditText chatInput = findViewById(R.id.chatInput);
        ImageButton sendButton = findViewById(R.id.sendButton);
        TextView usernameWidget = findViewById(R.id.username);
        sendButton.setOnClickListener(v -> {
            String messageText = chatInput.getText().toString();
            if (!messageText.isEmpty()) {

                Message newMessage = new Message(messageText, true, 1);
                messageList.add(newMessage);
                chatInput.setText("");
                chatRecyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        DatabaseHelper db = new DatabaseHelper(this);
        messageList = db.getMessagesForChat(chatId);
        chatRecyclerView = findViewById(R.id.messageRecyclerView);
        chatRecyclerView.setHasFixedSize(true);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        MessageAdapter adapter = new MessageAdapter(messageList);
        chatRecyclerView.setAdapter(adapter);
        usernameWidget.setText(username);
    }
}

