package com.example.together.group_screens;

import android.os.Bundle;
import android.util.Log;
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
import com.example.together.data.model.Group;
import com.example.together.data.storage.Storage;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.GroupViewModel;

import java.util.Objects;

import static com.example.together.utils.HelperClass.GROUP_FULL;
import static com.example.together.utils.HelperClass.PENDING;

public class ViewGroupInfo extends AppCompatActivity {


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
    Storage userStorage;
    Storage commonStorage;
    Group savedGroup;
    // TODO 10 is fixed for now
    int groupID = 2;

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

        commonStorage = new Storage();


        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);

        userStorage = new Storage(this);
        Log.i(HelperClass.TAG, "ViewGroup -- onCreate: btnIsEnable >> " + actionToGroup.isEnabled());

        savedGroup = commonStorage.getGroup(this);

        // see if user wait or group full
        checkUserStatusGroup(savedGroup.getUserGroupStatus());

        actionToGroup.setOnClickListener(v -> {
            // [show] dialoge of user join
            CustomProgressDialog.getInstance(this).show();
            if (HelperClass.checkInternetState(this)) {
                groupViewModel.requestJoinGroup(groupID, userStorage.getId(), userStorage.getToken())
                        .observe(this, this::observeSendJoinGroup);
            } else {
                CustomProgressDialog.getInstance(this).cancel();
                HelperClass.showAlert("Error", HelperClass.checkYourCon, this);
            }

        });

    }

    private void checkUserStatusGroup(String userGroupStatus) {
        if (userGroupStatus.equals(PENDING)) {
            Log.i(HelperClass.TAG, "ViewGroup -- observeJoinStatus: user waitting to response");
            disabledButton(PENDING);
        } else if (userGroupStatus.equals(GROUP_FULL)) {
            disabledButton("Sorry Group Is Full..");
        } else {
            Log.i(HelperClass.TAG, "checkUserStatusGroup: userGroupStatus >> else statment"
                    + userGroupStatus);
        }
    }


    private void observeSendJoinGroup(GeneralResponse generalRes) {
        Log.i(HelperClass.TAG, "observeSendJoinGroup: ");
        // [cancel] dialog of user join
        CustomProgressDialog.getInstance(this).cancel();
        Toast.makeText(this, generalRes.response, Toast.LENGTH_SHORT).show();
        disabledButton(PENDING);
    }

    private void disabledButton(String msg) {
        actionToGroup.setEnabled(false);
        actionToGroup.setBackground(ContextCompat.getDrawable(this,
                R.drawable.corners_from_all_without_stroke_grey));
        actionToGroup.setText(msg);
    }


}
