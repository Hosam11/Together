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
import com.example.together.view_model.UserViewModel;

import static com.example.together.utils.HelperClass.TAG;

public class TestApis extends AppCompatActivity {

    EditText gpId;
    EditText etUserId;
    EditText etGroupId;


    UserViewModel userViewModel;
    int etId;

    Storage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_apis);
        storage = new Storage(this);

        gpId = findViewById(R.id.test_gp_id);
        etUserId = findViewById(R.id.test_user_id);
        etGroupId = findViewById(R.id.test_group_id);

        findViewById(R.id.test_send_btn).setOnClickListener(v -> requestJoinGroup());
        findViewById(R.id.test_show_requests_btn).setOnClickListener(v -> showAllRequestsForGroup());
        findViewById(R.id.test_add_member).setOnClickListener(v -> addGroupMember());

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    private void addGroupMember() {

        userViewModel.addGroupMember(Integer.parseInt(etGroupId.getText().toString()),
                Integer.parseInt(etUserId.getText().toString()) , storage.getId());
    }

    private void showAllRequestsForGroup() {
        etId = Integer.parseInt(gpId.getText().toString().trim());
        userViewModel.getAllRequestJoinForGroup(etId).observe(this, requestsJoinList -> {
            for (JoinGroupResponse req : requestsJoinList) {
                Log.i(TAG, this.getLocalClassName() + " -- getAllResponsesForGroup() enqueue() a req >> " + req);
            }
        });
    }

    private void requestJoinGroup() {
        etId = Integer.parseInt(gpId.getText().toString().trim());
        userViewModel.requestJoinGroup(etId, storage.getId()).observe(this, genRes -> {
            Toast.makeText(this, genRes.response, Toast.LENGTH_SHORT).show();
            Log.i(TAG, this.getLocalClassName() + " -- requestJoinGroup: " + genRes.response);
        });


    }


}
