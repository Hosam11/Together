package com.example.together.utils;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.together.R;
import com.example.together.data.model.JoinGroupResponse;
import com.example.together.data.storage.Storage;
import com.example.together.view_model.GroupViewModel;
import com.example.together.view_model.UserViewModel;

import static com.example.together.utils.HelperClass.TAG;

public class TestApis extends AppCompatActivity {

    EditText gpId;
    EditText etUserId;
    EditText etGroupId;
    EditText reqID;
    GroupViewModel groupViewModel;
    int etId;

    Storage storage;
    String token;
    int curUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_apis);

        gpId = findViewById(R.id.test_gp_id);
        etUserId = findViewById(R.id.test_user_id);
        etGroupId = findViewById(R.id.test_group_id);
        reqID = findViewById(R.id.test_req_id);

        findViewById(R.id.test_send_btn).setOnClickListener(v -> requestJoinGroup());
        findViewById(R.id.test_show_requests_btn).setOnClickListener(v -> showAllRequestsForGroup());
        findViewById(R.id.test_add_member).setOnClickListener(v -> addGroupMember());

        findViewById(R.id.test_rej_join).setOnClickListener(v -> rejectReqJoinGroup());
        findViewById(R.id.test_acc_join).setOnClickListener(v -> acceptReqJoinGroup());

        storage = new Storage(this);
        token = storage.getToken();
        curUserID = storage.getId();

        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
    }

    private void acceptReqJoinGroup() {
        int reqID = Integer.parseInt(this.reqID.getText().toString().trim());

        groupViewModel.acceptJoinReqGroup(reqID, curUserID, token).observe(this, acceptRes -> {
            Log.i(TAG, "rejectReqJoinGrop: " + acceptRes);
        });
    }

    private void rejectReqJoinGroup() {
        int reqID = Integer.parseInt(this.reqID.getText().toString().trim());
        groupViewModel.rejectJoinReqGroup(reqID, token).observe(this, rejecRes -> {
            Log.i(TAG, "acceptReqJoinGrop: " + rejecRes);
        });


    }

    private void addGroupMember() {
        int gpID = Integer.parseInt(etGroupId.getText().toString());
        int userID = Integer.parseInt(etUserId.getText().toString());

        groupViewModel.addGroupMember(gpID, userID, curUserID, token).observe(this,
                addedRes -> {
                    Log.i(TAG, this.getLocalClassName() + " -- addGroupMember: " + addedRes.response);
                });
    }

    private void showAllRequestsForGroup() {
        etId = Integer.parseInt(gpId.getText().toString().trim());
        groupViewModel.getAllRequestJoinForGroup(etId, token).observe(this, requestsJoinList -> {
            for (JoinGroupResponse req : requestsJoinList) {
                Log.i(TAG, this.getLocalClassName() + " -- getAllResponsesForGroup() enqueue() a req >> " + req);
            }
        });
    }

    private void requestJoinGroup() {
        etId = Integer.parseInt(gpId.getText().toString().trim());
        groupViewModel.requestJoinGroup(etId, curUserID, token).observe(this, genRes -> {
            Toast.makeText(this, genRes.response, Toast.LENGTH_SHORT).show();
            Log.i(TAG, this.getLocalClassName() + " -- requestJoinGroup: " + genRes.response);
        });


    }


}
