package com.example.together.group_screens.single_group;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.together.group_screens.single_group.chat.ChatFragment;

public class GroupPagerAdapter extends FragmentStateAdapter {

    Context context;
    boolean isAdmin;

    GroupPagerAdapter(@NonNull FragmentActivity fragment, Context context,
                      boolean isAdmin) {
        super(fragment);
        this.context = context;
        this.isAdmin = isAdmin;

    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (isAdmin) {
            switch (position) {
                case 0:
                    return new ChatFragment();
                case 1:
                    return new JoinRequestsFragment();
                default:
                    return new AboutGroupFragment();
            }
        } else {
            switch (position) {
                case 0:
                    return new ChatFragment();
                default:
                    return new AboutGroupFragment();
            }
        }

    }


    @Override
    public int getItemCount() {
        if (isAdmin) {
            return 3;
        }
        return 2;
    }
}

