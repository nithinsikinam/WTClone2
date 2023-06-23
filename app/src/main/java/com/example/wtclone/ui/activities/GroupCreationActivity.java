package com.example.wtclone.ui.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wtclone.R;
import com.example.wtclone.adapters.ParticipantAdapter;
import com.example.wtclone.database.DatabaseHelper;
import com.example.wtclone.models.Chat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GroupCreationActivity extends AppCompatActivity {
    private RecyclerView participantsRecyclerView;
    private ParticipantAdapter participantAdapter;
    private List<Chat> participants;
    private DatabaseHelper databaseHelper;
    private EditText groupName;
    private ImageView groupImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_creation);

        databaseHelper = new DatabaseHelper(this);

        groupName = findViewById(R.id.groupName);
        groupImage = findViewById(R.id.groupImage);

        participantsRecyclerView = findViewById(R.id.participantsRecyclerView);
        participantsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Integer> selectedIds = getIntent().getIntegerArrayListExtra("selectedIds");

        participants = new ArrayList<>();
        for (Integer id : selectedIds) {
            participants.add(databaseHelper.getChat(id));
        }

        participantAdapter = new ParticipantAdapter(participants);
        participantsRecyclerView.setAdapter(participantAdapter);

        FloatingActionButton createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(v -> {

            String name = groupName.getText().toString();
            Toast.makeText(GroupCreationActivity.this, "Group Created", Toast.LENGTH_SHORT).show();
        });
    }
}
