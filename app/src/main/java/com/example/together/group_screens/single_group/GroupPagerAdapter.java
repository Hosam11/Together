package com.example.together.group_screens.single_group;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.together.group_screens.single_group.chat.ChatFragment;

public class GroupPagerAdapter extends FragmentStateAdapter {

    public GroupPagerAdapter(@NonNull FragmentActivity fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 1:
                return new ChatFragment();
            case 2:
                return new AboutGroupFragment();
            default:
                return new ToDoListFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

