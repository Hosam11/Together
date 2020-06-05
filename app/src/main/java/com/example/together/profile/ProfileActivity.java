package com.example.together.profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.together.R;
import com.example.together.data.model.User;
import com.example.together.data.storage.Storage;
import com.example.together.group_screens.AddGroup;
import com.example.together.view_model.UserViewModel;
import com.example.together.view_model.UsersViewModel;

import static com.example.together.utils.HelperClass.TAG;

public class ProfileActivity extends AppCompatActivity {
    TextView interestTv;
    String[] interests;

    UsersViewModel usersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().hide();
        interestTv = findViewById(R.id.interest_tv);
        String intereststr = "aaa-AAA-xAaax-abc-hkgjghaaA-jhkjhk-xxx";
        interests = intereststr.split("-");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.interests.length; i++) {

            builder.append("√ " + this.interests[i] + "<br/>");

        }
        intereststr = builder.toString();
        interestTv.setText(Html.fromHtml(intereststr));

        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        findViewById(R.id.profile_image).setOnClickListener(v -> displayToken());

        setProfileDataObservable();
    }

    private void setProfileDataObservable() {
        Storage storage = new Storage(this);
        Log.i(TAG, "setProfileDataObservable: storage.getId()" + storage.getId());
        usersViewModel.fetchUserData(storage.getId(), storage.getToken()).observe(this,
                userData -> {
                    Log.i(TAG, "setProfileDataObservable: userData >>  " + userData);
                    for (String interest : userData.getInterests()) {
                        Log.i(TAG, "setProfileDataObservable: #Interest# " + interest + "\n");
                    }

                    if (!userData.getGroups().isEmpty()) {
                        for (User.GroupReturned group : userData.getGroups()) {
                            Log.i(TAG, "setProfileDataObservable: #Groups# name: " +
                                    group.getName() +
                                    " -- id: " + group.getId() + "\n");
                        }
                    } else {
                        Log.i(TAG, "setProfileDataObservable: Groups is null");
                    }

                });


    }

    private void displayToken() {
        Storage storage = new Storage(this);
        Log.i(TAG, "joinGroup: token tokenPref>> " + storage.getToken() +
                "\nidPref: " + storage.getId());
        Intent intent = new Intent(this, AddGroup.class);
        startActivity(intent);
    }


}
