package com.example.together.group_screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.together.CustomProgressDialog;
import com.example.together.R;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.storage.Storage;
import com.example.together.group_screens.single_group.GroupViewPager;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.GroupViewModel;

import java.util.Objects;

import static com.example.together.utils.HelperClass.PENDING;
import static com.example.together.utils.HelperClass.showAlert;

public class ViewGroup extends AppCompatActivity {


    // TextViews
    TextView tvGroupName;
    TextView tvGroupDesc;
    TextView tvGroupInterest;
    TextView tvGroupDuration;
    TextView tvGroupLocation;
    TextView tvGroupLevel;
    //
    ImageView ivGroupImg;
    //
    Button actionToGroup;
    //
    GroupViewModel groupViewModel;
    Storage storage;
    // TODO 10 is fixed for now
    int groupID = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group);
        Objects.requireNonNull(getSupportActionBar()).hide();

        tvGroupName = findViewById(R.id.tv_view_gp_name);
        tvGroupDesc = findViewById(R.id.tv_view_gp_desc);
        tvGroupInterest = findViewById(R.id.tv_view_gp_interest);
        tvGroupDuration = findViewById(R.id.tv_view_gp_duration);
        tvGroupLocation = findViewById(R.id.tv_view_gp_location);
        tvGroupLevel = findViewById(R.id.tv_view_gp_level);
        //
        ivGroupImg = findViewById(R.id.iv_view_gp);
        //
        actionToGroup = findViewById(R.id.btn_action_group);

        // FixMe i need group id here
        // dummy data just for test
        // Hint only groupID and adminID that will be real to test
        String gpName = "Android fro begginers";
        String desc = "android desc";
        String gpInterest = "java";
        String gpDuration = "3 weeks";
        String loation = "Egypt"; // may be null
        String gpLevel = "Begginer";

        Storage s = new Storage();
        //Group receivedGroup = s.getGroupUser(this);

        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        storage = new Storage(this);
        Log.i(HelperClass.TAG, "ViewGroup -- onCreate: btnIsEnable >> " + actionToGroup.isEnabled());

        actionToGroup.setVisibility(View.GONE);
        // [show] dialoge of user status
        CustomProgressDialog.getInstance(this).show();

        // to see whether user is waiting or not in group
        groupViewModel.userRequestJoinStatus(groupID, storage.getId(), storage.getToken())
                .observe(this, this::observeJoinStatus);

    }

    /**
     * listen for reponse of server to see if user in group or waiting to join
     *
     * @param generalRes
     */
    private void observeJoinStatus(GeneralResponse generalRes) {
        // [cancel] dialoge of user status
        CustomProgressDialog.getInstance(this).cancel();
        actionToGroup.setVisibility(View.VISIBLE);

        // User Not Member
        if (generalRes.response.equals(HelperClass.USER_NOT_MEMBER)) {
            Log.i(HelperClass.TAG, "ViewGroup -- observeJoinStatus: user not member");
            // Send join request
            // TODO groupID will change later
            actionToGroup.setOnClickListener(v -> {
                // [show] dialoge of user join
                CustomProgressDialog.getInstance(this).show();

                groupViewModel.requestJoinGroup(groupID, storage.getId(), storage.getToken())
                        .observe(this, this::observeSendJoinGroup);
            });

        } // User Waiting
        else if (generalRes.response.equals(HelperClass.USER_WAITING_JOIN_GROUP)) {
            Log.i(HelperClass.TAG, "ViewGroup -- observeJoinStatus: user waitting to response");
            pendingButton();

        } // User In Group
        else if (generalRes.response.equals(HelperClass.USER_IN_GROUP)) {
            Log.i(HelperClass.TAG, "ViewGroup -- observeJoinStatus: user in group");
            // TODO go to the group [ViewPagerGroup]
            Log.i(HelperClass.TAG, "ViewGroup -- observeJoinStatus: joinGroup click");
            Intent goGroup = new Intent(this, GroupViewPager.class);
            startActivity(goGroup);

        } else {
            showAlert("Error!", generalRes.response, this);
        }
    }

    private void observeSendJoinGroup(GeneralResponse generalResponse) {
        Log.i(HelperClass.TAG, "observeSendJoinGroup: ");
        // [cancel] dialog of user join
        CustomProgressDialog.getInstance(this).cancel();
        Toast.makeText(this, generalResponse.response, Toast.LENGTH_SHORT).show();
        pendingButton();

    }

    private void pendingButton() {
        actionToGroup.setEnabled(false);
        actionToGroup.setBackground(ContextCompat.getDrawable(this,
                R.drawable.corners_from_all_without_stroke_grey));
        actionToGroup.setText(PENDING);
    }


}
