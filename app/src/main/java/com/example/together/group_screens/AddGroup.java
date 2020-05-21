package com.example.together.group_screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.together.R;
import com.example.together.group_screens.single_group.GroupPagerAdapter;
import com.example.together.group_screens.single_group.GroupViewPager;
import com.example.together.utils.CommonSpinner;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.ArrayList;
import java.util.List;

import static com.example.together.utils.HelperClass.TAG;

public class AddGroup extends AppCompatActivity {


    private final List<String> levels = new ArrayList<>();
    //
    CommonSpinner interestSpinner;
    CommonSpinner locationSpinner;
    CommonSpinner levelsSpinner;
    EditText etHiddenOther;
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
        setContentView(R.layout.activity_add_group);

        spInterests = findViewById(R.id.sp_interest);
        spLocations = findViewById(R.id.sp_locations);
        spLevels = findViewById(R.id.sp_required_Level);

        etHiddenOther = findViewById(R.id.ed_other_interest);

        interests = new ArrayList<>();
        locations = new ArrayList<>();

        interests.add("android");
        interests.add("ios");
        interests.add("web desing");
        interests.add("php");
        interests.add("react native");
        interests.add("other");


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


    }

    public void chooseImage(View view) {
        Log.i(TAG, "chooseImage: ");
        Log.i(TAG, "onCreate: interest item selected >> " + interestSpinner.getSpItemSelected());
        Log.i(TAG, "onCreate: location item selected >> " + locationSpinner.getSpItemSelected());
        Log.i(TAG, "onCreate: level item selected >> " + levelsSpinner.getSpItemSelected());
        Intent intent = new Intent(this, ViewGroup.class);
        startActivity(intent);

    }


    public void creatGroup(View view) {
        Intent intent = new Intent(this, GroupViewPager.class);
        startActivity(intent);
    }
}
