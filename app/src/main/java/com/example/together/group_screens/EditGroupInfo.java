package com.example.together.group_screens;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.together.R;
import com.example.together.data.model.Group;
import com.example.together.data.storage.Storage;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.UserViewModel;

public class EditGroupInfo extends AppCompatActivity {

    Group group;
    EditText gpName;
    EditText gpDesc;
    Button submitBtn;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        gpName = findViewById(R.id.et_edit_group_name);
        gpDesc = findViewById(R.id.et_edit_group_desc);
        submitBtn = findViewById(R.id.btn_edit_group);

        group = new Group(2, "loc", 5,
                "android", "fro begginer",
                3, "free", "begginer", "java");

        gpName.setText(group.getGroupName());
        gpDesc.setText(group.getGroupDesc());

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        submitBtn.setOnClickListener(v -> editGroupInfo());

    }

    private void editGroupInfo() {
        Storage storage = new Storage(this);
        String groupName = gpName.getText().toString();
        String groupDesc = gpDesc.getText().toString();
        group.setGroupName(groupName);
        group.setGroupDesc(groupDesc);
        userViewModel.updateGroupInfo(1, group.getAdminID(), group, storage.getToken())
                .observe(this , resEditGroup ->{
                    Log.i(HelperClass.TAG, "editGroupInfo: " + resEditGroup);
                });
    }
}
