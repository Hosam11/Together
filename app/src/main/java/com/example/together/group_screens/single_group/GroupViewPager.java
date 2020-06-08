package com.example.together.group_screens.single_group;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.together.R;
import com.example.together.ToDoListPachage.ToDoListMain;
import com.example.together.data.model.Group;
import com.example.together.data.storage.Storage;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Objects;


public class GroupViewPager extends AppCompatActivity {

    ViewPager2 viewPager2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_group);
        findViewById(R.id.goToDoBoard).setOnClickListener((e) -> {
            Intent goToToDoBoard = new Intent(this, ToDoListMain.class);
            goToToDoBoard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(goToToDoBoard);
            finish();
        });

        Objects.requireNonNull(getSupportActionBar()).hide();

        Storage userStorage = new Storage(this);
        Storage groupStorage = new Storage();

        Group savedGroup = groupStorage.getGroup(this);
        boolean isAdmin = savedGroup.getAdminID() == userStorage.getId();

        viewPager2 = findViewById(R.id.view_pager);
        viewPager2.setAdapter(new GroupPagerAdapter(this, this,
                isAdmin));

        TabLayout tabLayout = findViewById(R.id.tab_layout);


        TabLayoutMediator tabLayoutNotAdmin = new TabLayoutMediator(
                tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0: {
                    tab.setText("Chat");
                    break;
                }
                case 1: {
                    tab.setText("About");
                    break;
                }
            }
        }
        );

        TabLayoutMediator tabLayoutAdmin = new TabLayoutMediator(
                tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0: {
                    tab.setText("Chat");
                    break;
                }
                case 1: {
                    tab.setText("Requests");
                    break;
                }
                case 2: {
                    tab.setText("About");
                }
            }
        }
        );


        if (isAdmin) {
            tabLayoutAdmin.attach();
        } else {
            tabLayoutNotAdmin.attach();
        }



        findViewById(R.id.goToDoBoard).setOnClickListener(v -> {
            Intent todoList = new Intent(this, ToDoListMain.class);
            startActivity(todoList);

        });

    }
}
