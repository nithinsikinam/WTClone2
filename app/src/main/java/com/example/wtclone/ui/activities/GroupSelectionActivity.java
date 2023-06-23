package com.example.wtclone.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wtclone.R;
import com.example.wtclone.adapters.ToSelectAdapter;
import com.example.wtclone.adapters.SelectedAdapter;
import com.example.wtclone.database.DatabaseHelper;
import com.example.wtclone.models.Chat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GroupSelectionActivity extends AppCompatActivity {

    private RecyclerView toSelectRecyclerView;
    private RecyclerView selectedRecyclerView;

    private ToSelectAdapter toSelectAdapter;
    private SelectedAdapter selectedAdapter;

    private List<Chat> toSelectList;
    private List<Chat> selectedList;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_selection);

        databaseHelper = new DatabaseHelper(this);

        toSelectList = databaseHelper.getAllChats();
        selectedList = new ArrayList<>();

        toSelectRecyclerView = findViewById(R.id.toSelectRecyclerView);
        selectedRecyclerView = findViewById(R.id.selectedRecyclerView);

        toSelectAdapter = new ToSelectAdapter(toSelectList, new ToSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Chat chat) {
                toSelectList.remove(chat);
                selectedList.add(chat);
                toSelectAdapter.notifyDataSetChanged();
                selectedAdapter.notifyDataSetChanged();
            }
        });

        selectedAdapter = new SelectedAdapter(selectedList, new SelectedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Chat chat) {
                selectedList.remove(chat);
                toSelectList.add(chat);
                toSelectAdapter.notifyDataSetChanged();
                selectedAdapter.notifyDataSetChanged();
            }
        });

        toSelectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        toSelectRecyclerView.setAdapter(toSelectAdapter);

        selectedRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        selectedRecyclerView.setAdapter(selectedAdapter);

        FloatingActionButton nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Integer> selectedIds = new ArrayList<>();
                for (Chat chat : selectedList) {
                    selectedIds.add(chat.getId());
                }

                Intent intent = new Intent(GroupSelectionActivity.this, GroupCreationActivity.class);
                intent.putIntegerArrayListExtra("selectedIds", selectedIds);
                startActivity(intent);
            }
        });
    }
}
