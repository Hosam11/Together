package com.example.together.groups;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.together.Adapters.GroupsAdapter;
import com.example.together.CustomProgressDialog;
import com.example.together.R;
import com.example.together.data.model.Group;
import com.example.together.data.storage.Storage;
import com.example.together.group_screens.CreateGroup;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.ExploreViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GroupsUnderInterestActivity extends AppCompatActivity {
    FloatingActionButton createGroup;
    private List<Group> groupList = new ArrayList<>();
    private RecyclerView groupsRecyclerView;
    private TextView interestName;
    private GroupsAdapter groupsAdapter;
    ExploreViewModel exploreViewModel;
    CustomProgressDialog progressDialog;
    LinearLayout emptyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_under_interest);
        getSupportActionBar().hide();
        emptyData =findViewById(R.id.alert_layout);
        createGroup = findViewById(R.id.add_group_FAB);
        createGroup.setOnClickListener(v1 -> {
            Intent createGroup = new Intent(this, CreateGroup.class);
            startActivity(createGroup);
        });
        groupsRecyclerView = findViewById(R.id.groups);
        interestName = findViewById(R.id.interestName);
        exploreViewModel = new ViewModelProvider(this).get(ExploreViewModel.class);
        progressDialog= CustomProgressDialog.getInstance(this);
        progressDialog.show();
        interestName.setText(new Storage().getInterest(this).getName());
       // getGroups();
        groupsAdapter = new GroupsAdapter(groupList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        groupsRecyclerView.setLayoutManager(mLayoutManager);
        groupsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        groupsRecyclerView.setAdapter(groupsAdapter);

    }

    private void getGroups() {
        Storage storage = new Storage(this);
        exploreViewModel.getInterestGroups(storage.getToken(),storage.getInterest(this).getId())
                .observe(this, groups -> {
            if(groups!=null){
                if (groups.isEmpty()){
                    emptyData.setVisibility(View.VISIBLE);
                }
                groupList.clear();
                groupList.addAll(groups);
                groupsAdapter.notifyDataSetChanged();
                CustomProgressDialog.getInstance(this).cancel();
            }
            else {
                CustomProgressDialog.getInstance(this).cancel();
                HelperClass.showAlert("Error",HelperClass.SERVER_DOWN,this);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        CustomProgressDialog.getInstance(this).show();
        if(HelperClass.checkInternetState(this)){
            getGroups();
        }
        else {

            HelperClass.showAlert("Error",HelperClass.checkYourCon,this);
            CustomProgressDialog.getInstance(this).cancel();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        CustomProgressDialog.getInstance(this).cancel();
    }

}
