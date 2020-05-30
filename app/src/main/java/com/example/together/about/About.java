package com.example.together.about;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.Adapters.AboutMembersRecyclerAdapter;
import com.example.together.Adapters.POJO;
import com.example.together.R;

import java.util.ArrayList;

public class About extends AppCompatActivity {

    RecyclerView members_recycler;
    ArrayList<POJO> pojos = new ArrayList<>();
    AboutMembersRecyclerAdapter adapter;
    boolean isAdmin = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().hide();
        members_recycler = findViewById(R.id.members_recycler);

        pojos.add(new POJO("Android Developing", "", R.drawable.default_img));
        pojos.add(new POJO("IOS Developing", "", R.drawable.default_img));
        pojos.add(new POJO("Kotlin Developing", "", R.drawable.default_img));
        pojos.add(new POJO("Flutter Developing", "", R.drawable.default_img));
        pojos.add(new POJO("React Developing", "", R.drawable.default_img));
        pojos.add(new POJO("Ionic Developing", "", R.drawable.default_img));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        members_recycler.setLayoutManager(layoutManager);
        adapter = new AboutMembersRecyclerAdapter(pojos, isAdmin, getApplicationContext());
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

    }

    public void removeItem(int position) {

        //Alert here
        pojos.remove(position);
        adapter.notifyItemRemoved(position);
    }
}
