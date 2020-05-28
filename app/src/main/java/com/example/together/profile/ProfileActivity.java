package com.example.together.profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.together.R;
import com.example.together.data.storage.Storage;
import com.example.together.group_screens.AddGroup;
import com.example.together.view_model.UserViewModel;

import static com.example.together.utils.HelperClass.TAG;

public class ProfileActivity extends AppCompatActivity {
    TextView interestTv;
    String[] interests;

    UserViewModel userViewModel;

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

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        findViewById(R.id.profile_image).setOnClickListener(v -> displayToken());


        setProfileDataObservable();

    }

    private void setProfileDataObservable() {
        Storage storage = new Storage(this);
        userViewModel.fetchUserData(storage.getId()).observe(this, userData -> {
            Log.i(TAG, "setProfileDataObservable: userData >>  " + userData);
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
