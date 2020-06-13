package com.example.together;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.together.NavigationFragments.ExploreFragment;
import com.example.together.NavigationFragments.HomeFragment;
import com.example.together.NavigationFragments.NotificationFragment;
import com.example.together.NavigationFragments.ProfileFragment;
import com.example.together.group_screens.single_group.JoinRequestsFragment;

public class BottomNavigationView extends AppCompatActivity {

    String selectedFragmentTag;

    private com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                     Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                            selectedFragmentTag="Home";
                            break;
                        case R.id.nav_explore:
                            selectedFragment = new ExploreFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                            selectedFragmentTag="Explore";
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                            selectedFragmentTag="Profile";
                            break;
                        case R.id.nav_notification:
                            selectedFragment = new NotificationFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                            selectedFragmentTag="Notification";
                            break;
                    }
                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_view);
        getSupportActionBar().hide();
        if(savedInstanceState!=null){
            selectedFragmentTag =savedInstanceState.getString("fragment");
        }
        else {
            selectedFragmentTag="Home";
        }
        com.google.android.material.bottomnavigation.BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        switch (selectedFragmentTag){
            case "Home":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case "Explore":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ExploreFragment()).commit();
                break;
            case "Profile":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;
            case "Notification":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NotificationFragment()).commit();
                break;
            default:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
        }

    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("fragment",selectedFragmentTag);
        Log.d("myTag", "onSaveInstanceState(FirstFragment)");

        super.onSaveInstanceState(outState);
    }
}
