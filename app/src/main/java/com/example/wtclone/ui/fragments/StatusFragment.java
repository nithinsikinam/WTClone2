package com.example.wtclone.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wtclone.R;
import com.example.wtclone.models.Chat;
import com.example.wtclone.adapters.ChatAdapter;

import java.util.ArrayList;
import java.util.List;

public class StatusFragment extends Fragment {
    private List<Chat> chatList;
    private RecyclerView chatRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);


        return view;
    }


}

