package com.example.together.ToDoListPachage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.together.R;

import java.util.ArrayList;

public class ToDoListMainEntry extends Fragment implements GetAddTaskButton {

    ArrayList<Pair<Long, String>> list = new ArrayList<Pair<Long, String>>();
    public Button addTask;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_to_do_list_main_entry, container, false);
        addTask=v.findViewById(R.id.add_task);
        return v;
    }

    @Override
    public Button getAddTask() {
        return null;
    }
}
