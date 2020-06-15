package com.example.together.group_screens.single_group;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.together.CustomProgressDialog;
import com.example.together.R;
import com.example.together.ToDoListPachage.ToDoListMain;
import com.example.together.data.model.Group;
import com.example.together.data.model.User;
import com.example.together.data.storage.Storage;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.UsersViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Objects;


public class GroupViewPager extends AppCompatActivity {

    ViewPager2 viewPager2;
    TextView tvGroupName;
    Storage storage;

    private UsersViewModel usersViewModel;

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

        tvGroupName = findViewById(R.id.tv_pager_group_name);

        Objects.requireNonNull(getSupportActionBar()).hide();

        Storage userStorage = new Storage(this);
        Storage groupStorage = new Storage();

        Group savedGroup = groupStorage.getGroup(this);
        tvGroupName.setText(savedGroup.getGroupName());

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

        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);

         storage = new Storage(this);

        if (HelperClass.checkInternetState(this)) {
            CustomProgressDialog.getInstance(this).show();
            usersViewModel.fetchUserData(storage.getId(), storage.getToken()).observe(this,
                    this::fetchUserDataObserve);
        } else {
//            HelperClass.showAlert("Error", HelperClass.checkYourCon, this);
               CustomProgressDialog.getInstance(this).cancel();
        }

    }

    private void fetchUserDataObserve(User user) {
        if(user!=null) {
            storage.saveUserName(user.getName(), this);
            Log.i(HelperClass.TAG, "GroupViewPager --  fetchUserDataObserve: uNmae >> "
                    + user.getName());
            CustomProgressDialog.getInstance(this).cancel();
        }

        else {
            CustomProgressDialog.getInstance(this).cancel();
           // HelperClass.showAlert("Error", HelperClass.SERVER_DOWN, this);



        }
    }
}
