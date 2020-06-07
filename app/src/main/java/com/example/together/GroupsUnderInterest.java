package com.example.together;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.Adapters.GroupsAdapter;
import com.example.together.data.model.Group;
import com.example.together.group_screens.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class GroupsUnderInterest extends AppCompatActivity {

    private List<Group> groupList = new ArrayList<>();
    private RecyclerView groupsRecyclerView;
    private GroupsAdapter groupsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_under_interest);
        getSupportActionBar().hide();
        groupsRecyclerView = findViewById(R.id.groups);
        groupsAdapter = new GroupsAdapter(groupList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        groupsRecyclerView.setLayoutManager(mLayoutManager);
        groupsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        groupsRecyclerView.setAdapter(groupsAdapter);
        groupsRecyclerView.addOnItemTouchListener(new GroupTouchListener(getApplicationContext(), groupsRecyclerView, new GroupTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Group group = groupList.get(position);
                Toast.makeText(getApplicationContext(), group.getGroupName() + " is selected!", Toast.LENGTH_SHORT).show();
                // TODO Nahla store the current group into to shared preference
                //  hint we need to get all group details
                //  so i can retrieve it from view group screen to display details about it

                Intent goViewGroup = new Intent(getApplicationContext(), ViewGroup.class);
                goViewGroup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(goViewGroup);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        prepareGroupsData();
    }

    private void prepareGroupsData() {
        groupList.add(new Group("Learning Android", R.drawable.development, "IT, CS, Development, Android"));
        groupList.add(new Group("Learning Android", R.drawable.development, "IT, CS, Development, Android"));
        groupList.add(new Group("Learning Android", R.drawable.development, "IT, CS, Development, Android"));
        groupList.add(new Group("Learning Android", R.drawable.development, "IT, CS, Development, Android"));
        groupList.add(new Group("Learning Android", R.drawable.development, "IT, CS, Development, Android"));
        groupList.add(new Group("Learning Android", R.drawable.development, "IT, CS, Development, Android"));
        groupList.add(new Group("Learning Android", R.drawable.development, "IT, CS, Development, Android"));
        groupList.add(new Group("Learning Android", R.drawable.development, "IT, CS, Development, Android"));
        groupList.add(new Group("Learning Android", R.drawable.development, "IT, CS, Development, Android"));
        groupList.add(new Group("Learning Android", R.drawable.development, "IT, CS, Development, Android"));
        groupList.add(new Group("Learning Android", R.drawable.development, "IT, CS, Development, Android"));
        groupList.add(new Group("Learning Android", R.drawable.development, "IT, CS, Development, Android"));
        groupList.add(new Group("Learning Android", R.drawable.development, "IT, CS, Development, Android"));
        groupList.add(new Group("Learning Android", R.drawable.development, "IT, CS, Development, Android"));
        groupList.add(new Group("Learning Android ", R.drawable.development, "IT, CS, Development, Android"));
        groupsAdapter.notifyDataSetChanged();
    }
}
