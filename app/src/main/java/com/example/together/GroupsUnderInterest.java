package com.example.together;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.together.Adapters.GroupsAdapter;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.storage.Storage;
import com.example.together.group_screens.ViewGroupInfo;
import com.example.together.group_screens.single_group.GroupViewPager;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.GroupViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.together.utils.HelperClass.ALERT;
import static com.example.together.utils.HelperClass.GROUP_FULL;
import static com.example.together.utils.HelperClass.USER_NOT_MEMBER;
import static com.example.together.utils.HelperClass.USER_WAITING_JOIN_GROUP;
import static com.example.together.utils.HelperClass.showAlert;

public class GroupsUnderInterest extends AppCompatActivity {

    GroupViewModel groupViewModel;
    Group curGroup;
    private List<Group> groupList = new ArrayList<>();
    private RecyclerView groupsRecyclerView;
    private GroupsAdapter groupsAdapter;
    private int groupID = 2;
    private Storage userStorage;
    private Storage commonStorage;

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
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        // Storage objects
        userStorage = new Storage(this);
        commonStorage = new Storage();
        groupsRecyclerView.addOnItemTouchListener(new GroupTouchListener(getApplicationContext(),
                groupsRecyclerView, new GroupTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                // FixME is null - [Nahla] don't store it in sharedPreference
                //  just receive group object in that variable
                curGroup = groupList.get(position);

                Toast.makeText(getApplicationContext(), curGroup.getGroupName() + " is selected!", Toast.LENGTH_SHORT).show();

                CustomProgressDialog.getInstance(GroupsUnderInterest.this).show();
                if (HelperClass.checkInternetState(GroupsUnderInterest.this)) {
                    groupViewModel.userRequestJoinStatus(groupID, userStorage.getId(), userStorage.getToken())
                            .observe(GroupsUnderInterest.this, GroupsUnderInterest.this::observeJoinStatus);

                } else {
                    CustomProgressDialog.getInstance(GroupsUnderInterest.this).cancel();
                    HelperClass.showAlert(ALERT, HelperClass.checkYourCon,
                            GroupsUnderInterest.this);

                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        prepareGroupsData();
    }

    /**
     * listen for response of server to see status user for group
     * if user in group go to viewpager else set value of userGroupStatus and
     * go to ViewGroupInfo
     *
     * @param generalRes 4 possible response String
     */
    private void observeJoinStatus(GeneralResponse generalRes) {
        // [cancel] dialoge of user status
        CustomProgressDialog.getInstance(this).cancel();
        // User Not Member
        if (generalRes.response.equals(USER_NOT_MEMBER)) {
            Log.i(HelperClass.TAG, getLocalClassName() + " -- observeJoinStatus: user not member");
            goViewGroupWithUserGroupStatus(USER_NOT_MEMBER);
        } // User Waiting
        else if (generalRes.response.equals(USER_WAITING_JOIN_GROUP)) {
            Log.i(HelperClass.TAG, getLocalClassName() + "ViewGroup -- observeJoinStatus: user waiting to response");
            goViewGroupWithUserGroupStatus(USER_WAITING_JOIN_GROUP);
        } // User In Group
        else if (generalRes.response.equals(HelperClass.USER_IN_GROUP)) {
            // go to view pager
            Log.i(HelperClass.TAG, getLocalClassName() + " -- observeJoinStatus:  user in group");
            Intent goGroup = new Intent(this, GroupViewPager.class);
            startActivity(goGroup);

        } // Group Is Full
        else if (generalRes.response.equals(HelperClass.GROUP_FULL)) {
            goViewGroupWithUserGroupStatus(GROUP_FULL);
        } else {
            showAlert("Error!", generalRes.response, this);
        }
    }

    /**
     * got to ViewGroupInfo with userGroupStatus to avoid call api again to know it
     * @param userGroupStatus 4 possible response String
     */
    private void goViewGroupWithUserGroupStatus(String userGroupStatus) {
        Intent goViewGroup = new Intent(getApplicationContext(), ViewGroupInfo.class);
        goViewGroup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        curGroup.setUserGroupStatus(userGroupStatus);
        commonStorage.saveGroup(curGroup, GroupsUnderInterest.this);
        getApplicationContext().startActivity(goViewGroup);
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
