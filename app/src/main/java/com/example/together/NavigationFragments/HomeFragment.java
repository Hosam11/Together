package com.example.together.NavigationFragments;

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

import com.example.together.Adapters.HomeRecyclarViewAdapter;
import com.example.together.Adapters.POJO;
import com.example.together.BottomNavigationView;
import com.example.together.R;
import com.example.together.group_screens.AddGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    FloatingActionButton fab;
    RecyclerView recyclerView;
    HomeRecyclarViewAdapter adapter;
    ArrayList<POJO> pojos = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);
        fab = v.findViewById(R.id.add_group_FAB);
        fab.setOnClickListener(v1 -> {
            //Intent To Create Group Screen
        });
        recyclerView=v.findViewById(R.id.home_groups_rv);
        pojos.add(new POJO("Android Developing","Hello, my name is ali, I live in KafrEldawar sdfds sdfsd sdfsds  sdf sdf sdf sdfsd sdf sd sdfds  dsfsd ",R.drawable.default_img));
        pojos.add(new POJO("IOS Developing","Hello, my name is ali, I live in KafrEldawar",R.drawable.default_img));
        pojos.add(new POJO("Kotlin Developing","Hello, my name is ali, I live in KafrEldawar",R.drawable.default_img));
        pojos.add(new POJO("Flutter Developing","Hello, my name is ali, I live in KafrEldawar",R.drawable.default_img));
        pojos.add(new POJO("React Developing","Hello, my name is ali, I live in KafrEldawar",R.drawable.default_img));
        pojos.add(new POJO("Ionic Developing","Hello, my name is ali, I live in KafrEldawar",R.drawable.default_img));

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BottomNavigationView)getActivity()).setActionBarTitle("Home");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(recyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter= new HomeRecyclarViewAdapter(pojos, this.getContext());
        recyclerView.setAdapter(adapter);

        getActivity().findViewById(R.id.btn_create_group_fragment).setOnClickListener(v -> {
            Intent createGroup = new Intent(getContext(), AddGroup.class);
            startActivity(createGroup);
        });

    }
}
