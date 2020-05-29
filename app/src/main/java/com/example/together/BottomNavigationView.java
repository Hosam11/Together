package com.example.together;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.together.NavigationFragments.ExploreFragment;
import com.example.together.NavigationFragments.HomeFragment;
import com.example.together.NavigationFragments.NotificationFragment;
import com.example.together.NavigationFragments.ProfileFragment;
import com.example.together.ToDoListPachage.ToDoListMain;

public class BottomNavigationView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_view);
        getSupportActionBar().hide();
        com.google.android.material.bottomnavigation.BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

    }

    private com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                            break;
                        case R.id.nav_explore:
                            selectedFragment = new ExploreFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                            break;
                        case R.id.nav_notification:
                            selectedFragment = new NotificationFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                            break;
                    }
                    return true;
                }
            };

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

}
