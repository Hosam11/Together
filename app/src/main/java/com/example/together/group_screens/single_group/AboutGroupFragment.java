package com.example.together.group_screens.single_group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.Adapters.AboutMembersRecyclerAdapter;
import com.example.together.Adapters.POJO;
import com.example.together.R;

import java.util.ArrayList;

public class AboutGroupFragment extends Fragment {

    RecyclerView members_recycler;
    ArrayList<POJO> pojos = new ArrayList<>();
    AboutMembersRecyclerAdapter adapter;
    boolean isAdmin = true;

    public AboutGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about_group,
                container, false);
//        setContentView(R.layout.activity_about);

//        getSupportActionBar().hide();
        members_recycler = view.findViewById(R.id.members_recycler);

        pojos.add(new POJO("Android Developing", "", R.drawable.default_img));
        pojos.add(new POJO("IOS Developing", "", R.drawable.default_img));
        pojos.add(new POJO("Kotlin Developing", "", R.drawable.default_img));
        pojos.add(new POJO("Flutter Developing", "", R.drawable.default_img));
        pojos.add(new POJO("React Developing", "", R.drawable.default_img));
        pojos.add(new POJO("Ionic Developing", "", R.drawable.default_img));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        members_recycler.setLayoutManager(layoutManager);
        adapter = new AboutMembersRecyclerAdapter(pojos, isAdmin, getContext());
        members_recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new AboutMembersRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


    public void removeItem(int position) {
        //Alert here
        pojos.remove(position);
        adapter.notifyItemRemoved(position);
    }
}
