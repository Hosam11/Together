package com.example.together.group_screens;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.together.R;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.storage.Storage;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.GroupViewModel;

import java.util.Objects;

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


        findViewById(R.id.btn_action_group).setOnClickListener(v -> joinGroup());

        // FixMe i need group id here
        // dummy data just for test
        // Hint only groupID and adminID that will be real to test
        String gpName = "Android fro begginers";
        String desc = "android desc";
        String gpInterest = "java";
        String gpDuration = "3 weeks";
        String loation = "Egypt"; // may be null
        String gpLevel = "Begginer";


        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        Storage storage = new Storage(this);
        Log.i(HelperClass.TAG, "ViewGroup -- onCreate: btnIsEnable >> " + actionToGroup.isEnabled());


        actionToGroup.setVisibility(View.GONE);

        groupViewModel.userRequestJoinStatus(10, storage.getId(), storage.getToken())
                .observe(this, this::observeJoinStatus);

    }

    private void observeJoinStatus(GeneralResponse generalRes) {
        actionToGroup.setVisibility(View.VISIBLE);
        if (generalRes.response.equals(HelperClass.USER_NOT_MEMBER)) {
            Log.i(HelperClass.TAG, "observeJoinStatus: user not member");

        } else if (generalRes.response.equals(HelperClass.USER_WATING_JOIN_GROUP)) {
            Log.i(HelperClass.TAG, "observeJoinStatus: user waitting to response");
            actionToGroup.setEnabled(false);
        } else if (generalRes.response.equals(HelperClass.USER_IN_GROUP)) {
            Log.i(HelperClass.TAG, "observeJoinStatus: user in group");
            // TODO go to the group [ViewPagerGroup]
        } else {
            showAlert("Error!", generalRes.response, this);
        }
    }


    private void joinGroup() {

    }


}
