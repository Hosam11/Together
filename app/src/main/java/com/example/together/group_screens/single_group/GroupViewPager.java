package com.example.together.group_screens.single_group;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.together.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;


public class GroupViewPager extends AppCompatActivity {

    ViewPager2 viewPager2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_group);

        Objects.requireNonNull(getSupportActionBar()).hide();

        viewPager2 = findViewById(R.id.view_pager);
        viewPager2.setAdapter(new GroupPagerAdapter(this, this));

        TabLayout tabLayout = findViewById(R.id.tab_layout);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0: {
                    tab.setText("Chat");
                    break;
                }
                case 1: {
                    tab.setText("About");
                    break;
                }

            }
        }
        );

        tabLayoutMediator.attach();


    }
}
