package com.example.together.group_screens;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.together.R;
import com.example.together.Utils.CommonSpinner;

import java.util.ArrayList;
import java.util.List;

import static com.example.together.Utils.HelperClass.TAG;

public class AddGroup extends AppCompatActivity {

    CommonSpinner interestSpinner;
    CommonSpinner locationSpinner;
    CommonSpinner levelsSpinner;

    // Spinners Objects
    private Spinner spInterests;
    private Spinner spLocations;
    private Spinner spLevels;

    // List Objects
    private List<String> interests;
    private List<String> locations;

    private final List<String> levels = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        spInterests = findViewById(R.id.sp_interest);
        spLocations = findViewById(R.id.sp_locations);
        spLevels = findViewById(R.id.sp_required_Level);

        interests = new ArrayList<>();
        locations = new ArrayList<>();

        interests.add("android");
        interests.add("ios");
        interests.add("web desing");
        interests.add("php");
        interests.add("react native");


        locations.add("egypt");
        locations.add("german");
        locations.add("spain");

        levels.add("beginner");
        levels.add("intermediate");
        levels.add("expert");

        interestSpinner = new CommonSpinner(spInterests, this, interests);
        locationSpinner = new CommonSpinner(spLocations, this, locations);
        levelsSpinner = new CommonSpinner(spLevels, this, levels);


    }

    public void chooseImage(View view) {
        Log.i(TAG, "chooseImage: ");
        Log.i(TAG, "onCreate: interset item selected >> " + interestSpinner.getSpItemSelected());
        Log.i(TAG, "onCreate: location item selected >> " + locationSpinner.getSpItemSelected());
        Log.i(TAG, "onCreate: location item selected >> " + levelsSpinner.getSpItemSelected());
    }


}
