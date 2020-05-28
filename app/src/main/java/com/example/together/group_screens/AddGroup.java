package com.example.together.group_screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.together.R;
import com.example.together.data.model.FixedDBValues;
import com.example.together.data.model.GeneralResponse;
import com.example.together.data.model.Group;
import com.example.together.data.storage.Storage;
import com.example.together.group_screens.single_group.GroupViewPager;
import com.example.together.utils.CommonSpinner;
import com.example.together.utils.HelperClass;
import com.example.together.view_model.UserViewModel;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.ArrayList;
import java.util.List;

import static com.example.together.utils.HelperClass.TAG;
import static com.example.together.utils.HelperClass.showAlert;

public class AddGroup extends AppCompatActivity {


    private final List<String> levels = new ArrayList<>();
    //
    CommonSpinner interestSpinner;
    CommonSpinner locationSpinner;
    CommonSpinner levelsSpinner;
    // Edit Texts
    EditText edGroupName;
    EditText edGroupDesc;
    TextView edGroupMaxMembers;
    TextView edGroupDuration;
    EditText etHiddenOther;

    UserViewModel userViewModel;
    // Spinners Objects
    private BetterSpinner spInterests;
    private BetterSpinner spLocations;
    private BetterSpinner spLevels;
    // List Objects
    private List<String> interests;
    private List<String> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_group);
        setContentView(R.layout.activity_create_group2);

        getSupportActionBar().hide();

        spInterests = findViewById(R.id.sp_interest);
        spLocations = findViewById(R.id.sp_locations);
        spLevels = findViewById(R.id.sp_required_Level);

        // EditTexts
        edGroupName = findViewById(R.id.et_group_name);
        edGroupDesc = findViewById(R.id.ed_group_desc);

        edGroupMaxMembers = findViewById(R.id.tv_member_number);
        edGroupDuration = findViewById(R.id.tv_duration_week);

        etHiddenOther = findViewById(R.id.ed_other_interest);


        FixedDBValues dbValues = new FixedDBValues();

        interests = new ArrayList<>(dbValues.getInterests().values());
        locations = new ArrayList<>();

        /*interests.add("android");
        interests.add("ios");
        interests.add("web desing");
        interests.add("php");
        interests.add("react native");
        interests.add("other");
        */


        locations.add("egypt");
        locations.add("german");
        locations.add("spain");

        levels.add("beginner");
        levels.add("intermediate");
        levels.add("expert");


        interestSpinner = new CommonSpinner(spInterests, this, interests);
        interestSpinner.setEdOther(etHiddenOther);
        locationSpinner = new CommonSpinner(spLocations, this, locations);
        levelsSpinner = new CommonSpinner(spLevels, this, levels);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        findViewById(R.id.btn_create_group).setOnClickListener( v -> createGroup() );

    }

    public void chooseImage(View view) {
        Log.i(TAG, "chooseImage: ");
        Log.i(TAG, "onCreate: interest item selected >> " + interestSpinner.getSpItemSelected());
        Log.i(TAG, "onCreate: location item selected >> " + locationSpinner.getSpItemSelected());
        Log.i(TAG, "onCreate: level item selected >> " + levelsSpinner.getSpItemSelected());
        Intent intent = new Intent(this, ViewGroup.class);
        startActivity(intent);

    }


    public void createGroup() {
        String gpName = edGroupName.getText().toString();
        String gpDesc = edGroupDesc.getText().toString();
        // TODO will get it from maps that return it form db table contain interests
//        String gpInterest = interestSpinner.getSpItemSelected();
        String gpLevel = levelsSpinner.getSpItemSelected();
        int gpMembers = Integer.parseInt(edGroupMaxMembers.getText().toString());
        int gpDurtaion = Integer.parseInt(edGroupDuration.getText().toString());
        String gpLocation = locationSpinner.getSpItemSelected();

        Storage storage = new Storage(this);
//        FixedDBValues dbValues = new FixedDBValues();

        Group group = new Group(
                storage.getId(), gpLocation,
                gpMembers, gpDurtaion, gpName,
                gpDesc, HelperClass.FREE, gpLevel, 1);

        userViewModel.createGroup(group).observe(this, this::observCreateGroup);

    }

    private void observCreateGroup(GeneralResponse generalRes) {
        Log.i(TAG, "observCreateGroup: generalRes.res" + generalRes.response);
        if (generalRes.response.trim().equals(HelperClass.CREATE_GROUP_SUCCESS)) {

            Log.i(TAG, "AddGroup -- observCreateGroup: from if Statment");

            // 1- Go To Group Screens
//            userViewModel.clearCreateGroupRes();
            Toast.makeText(this, generalRes.response, Toast.LENGTH_SHORT).show();
            Intent goToSingleGroup = new Intent(this, GroupViewPager.class);
            startActivity(goToSingleGroup);

        } else {
            Log.i(TAG, "AddGroup -- observCreateGroup: from else statmet");
            showAlert(generalRes.response, this);
        }
    }


}
